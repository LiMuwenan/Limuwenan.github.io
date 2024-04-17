import cn.edu.xidian.dao.BlogMapper;
import cn.edu.xidian.domain.Blog;
import cn.edu.xidian.untils.IDUtils;
import cn.edu.xidian.untils.MybatisUntils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.*;

public class MyTest {

    @Test
    public void addInitBlog(){
        SqlSession sqlSession = MybatisUntils.getSqlSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);

        Blog blog = new Blog();
//        blog.setBlog_id(IDUtils.getId());
//        blog.setBlog_title("MineCraft");
//        blog.setBlog_author("ligen");
//        blog.setCreate_time(new Date());
//        blog.setBlog_views(1268);
//
//        blogMapper.addBlog(blog);
//
//        blog.setBlog_id(IDUtils.getId());
//        blog.setBlog_title("Terraria");
//        blogMapper.addBlog(blog);
//
//        blog.setBlog_id(IDUtils.getId());
//        blog.setBlog_title("DNF");
//        blogMapper.addBlog(blog);
//
//        blog.setBlog_id(IDUtils.getId());
//        blog.setBlog_title("Need For Speed");
//        blogMapper.addBlog(blog);

        blog.setBlog_id(IDUtils.getId());
        blog.setBlog_title("MineCraft");
        blog.setBlog_author("wendan");
        blog.setCreate_time(new Date());
        blog.setBlog_views(1268);
        blogMapper.addBlog(blog);

        sqlSession.commit();

        sqlSession.close();
    }

    @Test
    public void queryBlogIF(){
        SqlSession sqlSession = MybatisUntils.getSqlSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);

        HashMap map = new HashMap();

        map.put("blog_title","MineCraft");
        map.put("blog_author","ligen");

        List<Blog> blogs = blogMapper.queryBlogIF(map);

        for (Blog blog: blogs) {
            System.out.println(blog);
        }

        sqlSession.close();
    }

    @Test
    public void queryBlogChoose(){
        SqlSession sqlSession = MybatisUntils.getSqlSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);

        HashMap map = new HashMap();

//        map.put("blog_title","MineCraft");
//        map.put("blog_author","ligen");
        map.put("blog_views",1268);

        List<Blog> blogs = blogMapper.queryBlogChoose(map);

        for (Blog blog: blogs) {
            System.out.println(blog);
        }

        sqlSession.close();
    }

    @Test
    public void queryBlogWhere(){
        SqlSession sqlSession = MybatisUntils.getSqlSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);

        HashMap map = new HashMap();

        map.put("blog_title","MineCraft");
        map.put("blog_author","ligen");
//        map.put("blog_views",1268);

        List<Blog> blogs = blogMapper.queryBlogWhere(map);

        for (Blog blog: blogs) {
            System.out.println(blog);
        }

        sqlSession.close();
    }

    @Test
    public void updateBlog(){
        SqlSession sqlSession = MybatisUntils.getSqlSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);

        HashMap map = new HashMap();

//        map.put("blog_title","MineCraft");
//        map.put("create_time",new Date());
        map.put("blog_views",12831);
        map.put("blog_author","wendan");

        blogMapper.updateBlog(map);

        sqlSession.commit();

        sqlSession.close();
    }

    @Test
    public void queryBlogForeach(){
        SqlSession sqlSession = MybatisUntils.getSqlSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);

        HashMap map = new HashMap();

        ArrayList<Integer> views = new ArrayList<Integer>();

        views.add(1268);

        map.put("views",views);

        List<Blog> blogs = blogMapper.queryBlogForeach(map);

        for (Blog blog: blogs) {
            System.out.println(blog);
        }

        sqlSession.close();
    }


}
