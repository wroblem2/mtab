package com.mwroblewski.controller;

import com.mwroblewski.entity.Entry;
import com.mwroblewski.entity.Offer;
import com.mwroblewski.entity.User;
import com.mwroblewski.exception.EntryNotExistsException;
import com.mwroblewski.exception.OfferNotExistsException;
import com.mwroblewski.repository.EntryDAO;
import com.mwroblewski.repository.OfferDAO;
import com.mwroblewski.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/entry")
public class EntryController {

    @Autowired
    UserDAO userDAO;
    @Autowired
    OfferDAO offerDAO;
    @Autowired
    EntryDAO entryDAO;

    @PostMapping("/add")
    public void addEntry(@AuthenticationPrincipal Principal principal, @RequestParam(name = "id", required = true) Integer id, @RequestBody HashMap<String, String> inEntry){

        User user = userDAO.findByUsername(principal.getName());
        Offer offer = offerDAO.findById(id.longValue());

        if (offer != null){
            Entry entry = new Entry();
            entry.setApplied(new Date());
            entry.setComment(inEntry.get("comment"));
            entry.setUser(user);
            entry.setOffer(offer);
            entryDAO.save(entry);
        }
        else
            throw new OfferNotExistsException(id.toString());
    }

    @DeleteMapping("/delete")
    public void deleteEntry(@AuthenticationPrincipal Principal principal, @RequestParam(name = "id", required = true) Integer id){

        User user = userDAO.findByUsername(principal.getName());
        Entry entry = entryDAO.findOne(id.longValue());

        if(entry != null)
            entryDAO.delete(entry);
        else
            throw new EntryNotExistsException(id.toString());
    }


    @ExceptionHandler(OfferNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody Error offerNotExists(OfferNotExistsException e){
        return new Error("Offer with id: " + e.getParameters() + " not exists");
    }

    @ExceptionHandler(EntryNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody Error entryNotExists(EntryNotExistsException e){
        return new Error("Entry with id: " + e.getParameters() + " not exists");
    }
}
