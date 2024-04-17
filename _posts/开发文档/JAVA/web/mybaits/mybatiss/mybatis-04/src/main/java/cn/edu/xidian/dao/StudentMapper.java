package cn.edu.xidian.dao;

import cn.edu.xidian.domain.Student;

import java.util.List;

public interface StudentMapper {

    //查询所有学生的信息及对应的老师
    public List<Student> getStudentList();

    public List<Student> getStudent();
}
