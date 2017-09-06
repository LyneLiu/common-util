package com.lyne.mapper;

import com.lyne.entity.UserEntity;
import com.lyne.mapper.builder.UserSqlBuilder;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
	
	@Select("SELECT * FROM users")
	@Results({
		@Result(property = "userSex",  column = "user_sex", javaType = String.class),
		@Result(property = "nickName", column = "nick_name")
	})
	List<UserEntity> getAll();
	
	@Select("SELECT u.id, u.username, u.password, u.user_sex, u.nick_name FROM users as u WHERE id = #{id}")
	@Results({
		@Result(property = "userSex",  column = "user_sex", javaType = String.class),
		@Result(property = "nickName", column = "nick_name")
	})
	UserEntity getOne(Long id);

	@Insert("INSERT INTO users(userName,passWord,user_sex) VALUES(#{userName}, #{passWord}, #{userSex})")
	void insert(UserEntity user);

	@Update("UPDATE users SET userName=#{userName},nick_name=#{nickName} WHERE id =#{id}")
	void update(UserEntity user);

	@Delete("DELETE FROM users WHERE id =#{id}")
	void delete(Long id);


	@SelectProvider(type = UserSqlBuilder.class, method = "getUserByName")
	@Results({
			@Result(property = "userSex",  column = "user_sex", javaType = String.class),
			@Result(property = "nickName", column = "nick_name")
	})
	List<UserEntity> getUserByName(String userName);

}
