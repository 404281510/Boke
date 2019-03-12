package edu.ahpu.boke.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Channel entity. @author MyEclipse Persistence Tools
 */

public class Channel implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Set videos = new HashSet(0);

	// Constructors

	/** default constructor */
	public Channel() {
	}

	/** minimal constructor */
	public Channel(String name) {
		this.name = name;
	}

	/** full constructor */
	public Channel(String name, Set videos) {
		this.name = name;
		this.videos = videos;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getVideos() {
		return this.videos;
	}

	public void setVideos(Set videos) {
		this.videos = videos;
	}

}