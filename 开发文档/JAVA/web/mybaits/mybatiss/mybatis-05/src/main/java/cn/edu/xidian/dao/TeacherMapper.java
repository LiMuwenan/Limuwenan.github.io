package cn.edu.xidian.dao;

import cn.edu.xidian.domain.Teacher;

import java.util.List;

public interface TeacherMapper {

    List<Teacher> getTeacherList();

    //获得指定id的老师及其所有学生
    Teacher getTeacherByIdIncludeStudent(int tech_id);

    //获得指定id的老师及其所有学生
    Teacher getTeacherByIdIncludeStudent2(int tech_id);
}
