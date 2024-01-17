package com.remcoil.skiron.model.response;

import java.sql.Timestamp;

public record ExceptionDetails(
        Timestamp timestamp,
        int status,
        String message,
        String path
) {
    public ExceptionDetails(int status, String message, String path) {
        this(new Timestamp(System.currentTimeMillis()), status, message, path);
    }
}
