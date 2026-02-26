package com.linkedin.linkedin.features.authentication.configuration;


import com.linkedin.linkedin.features.authentication.model.AuthenticationUser;
import com.linkedin.linkedin.features.authentication.repository.AuthenticationUserRepository;
import com.linkedin.linkedin.features.authentication.utils.Encoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDataBaseConfiguration {

    @Bean
    public CommandLineRunner initDataBase(AuthenticationUserRepository authenticationUserRepository, Encoder encoder){
        AuthenticationUser user = new AuthenticationUser("naveen@gmail.com",encoder.encode("abc123"));

        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
               authenticationUserRepository.save(user);
            }
        };
    }

}
