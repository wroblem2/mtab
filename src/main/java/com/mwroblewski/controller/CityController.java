package com.mwroblewski.controller;

import com.mwroblewski.entity.City;
import com.mwroblewski.exception.CityAlreadyExistsException;
import com.mwroblewski.repository.CityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    CityDAO cityDAO;

    @GetMapping("/all")
    public List<City> getAllCities(){

        return (List<City>) cityDAO.findAll();
    }

    @PostMapping("/add")
    public void addCity(@RequestBody String name){

        List<City> cityList = (List<City>) cityDAO.findAll();
        Optional<City> optional = cityList
                .stream()
                .filter(c -> c.getName().toUpperCase().equals(name.toUpperCase()))
                .findFirst();

        if(!optional.isPresent()){
            City city = new City();
            city.setName(name);
            cityDAO.save(city);
        }
        else
            throw new CityAlreadyExistsException(name);
    }


    @ExceptionHandler(CityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody Error cityAlreadyExists(CityAlreadyExistsException e){
        return new Error("City with name: " + e.getParameters() + " already exists");
    }
}
