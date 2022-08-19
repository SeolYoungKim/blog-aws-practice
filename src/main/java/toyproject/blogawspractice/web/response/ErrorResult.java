package toyproject.blogawspractice.web.response;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ErrorResult {

    private final String code;
    private final String message;
    private final Map<String, String> errors = new HashMap<>();

    @Builder
    public ErrorResult(String code, String message, String field, String filedMessage) {
        this.code = code;
        this.message = message;
        errors.put("field", field);
        errors.put("default_message", filedMessage);
    }
}
