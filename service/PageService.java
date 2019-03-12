package edu.ahpu.boke.service;

import edu.ahpu.boke.domain.Channel;
import edu.ahpu.boke.util.PageBean;

public interface PageService {
    PageBean getVideoPageOfMain(Channel channel, int orderId, int page, int pageSize, int pageButtonSize);
}
