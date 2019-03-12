package edu.ahpu.boke.action;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.stereotype.Controller;

import edu.ahpu.boke.domain.User;
import edu.ahpu.boke.service.UserService;
import edu.ahpu.boke.util.SessionUtils;

@SuppressWarnings("serial")
@Controller
public class LoginAction extends BaseAction {
    @Resource
    private UserService userService;

    private String userName;
    private String password;
    private String verificationCode;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    // 初始化登录页面
    public String init() {
        return "login";
    }
    

    // 执行登录操作
    public String login() {
        // 判断验证码是否正确
        if (!SessionUtils.isCodeMatch(request)) {
            this.addFieldError("verification_code_error", "验证码错误！");
            return "login";
        }

        User u = userService.findUser(userName, password);
        if (u == null) {
            this.addFieldError("invalid_user_error", "用户名或密码错误！");
            return "login";
        } else {
            // 修改用户的最后登录时间
            u.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
            userService.updateLastLoginTime(u);
            // 登录成功，将用户对象存入session。
            SessionUtils.setUserToSession(request, u);
        }
        return "back_to_main";
    }

    // 执行注销操作
    public String logout() {
        // 删除之前存入session的用户对象
        SessionUtils.removeUserFormSession(request);
        return "back_to_main";
    }
}
