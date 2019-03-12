package edu.ahpu.boke.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import edu.ahpu.boke.service.FaceService;
import edu.ahpu.boke.service.UserService;
import edu.ahpu.boke.util.SessionUtils;

@SuppressWarnings("serial")
@Controller
public class RegisterAction extends BaseAction {
    @Resource
    private UserService userService;
    @Resource
    private FaceService faceService;

    private String userName;
    private String password;
    private String password2;
    private String verificationCode;
    private int faceId;

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

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public int getFaceId() {
        return faceId;
    }

    public void setFaceId(int faceId) {
        this.faceId = faceId;
    }

    // 初始化注册页面
    public String init() {
        ActionContext.getContext().put("all_faces", faceService.findAllFaces());
        ActionContext.getContext().put("default_face", faceService.findDefaultFace());
        return "register";
    }

    // 执行注册操作
    public String register() {
        // 判断验证码是否正确
        if (!SessionUtils.isCodeMatch(request)) {
            this.addFieldError("verification_code_error", "验证码错误！");
            return init();// 注意此处返回的不是视图名称
        }

        // 判断输入的用户名是否已存在
        if (userService.isUserNameExist(userName)) {
            this.addFieldError("user_name_exist_error", "用户名已存在！");
            return init();
        } else {
            userService.addUser(userName, password, faceId);
        }
        return "register_success";
    }
}
