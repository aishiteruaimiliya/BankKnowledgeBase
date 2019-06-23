/*
 * author:huangping
 *
 */


package com.summerpractice.BankKnowledgeBase.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/***
 * 	comment_id int auto_increment primary key,
 * 	know_id int,
 *     normal_user_id int,
 *     star int ,
 *     foreign key (normal_user_id) references normalUser(normal_id),
 *     foreign key(know_id) references knowledge(know_id)
 */
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GenericGenerator(strategy = "uuid",name = "myuuid")
    @GeneratedValue(generator = "myuuid")
    @Column(name = "comment_id",length = 50)
    private String commentId;
    /**
     * 一个知识对应多条评论
     */
    @ManyToOne
    @JoinColumn(name = "knowId")
    private Knowledge knowledge;
    /***
     * 一个用户对应多条评论
     */
    @ManyToOne
    @JoinColumn(name = "normal_id")
    private NormalUser normaluser;
    @Column(name="star")
    private int star;
    @Column(name = "content")
    private String content;
    @Column(name = "disable",columnDefinition = "tinyint(1) default false")
    private boolean disable;

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public Knowledge getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(Knowledge knowledge) {
        this.knowledge = knowledge;
    }

    public NormalUser getNormaluser() {
        return normaluser;
    }

    public void setNormaluser(NormalUser normaluser) {
        this.normaluser = normaluser;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comment(int star, String content, boolean disable) {
        this.star = star;
        this.content = content;
        this.disable = disable;
    }
    public Comment(){}
}
