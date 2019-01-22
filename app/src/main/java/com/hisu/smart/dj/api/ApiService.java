package com.hisu.smart.dj.api;

import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.entity.LoginUserEntity;
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
    Observable<BaseResponse<LoginUserEntity>> login(@Query("userName") String username, @Query("password") String password);

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
    Observable<BaseResponse> verifyPhoneCode(@Query("phone") String phone,@Query("code") String code);

    /**
     * 资讯活动列表
     * @param cateCode  1001时政新闻  1003党建要闻
     * @param keywords  关键字，根据关键字模糊筛选内容
     * @param pageNo    第几页
     * @param pageSize  每页条数
     * @return
     */
    @GET("party-app-education-front/res/readonly/listInformation")
    Observable<InformationResponse<InformationEntity>> listInformation(String cateCode,String keywords,Integer pageNo,Integer pageSize);


}
