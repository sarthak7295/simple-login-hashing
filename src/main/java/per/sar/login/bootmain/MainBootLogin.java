package per.sar.login.bootmain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
@EnableJpaRepositories("per.sar.login.*")
@ComponentScan(basePackages = { "per.sar.login.*" })
@EntityScan("per.sar.login.*")   
public class MainBootLogin extends SpringBootServletInitializer{

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MainBootLogin.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(MainBootLogin.class, args);
	}
}