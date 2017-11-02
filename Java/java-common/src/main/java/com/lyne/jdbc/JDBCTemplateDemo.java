package com.lyne.jdbc;

/**
 * Created by nn_liu on 2016/9/27.
 */

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * JDBC Template 的三种回调接口：ResultSetExtractor、RawCallbackHandler、RowMapper
 */
public class JDBCTemplateDemo {

    public static DriverManagerDataSource dataSource = null;

    private DataSource getDataSource() throws IOException {
        /*获取配置信息*/
        Properties prop = new Properties();
        InputStream input = null;
        input = JDBCTemplateDemo.class.getClassLoader().getResourceAsStream("application.properties");

        // load a properties file
        prop.load(input);
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(prop.getProperty("datasource.driver-class-name"));
        dataSource.setUrl(prop.getProperty("datasource.url"));
        dataSource.setUsername(prop.getProperty("datasource.username"));
        dataSource.setPassword(prop.getProperty("datasource.password"));
        return dataSource;
    }

    private JdbcTemplate getJdbcTemplate() throws IOException {
        return new JdbcTemplate(getDataSource());
    }

    /*ResultSetExtractor接口：返回Object类型结果，需要对查询结果进行强制转换*/
    public int ResultSetExtractorFun() throws IOException {

        List<Book> bookList = (List<Book>) getJdbcTemplate().query("select * from book", new ResultSetExtractor() {
            @Override
            public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<Book> books = new ArrayList<Book>();
                while (resultSet.next()) {
                    Book book = new Book();
                    book.setUsername(resultSet.getString(2));
                    book.setIsbn(resultSet.getString(3));
                    book.setTitle(resultSet.getString(4));
                    book.setAuthor(resultSet.getString(5));
                    book.setDescription(resultSet.getString(6));
                    books.add(book);
                }
                return books;
            }
        });

        return bookList.size();
    }

    /*RowCallbackHandler接口：返回值为void*/
    public int RowCallbackHandlerFun() throws IOException {
        final List<Book> books = new ArrayList<Book>();
        getJdbcTemplate().query("select * from book", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                Book book = new Book();
                book.setUsername(resultSet.getString(2));
                book.setIsbn(resultSet.getString(3));
                book.setTitle(resultSet.getString(4));
                book.setAuthor(resultSet.getString(5));
                book.setDescription(resultSet.getString(6));
                books.add(book);
            }
        });
        return books.size();
    }

    /*RowMapper接口：查询结果为List类型*/
    public int RowMapperFun() throws IOException {
        List<Book> bookList = getJdbcTemplate().query("select * from book", (resultSet, i) -> {
            Book book = new Book();
            book.setUsername(resultSet.getString(2));
            book.setIsbn(resultSet.getString(3));
            book.setTitle(resultSet.getString(4));
            book.setAuthor(resultSet.getString(5));
            book.setDescription(resultSet.getString(6));
            return book;
        });
        return bookList.size();
    }

    public static void main(String[] args) throws IOException {
        /*创建demo实例*/
        JDBCTemplateDemo jdbcTemplateDemo = new JDBCTemplateDemo();
        System.out.println(jdbcTemplateDemo.ResultSetExtractorFun());
        System.out.println(jdbcTemplateDemo.RowCallbackHandlerFun());
        System.out.println(jdbcTemplateDemo.RowMapperFun());
    }

}
