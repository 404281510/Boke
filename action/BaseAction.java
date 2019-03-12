package edu.ahpu.boke.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
// �̳�Struts��Action֧����
public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

    protected HttpServletRequest request;
    protected HttpServletResponse response;

    // ʵ��ServletRequestAware�ӿڶ���ķ���
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    // ʵ��ServletResponseAware�ӿڶ���ķ���
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }
}
