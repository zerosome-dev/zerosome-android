package com.zerosome.core.constants

class ClientExceptions(val clientError: ClientError): Exception()

enum class ClientError(val message: String) {
    LOGIN_NOT_VALIDATED_EXCEPTION("로그인이 되지 않았습니다."),
    RESPONSE_NOT_VALIDATE_EXCEPTION("서버에서 정상적인 데이터가 내려오지 않았습니다."),
    AUTH_NOT_VALIDATED("로그인이 만료되었습니다. 로그인 페이지로 이동합니다."),
    NETWORK_UNVALIDATED_INVALID("예상치 못한 에러가 발생했습니다"),

    /* Token Issue */
    TOKEN_NOT_GRANTED("소셜 로그인 과정 중 에러가 발생했습니다. 다시 시도해주세요."),
    LOGOUT_NOT_FINISHED("로그아웃이 정상적으로 이뤄지지 않았습니다. 다시 시도해주세요."),
    PARAMETER_NOT_AVAILABLE("정상적인 파라미터가 아닙니다. 다시 시도해주세요."),


    /* Category Issue */
    CATEGORY_NOT_AVAILABLE("원하신 카테고리를 찾을 수 없습니다."),

    /* Nickname Issue */
    NICKNAME_DUPLICATED("닉네임이 중복되었습니다. 다른 닉네임을 시도해주세요."),
    NICKNAME_NOT_VALIDATED("사용 불가한 닉네임입니다.")


}