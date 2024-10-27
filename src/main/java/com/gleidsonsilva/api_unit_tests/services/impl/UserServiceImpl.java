package com.gleidsonsilva.api_unit_tests.services.impl;

import com.gleidsonsilva.api_unit_tests.domain.User;
import com.gleidsonsilva.api_unit_tests.repositories.UserRepository;
import com.gleidsonsilva.api_unit_tests.services.UserNotFoundException;
import com.gleidsonsilva.api_unit_tests.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    public User findById(Integer id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }
}
