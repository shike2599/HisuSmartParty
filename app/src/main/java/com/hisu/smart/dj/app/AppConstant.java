package com.hisu.smart.dj.app;

/**
 * Created by lichee on 2019/1/21.
 */

public class AppConstant {
    //登录信息
    public static final String HOME_CURRENT_TAB_POSITION="HOME_CURRENT_TAB_POSITION";
    public static final String HOST_URL = "http://171.8.79.251";
    public static final String HOST_URL_QR = "http://171.8.79.251:8090";
    public static final String REQUEST_SUCCESS = "200";
    public static final String USER_ID = "user_id";                  //登录用户ID int
    public static final String USER_PHOTO = "user_photo";            //用户头像 str
    public static final String USER_NAME = "user_name";              //用户名 str
    public static final String NICK_NAME = "nick_name";             //昵称 str
    public static final String USER_PHONE = "user_phone";           //手机号 str
    public static final String USER_PASSWORD = "user_password";          //用户密码 str
    public static final String IS_PARTY_MEMBER = "is_party_member";      //false：非党员，true：党员
    public static final String IS_PARTY_BRANCH = "is_party_branch";      //false：非支部，true：支部
    public static final String IS_PARTY_COMMITTEE = "is_party_committee"; //false：非党委，true：党委
    public static final String COMMON_CATE_CODE = "common_cate_code";
    //党员个人信息
    public static final String MEMBER_ID = "member_id";  //党员序号 int
    public static final String MEMBER_NAME = "member_name"; //党员名称 str
    public static final String MEMBER_CODE = "member_code"; //党员编号 str
    public static final String MEMBER_PHONE = "member_phone";  //联系电话 str
    public static final String MEMBER_IDCARD = "member_idCard";  //党员身份证号 str
    public static final String MEMBER_SEX = "member_sex";  //性别 0:女 1:男  int
    public static final String MEMBER_PARTYBRANCH_ID = "member_partyBranchId";  //所属支部序号 int
    public static final String MEMBER_STATUS = "member_status";  //党员性质 0:在职 1:退休 2:农民 int
    public static final String MEMBER_INTEGRAL = "member_integral";  //党员积分 int

    public static final String BASE_URL = "http://59.152.38.196:8991/smart_dj_weixin/";
    public static final String BASE_URL_LOAD = "file:///android_asset/smart_dj_weixin/";
    public static String RESET_PWD_PHONE;
    public static Boolean IS_STUDY_BRANCH = false;

    public static final int QR_CODE =1;
    public static final String  IS_PARTY_NEWS = "IsPartyNews";
    public static final String  SHOW_TITLE = "title";
    public static final String  FOLLOW_ID = "FollowId";
    public static final String  UPLOAD_TITLE = "upload_title";
    public static final String  ADD_COLLECTION_TAG = "add_collection";
    public static final String  QUERY_COLLECTION_TAG = "query_collection";


    public static final String VIDEO = "video";
    public static final String COLLECT_SERI = "collectSeri";
    public static Boolean isUpLoad = false;

    public static String ZONE_PUBLISH_ADD = "ZONE_PUBLISH_ADD";//发布说说
//    public static  boolean  isExitLogin = false;

}
