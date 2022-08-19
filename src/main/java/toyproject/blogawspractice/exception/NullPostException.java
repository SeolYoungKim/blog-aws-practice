package toyproject.blogawspractice.exception;

public class NullPostException extends BlogException {

    private static final String MESSAGE = "조회할 글이 없습니다.";

    public NullPostException() {
        super(MESSAGE);
    }

    public NullPostException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
