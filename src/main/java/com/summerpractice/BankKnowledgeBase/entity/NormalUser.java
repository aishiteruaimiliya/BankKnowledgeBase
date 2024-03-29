/*
 * author:huangping
 *
 */

/*
 * author:huangping
 *
 */

package com.summerpractice.BankKnowledgeBase.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "normal_user")
public class NormalUser extends User {
    @ManyToOne
    @JoinColumn(name = "dep_id")
    private Department department;
    @ManyToMany(targetEntity = Knowledge.class)
    @JoinTable(name = "favorite",
            joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},
            inverseJoinColumns ={@JoinColumn(name = "know_id",referencedColumnName = "know_id")})
    private List<Knowledge> favorite=new ArrayList<>();

    @ManyToMany(targetEntity = Knowledge.class)
    @JoinTable(name = "recommend",
            joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},
            inverseJoinColumns ={@JoinColumn(name = "know_id",referencedColumnName = "know_id")})
    private Set<Knowledge> recommends=new HashSet<>();
    public NormalUser(String name, String account, String password,int dep_id) {
        super(name, account, password);
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Knowledge> getFavorite() {
        return favorite;
    }

    public void setFavorite(List<Knowledge> favorite) {
        this.favorite = favorite;
    }

    public NormalUser(String name, String account, String password) {
        super(name, account, password);
    }
    public NormalUser(){
    }
    public void setDepartment(String dep_id){
        if(this.department==null) department=new Department();
        this.department.setDepId(dep_id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"account\":\"")
                .append(account).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public Set<Knowledge> getRecommends() {
        return recommends;
    }

    public void setRecommends(Set<Knowledge> recommends) {
        this.recommends = recommends;
    }
}
