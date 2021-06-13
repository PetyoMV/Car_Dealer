package bri4ki.controller;

import bri4ki.exceptions.AuthenticationException;
import bri4ki.exceptions.BadRequestException;
import bri4ki.model.dto.LoginUserDTO;
import bri4ki.model.dto.RegisterRequestUserDTO;
import bri4ki.model.dto.RegisterResponseUserDTO;
import bri4ki.model.dto.UserWithoutPasswordDTO;
import bri4ki.model.pojo.User;
import bri4ki.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class UserController extends AbstractController{

    @Autowired
    private UserService userService;

    @Autowired
    private SessionManager sessionManager;

    @PutMapping("/user")
    public RegisterResponseUserDTO register(@RequestBody RegisterRequestUserDTO userDTO){
        return userService.addUser(userDTO);
    }

    @PostMapping("/user")
    public UserWithoutPasswordDTO loginUser(@RequestBody LoginUserDTO dto, HttpSession session){
        UserWithoutPasswordDTO responseDTO = userService.login(dto);
        session.setAttribute("LoggedUser", responseDTO.getId());
        return responseDTO;
    }

    @PostMapping("/logout")
    public void logOut(HttpSession session){
       sessionManager.logoutUser(session);
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

        if(session.getAttribute("LoggedUser") == null){
            throw new AuthenticationException("You have to be logged in!");
        }else {
            int loggedId = (int) session.getAttribute("LoggedUser");
            if(loggedId != userId){
                throw new BadRequestException("You cannot buy car on behalf of another user");
            }
        }
        return  userService.buyCar(userId, carId);
    }

}
