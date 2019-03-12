package edu.ahpu.boke.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.ahpu.boke.dao.FaceDao;
import edu.ahpu.boke.dao.UserDao;
import edu.ahpu.boke.domain.User;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private FaceDao faceDao;

    // 判断输入的用户名是否已存在
    public boolean isUserNameExist(String userName) {
        return userDao.findFirstByCondition("and o.name=?", new Object[] { userName }, false) != null;
    }

    // 添加用户信息
    public void addUser(String userName, String password, int faceId) {
        User user = new User();
        user.setName(userName);
        user.setPassword(password);
        user.setFace(faceDao.findById(faceId));
        user.setVisitCount(0);
        user.setTotalPlayCount(0);
        userDao.save(user);
    }

    // 根据用户名和密码查找用户
    public User findUser(String username, String password) {
        return userDao.findFirstByCondition("and o.name=? and o.password=?", new Object[] { username, password }, false);
    }

    // 登录时，更新用户的最后登录时间。
    public void updateLastLoginTime(User user) {
        userDao.update(user);
    }
}
