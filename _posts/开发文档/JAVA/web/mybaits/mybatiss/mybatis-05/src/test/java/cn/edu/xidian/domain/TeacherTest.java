package cn.edu.xidian.domain;

import cn.edu.xidian.dao.TeacherMapper;
import cn.edu.xidian.untils.MybatisUntils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class TeacherTest {

    @Test
    public void getTeacherListTest(){
        SqlSession sqlSession = MybatisUntils.getSqlSession();

        TeacherMapper teacherMapper = sqlSession.getMapper(TeacherMapper.class);
        List<Teacher> teachers = teacherMapper.getTeacherList();

        for(Teacher teacher : teachers){
            System.out.println(teacher);
        }

        sqlSession.close();
    }

    @Test
    public void getTeacherByIdIncludeStudentTeat(){
        SqlSession sqlSession = MybatisUntils.getSqlSession();

        TeacherMapper teacherMapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher = teacherMapper.getTeacherByIdIncludeStudent2(1);

        System.out.println(teacher);



        sqlSession.close();
    }
}
