package com.remcoil.skiron.model.response;

import java.sql.Timestamp;

public record ExceptionDetails(
        Timestamp timestamp,
        int status,
        String error,
        String path
) {
    public ExceptionDetails(int status, String error, String path) {
        this(new Timestamp(System.currentTimeMillis()), status, error, path);
    }
}
