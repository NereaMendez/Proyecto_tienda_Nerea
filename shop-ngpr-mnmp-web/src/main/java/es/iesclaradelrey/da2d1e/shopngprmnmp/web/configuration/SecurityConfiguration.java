package es.iesclaradelrey.da2d1e.shopngprmnmp.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Rutas que requieren estar logueado (Si no lo estás, Spring te manda al login)
                        .antMatchers("/h2-console/**").authenticated()
                        .antMatchers("/admin/**").authenticated()
                        .antMatchers("/users/profile").authenticated() // <--- AÑADIDO PARA EL PUNTO 3.5

                        // El resto de la web es pública
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.ignoringAntMatchers("/h2-console/**"))
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                // .formLogin(Customizer.withDefaults()) usa el formulario por defecto de Spring
                .formLogin(Customizer.withDefaults())
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }


}