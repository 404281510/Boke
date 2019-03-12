package edu.ahpu.boke.util;

import org.junit.Test;

public class Const {
    // ��ʶsession�е���֤��
	
    public static final String KEY_VERIFICATION_CODE = "verification_code";
    // ��ʶsession�еĵ�¼�û�����
    public static final String KEY_LOGINED_USER = "logined_user";

    // ע��ʱĬ���û�ͷ���id
    public static final int DEFAULT_CHANNEL_ID = 1;
    // config���д���Ĭ���û�ͷ��id���������������
    public static final String CONFIG_NAME_DEFAULT_FACE_ID = "default_face_id";

    // �����Ƶ����ͼ�ļ����ļ�������
    public static final String UPLOAD_FOLDER = "video";
    // UPLOAD_FOLDER�ľ���·����
    public static String UPLOAD_REAL_PATH;
    
    
    // �ϴ��ļ�������С��200�ף�
    public static final long MAX_UPLOAD_FILE_SIZE = 200 * 1024 * 1024;

    // ��Ƶ״̬
    public static final String VIDEO_STATUS_UPLOADED = "U";// ���ϴ�
    public static final String VIDEO_STATUS_CONVERTED = "C";// ��ת��
    public static final String VIDEO_STATUS_APPROVED = "A";// �����

    // ת�빤��mencoder��ffmpeg���ڵ�·��
    public static String MENCODER_EXE;
    public static String FFMPEG_EXE;

    // ��������ĳ���
    public static final String ORDER_ASC = "asc";
    public static final String ORDER_DESC = "desc";
    // ����Ƶ����ʱ���ڵ��ֶ�����
    public static final String[] VIDEO_ORDER_FIELDS = { "uploadTime", "playCount", "commentCount", "goodCommentCount" };

    // ���ڷ�ҳ�ĳ���
    public static final int VIDEO_SIZE_PER_PAGE = 10;// ÿҳ����Ƶ��
    public static final int PAGE_BUTTON_SIZE_PER_PAGE = 10;// ÿҳ�ķ�ҳ��ť����
}
