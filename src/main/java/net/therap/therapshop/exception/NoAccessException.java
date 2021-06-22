package net.therap.therapshop.exception;

/**
 * @author aditya.chakma
 * @since 6/21/21
 */
public class NoAccessException extends RuntimeException{

    private static final String message = "No Access for this user!";

    public NoAccessException(String message) {
        super(message);
    }

    public NoAccessException() {
        super(message);
    }
}
