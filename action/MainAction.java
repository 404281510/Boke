package edu.ahpu.boke.action;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import edu.ahpu.boke.dao.ChannelDao;
import edu.ahpu.boke.dao.UserDao;
import edu.ahpu.boke.dao.VideoDao;
import edu.ahpu.boke.domain.Channel;
import edu.ahpu.boke.domain.Comment;
import edu.ahpu.boke.domain.User;
import edu.ahpu.boke.domain.Video;
import edu.ahpu.boke.service.ChannelService;
import edu.ahpu.boke.service.PageService;
import edu.ahpu.boke.util.Const;
import edu.ahpu.boke.util.PageBean;

@SuppressWarnings("serial")
@Controller
public class MainAction extends BaseAction {
    @Resource
    private ChannelService channelService;
    @Resource
    private PageService pageService;

    private int channelId; // 频道id
    private int orderId; // 排序字段的下标
    private int page;// 当前页号

    @Resource
    private UserDao userDao;
    @Resource
    private ChannelDao channelDao;
    @Resource
    private VideoDao videoDao;

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public String execute() {
        // generateData4Test(513); // 产生513条测试数据

        // 判断排序字段下标的合法性
        if (orderId != 1 && orderId != 2 && orderId != 3 && orderId != 4) {
            orderId = 1;
        }
        List<Channel> all_channels = channelService.findAllChannels();
        Channel channel = channelService.findChannel(channelId);
        channelId = channel.getId();// 页面传入的频道id可能不存在
        // genju获取分页对象
        PageBean videoPageBean = pageService.getVideoPageOfMain(channel, orderId - 1, page, Const.VIDEO_SIZE_PER_PAGE, Const.PAGE_BUTTON_SIZE_PER_PAGE);

        ActionContext.getContext().put("all_channels", all_channels);
        ActionContext.getContext().put("channel", channel);
        ActionContext.getContext().put("page_bean", videoPageBean);
        return "main";
    }

    // 产生测试数据，参数为产生的数据总量。
    private void generateData4Test(int count) {
        Random r = new Random();
        String s = "视频";
        User u = userDao.findById(1);// 1为用户的id
        Channel c = channelDao.findById(1);// 1为频道的id
        int i = 0;
        while (i++ < count) {
            Video v = new Video();
            v.setChannel(c);
            v.setUser(u);
            v.setClientFileName("1.mkv");
            // 使用之前的转码和截图文件
            v.setServerFileName("2762d3ce-a0e3-40d1-9096-b3d93380c75f");
            v.setPicFileName("2762d3ce-a0e3-40d1-9096-b3d93380c75f");
            v.setTitle(s + "标题" + i);
            v.setTags("标签1 标签2 标签3");
            v.setDescription(s + "描述" + i);
            v.setPlayCount(r.nextInt(10000));// 随机产生播放次数等
            v.setCommentCount(r.nextInt(1000));
            v.setComments(new HashSet<Comment>());
            v.setGoodCommentCount(r.nextInt(1000));
            v.setBadCommentCount(r.nextInt(1000));
            v.setDuration(r.nextInt(3000) + 10);
            v.setUploadTime(new Timestamp(System.currentTimeMillis() - r.nextInt()));
            v.setStatus(Const.VIDEO_STATUS_CONVERTED);
            videoDao.save(v);
        }
    }
}
