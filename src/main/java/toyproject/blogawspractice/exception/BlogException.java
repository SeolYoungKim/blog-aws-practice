package toyproject.blogawspractice.exception;

import lombok.Getter;

@Getter
public abstract class BlogException extends RuntimeException {

//    private final Map<String, String> validation = new HashMap<>();

    public BlogException(String message) {
        super(message);
    }

    public BlogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

//    public void addValidation(String filedName, String message) {
//        validation.put(filedName, message);
//    }
}
