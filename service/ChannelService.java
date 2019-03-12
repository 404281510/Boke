package edu.ahpu.boke.service;

import java.util.List;

import edu.ahpu.boke.domain.Channel;

public interface ChannelService {
	List<Channel> findAllChannels();

	Channel findChannel(int channelId);
}
