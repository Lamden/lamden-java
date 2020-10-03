package io.lamden.exception;

public class RequestFailedException extends RuntimeException{

    private final Integer statusCode;
    private final String response;

    public RequestFailedException(String message, int statusCode, String response){
        super(message);
        this.statusCode = statusCode;
        this.response = response;
    }

    public RequestFailedException(String message, Throwable throwable) {
        super(message, throwable);
        this.statusCode = null;
        this.response = null;
    }

    public String getResponse() {
        return response;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
