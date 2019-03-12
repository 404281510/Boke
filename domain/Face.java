package edu.ahpu.boke.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Face entity. @author MyEclipse Persistence Tools
 */

public class Face implements java.io.Serializable {

	// Fields

	private Integer id;
	private String picFileName;
	private String description;
	private Set users = new HashSet(0);

	// Constructors

	/** default constructor */
	public Face() {
	}

	/** minimal constructor */
	public Face(String picFileName) {
		this.picFileName = picFileName;
	}

	/** full constructor */
	public Face(String picFileName, String description, Set users) {
		this.picFileName = picFileName;
		this.description = description;
		this.users = users;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPicFileName() {
		return this.picFileName;
	}

	public void setPicFileName(String picFileName) {
		this.picFileName = picFileName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set getUsers() {
		return this.users;
	}

	public void setUsers(Set users) {
		this.users = users;
	}

}