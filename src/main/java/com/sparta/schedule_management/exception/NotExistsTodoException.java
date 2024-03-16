package com.sparta.schedule_management.exception;

public class NotExistsTodoException extends RuntimeException {

    public NotExistsTodoException() {
        super("해당 투두가 존재하지 않습니다");
    }

}
