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
public class EditUserRequestDTO {

    @NotBlank(message = "Username" + ValidationUtil.NOR_NULL_OR_EMPTY)
    private String username;
    @Email
    @NotBlank(message = "Email" + ValidationUtil.NOR_NULL_OR_EMPTY)
    private String email;
    @NotBlank(message = "Old Password" + ValidationUtil.NOR_NULL_OR_EMPTY)
    @JsonProperty("old_password")
    private String oldPassword;
    @NotBlank(message = "New Password" + ValidationUtil.NOR_NULL_OR_EMPTY)
    @JsonProperty("new_password")
    private String newPassword;
    @NotBlank(message = "Confirm Password" + ValidationUtil.NOR_NULL_OR_EMPTY)
    @JsonProperty("confirm_new_password")
    private String confirmNewPassword;
    @NotBlank(message = "Address" + ValidationUtil.NOR_NULL_OR_EMPTY)
    @Size(min = 5, message = ValidationUtil.LENGTH_LIMITS_FOR_ADDRESS)
    private String address;
    private String phone;

}
