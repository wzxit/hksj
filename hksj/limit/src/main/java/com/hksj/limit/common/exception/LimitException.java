package com.hksj.limit.common.exception;

public class LimitException extends RuntimeException {
    private static final long serialVersionUID = 7895884193269203187L;

    public LimitException() {
        super();
    }

    public LimitException(String message) {
        super(message);
    }

    public LimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public LimitException(Throwable cause) {
        super(cause);
    }
}
