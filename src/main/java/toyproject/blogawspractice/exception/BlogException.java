package toyproject.blogawspractice.exception;

import lombok.Getter;

@Getter
public abstract class BlogException extends RuntimeException {

    public BlogException(String message) {
        super(message);
    }

    public BlogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

}
