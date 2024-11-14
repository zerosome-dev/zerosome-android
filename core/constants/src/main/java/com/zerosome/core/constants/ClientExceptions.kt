package com.zerosome.core.constants

class ClientExceptions(val clientError: ClientError): Exception()

enum class ClientError(val message: String) {
    LOGIN_NOT_VALIDATED_EXCEPTION("로그인이 되지 않았습니다."),
    RESPONSE_NOT_VALIDATE_EXCEPTION("서버에서 정상적인 데이터가 내려오지 않았습니다."),
    AUTH_NOT_VALIDATED("로그인이 만료되었습니다. 로그인 페이지로 이동합니다."),
    NETWORK_UNVALIDATED_INVALID("예상치 못한 에러가 발생했습니다");
}