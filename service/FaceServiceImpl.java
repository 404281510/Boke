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

    // ��ò��������п�ѡͷ��
    public List<Face> findAllFaces() {
        return faceDao.findAll(true);
    }

    // �õ�ϵͳ������ָ����Ĭ��ͷ��
    public Face findDefaultFace() {
        // ��ò�����ϵͳ��������Ϊ��default_face_id����������
        Config config = configDao.findFirstByCondition("and o.name=?", new Object[] { Const.CONFIG_NAME_DEFAULT_FACE_ID }, true);
        if (config != null) {
            // �����������ֵ��ö�Ӧ��ͷ��
            Face face = faceDao.findById(Integer.parseInt(config.getValue()));
            return face;
        }
        return null;
    }
}
