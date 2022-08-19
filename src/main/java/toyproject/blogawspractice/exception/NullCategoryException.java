package toyproject.blogawspractice.exception;

public class NullCategoryException extends BlogException {

    private static final String MESSAGE = "조회할 카테고리가 없습니다.";

    public NullCategoryException() {
        super(MESSAGE);
    }

    public NullCategoryException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
