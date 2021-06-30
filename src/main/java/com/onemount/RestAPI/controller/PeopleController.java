package com.onemount.RestAPI.controller;

import com.onemount.RestAPI.model.Person;
import com.onemount.RestAPI.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class PeopleController {
    @Autowired
    private PersonRepository personRepo;

    @GetMapping("devhanoisaigoshanghai")
    public Map<String,List<Person>> listDeveloperByCity(){
        return personRepo.listdeveloperbycity();
    }
    @GetMapping("youngestdevs")
    public Map<String,Double> cityHasYoungerDeveloper(){
        return personRepo.cityHasYoungerDeveloper();
    }
    @GetMapping("malefemaleratio")
    public List<Map.Entry<String,Double>> maleFemaleRatioByCity(){
        return personRepo.maleFemaleRatioByCity();
    }
    @GetMapping("avgsalarypeopleabove30")
    public Double averageSalaryPeopleThan30(){
        return personRepo.averageSalaryPeopleThan30();
    }
}

