package com.example.batch_h2_demo.repository;

import com.example.batch_h2_demo.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface CountyRepository extends JpaRepository<Country, Integer> {
    List<CountryView> getCountryByName(String name);

    //@Query(value="SELECT DISTINCT name, continent from Country", nativeQuery = true)
    //List<CountryDistinctView> getDistinctCountry();

    List<CountryDistinctView> findDistinctByCreatedAfter(LocalDate created);
}
