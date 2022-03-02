package comcsvfilehandling.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springBootApi()
    {
        OpenAPI info;
        info = new OpenAPI()
                .info(new Info().
                        title("MOVIESCOLLECTION").version("0.1")
                        .description("KollyWood-->Movie of Collection").
                        license(new License().name("KollywoodMovies").url("https://www.instagram.com/subhash_doff/")).
                        termsOfService("create and get Movies Only "));
        return info;

    }

}
