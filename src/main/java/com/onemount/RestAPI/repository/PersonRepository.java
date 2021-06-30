package com.onemount.RestAPI.repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.onemount.RestAPI.model.Person;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PersonRepository {
    private List<Person> people = new ArrayList<Person>();

    public PersonRepository() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        File file;
        try {
            file = ResourceUtils.getFile("classpath:static/person.json");
            people.addAll(mapper.readValue(file, new TypeReference<List<Person>>() {
            }));
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Person> getAll() {
        return people;
    }

    public Map<String, List<Person>> groupPeopleByCity() {
        return people
                .stream()
                .collect(Collectors.groupingBy(Person::getCity));
    }
    public Map<String,List<Person>> groupDeveloperByCity(){
        return people.stream().filter(p -> p.getJob().equals("developer"))
                .collect(Collectors.groupingBy(Person::getCity));
    }

    public Map<String, List<Person>> listdeveloperbycity() {
        Map<String, List<Person>> arr = groupDeveloperByCity();
        Map<String, List<Person>> result = arr.entrySet().stream()
                .filter(p -> p.getKey().equals("Hanoi") || p.getKey().equals("Saigon") || p.getKey().equals("Shanghai"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new
                ));
        return result;
    }

    public Map<String, Double> cityHasYoungerDeveloper() {
        Map<String, List<Person>> groupDeveloperByCity = groupDeveloperByCity();

        Map<String, Double> result = groupDeveloperByCity.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue()
                        .stream().collect(Collectors.averagingDouble(Person::getAge))));
        return result.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new
                ));
    }

    public List<Map.Entry<String, Double>> maleFemaleRatioByCity() {
        Map<String, List<Person>> groupPeopleByCity = groupPeopleByCity();
        return groupPeopleByCity.entrySet().stream()
                .map(person -> new AbstractMap.SimpleEntry<>(person.getKey(), getRatio(person.getValue())))
                .collect(Collectors.toList());
    }

    public Double getRatio(List<Person> peopleGroup) {
        Long maleCount = peopleGroup
                .stream()
                .filter(p -> p.getGender().equals("Male"))
                .collect(Collectors.counting());

        return (double) maleCount / (double) (peopleGroup.size() - maleCount);
    }

    public Double averageSalaryPeopleThan30() {
        return people.stream().filter(p -> p.getAge() > 30).
                collect(Collectors.averagingDouble(Person::getSalary));
    }
}
