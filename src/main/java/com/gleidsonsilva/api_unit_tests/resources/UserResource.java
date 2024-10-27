package com.gleidsonsilva.api_unit_tests.resources;

import com.gleidsonsilva.api_unit_tests.domain.dto.UserDTO;
import com.gleidsonsilva.api_unit_tests.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserResource {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(mapper.map(service.findById(id), UserDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(service.findAll()
                .stream().map(user -> mapper.map(user, UserDTO.class)).toList());
    }

}
