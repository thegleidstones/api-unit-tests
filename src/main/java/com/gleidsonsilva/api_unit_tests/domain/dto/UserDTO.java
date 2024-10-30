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

        @NotBlank(message = "Invalid name, null or empty values found.")
        private String name;

        @NotBlank(message = "Invalid e-mail, null or empty values found.")
        @Email(message = "E-mail must be a valid format")
        private String email;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotBlank(message = "Invalid password, null or empty values found.")
        @Size(min = 6, max = 20, message = "Password must be min 6 chars and max 20 chars.")
        private String password;
}
