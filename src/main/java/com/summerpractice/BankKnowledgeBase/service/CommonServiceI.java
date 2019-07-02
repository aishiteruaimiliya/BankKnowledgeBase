/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.service;

import com.summerpractice.BankKnowledgeBase.entity.KnowledgeType;

public interface CommonServiceI {
    public boolean changePassword(String account,String old,String newPassword);

    /***
     * 根据叶子查询根节点
     * @param typeId
     * @return
     */
    public KnowledgeType findRootTypeByTypeId(String typeId);
}
