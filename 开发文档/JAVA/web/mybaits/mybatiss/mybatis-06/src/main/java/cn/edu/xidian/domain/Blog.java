package cn.edu.xidian.domain;

import java.util.Date;

public class Blog {
    private String blog_id;
    private String blog_title;
    private String blog_author;

    private Date create_time;

    private int blog_views;

    public Blog() {
    }

    public Blog(String blog_id, String blog_title, String blog_author, Date create_time, int blog_views) {
        this.blog_id = blog_id;
        this.blog_title = blog_title;
        this.blog_author = blog_author;
        this.create_time = create_time;
        this.blog_views = blog_views;
    }

    public String getBlog_id() {
        return blog_id;
    }

    public void setBlog_id(String blog_id) {
        this.blog_id = blog_id;
    }

    public String getBlog_title() {
        return blog_title;
    }

    public void setBlog_title(String blog_title) {
        this.blog_title = blog_title;
    }

    public String getBlog_author() {
        return blog_author;
    }

    public void setBlog_author(String blog_author) {
        this.blog_author = blog_author;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public int getBlog_views() {
        return blog_views;
    }

    public void setBlog_views(int blog_views) {
        this.blog_views = blog_views;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "blog_id=" + blog_id +
                ", blog_title='" + blog_title + '\'' +
                ", blog_author='" + blog_author + '\'' +
                ", create_time=" + create_time +
                ", blog_views=" + blog_views +
                '}';
    }
}
