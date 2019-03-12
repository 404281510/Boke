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

//��¼��������
public class LoginChecker implements Filter {
    // ���������Ҫ��¼���ܷ��ʵ���Դ��Ҳ���Դ���䲹����
    private List<String> pathsNeedLogin;

    // ����WebӦ��ʱִ��
    public void init(FilterConfig filterConfig) throws ServletException {
        // ��Ӹ�����Ҫ��¼���ܷ��ʵ���Դ
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
        String path = request.getServletPath();// �õ�������������ַ

        if (!pathsNeedLogin.contains(path)) { // �����¼�Ϳɷ���
            chain.doFilter(request, response);// ����
            return;
        }

        /**** ��ִ�е��˴���˵����Ҫ��¼���ܷ��ʡ� ****/
        // �õ�session�е��û�����
        User user = SessionUtils.getUserFormSession(request);//

        if (user != null) {// �ѵ�¼�����
            chain.doFilter(request, response);
        } else {// δ��¼��ת���½ҳ
            response.sendRedirect(request.getContextPath() + "/login_init.do");
        }
    }

    // ж��WebӦ��ʱִ��
    public void destroy() {
        VideoConverter.getInstance().stopConvertJob();
    }
}
