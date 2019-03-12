package edu.ahpu.boke.action;

import org.springframework.stereotype.Controller;

@SuppressWarnings("serial")
@Controller
public class VerificationCodeAction extends BaseAction {

    @Override
    public String execute() {
        return "init";
    }
}
