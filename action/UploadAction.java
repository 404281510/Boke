package edu.ahpu.boke.action;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import edu.ahpu.boke.domain.User;
import edu.ahpu.boke.service.ChannelService;
import edu.ahpu.boke.service.VideoService;
import edu.ahpu.boke.util.Const;
import edu.ahpu.boke.util.SessionUtils;

@SuppressWarnings("serial")
@Controller
public class UploadAction extends BaseAction {
    @Resource
    private ChannelService channelService;
    @Resource
    private VideoService videoService;

    private int userId;
    private File video;
    private String videoFileName;
    private String title;
    private String description;
    private int channelId;
    private String tags;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public File getVideo() {
        return video;
    }

    public void setVideo(File video) {
        this.video = video;
    }

    public String getVideoFileName() {
        return videoFileName;
    }

    public void setVideoFileName(String videoFileName) {
        this.videoFileName = videoFileName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String init() {
        ActionContext.getContext().put("all_channels", channelService.findAllChannels());
        return "upload";
    }

    public String upload() throws Exception {
        if (video.length() > Const.MAX_UPLOAD_FILE_SIZE) {
            throw new Exception("文件超过了 " + Const.MAX_UPLOAD_FILE_SIZE / 1024 / 1024 + " 兆！");
        }
        User user = SessionUtils.getUserFormSession(request);
        videoService.addVideo(user, channelId, title, tags, description, video, videoFileName);
        return "upload_success";
    }
}
