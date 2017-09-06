package com.lyne.service;

import com.lyne.domain.User;
import com.lyne.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nn_liu on 2016/8/17.
 */

@Repository
public class UserServiceImpl {

    private SqlSession session = null;

    public List<User> getAll(){
        try {
            session = MyBatisUtil.GetSqlSession(true);
            List<User> users = session.selectList("com.lyne.dao.UserDao.getAllUsers");
            return users;
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            MyBatisUtil.closeSqlSession(session);
        }
    }


    /**
     * 添加用户
     * @param u
     * @return
     */
    public int addUser(User u){
        try {
            session = MyBatisUtil.GetSqlSession(true);
            int result = session.insert("com.lyne.dao.UserDao.addUser", u);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }finally {
            MyBatisUtil.closeSqlSession(session);
        }
    }

    /**
     * 更新用户
     * @param u
     * @return
     */
    public int updateUser(User u){
        try {
            session = MyBatisUtil.GetSqlSession(true);
            int result = session.update("com.lyne.dao.UserDao.updateUser", u);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }finally {
            MyBatisUtil.closeSqlSession(session);
        }
    }

    /**
     * 根据用户age删除用户
     * @return
     */
    public int deleteUser(int id){
        try {
            session = MyBatisUtil.GetSqlSession(true);
            int result = session.delete("com.lyne.dao.UserDao.deleteUserByage", id);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }finally {
            MyBatisUtil.closeSqlSession(session);
        }
    }
}
