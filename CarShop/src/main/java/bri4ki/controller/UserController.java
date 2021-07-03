package bri4ki.controller;

import bri4ki.exceptions.AuthenticationException;
import bri4ki.exceptions.BadRequestException;
import bri4ki.model.dto.*;
import bri4ki.model.pojo.User;
import bri4ki.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController extends AbstractController{

    @Autowired
    private UserService userService;

    @Autowired
    private SessionManager sessionManager;

    @PutMapping("/user")
    public RegisterResponseUserDTO register(@RequestBody RegisterRequestUserDTO userDTO, HttpSession session){
        if(sessionManager.isSomeoneLoggedIn(session)){
            throw new BadRequestException("You have to log out first!");
        }
        return userService.addUser(userDTO);
    }

    @PostMapping("/user")
    public UserWithoutPasswordDTO loginUser(@RequestBody LoginUserDTO dto, HttpSession session){
        if (sessionManager.isSomeoneLoggedIn(session)){
            throw new BadRequestException("You are already logged in!");
        }
        User user = userService.login(dto);
        sessionManager.loginUser(session,user.getId());
        return new UserWithoutPasswordDTO(user);
    }

    @PutMapping("/user/edit")
    public UserWithoutPasswordDTO edit(@Valid @RequestBody EditUserRequestDTO requestDto, HttpSession session){
        User user = sessionManager.getLoggedUser(session);
        user = userService.edit(requestDto,user);
        return new UserWithoutPasswordDTO(user);
    }

    @DeleteMapping("/user")
    public MessageResponseDTO delete(@Valid @RequestBody PasswordRequestDTO password , HttpSession session){
        User user = sessionManager.getLoggedUser(session);
        sessionManager.logoutUser(session);
        userService.delete(password ,user);
        return new MessageResponseDTO("Delete successfully");
    }

    @PostMapping("/logout")
    public String logOut(HttpSession session){
        if(sessionManager.isSomeoneLoggedIn(session)) {
            sessionManager.logoutUser(session);
            return "System - log out confirm";
        }
        else {
            return "You are not log in";
        }
    }

    @GetMapping("/users")
    public List<UserWithoutPasswordDTO> getAll(){
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public UserWithoutPasswordDTO getById(@PathVariable int id){
        return userService.getUserById(id);
    }

    @PutMapping("user/{user_id}/car/{car_id}")
    public UserWithoutPasswordDTO buyCar(@PathVariable(name = "user_id")int userId, @PathVariable(name = "car_id")int carId, HttpSession session){
        if(session.getAttribute("LOGGED_USER_ID") == null){
            throw new AuthenticationException("You have to be logged in!");
        }else {
            int loggedId = (int) session.getAttribute("LOGGED_USER_ID");
            if(loggedId != userId){
                throw new BadRequestException("You cannot buy car on behalf of another user");
            }
        }
        return  userService.buyCar(userId, carId);
    }

}
