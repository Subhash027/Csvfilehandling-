package comcsvfilehandling.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

public class ActorNotFoundException extends RuntimeException{

    public ActorNotFoundException(String actor_not_found_in_moviecollection) {

    }

}
