/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.entity;

import java.util.List;

public class TypeNode {
    private KnowledgeType parentt;
    private List<KnowledgeType> child;

    public KnowledgeType getParentt() {
        return parentt;
    }

    public void setParentt(KnowledgeType parentt) {
        this.parentt = parentt;
    }

    public void setChild(List<KnowledgeType> child) {
        this.child = child;
    }

    public List<KnowledgeType> getChild() {
        return child;
    }

    public TypeNode(KnowledgeType parentt, List<KnowledgeType> child) {
        this.parentt = parentt;
        this.child = child;
    }
}
