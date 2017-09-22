package com.chinaredstar.core.constant;

/**
 * Created by hairui.xiang on 2017/9/8.
 */

public class RC {// requestcode
    public static final int RC_SETTINGS_SCREEN = 4000;//系统setting页
    public static final int RC_USER_REQUEST_PERMISSIONS = 4001;//动态权限申请
    public final static int RC_ACTION_IMAGE_CAPTURE = 4002;//打开相机
    public final static int RC_ACTION_PICK = 4003;//打开相册
    public static final int RC_GET_QRCODE = 4004;//获取二维码
    public static final int RC_AUTO_CHECKING_PERMISSIONS = 4005;//动态权限检查

    //////////////////////////////////////////////////////////////////
    public static final int RC_CAMERA_PERM = 4006;//相机动态权限请求code
    public static final int RC_WRITE_EXTERNAL_STORAGE_PERM = 4007;//sd卡访问权限
    public static final int RC_READ_EXTERNAL_STORAGE_PERM = 4008;//sd卡访问权限
    public static final int RC_CONTACTS_PERM = 4009;//访问联系人列表
    public static final int RC_CALL_PHONE_PERM = 4010;//
    public static final int RC_READ_PHONE_STATE_PERM = 4011;//
    public static final int RC_LOCATION_PERM = 4012;//
}
