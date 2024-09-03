package com.zerosome.network

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class NetworkResultSerializer<T : Any>(
    tSerializer: KSerializer<T>
) : KSerializer<NetworkResult<T>> {
    @Serializable
    @SerialName("NetworkResult")
    data class ServiceResultSurrogate<T : Any>(
        val type: Type,
        // The annotation is not necessary, but it avoids serializing "data = null"
        // for "Error" results.
        @EncodeDefault(EncodeDefault.Mode.NEVER)
        val data: T? = null,
        @EncodeDefault(EncodeDefault.Mode.NEVER)
        val code: String? = "",
        @EncodeDefault(EncodeDefault.Mode.NEVER)
        val status: Boolean = false,
    ) {
        enum class Type { SUCCESS, ERROR, LOADING }
    }

    private val surrogateSerializer = ServiceResultSurrogate.serializer(tSerializer)

    override val descriptor: SerialDescriptor = surrogateSerializer.descriptor

    override fun deserialize(decoder: Decoder): NetworkResult<T> {
        val surrogate = surrogateSerializer.deserialize(decoder)
        return when (surrogate.type) {
            ServiceResultSurrogate.Type.SUCCESS ->
                if (surrogate.data != null)
                    NetworkResult.Success(surrogate.data)
                else
                    throw SerializationException("Missing data for successful result")
            ServiceResultSurrogate.Type.ERROR ->
                NetworkResult.Error(NetworkError.from("${surrogate.code}"))
            ServiceResultSurrogate.Type.LOADING -> NetworkResult.Loading
        }
    }

    override fun serialize(encoder: Encoder, value: NetworkResult<T>) {
        val surrogate = when (value) {
            is NetworkResult.Error -> ServiceResultSurrogate(
                type = ServiceResultSurrogate.Type.ERROR,
                code = value.error.errorCode,
                status = false
            )
            is NetworkResult.Success -> ServiceResultSurrogate(
                type = ServiceResultSurrogate.Type.SUCCESS,
                status = true,
                data = value.data
            )
            else -> ServiceResultSurrogate(
                type = ServiceResultSurrogate.Type.LOADING
            )
        }
        surrogateSerializer.serialize(encoder, surrogate)
    }
}