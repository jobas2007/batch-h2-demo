package com.example.batch_h2_demo;


import com.example.batch_h2_demo.entity.Country;
import com.example.batch_h2_demo.repository.CountryDistinctView;
import com.example.batch_h2_demo.repository.CountryView;
import com.example.batch_h2_demo.repository.CountyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("country-rest")
public class CountryController {

    @Autowired
    CountyRepository repository;

    @GetMapping("/{name}")
    public List<CountryView> getCountryByName(@PathVariable String name) {
        return repository.getCountryByName(name);
    }

    @GetMapping("/distinct")
    public List<CountryDistinctView> getDistinctCountry() {
        return repository.findDistinctByCreatedAfter(LocalDate.now());
    }


}
