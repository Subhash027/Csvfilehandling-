package comcsvfilehandling.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

public class GenreNotFoundException extends RuntimeException{
    public GenreNotFoundException(String genre_not_found_in_moviecollection) {

    }

}
