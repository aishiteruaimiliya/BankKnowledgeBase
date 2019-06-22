package com.summerpractice.BankKnowledgeBase;

import org.junit.Test;



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
