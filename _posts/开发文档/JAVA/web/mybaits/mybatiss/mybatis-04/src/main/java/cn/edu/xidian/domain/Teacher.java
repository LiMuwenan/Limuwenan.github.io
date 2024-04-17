package cn.edu.xidian.domain;

import java.io.Serializable;

public class Teacher implements Serializable {
    private int tech_id;
    private String tech_name;

    public Teacher() {
    }

    public Teacher(int tech_id, String tech_name) {
        this.tech_id = tech_id;
        this.tech_name = tech_name;
    }

    public int getTech_id() {
        return tech_id;
    }

    public void setTech_id(int tech_id) {
        this.tech_id = tech_id;
    }

    public String getTech_name() {
        return tech_name;
    }

    public void setTech_name(String tech_name) {
        this.tech_name = tech_name;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "tech_id=" + tech_id +
                ", tech_name='" + tech_name + '\'' +
                '}';
    }
}
