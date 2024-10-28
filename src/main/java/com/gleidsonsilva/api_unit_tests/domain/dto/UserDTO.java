package com.gleidsonsilva.api_unit_tests.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
        private Integer id;

        @NotBlank(message = "Nome não pode ser nulo ou vazio")
        private String name;

        @NotBlank(message = "E-mail não pode ser nulo ou vazio")
        @Email(message = "E-mail deve ser válido")
        private String email;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotBlank(message = "Senha não pode ser nula ou vazia")
        @Size(min = 6, max = 20, message = "Senha deve ter no mínimo 6 caracteres e máximo 20")
        private String password;
}
