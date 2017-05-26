package hello;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class Application implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    public void run(String... strings) throws Exception {
//        selectorRepository.save(new Selector("Vendor", Arrays.asList("", "GMS")));
//        selectorRepository.save(new Selector("Model", Arrays.asList("", "Terrain", "Acadia", "Canyon", "Sierra")));
//        selectorRepository.save(new Selector("Vehicle class", Arrays.asList("", "All", "XL", "Denali trims", "Hybrid trims")));
//        selectorRepository.save(new Selector("Trim", Arrays.asList("", "SUV", "Pickup", "VAN")));
//        selectorRepository.save(new Selector("Engine code", Arrays.asList("", "All", "4INAG2.4")));
//        selectorRepository.save(new Selector("Engine size", Arrays.asList("", "2,4", "3,6", "All")));
//        selectorRepository.save(new Selector("Cylinders", Arrays.asList("", "All", "4", "6", "8")));
    }
}
