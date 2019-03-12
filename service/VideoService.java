package edu.ahpu.boke.service;

import java.io.File;
import java.util.List;

import edu.ahpu.boke.domain.Comment;
import edu.ahpu.boke.domain.User;
import edu.ahpu.boke.domain.Video;

public interface VideoService {
    void addVideo(User user, int channelId, String title, String tags, String description, File videoFile, String fileNameOnClient) throws Exception;

    Video findVideo(int videoId);

    void updateVideoOnPlay(Video v);

    void updateVideoOnDing(Video v);

    void updateVideoOnCai(Video v);

    List<Comment> findComments(Video v);

    void addComment(Comment c, Video v);
}
