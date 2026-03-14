package es.iesclaradelrey.da2d1e.shopngprmnmp.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities")
@EnableJpaRepositories(basePackages = "es.iesclaradelrey.da2d1e.shopngprmnmp.common.repositories")
@ComponentScan({
        "es.iesclaradelrey.da2d1e.shopngprmnmp.web",
        "es.iesclaradelrey.da2d1e.shopngprmnmp.security",
        "es.iesclaradelrey.da2d1e.shopngprmnmp.common"
})
public class ShopNgprMnmpWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopNgprMnmpWebApplication.class, args);
    }

}