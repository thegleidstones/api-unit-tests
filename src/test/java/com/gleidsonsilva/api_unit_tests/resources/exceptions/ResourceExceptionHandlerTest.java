package com.gleidsonsilva.api_unit_tests.resources.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gleidsonsilva.api_unit_tests.domain.dto.UserDTO;
import com.gleidsonsilva.api_unit_tests.services.exceptions.DataIntegrityViolationException;
import com.gleidsonsilva.api_unit_tests.services.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ResourceExceptionHandlerTest {

    public static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";
    public static final String E_MAIL_JA_CADASTRADO = "E-mail já cadastrado";

    @InjectMocks
    private ResourceExceptionHandler exceptionHandler;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDTO invalidUserDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createInvalidUserDTO();
    }

    @Test
    void whenUserNotFoundExceptionThenReturnAResponseEntity() {
        ResponseEntity<StandardError> response = exceptionHandler
                .userNotFound(new UserNotFoundException(USUARIO_NAO_ENCONTRADO), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(USUARIO_NAO_ENCONTRADO, response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());
    }

    @Test
    void whenDataIntegrityViolationThenReturnAResponseEntity() {
        ResponseEntity<StandardError> response = exceptionHandler
                .dataIntegrityViolation(
                        new DataIntegrityViolationException(E_MAIL_JA_CADASTRADO),
                        new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(E_MAIL_JA_CADASTRADO, response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }

    @Test
    void whenHandleValidationsExceptions() throws Exception {
        String userJson = objectMapper.writeValueAsString(invalidUserDTO);

        MvcResult result = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        ValidationStandardError error = objectMapper.readValue(jsonResponse, ValidationStandardError.class);

        assertEquals(400, error.getStatus());
        assertNotNull(error);
        assertNotNull(result.getResponse());
        assertThat(error.getErrorsList()).contains("name: Invalid name, null or empty values found.");
        assertThat(error.getErrorsList()).contains("email: E-mail must be a valid format");
        assertThat(error.getErrorsList()).contains("password: Invalid password, null or empty values found.");
    }

    @Test
    void whenHandleNoResourceFoundExceptionThenReturnAResponseEntity() {
        ResponseEntity<StandardError> response = exceptionHandler
                .handleNoResourceFoundException(
                        new MockHttpServletRequest());
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("Url inválida informada", response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());
    }

    @Test
    void whenMethodArgumentTypeMismatchExceptionThenReturnAResponseEntity() {
        ResponseEntity<StandardError> response = exceptionHandler
                .handleMethodArgumentTypeMismatchException(
                        new MockHttpServletRequest());
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("Tipo de dados inválido enviado no parâmetro da url", response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }

    private void createInvalidUserDTO() {
        invalidUserDTO = new UserDTO(1, "", "email_invalido", "");
    }
}