package comcsvfilehandling.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

public class YearNotFoundException extends RuntimeException{

    private static final long serialVersionUID=1L;


    public YearNotFoundException(String message) {
        super(message);
    }



}
