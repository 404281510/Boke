package edu.ahpu.boke.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ahpu.boke.domain.User;
import edu.ahpu.boke.util.Const;
import edu.ahpu.boke.util.SessionUtils;
import edu.ahpu.boke.util.VideoConverter;

//登录检查过滤器
public class LoginChecker implements Filter {
    // 存放所有需要登录才能访问的资源（也可以存放其补集）
    private List<String> pathsNeedLogin;

    // 部署Web应用时执行
    public void init(FilterConfig filterConfig) throws ServletException {
        // 添加各个需要登录才能访问的资源
        pathsNeedLogin = new ArrayList<String>();
        pathsNeedLogin.add("/login_logout.do");
        pathsNeedLogin.add("/upload_init.do");
        pathsNeedLogin.add("/upload_upload.do");
        pathsNeedLogin.add("/player_comment.do");

        Const.UPLOAD_REAL_PATH = filterConfig.getServletContext().getRealPath(Const.UPLOAD_FOLDER) + "\\";
        System.out.println(Const.UPLOAD_REAL_PATH);
        Const.MENCODER_EXE = filterConfig.getServletContext().getRealPath("\\mplayer\\mencoder.exe");
        System.out.println(Const.MENCODER_EXE);
        Const.FFMPEG_EXE = filterConfig.getServletContext().getRealPath("\\mplayer\\ffmpeg.exe");
        System.out.println(Const.FFMPEG_EXE);
       
        VideoConverter.getInstance().startConvertJob();
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String path = request.getServletPath();// 得到浏览器的请求地址

        if (!pathsNeedLogin.contains(path)) { // 不需登录就可访问
            chain.doFilter(request, response);// 放行
            return;
        }

        /**** 若执行到此处，说明需要登录才能访问。 ****/
        // 得到session中的用户对象
        User user = SessionUtils.getUserFormSession(request);//

        if (user != null) {// 已登录则放行
            chain.doFilter(request, response);
        } else {// 未登录则转向登陆页
            response.sendRedirect(request.getContextPath() + "/login_init.do");
        }
    }

    // 卸载Web应用时执行
    public void destroy() {
        VideoConverter.getInstance().stopConvertJob();
    }
}
