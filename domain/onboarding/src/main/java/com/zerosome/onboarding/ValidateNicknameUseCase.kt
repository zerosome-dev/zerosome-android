package com.zerosome.onboarding

//
//class ValidateNicknameUseCase @Inject constructor(
//    private val repository: UserRepository
//) {
//
//    operator fun invoke(nickname: String): Flow<NetworkResult<ValidateReason>> = flow {
//        Log.d("CPRI", "VALIDATE STARTED")
//        emit(NetworkResult.Loading)
//
//        kotlinx.coroutines.delay(300)
//        if (nickname.length in 2..12) {
//            repository.validateNickname(nickname).onEach {
//                when (it) {
//                    NetworkResult.Loading -> emit(NetworkResult.Loading)
//                    is NetworkResult.Success -> emit(NetworkResult.Success(if (it.data) ValidateReason.SUCCESS else ValidateReason.NOT_VERIFIED))
//                    is NetworkResult.Error -> emit(NetworkResult.Error(it.error))
//                }
//            }.collect()
//        } else {
//            emit(NetworkResult.Success(ValidateReason.NOT_VALIDATED))
//        }
//    }
//}

enum class ValidateReason {
    NOT_VALIDATED, SUCCESS, NOT_VERIFIED,
}