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

    // �ϴ���Ƶ
    public void addVideo(User user, int channelId, String title, String tags, String description, File videoFile, String fileNameOnClient) throws Exception {
        if (videoFile != null) {
            long duration = 0;
            // ʹ��Jave����API��ȡ��Ƶ�ļ��Ĳ���ʱ��
            Encoder encoder = new Encoder();
            MultimediaInfo m;
            try {
                m = encoder.getInfo(videoFile);
                if (m == null || m.getVideo() == null) {
                    throw new Exception("����ʶ����Ƶ�����ʽ����ȷ���ϴ�������Ƶ�ļ���");
                }
                duration = m.getDuration() / 1000; // ��ȡ����ʱ��
            } catch (InputFormatException e) {
                throw e;
            } catch (EncoderException e) {
                throw e;
            }

            // ����һ�������UUID������Ϊ��Ƶ�ļ��ڷ������˵����ơ�
            String fileNameOnServer = UUID.randomUUID().toString();
            File fileOnServer = new File(Const.UPLOAD_REAL_PATH, fileNameOnServer);
            try {
                FileUtils.copyFile(videoFile, fileOnServer);// �����ļ�
            } catch (IOException e) {
                throw new Exception("�����ļ�ʱ��������");
            }

            // ���첢��ʼ����Ƶ����
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
            video.setStatus(Const.VIDEO_STATUS_UPLOADED);// �����Ƶ״̬Ϊ���ϴ�
            videoDao.save(video); // �����Ƶ��¼�����ݿ�

            VideoConverter converter = VideoConverter.getInstance();
            if (converter.getVideoDao() == null) {
                // ����VideoConverter���videoDao�ֶ�
                converter.setVideoDao(videoDao);
            }
            converter.add(video); // ����Ƶ�ӵ���ת�����
        }
    }

    // ������Ƶid������Ƶ����
    public Video findVideo(int videoId) {
        return videoDao.findById(videoId);
    }

    // ������Ƶʱ�޸Ĳ��Ŵ���
    public void updateVideoOnPlay(Video v) {
        // �޸���Ƶ�Ĳ��Ŵ���
        v.setPlayCount(v.getPlayCount() + 1);
        User u = v.getUser();
        // �޸���Ӧ�û�����Ƶ�ܲ��Ŵ���
        u.setTotalPlayCount(u.getTotalPlayCount() + 1);
        videoDao.update(v);
        userDao.update(u);
    }

    // �޸���Ƶ�ĺ�������
    public void updateVideoOnDing(Video v) {
        v.setGoodCommentCount(v.getGoodCommentCount() + 1);
        videoDao.update(v);
    }

    // �޸���Ƶ�Ĳ�������
    public void updateVideoOnCai(Video v) {
        v.setBadCommentCount(v.getBadCommentCount() + 1);
        videoDao.update(v);
    }

    // ��ȡ��Ƶ������
    public List<Comment> findComments(Video v) {
        Object[] params = new Object[] { v };

        Map<String, String> orderBy = new LinkedHashMap<String, String>();
        orderBy.put("o.time", Const.ORDER_DESC);// ������ʱ����������

        return commentDao.findByCondition("and o.video=?", params, orderBy, false);
    }

    // �������
    public void addComment(Comment c, Video v) {
        commentDao.save(c);
        // �޸���Ƶ�����۴���
        v.setCommentCount(v.getCommentCount() + 1);
        videoDao.update(v);
    }
}
