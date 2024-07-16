package bet.notification.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
public class ResourceNotFoundException extends RuntimeException {

    private HttpStatus status = HttpStatus.NOT_FOUND;


    public ResourceNotFoundException(String message){
        super(message);
    }

    public ResourceNotFoundException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }

    public ResourceNotFoundException(){
        super();
    }
}
