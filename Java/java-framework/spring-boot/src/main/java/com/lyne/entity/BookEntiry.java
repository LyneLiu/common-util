package com.lyne.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author nn_liu
 * @Created 2017-07-28-17:59
 */

@Table(name = "book")
public class BookEntiry {

    @Id
    @Column(name = "Id")
    private Long id;

    @Column(name = "Description")
    private String description;

    @Column(name = "Isbn")
    private String isbn;

    @Column(name = "Title")
    private String title;

    @Column(name = "Author_Id")
    private Long authorId;

    @Column(name = "Publisher_Id")
    private Long publisherId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    @Override
    public String toString(){
        return String.format("BookEntity{id=%d, description=%s, isbn=%s, title=%s, authorId=%d, publisherId=%d},",id, description, isbn, title, authorId, publisherId);
    }
}
