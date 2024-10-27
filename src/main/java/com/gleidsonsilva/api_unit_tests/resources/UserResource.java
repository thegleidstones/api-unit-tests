package com.gleidsonsilva.api_unit_tests.resources;

import com.gleidsonsilva.api_unit_tests.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/users")
public class UserResource {
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(new User(1, "Gleidson Silva", "gleidson.silva@gmail.com", "123456789"));
    }

}
