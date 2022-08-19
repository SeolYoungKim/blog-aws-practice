package toyproject.blogawspractice.exception;

public class NoAuthorityException extends BlogException{
    private static final String MESSAGE = "수정 또는 삭제 권한이 없습니다.";

    public NoAuthorityException() {
        super(MESSAGE);
    }

    public NoAuthorityException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
