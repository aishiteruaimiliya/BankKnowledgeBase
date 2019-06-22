package com.summerpractice.BankKnowledgeBase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.summerpractice"})
@EntityScan(basePackages = {"com.summerpractice.BankKnowledgeBase.entity"})
@EnableJpaRepositories("com.summerpractice.BankKnowledgeBase.dao")
@SpringBootApplication
public class BankKnowledgeBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankKnowledgeBaseApplication.class, args);
    }

}
