package cn.edu.xidian.domain;

import java.io.Serializable;

public class Student implements Serializable {
    private int stu_id;
    private String stu_name;
    private int stu_tid;

    public Student() {
    }

    public Student(int stu_id, String stu_name, int stu_tid) {
        this.stu_id = stu_id;
        this.stu_name = stu_name;
        this.stu_tid = stu_tid;
    }

    public int getStu_id() {
        return stu_id;
    }

    public void setStu_id(int stu_id) {
        this.stu_id = stu_id;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public int getStu_tid() {
        return stu_tid;
    }

    public void setStu_tid(int stu_tid) {
        this.stu_tid = stu_tid;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stu_id=" + stu_id +
                ", stu_name='" + stu_name + '\'' +
                ", stu_tid=" + stu_tid +
                '}';
    }
}
