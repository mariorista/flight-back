package mar.cod.flightback.utils.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
                .cors()
                .configurationSource(request-> corsConfigurationSource().getCorsConfiguration(request))
                // .configurationSource(corsConfigurationSource())
                // .disable()
                .and().authorizeHttpRequests()
                // .requestMatchers("/**").authenticated()
                // .requestMatchers("**/admin/**").hasRole("ADMIN")

                .requestMatchers("/**admin**").hasRole(Roles.ADMIN.name())
                .anyRequest().authenticated()
                // .anyRequest().permitAll()
                // .and().formLogin()
                .and().logout()
                // .logoutUrl("/logout")
                // .logoutSuccessUrl("/logout-success")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")

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

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
