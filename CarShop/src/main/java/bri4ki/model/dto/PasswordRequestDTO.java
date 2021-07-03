package bri4ki.model.dto;


import bri4ki.util.ValidationUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@Component
public class PasswordRequestDTO {

    @NotBlank(message = "Password" + ValidationUtil.NOR_NULL_OR_EMPTY)
    private String password;
}
