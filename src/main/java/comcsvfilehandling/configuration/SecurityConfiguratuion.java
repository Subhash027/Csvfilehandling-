package comcsvfilehandling.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration

@EnableWebSecurity
public class SecurityConfiguratuion extends WebSecurityConfigurerAdapter {

    private static final String[] allowUser ={
            "/swagger-ui/**","/v3/api-docs/**"

    };
    private static final String[] Adminuser ={
            "/upload"
    };
    private static final String[] User={
            "/movies/**"
    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers(allowUser).permitAll()
                .antMatchers(Adminuser).hasRole("ADMIN")
                .antMatchers(User).hasAnyRole("ADMIN","USER")
                .anyRequest().authenticated()
                .and().httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication().withUser("admin")
                .password(passwordEncoder().encode("admin")).roles("ADMIN")
                .and().withUser("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER");
    }
    @Bean
    private static PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}
