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
     * �õ���ҳ�е���Ƶ��ҳ����
     * 
     * @param channel
     *            ��Ƶ����Ƶ��
     * @param orderId
     *            �����ֶ������±�
     * @param page
     *            ��ǰҳ��
     * @param pageSize
     *            ÿҳ����Ƶ��
     * @param pageButtonSize
     *            ҳ���еķ�ҳ��ť��
     * @return ��ҳ����
     */
    public PageBean getVideoPageOfMain(Channel channel, int orderId, int page, int pageSize, int pageButtonSize) {
        // ������Ƶ����Ƶ������Ƶ״̬��ѯ
        String whereHql = "and o.channel=? and o.status=? ";
        // ��ʽӦ���У�ת������Ƶ�����˹���ˣ�����Ƶ״̬ӦΪConst.VIDEO_STATUS_APPROVED��
        Object[] params = new Object[] { channel, Const.VIDEO_STATUS_CONVERTED };

        // �������ֶ�����������
        Map<String, String> orderBy = new LinkedHashMap<String, String>();
        orderBy.put("o." + Const.VIDEO_ORDER_FIELDS[orderId], Const.ORDER_DESC);

        int rowCount = videoDao.getRowCount(whereHql, params); // �ܼ�¼��
        PageBean pageBean = new PageBean(rowCount, page, pageSize, pageButtonSize);
        List<Video> list = videoDao.findByConditionWithPaging(whereHql, params, orderBy, pageBean.getOffset(), pageSize);
        pageBean.setContents(list);

        return pageBean;
    }
}
