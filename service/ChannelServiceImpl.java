package edu.ahpu.boke.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.ahpu.boke.dao.ChannelDao;
import edu.ahpu.boke.domain.Channel;
import edu.ahpu.boke.util.Const;

@Service
public class ChannelServiceImpl implements ChannelService {
	@Resource
	private ChannelDao channelDao;

	public List<Channel> findAllChannels() {
		return channelDao.findAll(true);
	}

	public Channel findChannel(int channelId) {
		Channel channel = channelDao.findById(channelId);
		if (channel == null) {
			channel = channelDao.findById(Const.DEFAULT_CHANNEL_ID);
		}
		return channel;
	}
}
