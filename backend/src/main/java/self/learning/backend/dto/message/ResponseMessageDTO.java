package self.learning.backend.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseMessageDTO {
    private LocalDateTime timestamp;
    private String message;

    public ResponseMessageDTO(String message){
        this.timestamp = LocalDateTime.now();
        this.message = message;
    }
}
