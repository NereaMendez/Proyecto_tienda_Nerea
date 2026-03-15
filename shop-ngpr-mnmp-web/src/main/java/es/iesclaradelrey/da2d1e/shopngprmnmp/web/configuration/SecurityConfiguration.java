package es.iesclaradelrey.da2d1e.shopngprmnmp.web.configuration;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final SecurityMonitor securityMonitor;

    public SecurityConfiguration(SecurityMonitor securityMonitor) {
        this.securityMonitor = securityMonitor;
    }

    @Bean
    public DefaultAuthenticationEventPublisher authenticationEventPublisher(
            ApplicationEventPublisher applicationEventPublisher) {
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .antMatchers("/h2-console/**").authenticated()
                        .antMatchers("/admin/**").authenticated()
                        .antMatchers("/users/profile").authenticated()
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.ignoringAntMatchers("/h2-console/**"))
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )

                //Form de login: Nerea yo
                .formLogin(form -> form
                        .loginPage("/users/login")
                        .loginProcessingUrl("/login")
                        //a dónde va después del login exitoso:
                        .defaultSuccessUrl("/")
                        //para mostrar mensaje de error
                        .failureUrl("/users/login?error")
                        .permitAll()
                )

                //configuramos el logout para que use monitor?
                //redireccion
                .logout(logout -> logout
                        .logoutUrl("/logout")

                        //Registra el logout:
                        .logoutSuccessHandler(securityMonitor)
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)//limpiar sesión
                        .clearAuthentication(true)//limpiar autentificación
                        .permitAll()
                )
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }
}