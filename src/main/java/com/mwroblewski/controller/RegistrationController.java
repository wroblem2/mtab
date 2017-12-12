package com.mwroblewski.controller;

import com.mwroblewski.exception.*;
import com.mwroblewski.exception.Error;
import com.mwroblewski.entity.Attachments;
import com.mwroblewski.entity.Contract;
import com.mwroblewski.entity.Profile;
import com.mwroblewski.entity.User;
import com.mwroblewski.repository.ProfileDAO;
import com.mwroblewski.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class RegistrationController {

    @Autowired
    UserDAO userDAO;
    @Autowired
    ProfileDAO profileDAO;

    @PostMapping("/registration")
    public void createNewAccount(@RequestBody HashMap<String, Object> details) throws ParseException {

        if(details.get("username") == null || details.get("password") == null || details.get("name") == null || details.get("surname") == null || details.get("email") == null)
            throw new NotAllMandatoryAttributesException();
        else if(userDAO.findByUsername((String) details.get("username")) != null)
            throw new UsernameAlreadyExistsException((String) details.get("username"));
        else if(profileDAO.findByEmail((String) details.get("email")) != null)
            throw new EmailAlreadyExistsException((String) details.get("email"));

        User user = new User();
        user.setUsername((String) details.get("username"));
        user.setPassword((String) details.get("password"));
        user.setEnabled(true);
        user.setRole(User.Role.ROLE_USER);

        Profile profile = new Profile();
        profile.setName((String) details.get("name"));
        profile.setSurname((String) details.get("surname"));
        profile.setEmail((String) details.get("email"));
        profile.setPhone((String) details.get("phone"));
        profile.setAddress((String) details.get("address"));

        try {
            DateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
            Date born = formatter.parse((String) details.get("born"));
            profile.setBorn(born);
        } catch (ParseException e) {
            throw new DateFormatException((String) details.get("born"));
        }

        for (Contract contract : Contract.values()) {
            if (((String) details.get("contract")).equals(contract.toString())) {
                profile.setContract(contract);
                break;
            }
        }

        profile.setMaxSalary(BigDecimal.valueOf((Double) details.get("maxSalary")));
        profile.setMinSalary(BigDecimal.valueOf((Double) details.get("minSalary")));

        profile.setExperiences((String) details.get("experiences"));
        profile.setAccomplishments((String) details.get("accomplishments"));
        profile.setInterests((String) details.get("interests"));

        Map<String, Object> inAttachments = (HashMap<String, Object>) details.get("attachments");
        if (inAttachments != null) {
            Attachments attachments = new Attachments();
            attachments.setPhoto(((String) inAttachments.get("photo")).getBytes());
            attachments.setCv(((String) inAttachments.get("cv")).getBytes());
            attachments.setCoverLetter(((String) inAttachments.get("coverLetter")).getBytes());
            profile.setAttachments(attachments);
        }
        profile.setUser(user);
        user.setProfile(profile);
        userDAO.save(user);
    }


    @ExceptionHandler(NotAllMandatoryAttributesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody Error notAllMandatoryAttributes(NotAllMandatoryAttributesException e){
        return new Error("Must to fill in all mandatory attributes");
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody Error usernameAlreadyExists(UsernameAlreadyExistsException e){
        return new Error("User with username: " + e.getParameters() + " already exists");
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody Error emailAlreadyExists(EmailAlreadyExistsException e){
        return new Error("Profile with email: " + e.getParameters() + " already exists");
    }

    @ExceptionHandler(DateFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody Error emailAlreadyExists(DateFormatException e){
        return new Error("Incorrect data format: " + e.getParameters() + " \"yyyy.MM.dd\"");
    }
}
