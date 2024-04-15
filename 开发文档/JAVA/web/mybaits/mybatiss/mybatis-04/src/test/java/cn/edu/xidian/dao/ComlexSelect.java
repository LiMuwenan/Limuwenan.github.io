package cn.edu.xidian.dao;

import cn.edu.xidian.domain.Student;
import cn.edu.xidian.domain.Teacher;
import cn.edu.xidian.untils.MybaitsUntils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class ComlexSelect {

    @Test
    public void getTeacherTest(){
        SqlSession sqlSession = MybaitsUntils.getSqlSession();

        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> student = studentMapper.getStudentList();

        for(Student st : student){
            System.out.println(st);
        }

        sqlSession.close();

    }

    @Test
    public void getStudentTest(){
        SqlSession sqlSession = MybaitsUntils.getSqlSession();

        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = studentMapper.getStudent();

        for(Student student : students){
            System.out.println(student.toString());
        }

        sqlSession.close();
    }
}
