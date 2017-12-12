package com.mwroblewski.controller;

import com.mwroblewski.entity.Category;
import com.mwroblewski.exception.CategoryAlreadyExistsException;
import com.mwroblewski.exception.CityAlreadyExistsException;
import com.mwroblewski.repository.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryDAO categoryDAO;

    @GetMapping("/all")
    public List<Category> getAllCategories(){

        return (List<Category>) categoryDAO.findAll();
    }

    @PostMapping("/add")
    public void addCategory(@RequestBody String name){

        List<Category> categoryList = (List<Category>) categoryDAO.findAll();
        Optional<Category> optional = categoryList
                .stream()
                .filter(c -> c.getName().toUpperCase().equals(name.toUpperCase()))
                .findFirst();

            if(!optional.isPresent()){
                Category category = new Category();
                category.setName(name);
                categoryDAO.save(category);
            }
            else
                throw new CategoryAlreadyExistsException(name);
    }


    @ExceptionHandler(CategoryAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody Error cityAlreadyExists(CategoryAlreadyExistsException e){
        return new Error("Category with name: " + e.getParameters() + " already exists");
    }

}
