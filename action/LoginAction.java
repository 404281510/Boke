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

    // ��ʼ����¼ҳ��
    public String init() {
        return "login";
    }
    

    // ִ�е�¼����
    public String login() {
        // �ж���֤���Ƿ���ȷ
        if (!SessionUtils.isCodeMatch(request)) {
            this.addFieldError("verification_code_error", "��֤�����");
            return "login";
        }

        User u = userService.findUser(userName, password);
        if (u == null) {
            this.addFieldError("invalid_user_error", "�û������������");
            return "login";
        } else {
            // �޸��û�������¼ʱ��
            u.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
            userService.updateLastLoginTime(u);
            // ��¼�ɹ������û��������session��
            SessionUtils.setUserToSession(request, u);
        }
        return "back_to_main";
    }

    // ִ��ע������
    public String logout() {
        // ɾ��֮ǰ����session���û�����
        SessionUtils.removeUserFormSession(request);
        return "back_to_main";
    }
}
