package com.summerpractice.BankKnowledgeBase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@MybatisTest
@Rollback(false)
@RunWith(SpringRunner.class)
@AutoConfigureMybatis
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class BaseTest {
    @Test
    public abstract void insert();
    @Test
    public abstract void delete();
    @Test
    public abstract void findByID();
    @Test
    public abstract void update();
}
