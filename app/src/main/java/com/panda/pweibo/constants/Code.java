package com.panda.pweibo.constants;

/**
 * ���һЩ��̬������
 *
 * Created by Administrator on 2015/8/28:11:58.
 */
public interface Code {

    /** ��Ϣ������ */
    int MESSAGE_AT        = 1;      /* @�ҵ����� */
    int MESSAGE_COMMENT   = 2;      /* �ҵ��������� */
    int MESSAGE_GOOD      = 3;      /* �� */
    int MESSAGE_BOX       = 4;      /* ��Ϣ���� */

    /** ��д����activityʱ,д���۵����� */
    int REPLY_COMMENT     = 1;     /* ����Ϊ�ظ����� */
    int CREATE_COMMENT    = 2;     /* �������� */

    /** д����activity�ķ����� */
    int REQUEST_CODE_WRITE_COMMENT_BACK_TO_COMMENT = 22;  /* ��ת���������۵�activity */

    /** ��ת��д����ҳ��code */
    int REQUEST_CODE_WRITE_COMMENT_BACK_TO_DETAIL = 2333;
}
