package toyproject.blogawspractice.exception;

public class NullPostException extends Exception {
    public NullPostException(String message) {
        super(message);
    }

    public NullPostException(String message, Throwable cause) {
        super(message, cause);
    }
}
