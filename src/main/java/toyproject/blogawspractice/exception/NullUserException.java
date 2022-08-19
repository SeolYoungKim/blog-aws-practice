package toyproject.blogawspractice.exception;

public class NullUserException extends BlogException{

    private static final String MESSAGE = "조회할 유저가 없습니다.";

    public NullUserException() {
        super(MESSAGE);
    }

    public NullUserException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
