package cn.edu.xidian.dao;

import cn.edu.xidian.domain.User;
import cn.edu.xidian.untils.MybatisUntils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.List;


public class UserMapperTest {

    static Logger logger = Logger.getLogger(UserMapperTest.class);

    @Test
    public void test() {
        //1.获取SqlSession对象
        SqlSession sqlSession = MybatisUntils.getSqlSession();

        //2.方式一：getMapper
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = userMapper.getUserList();

        //2.方式二：
//        List<User> userList = sqlSession.selectList("cn.edu.xidian.dao.UserDao.getUserList");

        for (int i = 0; i < userList.size(); i++) {
            System.out.println(userList.get(i).toString());
        }

        sqlSession.close();
    }

    @Test
    public void getById() {
        //1.获取SqlSession对象
        SqlSession sqlSession = MybatisUntils.getSqlSession();

        //2.方式一：getMapper
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getUserById(1);

        //2.方式二：
//        List<User> userList = sqlSession.selectList("cn.edu.xidian.dao.UserDao.getUserList");

        System.out.println(user.toString());

        sqlSession.close();
    }

    @Test
    public void log4jTest(){
        //使用apache的log包，参数为当前类的class
//        Logger logger = Logger.getLogger(UserMapperTest.class);

        logger.info("info:进入了info log4jTest");
        logger.debug("debug:进入了debug方式");
        logger.error("error:进入了error方式");
    }

}