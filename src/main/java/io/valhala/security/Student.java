package io.valhala.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Student {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long uid; //unique ID for the object
	private String firstName, lastName, sid;
	
	public Student(String firstName, String lastName, String sid) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.sid = sid;
	}
	
	public Student(String firstName, String lastName, String sid, Long uid) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.sid = sid;
		this.uid = uid;
	}
	
	public Student() {}

}
