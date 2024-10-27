package com.gleidsonsilva.api_unit_tests.resources;

import com.gleidsonsilva.api_unit_tests.domain.User;
import com.gleidsonsilva.api_unit_tests.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/users")
public class UserResource {
    @Autowired
    private UserService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

}
