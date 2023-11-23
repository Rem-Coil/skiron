package com.remcoil.skiron.exception;

public class LockedProductException extends RuntimeException {
    public LockedProductException(String message) {
        super(message);
    }
}
