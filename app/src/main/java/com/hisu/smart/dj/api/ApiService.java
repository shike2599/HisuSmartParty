package com.hisu.smart.dj.api;

import com.hisu.smart.dj.entity.CateEntity;
import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.entity.LoginResponse;

import com.hisu.smart.dj.entity.MemberInfoResponse;
import com.hisu.smart.dj.entity.RankEntity;
import com.hisu.smart.dj.entity.StudyPlanRespone;
import com.jaydenxiao.common.basebean.BaseResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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


}
