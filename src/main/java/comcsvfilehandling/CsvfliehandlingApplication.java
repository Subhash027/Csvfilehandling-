package comcsvfilehandling;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@SecurityScheme(
        name = "Movies", // can be set to anything
        type = SecuritySchemeType.HTTP,
        scheme = "Bearer",
		in = SecuritySchemeIn.HEADER
)
//@SecurityScheme(name = "Moviescollection", scheme = "bearer", type = SecuritySchemeType.APIKEY, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class CsvfliehandlingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsvfliehandlingApplication.class, args);
	}
	@Bean
	public static PasswordEncoder passwordEncoder()
	{
		return NoOpPasswordEncoder.getInstance();
	}

}
