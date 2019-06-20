package com.summerpractice.BankKnowledgeBase;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.summerpratice.BankBankKnowledgeBase.dao")
@SpringBootApplication
public class BankKnowledgeBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankKnowledgeBaseApplication.class, args);
    }

}
