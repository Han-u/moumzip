package com.ssafy.web.global.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // common
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C000", "요청 처리 중 오류가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "C001", "잘못된 요청입니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "C002", "잘못된 요청 데이터 입니다."),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "C003", "데이터 타입이 올바르지 않습니다"),

    // auth
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "A001", "인증 토큰이 올바르지 않습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "A002", "권한이 없습니다."),
    OAUTH2_PROVIDER_NOT_FOUND(HttpStatus.BAD_REQUEST, "OA001", "해당 Provider는 지원되지 않습니다."),
    OAUTH2_AUTHENTICATION_FAILED(HttpStatus.BAD_REQUEST, "OA002", "OAUTH2 사용자 정보를 가져오는 중 오류가 발생했습니다."),

    // user
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "유저를 찾을 수 없습니다."),
    USER_EMAIL_DUPLICATE(HttpStatus.CONFLICT, "U002", "사용중인 이메일 입니다."),
    USER_NICKNAME_DUPLICATE(HttpStatus.CONFLICT, "U003", "사용중인 닉네임 입니다."),
    USER_CANNOT_DELETE(HttpStatus.NOT_ACCEPTABLE, "U004", "유저를 삭제할 수 없습니다."),
    ADMIN_CANNOT_DELETE(HttpStatus.NOT_ACCEPTABLE, "U005", "관리자는 삭제할 수 없습니다."),
    ACCOUNT_NOT_REGISTERED(HttpStatus.BAD_REQUEST, "U006", "계좌가 등록되지 않았습니다."),

    // auction
    AUCTION_NOT_IN_PROGRESS(HttpStatus.BAD_REQUEST, "AC001", "진행중인 경매가 아닙니다."),
    AUCTION_NOT_QUALIFIED(HttpStatus.BAD_REQUEST, "AC002", "참여 자격이 없습니다."),
    AUCTION_ALREADY_PARTICIPATED(HttpStatus.BAD_REQUEST, "AC003", "이미 참여한 경매입니다"),
    AUCTION_NOT_FOUND(HttpStatus.NOT_FOUND, "AC004", "경매를 찾을 수 없습니다."),
    AUCTION_CANNOT_DELETE(HttpStatus.NOT_ACCEPTABLE, "AC005", "경매를 삭제할 수 없습니다. 유효한 보증금이 있습니다."),
    AUCTION_INVALID_ID(HttpStatus.NOT_FOUND, "AC006", "유효하지 않은 Auction Id 입니다." ),
    AUCTION_INVALID_TIME(HttpStatus.NOT_ACCEPTABLE, "AC007", "유효하지 않은 날짜입니다."),
    AUCTION_CANNOT_UPDATE(HttpStatus.NOT_ACCEPTABLE,"AC008", "경매를 수정할 수 없습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;
}
