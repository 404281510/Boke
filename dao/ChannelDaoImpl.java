package edu.ahpu.boke.dao;

import org.springframework.stereotype.Repository;

import edu.ahpu.boke.domain.Channel;

//DAO类使用此注解以交由Spring管理
@Repository
public class ChannelDaoImpl extends BaseDaoImpl<Channel> implements ChannelDao {
}
