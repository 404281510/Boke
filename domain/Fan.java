package edu.ahpu.boke.domain;

/**
 * Fan entity. @author MyEclipse Persistence Tools
 */

public class Fan implements java.io.Serializable {

	// Fields

	private Integer id;
	private User userByListenerId;
	private User userByHostId;

	// Constructors

	/** default constructor */
	public Fan() {
	}

	/** full constructor */
	public Fan(User userByListenerId, User userByHostId) {
		this.userByListenerId = userByListenerId;
		this.userByHostId = userByHostId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUserByListenerId() {
		return this.userByListenerId;
	}

	public void setUserByListenerId(User userByListenerId) {
		this.userByListenerId = userByListenerId;
	}

	public User getUserByHostId() {
		return this.userByHostId;
	}

	public void setUserByHostId(User userByHostId) {
		this.userByHostId = userByHostId;
	}

}