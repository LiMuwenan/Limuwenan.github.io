package cn.edu.xidian.dao;

import cn.edu.xidian.domain.User;
import cn.edu.xidian.untils.MybatisUntils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class UserDaoTest{

    @Test
    public void test(){
        //1.获取SqlSession对象
        SqlSession sqlSession = MybatisUntils.getSqlSession();

        //2.方式一：getMapper
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> userList = userDao.getUserList();

        //2.方式二：
//        List<User> userList = sqlSession.selectList("cn.edu.xidian.dao.UserDao.getUserList");

        for (int i = 0; i < userList.size(); i++) {
            System.out.println(userList.get(i).toString());
        }

        sqlSession.close();
    }

    @Test
    public void getUserById(){
        SqlSession sqlSession = MybatisUntils.getSqlSession();

        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = userDao.getUserById(5);

        System.out.println(user.toString());

        sqlSession.close();
    }

    @Test
    public void insertUser(){
        SqlSession sqlSession = MybatisUntils.getSqlSession();

        UserDao userDao = sqlSession.getMapper(UserDao.class);

        userDao.insertUser(new User(1,"lisi","150621"));

        //提交事务
        sqlSession.commit();

        sqlSession.close();
    }

    @Test
    public void updateUserByID(){
        SqlSession sqlSession = MybatisUntils.getSqlSession();

        UserDao userDao = sqlSession.getMapper(UserDao.class);

        userDao.updateUserById(new User(5,"zhangsna","15123"));

        //提交事务
        sqlSession.commit();

        sqlSession.close();
    }

    @Test
    public void deleteUserById(){
        SqlSession sqlSession = MybatisUntils.getSqlSession();

        UserDao userDao = sqlSession.getMapper(UserDao.class);

        for (int i = 5; i < 9; i++) {
            userDao.deleteUserById(i);
        }

        sqlSession.commit();
        sqlSession.close();

    }

    @Test
    public void getUserByLimitTest(){
        SqlSession sqlSession = MybatisUntils.getSqlSession();

        UserDao userMapper = sqlSession.getMapper(UserDao.class);

        HashMap<String, Integer> map = new HashMap<>();
        map.put("startIndex",0);
        map.put("pageSize",2);
        map.put("startIndex",2);

        List<User> userList = userMapper.getUserByLimit(map);

        for (User user : userList) {
            System.out.println(user.toString());
        }

        sqlSession.close();
    }


}
