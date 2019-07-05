/*
 * author:huangping
 *
 */

/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.service;

import com.summerpractice.BankKnowledgeBase.entity.*;

import java.util.List;
import java.util.Set;

public interface NormalUserServiceI {
    /**
     *
     * @param account 登录账号
     * @param password 登陆密码
     * @return 是否存在用户
     */
    public User login(String account,String password);

    public User getUserByAccount(String account);

    /**
     * 添加评论
     * @param comment 评论
     * @return 是否成功
     */
    public boolean addComment(Comment  comment);
    public boolean addComment(Comment comment,String know_id);

    /**
     * 添加知识
     * @param knowledge 知识
     * @return 是否添加成功
     */
    public boolean addKnowledge(Knowledge knowledge);

    /**
     * 添加收藏
     * @param userid 用户id
     * @param knowId 知识id
     * @return 是否添加成功
     */
    public boolean addFavorite(NormalUser normalUser,String knowId);

    /***
     * 删除收藏夹
     * @param userid 用户id
     * @param knowid 知识id
     * @return 是否删除成功
     */
    public boolean deleteFavorite(NormalUser normalUser,String knowid);

    /***
     * 根据用户查询推荐知识
     * @param normalUser 用户
     * @return 推荐的知识条目
     */
    public Set<Knowledge> getRecommend(NormalUser normalUser);

    /***
     * 根据知识类别获取知识
     * @param knowledgeType 知识类别
     * @return 知识
     */
    public List<Knowledge> getKnowledgeByType(KnowledgeType knowledgeType);

    /***
     * 根据知识类别获取知识
     * @param typeid 知识类别
     * @return 下一个级别的知识类别
     */
    public List<KnowledgeType> getKnowledgeTypeByPreID(String typeid);
    public List<KnowledgeType> getKnowledgeType(KnowledgeType knowledgeType);

    /***
     *
     * @param normalUser 用户
     * @return 用户的收藏夹的知识
     */
    public List<Knowledge> getFavorite(String id);

    public List<KnowledgeType> getFirstLayer();

    public  boolean changePassword(String id,String oldPass,String newPass);

    public KnowledgeType findKnowlegeTypeById(String typeid);
    public List<KnowledgeType> findnextKnowlegeTypeByPreId(String pretypeid);

    public List<Comment> getCommentByKnowledge(Knowledge knowledge);

    public List<Comment> getCommentByKnowledgeId(String knowId);

    public List<Knowledge> searchByKeyWord(String keyword);

    public List<KnowledgeType> getLastLayer();

    public List<Knowledge> getRankBoard();

    public ExpertUser findExpertUserByTypeId(String typeid);

    public List<Knowledge> findKnowledgeByNormalUser(String account);

    public List<Knowledge> findKnowledgeByKeyWord(String keyword);

    public Knowledge getCaogaoByID(String account);

    public boolean addCaogao(String userid,Knowledge knowledge);


    public Knowledge getKnledgeByKnowId(String knowID,String account);

    public Set<Knowledge> findKnowledgeByTypeId(String typeId);

}
