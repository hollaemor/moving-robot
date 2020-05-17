package org.hollaemor.movingrobot.exception;

public class InvalidMoveException  extends RuntimeException{

    public InvalidMoveException() {
        super();
    }

    public InvalidMoveException(String message) {
        super(message);
    }
}
