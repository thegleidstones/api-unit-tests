package com.gleidsonsilva.api_unit_tests.services.impl;

import com.gleidsonsilva.api_unit_tests.domain.User;
import com.gleidsonsilva.api_unit_tests.domain.dto.UserDTO;
import com.gleidsonsilva.api_unit_tests.repositories.UserRepository;
import com.gleidsonsilva.api_unit_tests.services.exceptions.DataIntegrityViolationException;
import com.gleidsonsilva.api_unit_tests.services.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    public static final Integer ID = 1;
    public static final String NAME = "Gleidson Silva";
    public static final String EMAIL = "gleidson@gmail.com";
    public static final String PASSWORD = "123";
    public static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";
    public static final Integer INDEX = 0;
    public static final String E_MAIL_JA_CADASTRADO = "E-mail já cadastrado no sistema";
    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() {
        when(repository.findById(anyInt())).thenReturn(optionalUser);

        User response = service.findById(ID);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenFindByIdThenReturnAnUserNotFoundException() {
        when(repository.findById(anyInt())).thenThrow(new UserNotFoundException(USUARIO_NAO_ENCONTRADO));
        try {
            service.findById(ID);
        } catch (Exception exception) {
            assertEquals(UserNotFoundException.class, exception.getClass());
            assertEquals(USUARIO_NAO_ENCONTRADO, exception.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        when(repository.findAll()).thenReturn(List.of(user));

        List<User> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(User.class, response.get(INDEX).getClass());

        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(NAME, response.get(INDEX).getName());
        assertEquals(EMAIL, response.get(INDEX).getEmail());
        assertEquals(PASSWORD, response.get(INDEX).getPassword());
    }

    @Test
    void whenCreateReturnSuccess() {
        when(repository.save(any())).thenReturn(user);

        User response = service.create(userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenCreateReturnDataIntegrityViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try {
            optionalUser.get().setId(2);
            service.create(userDTO);
        } catch (Exception exception) {
            assertEquals(DataIntegrityViolationException.class, exception.getClass());
            assertEquals(E_MAIL_JA_CADASTRADO, exception.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(user);

        User response = service.update(userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenUpdateThenReturnDataIntegrityViolationException() {
        when(repository.findByEmail(anyString())).thenThrow(new DataIntegrityViolationException(E_MAIL_JA_CADASTRADO));

        try {
            service.update(userDTO);
        } catch (Exception exception) {
            assertEquals(DataIntegrityViolationException.class, exception.getClass());
            assertEquals(E_MAIL_JA_CADASTRADO, exception.getMessage());
        }
    }

    @Test
    void delete() {
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}