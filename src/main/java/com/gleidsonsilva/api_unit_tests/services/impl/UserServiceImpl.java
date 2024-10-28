package com.gleidsonsilva.api_unit_tests.services.impl;

import com.gleidsonsilva.api_unit_tests.domain.User;
import com.gleidsonsilva.api_unit_tests.domain.dto.UserDTO;
import com.gleidsonsilva.api_unit_tests.repositories.UserRepository;
import com.gleidsonsilva.api_unit_tests.services.exceptions.DataIntegrityViolationException;
import com.gleidsonsilva.api_unit_tests.services.exceptions.UserNotFoundException;
import com.gleidsonsilva.api_unit_tests.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public User findById(Integer id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User create(UserDTO dto) {
        findByEmail(dto);
        return repository.save(mapper.map(dto, User.class));
    }

    @Override
    public User update(UserDTO dto) {
        findByEmail(dto);
        return repository.save(mapper.map(dto, User.class));
    }

    @Override
    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }

    private void findByEmail(UserDTO dto) {
        Optional<User> user = repository.findByEmail(dto.getEmail());
        if (user.isPresent() && !user.get().getId().equals(dto.getId())) {
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema");
        }
    }
}
