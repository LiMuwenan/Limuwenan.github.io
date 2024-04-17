package cn.edu.xidian.untils;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class IDUtils {

    public static String getId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    @Test
    public void test(){
        System.out.println(IDUtils.getId());
    }
}
