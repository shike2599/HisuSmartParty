package com.hisu.smart.dj.api;

import com.hisu.smart.dj.entity.CateEntity;
import com.hisu.smart.dj.entity.FollowActionListBean;
import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.entity.LoginResponse;

import com.hisu.smart.dj.entity.MemberInfoResponse;
import com.hisu.smart.dj.entity.NewsInfoResponse;
import com.hisu.smart.dj.entity.NoticeInfoEntity;
import com.hisu.smart.dj.entity.RankEntity;
import com.hisu.smart.dj.entity.StudyPlanRespone;
import com.hisu.smart.dj.entity.UnReadSizeEntity;
import com.jaydenxiao.common.basebean.BaseResponse;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author lichee
 */
public interface ApiService {

    /**
     * 登陆
     * @param username  用户名（身份证）
     * @param password  用户密码
     * @return
     */
    @FormUrlEncoded
    @POST("party-user-front/login/readonly/login")
    Observable<LoginResponse> login(@Field("userName") String username,
                                    @Field("password") String password);

    /**
     * 发送验证码
     * @param phone 手机号
     * @return
     */
    @FormUrlEncoded
    @POST("party-user-front/login/readonly/sendVerifyCode")
    Observable<BaseResponse> sendVerifyCode(@Field("phone") String phone);

    /**
     * 校验验证码
     * @param phone  手机号
     * @param code   手机验证码
     * @return
     */
    @FormUrlEncoded
    @POST("party-user-front/login/readonly/verifyPhoneCode")
    Observable<BaseResponse> verifyPhoneCode(@Field("phone") String phone,
                                             @Field("code") String code);

    /**
     * 资讯活动列表
     * @param cateCode  1001时政新闻  1003党建要闻
     * @param keywords  关键字，根据关键字模糊筛选内容
     * @param pageNo    第几页
     * @param pageSize  每页条数
     * @return
     */
    @GET("party-app-education-front/res/readonly/listInformation")
    Observable<InformationResponse<InformationEntity>> listInformation(@Query("cateCode")String cateCode,
                                                                       @Query("keywords")String keywords,
                                                                       @Query("pageNo")Integer pageNo,
                                                                       @Query("pageSize")Integer pageSize);

    /**
     * 党员排名
     * @param userId   用户ID
     * @param partyMemberId   党员ID
     * @param sortType  0：按照总学时排名，1：按照常规学时排名，2：按照专题学时排名
     * @param limitNum 取前几名
     * @return
     */
    @GET("party-data-statistics-front/studyRank/readonly/listPartyMemberRank")
    Observable<BaseResponse<RankEntity>> listPartyMemberRank(@Query("userId") Integer userId,
                                                             @Query("partyMemberId") Integer  partyMemberId,
                                                             @Query("sortType") Integer sortType,
                                                             @Query("limitNum") Integer limitNum);

    @GET("party-data-statistics-front/studyRank/readonly/listPartyBranchRank")
    Observable<BaseResponse<RankEntity>> listPartyBranchRank(@Query("userId") Integer userId,
                                                             @Query("partyBranchId") Integer  partyBranchId,
                                                             @Query("sortType") Integer sortType,
                                                             @Query("limitNum") Integer limitNum);


    @GET("party-app-education-front/plan/readonly/listMemberTopicResPlan")
    Observable<InformationResponse<InformationEntity>> listMemberTopicResPlan(@Query("userId") Integer userId,
                                                                        @Query("pageNo")Integer pageNo,
                                                                        @Query("pageSize")Integer pageSize);

    @GET("party-app-education-front/plan/readonly/listBranchTopicResPlan")
    Observable<InformationResponse<InformationEntity>> listBranchTopicResPlan(@Query("userId") Integer userId,
                                                                            @Query("pageNo")Integer pageNo,
                                                                            @Query("pageSize")Integer pageSize);

    @GET("party-app-education-front/res/readonly/listCommonCate")
    Observable<BaseResponse<CateEntity>> listCommonCate(@Query("parentId") Integer parentId,
                                                        @Query("parentCode") String parentCode,
                                                        @Query("codeKeywords") String codeKeywords);


    @GET("party-app-education-front/res/readonly/listCommonContent")
    Observable<InformationResponse<InformationEntity>> listCommonContent(@Query("cateId") Integer cateId,
                                                                         @Query("cateCode") String cateCode,
                                                                         @Query("codeKeywords") String codeKeywords,
                                                                         @Query("pageNo")Integer pageNo,
                                                                         @Query("pageSize")Integer pageSize);

    /**
     * 党员信息
     * @param userId  党员ID
     * @return
     */
    @GET("party-app-building-front/member/readonly/getPartyMember")
    Observable<MemberInfoResponse> getPartyMember(@Query("userId") Integer userId);

    /**
     * 学习计划（个人）
     * @param userId   用户ID
     * @param partyMemberId   党员ID
     * @param timeType  0：年度，1：季度，2：月度
     * @return
     */
    @GET("party-app-education-front/plan/readonly/partyMemberPlan")
    Observable<StudyPlanRespone> partyMemberPlan(@Query("userId") Integer userId,
                                                             @Query("partyMemberId") Integer  partyMemberId,
                                                             @Query("timeType") Integer timeType);
    /**
     * 学习计划（支部）
     * @param userId   用户ID
     * @param partyBranchId   支部ID
     * @param timeType  0：年度，1：季度，2：月度
     * @return
     */
    @GET("party-app-education-front/plan/readonly/partyBranchPlan")
    Observable<StudyPlanRespone> partyBranchPlan(@Query("userId") Integer userId,
                                                             @Query("partyBranchId") Integer  partyBranchId,
                                                             @Query("timeType") Integer timeType);

    /**
     * 新闻详情
     * @param id   新闻ID
     */
    @GET("party-app-education-front/res/readonly/getInformation")
    Observable<NewsInfoResponse> getInformation(@Query("id") Integer id);

    /**
     * 三会一课
     * @param cateCode   分类编号，不传查所有
     * @param keywords   关键字，根据关键字模糊筛选内容名称
     * @param pageNo   第几页
     * @param pageSize  每页条数
     * @return
     */
    @GET("party-app-education-front/res/readonly/listTopicContent")
    Observable<InformationResponse<InformationEntity>> listTopicContent(@Query("cateCode") String cateCode,
                                                 @Query("keywords") String  keywords,
                                                 @Query("pageNo") Integer  pageNo,
                                                 @Query("pageSize") Integer pageSize);
    /**
     * 4.7.专题活动内容详情
     * @param id   活动序号
     * @return
     */
    @GET("party-app-education-front/res/readonly/getTopicContent")
    Observable<NewsInfoResponse> getTopicContent(@Query("id") Integer id);

    /**
     * 6.1.践行活动分类
     * @param parentId   父级分类序号，不传则查所有，传0则查询一级分类 Integer
     * @param parentCode   父级分类编号
     * @param codeKeywords   关键字，根据分类编号模糊搜索
     * @return
     */
    @GET("party-app-practice-front/res/readonly/listActionCate")
    Observable<FollowActionListBean> listActionCate(@Query("parentId") Integer parentId,
                                                    @Query("parentCode") String  parentCode,
                                                    @Query("codeKeywords") Integer  codeKeywords);

    /**
     * 6.2.践行活动列表
     * @param cateCode   分类编号，不传则查所有
     * @param keywords   关键字，根据关键字模糊筛选内容
     * @param pageNo   第几页
     * @param pageSize   每页条数
     * @return
     */
    @GET("party-app-practice-front/res/readonly/listActionContent")
    Observable<InformationResponse<InformationEntity>> listActionContent(@Query("cateCode") String cateCode,
                                                    @Query("keywords") String  keywords,
                                                    @Query("pageNo") Integer  pageNo,
                                                    @Query("pageSize") Integer  pageSize);

    /**
     * 6.3.践行活动内容详情
     * @param id   活动序号
     * @return
     */
    @GET("party-app-practice-front/res/readonly/getActionContent")
    Observable<NewsInfoResponse> getActionContent(@Query("id") Integer id);

    /**
     * 党员提交践行活动
     * @param userId   用户序号，党员用户才有权限。 是
     * @param partyMemberId   党员序号（如能取到传入，性能会更好一点） 否
     * @param cateId   分类序号，分类序号与分类编号不能同时为空。 否
     * @param cateCode   分类编号，分类序号与分类编号不能同时为空 否
     * @param name   名称 是
     * @param icon   列表页小图标 否
     * @param imgPaths   是	图片数组（多个图片用英文逗号隔开）
     * @param mediaType	Integer	是	内容类型（0：视频，1：图文，2：纯文字）
     * @param content	String	是	资讯内容
     * @param url	String	否	视频链接地址
     * @param publishTime	String	是	活动发布时间（yyyy-MM-dd HH:mm:ss）
     * @param isNeedSign	Boolean	是	true：需要签到，false：不需要
     * @return
     */
    @Multipart
    @POST("party-app-practice-front/res/write/submitActionContent")
    Observable<BaseResponse> submitActionContent(@Query("userId") Integer userId,
                                                      @Query("partyMemberId") Integer partyMemberId,
                                                      @Query("cateId") Integer cateId,
                                                      @Query("cateCode") String cateCode,
                                                      @Query("name") String name,
                                                      @Query("icon") String icon,
                                                      @PartMap Map<String,RequestBody> imgPaths,
                                                      @Query("mediaType") Integer mediaType,
                                                      @Query("content") String content,
                                                      @Query("url") String url,
                                                      @Query("publishTime") String publishTime,
                                                      @Query("isNeedSign") Boolean isNeedSign);
    /**
     * 9.4.获取通知公告数据（手机端）
     * @param userId   用户ID
     * @param partyBranchId   支部ID（党员所在支部|支部领导管理的支部
     * @param publishTime   发布时间
     * @param limitNum   返回最大条数
     * @return
     */
    @GET("party-app-education-front/info/readonly/listNoticeByTime")
    Observable<NoticeInfoEntity> listNoticeByTime(@Query("userId") Integer userId,
                                                  @Query("partyBranchId") Integer  partyBranchId,
                                                  @Query("publishTime") String  publishTime,
                                                  @Query("limitNum") Integer  limitNum);

    /**
     * 9.3.获取用户未读通知公告个数
     * @param userId   用户ID
     * @param partyBranchId   支部ID（党员所在支部|支部领导管理的支部
     * @return
     */
    @GET("party-app-education-front/info/readonly/getUnReadNoticeNum")
    Observable<UnReadSizeEntity> getUnReadNoticeNum(@Query("userId") Integer userId,
                                                    @Query("partyBranchId") Integer  partyBranchId);
}
