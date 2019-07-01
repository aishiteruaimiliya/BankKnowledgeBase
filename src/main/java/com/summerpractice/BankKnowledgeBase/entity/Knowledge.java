/*
 * author:huangping
 *
 */

/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//knowId int auto_increment primary key,
//        title varchar(50) not null,
//        detail text not null,
//        typeid int,
//        digest varchar(20),
//        from_user_id int not null,
//        judge_id int not null,
//        clicked int default 0,
//        status varchar(20) not null,
//        foreign key (typeid) references knowledgetype(typeid),
//        foreign key (from_user_id) references normalUser(normal_id),
////        foreign key(judge_id) references expertUser(expect_id)
@Entity
@Table(name = "knowledge")
public class Knowledge {
    @Id
    @GenericGenerator(strategy = "uuid",name = "myuuid")
    @GeneratedValue(generator = "myuuid")
    @Column(name = "know_id",length = 50)
    private String knowId;
    @Column(name = "title")
    private String title;
    @Column(name = "detail")
    private String detail;
    @Column(name = "digest")
    private String digest;
    @ManyToOne
    @JoinColumn(name = "fromUserID")
    private NormalUser normalUser;
    @ManyToOne
    @JoinColumn(name = "judge_id")
    private ExpertUser expertUser;
    @Column(name = "clicked",columnDefinition = "int default 0")
    private int clicked;
    @Column(name = "status")
    private String status;
    //todo 映射
    //一对多
    @Column(name = "disable",columnDefinition = "tinyint(1) default false")
    private boolean disable;
    @ManyToOne(targetEntity = KnowledgeType.class)
    @JoinColumn(name = "type_id")
    private KnowledgeType knowledgeType;
    @ManyToMany(targetEntity = NormalUser.class,mappedBy = "favorite")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<NormalUser> normalUsers=new ArrayList<>();

    public KnowledgeType getTypeId() {
        return knowledgeType;
    }

    public void setTypeId(KnowledgeType knowledgeType) {
        this.knowledgeType = knowledgeType;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @OneToMany(targetEntity = NormalUser.class)
    private List<Comment> comments=new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKnowId() {
        return knowId;
    }

    public void setKnowId(String knowId) {
        this.knowId = knowId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public NormalUser getNormalUser() {
        return normalUser;
    }

    public void setNormalUser(NormalUser normalUser) {
        this.normalUser = normalUser;
    }

    public ExpertUser getExpertUser() {
        return expertUser;
    }

    public void setExpertUser(ExpertUser expertUser) {
        this.expertUser = expertUser;
    }

    public int getClicked() {
        return clicked;
    }

    public void setClicked(int clicked) {
        this.clicked = clicked;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public List<NormalUser> getNormalUsers() {
        return normalUsers;
    }

    public void setNormalUsers(List<NormalUser> normalUsers) {
        this.normalUsers = normalUsers;
    }

    public Knowledge(String title, String detail, String digest, int clicked, String status, boolean disable) {
        this.title = title;
        this.detail = detail;
        this.digest = digest;
        this.clicked = clicked;
        this.status = status;
        this.disable = disable;
    }

    public Knowledge() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"knowId\":\"")
                .append(knowId).append('\"');
        sb.append(",\"title\":\"")
                .append(title).append('\"');
        sb.append(",\"detail\":\"")
                .append(detail).append('\"');
        sb.append(",\"digest\":\"")
                .append(digest).append('\"');
        sb.append(",\"clicked\":")
                .append(clicked);
        sb.append(",\"knowledgeType\":")
                .append(knowledgeType);
        sb.append(",\"comments\":")
                .append(comments);
        sb.append('}');
        return sb.toString();
    }
}
