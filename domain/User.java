package edu.ahpu.boke.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

    // Fields

    private Integer id;
    private Face face;
    private String name;
    private String password;
    private Timestamp lastLoginTime;
    private int visitCount;
    private int totalPlayCount;
    private Set videos = new HashSet(0);
    private Set fansForListenerId = new HashSet(0);
    private Set messagesForReceiverId = new HashSet(0);
    private Set comments = new HashSet(0);
    private Set messagesForSenderId = new HashSet(0);
    private Set fansForHostId = new HashSet(0);

    // Constructors

    /** default constructor */
    public User() {
    }

    /** minimal constructor */
    public User(Face face, String name, String password, Integer visitCount, Integer totalPlayCount) {
        this.face = face;
        this.name = name;
        this.password = password;
        this.visitCount = visitCount;
        this.totalPlayCount = totalPlayCount;
    }

    /** full constructor */
    public User(Face face, String name, String password, Timestamp lastLoginTime, Integer visitCount, Integer totalPlayCount, Set videos, Set fansForListenerId, Set messagesForReceiverId, Set comments, Set messagesForSenderId, Set fansForHostId) {
        this.face = face;
        this.name = name;
        this.password = password;
        this.lastLoginTime = lastLoginTime;
        this.visitCount = visitCount;
        this.totalPlayCount = totalPlayCount;
        this.videos = videos;
        this.fansForListenerId = fansForListenerId;
        this.messagesForReceiverId = messagesForReceiverId;
        this.comments = comments;
        this.messagesForSenderId = messagesForSenderId;
        this.fansForHostId = fansForHostId;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Face getFace() {
        return this.face;
    }

    public void setFace(Face face) {
        this.face = face;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getLastLoginTime() {
        return this.lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getVisitCount() {
        return this.visitCount;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

    public Integer getTotalPlayCount() {
        return this.totalPlayCount;
    }

    public void setTotalPlayCount(Integer totalPlayCount) {
        this.totalPlayCount = totalPlayCount;
    }

    public Set getVideos() {
        return this.videos;
    }

    public void setVideos(Set videos) {
        this.videos = videos;
    }

    public Set getFansForListenerId() {
        return this.fansForListenerId;
    }

    public void setFansForListenerId(Set fansForListenerId) {
        this.fansForListenerId = fansForListenerId;
    }

    public Set getMessagesForReceiverId() {
        return this.messagesForReceiverId;
    }

    public void setMessagesForReceiverId(Set messagesForReceiverId) {
        this.messagesForReceiverId = messagesForReceiverId;
    }

    public Set getComments() {
        return this.comments;
    }

    public void setComments(Set comments) {
        this.comments = comments;
    }

    public Set getMessagesForSenderId() {
        return this.messagesForSenderId;
    }

    public void setMessagesForSenderId(Set messagesForSenderId) {
        this.messagesForSenderId = messagesForSenderId;
    }

    public Set getFansForHostId() {
        return this.fansForHostId;
    }

    public void setFansForHostId(Set fansForHostId) {
        this.fansForHostId = fansForHostId;
    }

}