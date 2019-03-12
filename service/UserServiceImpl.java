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

    // �ж�������û����Ƿ��Ѵ���
    public boolean isUserNameExist(String userName) {
        return userDao.findFirstByCondition("and o.name=?", new Object[] { userName }, false) != null;
    }

    // ����û���Ϣ
    public void addUser(String userName, String password, int faceId) {
        User user = new User();
        user.setName(userName);
        user.setPassword(password);
        user.setFace(faceDao.findById(faceId));
        user.setVisitCount(0);
        user.setTotalPlayCount(0);
        userDao.save(user);
    }

    // �����û�������������û�
    public User findUser(String username, String password) {
        return userDao.findFirstByCondition("and o.name=? and o.password=?", new Object[] { username, password }, false);
    }

    // ��¼ʱ�������û�������¼ʱ�䡣
    public void updateLastLoginTime(User user) {
        userDao.update(user);
    }
}
