package bri4ki.controller;

import bri4ki.exceptions.AuthenticationException;
import bri4ki.model.pojo.User;
import bri4ki.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SessionManager {

    private static final String LOGGED_USER_ID = "LOGGED_USER_ID";

    @Autowired
    private UserRepository userRepository;

    public User getLoggedUser(HttpSession session){
        if(session.getAttribute(LOGGED_USER_ID) == null){
            throw new AuthenticationException("You have to be logged in!");
        }
        else {
            int userId = (int) session.getAttribute(LOGGED_USER_ID);
            return userRepository.findById(userId).get();
        }
    }

    public void loginUser(HttpSession session, int id) {
        session.setAttribute(LOGGED_USER_ID,id);
    }

    public void logoutUser(HttpSession session) {
        session.invalidate();
    }
}
