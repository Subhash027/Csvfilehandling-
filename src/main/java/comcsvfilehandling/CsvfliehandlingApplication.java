package comcsvfilehandling;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(
        name = "Movies", // can be set to anything
        type = SecuritySchemeType.HTTP,
        scheme = "basic",
		in = SecuritySchemeIn.HEADER
)
public class CsvfliehandlingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsvfliehandlingApplication.class, args);
	}

}
