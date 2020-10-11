package io.lamden.exception;

public class MasternodesNotAvailableException extends RuntimeException{

    public MasternodesNotAvailableException(String message, Throwable e) {
        super(message, e);
    }

    public MasternodesNotAvailableException(String message) {
        super(message);
    }

}
