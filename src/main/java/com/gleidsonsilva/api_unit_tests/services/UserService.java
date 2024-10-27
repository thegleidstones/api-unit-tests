package com.gleidsonsilva.api_unit_tests.services;

import com.gleidsonsilva.api_unit_tests.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findById(Integer id);
}
