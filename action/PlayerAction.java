package edu.ahpu.boke.action;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import edu.ahpu.boke.domain.Comment;
import edu.ahpu.boke.domain.User;
import edu.ahpu.boke.domain.Video;
import edu.ahpu.boke.service.VideoService;
import edu.ahpu.boke.util.SessionUtils;

@SuppressWarnings("serial")
@Controller
public class PlayerAction extends BaseAction {
    @Resource
    private VideoService videoService;

    private int videoId; // ��Ƶid
    private String commentContent; // ��������

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    // ��ʼ������ҳ��
    public String init() {
        // ������Ƶ
        Video v = videoService.findVideo(videoId);
        if (v == null) {
            return "player_error";
        }
        // �����Ƶ������
        List<Comment> comments = videoService.findComments(v);
        videoService.updateVideoOnPlay(v);

        ActionContext.getContext().put("video", v);
        ActionContext.getContext().put("comments", comments);
        return "player";
    }

    // ����
    public String ding() {
        Video v = videoService.findVideo(videoId);
        if (v == null) {
            return "player_error";
        }
        videoService.updateVideoOnDing(v);
        ActionContext.getContext().put("video", v);
        return "player";
    }

    // ����
    public String cai() {
        Video v = videoService.findVideo(videoId);
        if (v == null) {
            return "player_error";
        }
        videoService.updateVideoOnCai(v);
        ActionContext.getContext().put("video", v);
        return "player";
    }

    // ����
    public String comment() {
        // ��ȡ��ǰ�û�
        User u = SessionUtils.getUserFormSession(request);

        Video v = videoService.findVideo(videoId);
        if (v == null) {
            return "player_error";
        }
        // �������۶���
        Comment c = new Comment();
        c.setUser(u);
        c.setVideo(v);
        c.setContent(commentContent);
        c.setTime(new Timestamp(System.currentTimeMillis()));

        videoService.addComment(c, v);
        // ���»����Ƶ����
        List<Comment> comments = videoService.findComments(v);

        ActionContext.getContext().put("video", v);
        ActionContext.getContext().put("comments", comments);
        return "player";
    }
}
