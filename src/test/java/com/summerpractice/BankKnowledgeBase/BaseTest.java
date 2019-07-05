/*
 * author:huangping
 *
 */

/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase;

import com.alibaba.fastjson.JSON;
import com.summerpractice.BankKnowledgeBase.entity.Knowledge;
import com.summerpractice.BankKnowledgeBase.entity.NormalUser;
import com.summerpractice.BankKnowledgeBase.entity.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public  class BaseTest {
    @Test
    public  void insert(){
        User user=new NormalUser();
        user.setName("huangping");
        user.setPassword("123456");
        user.setDisable(false);
        user.setAccount("1235");
        user.setId("12346");
        System.out.println(JSON.toJSONString(user));

        List<Knowledge> knowledges=new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            Knowledge knowledge=new Knowledge();
            knowledge.setStatus("dafb");
            knowledge.setClicked(1);
            knowledge.setDetail("王潇大帅哥"+i);
            knowledge.setTitle("dakjda");
            knowledges.add(knowledge);
        }
        System.out.println(JSON.toJSONString(knowledges,true));
        System.out.println(knowledges.toString());

    }

}
