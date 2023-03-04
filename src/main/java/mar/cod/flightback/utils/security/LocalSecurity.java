package mar.cod.flightback.utils.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import mar.cod.flightback.utils.Roles;

@EnableWebSecurity
// @EnableMethodSecurity
@Configuration
public class LocalSecurity {

    @Bean
    // authentication
    public UserDetailsService userDetailsService() {
        // UserDetails admin = User.withUsername("admin")
        // .password(encoder.encode("admin"))
        // .roles("ADMIN")
        // .authorities("ROLE_ADMIN")
        // .build();
        // UserDetails user = User.withUsername("user")
        // .password(encoder.encode("pass"))
        // .roles("SIMPLE")
        // .build();
        // return new InMemoryUserDetailsManager(admin, user);
        return new UserInfoUserDetailsService();
    }

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    // return new BCryptPasswordEncoder();
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
.csrf().disable()

                .authorizeHttpRequests()
                // .requestMatchers("/**").authenticated()
                // .requestMatchers("**/admin/**").hasRole("ADMIN")

                .requestMatchers("/**admin**").hasRole(Roles.ADMIN.name())
                // .requestMatchers("/**").authenticated()
                .anyRequest().authenticated()
                // .and()
                // .exceptionHandling()
                // .accessdenydPage("/access-denyd")

                .and().formLogin()
                .and().httpBasic()
                .and().build();

    }

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}
