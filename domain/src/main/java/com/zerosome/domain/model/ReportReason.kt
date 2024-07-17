package com.zerosome.domain.model

enum class ReportReason(val reasonName: String) {
    FAKE_INFORMATION("거짓 정보 및 허위사실 유포"),
    BAD_WORDS("욕설/인신공격"),
    TOO_MUCH_REVIEWS("도배, 홍보/광고 행위"),
    VIOLENT_WORDS("살인/폭력/성적 위협 발언"),
    REPEAT_REVIEWS("같은 내용 반복 게시"),
    PRIVACY("개인정보 노출"),
    ETC("기타")
}