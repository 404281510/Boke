package edu.ahpu.boke.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.ahpu.boke.dao.ConfigDao;
import edu.ahpu.boke.dao.FaceDao;
import edu.ahpu.boke.domain.Config;
import edu.ahpu.boke.domain.Face;
import edu.ahpu.boke.util.Const;

@Service
public class FaceServiceImpl implements FaceService {
    @Resource
    private ConfigDao configDao;
    @Resource
    private FaceDao faceDao;

    // 获得并缓存所有可选头像
    public List<Face> findAllFaces() {
        return faceDao.findAll(true);
    }

    // 得到系统设置中指定的默认头像
    public Face findDefaultFace() {
        // 获得并缓存系统设置中名为“default_face_id”的设置项
        Config config = configDao.findFirstByCondition("and o.name=?", new Object[] { Const.CONFIG_NAME_DEFAULT_FACE_ID }, true);
        if (config != null) {
            // 根据设置项的值获得对应的头像
            Face face = faceDao.findById(Integer.parseInt(config.getValue()));
            return face;
        }
        return null;
    }
}
