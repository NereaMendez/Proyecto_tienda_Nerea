package es.iesclaradelrey.da2d1e.shopngprmnmp.web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EntityScan("es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities")
@EnableJpaRepositories("es.iesclaradelrey.da2d1e.shopngprmnmp.common.repositories")
@ComponentScan({
        "es.iesclaradelrey.da2d1e.shopngprmnmp.web",
        "es.iesclaradelrey.da2d1e.shopngprmnmp.security",
        "es.iesclaradelrey.da2d1e.shopngprmnmp.common"
})
public class ShopNgprMnmpWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopNgprMnmpWebApplication.class, args);
    }

    /*
    @Bean
    public CommandLineRunner printHash(PasswordEncoder encoder) {
        return args -> {
            String hash = encoder.encode("Password");
            System.out.println("\n---------------------------------------------------------");
            System.out.println("COPIA ESTE HASH PARA TU DATA.SQL:");
            System.out.println(hash);
            System.out.println("---------------------------------------------------------\n");
        };
    } */
}