package bri4ki.util;

import bri4ki.exceptions.BadRequestException;
import bri4ki.model.dto.EditUserRequestDTO;
import bri4ki.model.dto.RegisterRequestUserDTO;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {

    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
    private static final String PHONE_REGEX ="^((\\+)|(00)|(\\*)|())[0-9]{9,14}((\\#)|())$";
    private static final String NAME_REGEX = "^[a-zA-Z._-]{3,}$";
    private static final String PASSWORD_REQUIREMENTS = "Password must have one upper and lower letter, " +
            "one number and no spaces and it must be at least 8 symbols";
    private static final String INVALID_USERNAME = "Invalid username";
    private static final String INVALID_PHONE_NUMBER = "Invalid phone number";
    private static final String PASSWORDS_DO_NOT_MATCH = "New password and confirm password do not match";
    public static final String NOR_NULL_OR_EMPTY = " cannot be null ot empty!";
    public static final String LENGTH_LIMITS_FOR_NAME = "Name cannot be more then "
            + ValidationUtil.MAX_NAME_LENGTH + " and less then " +
            ValidationUtil.MIN_NAME_LENGTH + " characters";
    public static final String LENGTH_LIMITS_FOR_ADDRESS = "Address must be at least "
            + ValidationUtil.MIN_ADDRESS_LENGTH + " symbols";
    public static final String POSITIVE_NUMBER = " must be a positive number!";
    public static final int MAX_NAME_LENGTH = 40;
    public static final int MIN_NAME_LENGTH = 3;
    public static final int MIN_ADDRESS_LENGTH = 5;


    public void checkUser(RegisterRequestUserDTO user) {
        checkUsername(user.getUsername());
        checkPassword(user.getPassword());
        checkConfirmPassword(user.getPassword(), user.getConfirmPassword());
        if (user.getPhone() != null) {
            checkPhone(user.getPhone());
        }
    }

    public void checkUser(EditUserRequestDTO user){
        checkUsername(user.getUsername());
        checkPassword(user.getNewPassword());
        checkConfirmPassword(user.getNewPassword(), user.getConfirmNewPassword());
        if (user.getPhone() != null) {
            checkPhone(user.getPhone());
        }
    }

    private void checkPhone(String phone) {
        if(!phone.matches(PHONE_REGEX)){
            throw new BadRequestException(INVALID_PHONE_NUMBER);
        }
    }

    private void checkConfirmPassword(String password, String confirmPassword) {
        if(!password.equals(confirmPassword)){
            throw new BadRequestException(PASSWORDS_DO_NOT_MATCH);
        }
    }

    private void checkPassword(String password) {
        if(!password.matches(PASSWORD_REGEX)){
            throw new BadRequestException(PASSWORD_REQUIREMENTS);
        }
    }



    private void checkUsername(String name) {
        if(!name.matches(NAME_REGEX)){
            throw new BadRequestException(INVALID_USERNAME);
        }
    }
}
