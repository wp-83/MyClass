package self.learning.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ApiException {
    private final LocalDateTime timestamp;
    private final Integer status;
    private final String error;
    private final String message;
}
