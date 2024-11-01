package com.gleidsonsilva.api_unit_tests.services;

import com.gleidsonsilva.api_unit_tests.domain.User;
import com.gleidsonsilva.api_unit_tests.domain.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User findById(Integer id);
    List<User> findAll();
    User create(UserDTO dto);
    User update(UserDTO dto);
    void delete(Integer id);
}
