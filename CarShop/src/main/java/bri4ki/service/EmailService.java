package bri4ki.service;

import bri4ki.model.pojo.Discount;
import bri4ki.model.pojo.User;
import bri4ki.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService{

    private static final String EMAIL_SUBJECT = "One of your favourite car has been discounted! Check it out!";
    private static final String URL = "localhost::7171/discounts/";


    private JavaMailSender emailSender;
    @Autowired
    private UserRepository userRepository;

    public void sendMessage(Discount discount) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("carshopapi@mail.bg");
        message.setTo(getRecipients());
        message.setSubject(EMAIL_SUBJECT);
        message.setText(createMailText(discount));
        emailSender.send(message);
        System.out.println("Message sent successfully");
    }

    private String[] getRecipients(){
        List<User> users = userRepository.findAll();
        List<String> emails = new ArrayList<>();
        users.forEach(user -> emails.add(user.getEmail()));
        return emails.toArray(new String[0]);
    }

    private String createMailText(Discount discount) {
        StringBuilder text = new StringBuilder("There is a new discount \"")
                .append(discount.getTitle())
                .append("\" with ")
                .append(discount.getDiscountPercent())
                .append(" discount percent starting at: ")
                .append(discount.getStartAt())
                .append(" and end at: ")
                .append(discount.getEndAt())
                .append("!!!\n")
                .append("Don't waste time and get our awesome offers now!!!\n")
                .append("You can see more about this discount on: \n")
                .append(URL).append(discount.getId());
        return text.toString();
    }
}
