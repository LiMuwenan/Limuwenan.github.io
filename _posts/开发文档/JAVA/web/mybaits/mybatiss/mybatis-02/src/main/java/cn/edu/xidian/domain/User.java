package cn.edu.xidian.domain;

import java.io.Serializable;

public class User implements Serializable {
    private int user_id;
    private String name;
    private String pwd;

    public User() {
    }

    public User(int user_id, String name, String pwd) {
        this.user_id = user_id;
        this.name = name;
        this.pwd = pwd;
    }

    public int getId() {
        return user_id;
    }

    public void setId(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
