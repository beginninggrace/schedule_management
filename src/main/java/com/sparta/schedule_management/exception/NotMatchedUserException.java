package com.sparta.schedule_management.exception;

public class NotMatchedUserException extends RuntimeException{
    public NotMatchedUserException() {super("해당하는 유저가 아닙니다.");}

}
