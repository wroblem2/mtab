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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class UpdateUserProfileController {

    @Autowired
    UserDAO userDAO;
    @Autowired
    ProfileDAO profileDAO;

    @PutMapping("/update/user")
    public void updateAccount(@AuthenticationPrincipal Principal principal, @RequestBody HashMap<String, Object> details){

        if(details.get("username") != null|| details.get("email") != null){
            if(userDAO.findByUsername((String) details.get("username")) != null)
                throw new UsernameAlreadyExistsException((String) details.get("username"));
            else if(profileDAO.findByEmail((String) details.get("email")) != null)
                throw new EmailAlreadyExistsException((String) details.get("email"));
        }

        User user = userDAO.findByUsername(principal.getName());
        Profile profile = user.getProfile();

        details.forEach((k,v) ->{
            switch (k){
                case "username":
                    user.setUsername((String) v);
                    break;
                case "password":
                    user.setPassword((String) v);
                    break;
                case "name":
                    profile.setName((String) v);
                    break;
                case "surname":
                    profile.setSurname((String) v);
                    break;
                case "email":
                    profile.setEmail((String) v);
                    break;
                case "phone":
                    profile.setPhone((String) v);
                    break;
                case "address":
                    profile.setAddress((String) v);
                    break;
                case "born":
                    try {
                        DateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
                        Date born = formatter.parse((String) details.get("born"));
                        profile.setBorn(born);
                    } catch (ParseException e) {
                        throw new DateFormatException((String) details.get("born"));
                    }
                    break;
                case "contract":
                    for (Contract contract : Contract.values()) {
                        if (((String) details.get("contract")).equals(contract)) {
                            profile.setContract(contract);
                            break;
                        }
                    }
                    break;
                case "maxSalary":
                    profile.setMaxSalary(BigDecimal.valueOf((Double) v));
                    break;
                case "minSalary":
                    profile.setMinSalary(BigDecimal.valueOf((Double) v));
                    break;
                case "attachments":
                    Map<String, Object> inAttachments = (HashMap<String, Object>) details.get("attachments");
                    if(inAttachments != null){
                        Attachments attachments = new Attachments();
                        inAttachments.forEach((k1,v1) ->
                        {
                            switch (k1){
                                case "photo":
                                    attachments.setPhoto(((String) inAttachments.get("photo")).getBytes());
                                    break;
                                case "cv":
                                    attachments.setCv(((String) inAttachments.get("cv")).getBytes());
                                    break;
                                case "coverLetter":
                                    attachments.setCoverLetter(((String) inAttachments.get("coverLetter")).getBytes());
                                    break;
                            }
                        });
                        profile.setAttachments(attachments);
                    }
            }

        });
        user.setProfile(profile);
        userDAO.save(user);
    }


    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Error usernameAlreadyExists(UsernameAlreadyExistsException e){
        return new Error("User with username: " + e.getParameters() + " already exists");
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Error emailAlreadyExists(EmailAlreadyExistsException e){
        return new Error("Profile with email: " + e.getParameters() + " already exists");
    }

    @ExceptionHandler(DateFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error emailAlreadyExists(DateFormatException e){
        return new Error("Incorrect data format: " + e.getParameters() + " \"yyyy.MM.dd\"");
    }


}
