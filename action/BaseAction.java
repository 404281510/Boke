package edu.ahpu.boke.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
// 继承Struts的Action支持类
public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

    protected HttpServletRequest request;
    protected HttpServletResponse response;

    // 实现ServletRequestAware接口定义的方法
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    // 实现ServletResponseAware接口定义的方法
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }
}
