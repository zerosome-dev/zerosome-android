package com.zerosome.onboarding

//@Singleton
//class SignUpUseCase @Inject constructor(
//    private val userRepository: UserRepository,
//    private val tokenRepository: TokenRepository
//) {
//    operator fun invoke(socialToken: String, socialType: String, nickname: String, marketingAgreed: Boolean): Flow<NetworkResult<Boolean>> = userRepository.signUp(socialToken = socialToken, socialType = socialType, nickname, marketingAgreed).map {
//        when (it) {
//            is NetworkResult.Loading -> NetworkResult.Loading
//            is NetworkResult.Success -> {
//                tokenRepository.updateToken(it.data.accessToken, it.data.refreshToken)
//                NetworkResult.Success(true)
//            }
//            is NetworkResult.Error -> NetworkResult.Error(it.error)
//        }
//    }
//}