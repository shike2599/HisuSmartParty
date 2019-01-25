package com.hisu.smart.dj.api;

import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.entity.LoginUserEntity;
import com.hisu.smart.dj.entity.RankEntity;
import com.jaydenxiao.common.basebean.BaseResponse;

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
    @POST("party-user-front/login/readonly/login")
    Observable<BaseResponse<LoginUserEntity>> login(@Query("userName") String username,
                                                    @Query("password") String password);

    /**
     * 发送验证码
     * @param phone 手机号
     * @return
     */
    @POST("party-user-front/login/readonly/sendVerifyCode")
    Observable<BaseResponse> sendVerifyCode(@Query("phone") String phone);

    /**
     * 校验验证码
     * @param phone  手机号
     * @param code   手机验证码
     * @return
     */
    @POST("party-user-front/login/readonly/verifyPhoneCode")
    Observable<BaseResponse> verifyPhoneCode(@Query("phone") String phone,
                                             @Query("code") String code);

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
}
