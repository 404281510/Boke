package edu.ahpu.boke.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.ahpu.boke.dao.VideoDao;
import edu.ahpu.boke.domain.Channel;
import edu.ahpu.boke.domain.Video;
import edu.ahpu.boke.util.Const;
import edu.ahpu.boke.util.PageBean;

@Service
public class PageServiceImpl implements PageService {
    @Resource
    VideoDao videoDao;

    /**
     * 得到首页中的视频分页对象
     * 
     * @param channel
     *            视频所属频道
     * @param orderId
     *            排序字段名的下标
     * @param page
     *            当前页号
     * @param pageSize
     *            每页的视频数
     * @param pageButtonSize
     *            页面中的分页按钮数
     * @return 分页对象
     */
    public PageBean getVideoPageOfMain(Channel channel, int orderId, int page, int pageSize, int pageButtonSize) {
        // 根据视频所属频道及视频状态查询
        String whereHql = "and o.channel=? and o.status=? ";
        // 正式应用中，转码后的视频还需人工审核，故视频状态应为Const.VIDEO_STATUS_APPROVED。
        Object[] params = new Object[] { channel, Const.VIDEO_STATUS_CONVERTED };

        // 按排序字段名降序排列
        Map<String, String> orderBy = new LinkedHashMap<String, String>();
        orderBy.put("o." + Const.VIDEO_ORDER_FIELDS[orderId], Const.ORDER_DESC);

        int rowCount = videoDao.getRowCount(whereHql, params); // 总记录数
        PageBean pageBean = new PageBean(rowCount, page, pageSize, pageButtonSize);
        List<Video> list = videoDao.findByConditionWithPaging(whereHql, params, orderBy, pageBean.getOffset(), pageSize);
        pageBean.setContents(list);

        return pageBean;
    }
}
