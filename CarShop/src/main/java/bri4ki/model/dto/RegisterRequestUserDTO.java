package bri4ki.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class RegisterRequestUserDTO {


    private String username;
    private String email;
    private String password;
    @JsonProperty("confirm_password")
    private String confirmPassword;
    private String phone;
    private String address;

}
