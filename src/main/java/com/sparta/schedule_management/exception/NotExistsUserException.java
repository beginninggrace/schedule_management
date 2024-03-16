package com.sparta.schedule_management.exception;

public class NotExistsUserException extends RuntimeException {

    public NotExistsUserException() {
        super("유저를 존재하지 않습니다.");
    }

}
