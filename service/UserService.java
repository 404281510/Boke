package edu.ahpu.boke.service;

import edu.ahpu.boke.domain.User;

public interface UserService {
    boolean isUserNameExist(String username);

    void addUser(String username, String password, int faceId);

    public User findUser(String username, String password);

    public void updateLastLoginTime(User user);
}
