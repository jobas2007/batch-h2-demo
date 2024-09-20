package com.example.batch_h2_demo.batch;


import com.example.batch_h2_demo.repository.CountryDistinctView;
import com.example.batch_h2_demo.repository.CountyRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

@Configuration
public class BatchConfig {
    @Autowired
    CountyRepository repository;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    PlatformTransactionManager platformTransactionManager;

    @Bean
    public Job countryJob() {
        return new JobBuilder("countryJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(countyStep())
                .build();
    }

    @Bean
    public Step countyStep() {
        return new StepBuilder("countryStep", jobRepository)
                .<CountryDistinctView, CountryDistinctView>chunk(1, platformTransactionManager)
                .reader(customerItemReader())
                .processor(countryItemProcessor())
                .writer(countryItemWriter())
                .build();
    }

    @Bean
    public RepositoryItemReader<CountryDistinctView> customerItemReader() {
        return new RepositoryItemReaderBuilder<CountryDistinctView>()
                .name("customerItemReader")
                .repository(repository)
                .methodName("findDistinctByCreatedAfter")
                .arguments(Arrays.asList(LocalDate.now()))
                .pageSize(10)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }


    @Bean
    public ItemProcessor<CountryDistinctView, CountryDistinctView> countryItemProcessor() {
        return item -> item; // No processing needed for now
    }

    @Bean
    public ItemWriter<CountryDistinctView> countryItemWriter() {
        return items -> {
            for (CountryDistinctView countryView : items) {
                System.out.println("Writing county: " + countryView.getName());
            }
        };
    }
}
