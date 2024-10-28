package com.gleidsonsilva.api_unit_tests.config;

import com.gleidsonsilva.api_unit_tests.domain.User;
import com.gleidsonsilva.api_unit_tests.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile(value = "local")
public class LocalConfig {
    @Autowired
    private UserRepository repository;

    @Bean
    public List<User> startDB() {
        User u1 = new User(null, "Moises", "moises@gmail.com", "456789");
        User u2 = new User(null, "Pedro", "pedro@gmail.com", "123456");

        return repository.saveAll(List.of(u1, u2));
    }
}
