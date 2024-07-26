package com.zerosome.onboarding

//@Singleton
//class LoginUseCase @Inject constructor(
//    private val userRepository: UserRepository,
//    private val tokenRepository: TokenRepository
//){
//    operator fun invoke(socialType: String, socialToken: String): Flow<NetworkResult<Boolean>> = userRepository.login(socialToken = socialToken, socialType = socialType).map {
//        when (it) {
//            is NetworkResult.Loading -> NetworkResult.Loading
//            is NetworkResult.Success -> {
//                if (it.data.isMember) {
//                    tokenRepository.updateToken(it.data.token?.accessToken, it.data.token?.refreshToken)
//                }
//                NetworkResult.Success(it.data.isMember)
//            }
//            is NetworkResult.Error -> NetworkResult.Error(it.error)
//        }
//    }
//}