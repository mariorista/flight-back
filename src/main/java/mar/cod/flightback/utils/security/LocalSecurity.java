package mar.cod.flightback.utils.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import mar.cod.flightback.utils.Roles;

@EnableWebSecurity
@Configuration
public class LocalSecurity {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance(); 
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // http.authorizeRequests().anyRequest().authenticated();
        http.authorizeHttpRequests()
        //         .requestMatchers("/**").hasRole(Roles.ADMIN.name()).and().httpBasic();

        .anyRequest().authenticated().and().httpBasic();
        // http.formLogin();
        http.httpBasic();
        return http.build();
    }

}
