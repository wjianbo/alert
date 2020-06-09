package com.example.demo;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private Date date;
	@Column(nullable = false)
    private Integer doRepeat;
    @Column(nullable = false)
    private String user;
    
	public long getId() {
		return id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
    public Integer getRepeat() {
		return doRepeat;
	}
	public void setRepeat(Integer repeat) {
		this.doRepeat = repeat;
	}

    
}