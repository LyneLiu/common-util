package com.lyne.util;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * Created by nn_liu on 2016/8/17.
 */
public class MyBatisUtil {
    static SqlSessionFactory factory;

    static {
        //默认获取src的根目录
        String resource = "mybatis-config.xml";
        //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
        InputStream is = MyBatisUtil.class.getClassLoader().getResourceAsStream(resource);
        factory = new SqlSessionFactoryBuilder().build(is);
    }

    /**
     * 获取SqlSessionFactory
     * @return SqlSessionFactory
     */
    public static SqlSessionFactory GetSqlSessionFactory() {
        return factory;
    }


    /**
     * 获取SqlSession
     * @return SqlSession
     */
    public static SqlSession GetSqlSession() {
        return GetSqlSessionFactory().openSession();
    }


    /**
     * 获取SqlSession
     * @param isAutoCommit
     *         true 表示创建的SqlSession对象在执行完SQL之后会自动提交事务
     *         false 表示创建的SqlSession对象在执行完SQL之后不会自动提交事务，这时就需要我们手动调用sqlSession.commit()提交事务
     * @return SqlSession
     */
    public static SqlSession GetSqlSession(boolean isAutoCommit) {
        return GetSqlSessionFactory().openSession(isAutoCommit);
    }


    /**
     * 关闭SqlSession
     */
    public static void closeSqlSession(SqlSession sqlSession) {
        try {
            sqlSession.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession = null;
        }
    }
}
