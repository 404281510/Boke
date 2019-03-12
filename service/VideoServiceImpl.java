package edu.ahpu.boke.service;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.InputFormatException;
import it.sauronsoftware.jave.MultimediaInfo;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import edu.ahpu.boke.dao.ChannelDao;
import edu.ahpu.boke.dao.CommentDao;
import edu.ahpu.boke.dao.UserDao;
import edu.ahpu.boke.dao.VideoDao;
import edu.ahpu.boke.domain.Comment;
import edu.ahpu.boke.domain.User;
import edu.ahpu.boke.domain.Video;
import edu.ahpu.boke.util.Const;
import edu.ahpu.boke.util.VideoConverter;

@Service
public class VideoServiceImpl implements VideoService {
    @Resource
    private ChannelDao channelDao;
    @Resource
    private VideoDao videoDao;
    @Resource
    private UserDao userDao;
    @Resource
    private CommentDao commentDao;

    // 上传视频
    public void addVideo(User user, int channelId, String title, String tags, String description, File videoFile, String fileNameOnClient) throws Exception {
        if (videoFile != null) {
            long duration = 0;
            // 使用Jave包的API获取视频文件的播放时长
            Encoder encoder = new Encoder();
            MultimediaInfo m;
            try {
                m = encoder.getInfo(videoFile);
                if (m == null || m.getVideo() == null) {
                    throw new Exception("不能识别视频编码格式，请确认上传的是视频文件！");
                }
                duration = m.getDuration() / 1000; // 获取播放时长
            } catch (InputFormatException e) {
                throw e;
            } catch (EncoderException e) {
                throw e;
            }

            // 产生一个随机的UUID串，作为视频文件在服务器端的名称。
            String fileNameOnServer = UUID.randomUUID().toString();
            File fileOnServer = new File(Const.UPLOAD_REAL_PATH, fileNameOnServer);
            try {
                FileUtils.copyFile(videoFile, fileOnServer);// 复制文件
            } catch (IOException e) {
                throw new Exception("复制文件时发生错误！");
            }

            // 构造并初始化视频对象
            Video video = new Video();
            video.setChannel(channelDao.findById(channelId));
            video.setUser(user);
            video.setClientFileName(fileNameOnClient);
            video.setServerFileName(fileNameOnServer);
            video.setPicFileName(fileNameOnServer);
            video.setTitle(title);
            video.setTags(tags);
            video.setDescription(description);
            video.setPlayCount(0);
            video.setCommentCount(0);
            video.setGoodCommentCount(0);
            video.setBadCommentCount(0);
            video.setDuration((int) duration);
            video.setUploadTime(new Timestamp(System.currentTimeMillis()));
            video.setStatus(Const.VIDEO_STATUS_UPLOADED);// 标记视频状态为已上传
            videoDao.save(video); // 添加视频记录到数据库

            VideoConverter converter = VideoConverter.getInstance();
            if (converter.getVideoDao() == null) {
                // 设置VideoConverter类的videoDao字段
                converter.setVideoDao(videoDao);
            }
            converter.add(video); // 将视频加到待转码队列
        }
    }

    // 根据视频id查找视频对象
    public Video findVideo(int videoId) {
        return videoDao.findById(videoId);
    }

    // 播放视频时修改播放次数
    public void updateVideoOnPlay(Video v) {
        // 修改视频的播放次数
        v.setPlayCount(v.getPlayCount() + 1);
        User u = v.getUser();
        // 修改相应用户的视频总播放次数
        u.setTotalPlayCount(u.getTotalPlayCount() + 1);
        videoDao.update(v);
        userDao.update(u);
    }

    // 修改视频的好评次数
    public void updateVideoOnDing(Video v) {
        v.setGoodCommentCount(v.getGoodCommentCount() + 1);
        videoDao.update(v);
    }

    // 修改视频的差评次数
    public void updateVideoOnCai(Video v) {
        v.setBadCommentCount(v.getBadCommentCount() + 1);
        videoDao.update(v);
    }

    // 获取视频的评论
    public List<Comment> findComments(Video v) {
        Object[] params = new Object[] { v };

        Map<String, String> orderBy = new LinkedHashMap<String, String>();
        orderBy.put("o.time", Const.ORDER_DESC);// 按评论时间逆序排列

        return commentDao.findByCondition("and o.video=?", params, orderBy, false);
    }

    // 添加评论
    public void addComment(Comment c, Video v) {
        commentDao.save(c);
        // 修改视频的评论次数
        v.setCommentCount(v.getCommentCount() + 1);
        videoDao.update(v);
    }
}
