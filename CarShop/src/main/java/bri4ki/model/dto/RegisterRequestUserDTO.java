package bri4ki.model.dto;


import bri4ki.util.ValidationUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Component
public class RegisterRequestUserDTO {

    @NotBlank(message = "Username" + ValidationUtil.NOR_NULL_OR_EMPTY)
    private String username;
    @NotBlank(message = "Email" + ValidationUtil.NOR_NULL_OR_EMPTY)
    @Email(message = "Not valid email!")
    private String email;
    @NotBlank(message = "Password" + ValidationUtil.NOR_NULL_OR_EMPTY)
    private String password;
    @NotBlank(message = "Confirm Password" + ValidationUtil.NOR_NULL_OR_EMPTY)
    @JsonProperty("confirm_password")
    private String confirmPassword;
    private String phone;
    @NotBlank(message = "Address" + ValidationUtil.NOR_NULL_OR_EMPTY)
    @Size(min = ValidationUtil.MIN_ADDRESS_LENGTH, message = ValidationUtil.LENGTH_LIMITS_FOR_ADDRESS)
    private String address;

    public RegisterRequestUserDTO(String name, String email, String password, String confirmPassword, String phone, String address) {
        this.username = name;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phone = phone;
        this.address = address;
    }

}
