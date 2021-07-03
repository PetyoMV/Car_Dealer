package bri4ki.controller;

import bri4ki.exceptions.*;
import bri4ki.model.dto.ExceptionDTO;
import bri4ki.model.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


public abstract class AbstractController {

    @Autowired
    protected SessionManager sessionManager;

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleBadRequest(BadRequestException e){
        return new ExceptionDTO(e.getMessage());
        }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDTO handleBadRequest(NotFoundException e){
        return new ExceptionDTO(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionDTO handleNotAuthorized(AuthenticationException e){
        return new ExceptionDTO(e.getMessage());
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDTO handleFileNotFound(FileNotFoundException e){
        return new ExceptionDTO(e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionDTO handleIOException(IOException e){
        return new ExceptionDTO(e.getMessage());
    }

    public void adminProtection(User user){
        if (!user.getEmail().equals("Misho@mail.bg")){
            throw new AuthenticationException("Only admins can do this!");
        }
    }
}
