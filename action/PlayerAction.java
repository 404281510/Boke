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

    private int videoId; // 视频id
    private String commentContent; // 评论内容

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

    // 初始化播放页面
    public String init() {
        // 查找视频
        Video v = videoService.findVideo(videoId);
        if (v == null) {
            return "player_error";
        }
        // 获得视频的评论
        List<Comment> comments = videoService.findComments(v);
        videoService.updateVideoOnPlay(v);

        ActionContext.getContext().put("video", v);
        ActionContext.getContext().put("comments", comments);
        return "player";
    }

    // 好评
    public String ding() {
        Video v = videoService.findVideo(videoId);
        if (v == null) {
            return "player_error";
        }
        videoService.updateVideoOnDing(v);
        ActionContext.getContext().put("video", v);
        return "player";
    }

    // 差评
    public String cai() {
        Video v = videoService.findVideo(videoId);
        if (v == null) {
            return "player_error";
        }
        videoService.updateVideoOnCai(v);
        ActionContext.getContext().put("video", v);
        return "player";
    }

    // 评论
    public String comment() {
        // 获取当前用户
        User u = SessionUtils.getUserFormSession(request);

        Video v = videoService.findVideo(videoId);
        if (v == null) {
            return "player_error";
        }
        // 构造评论对象
        Comment c = new Comment();
        c.setUser(u);
        c.setVideo(v);
        c.setContent(commentContent);
        c.setTime(new Timestamp(System.currentTimeMillis()));

        videoService.addComment(c, v);
        // 重新获得视频评论
        List<Comment> comments = videoService.findComments(v);

        ActionContext.getContext().put("video", v);
        ActionContext.getContext().put("comments", comments);
        return "player";
    }
}
