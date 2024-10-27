package com.gleidsonsilva.api_unit_tests.repositories;

import com.gleidsonsilva.api_unit_tests.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
