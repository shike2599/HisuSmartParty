package com.hisu.smart.dj.ui.study.model;

import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.RankEntity;
import com.hisu.smart.dj.ui.study.contract.StudyRankContract;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSchedulers;

import rx.Observable;
import rx.functions.Func1;


/**
 * des:新闻列表model
 * Created by xsf
 * on 2016.09.14:54
 * @author lichee
 */
public class StudyRankModel implements StudyRankContract.Model {

    @Override
    public Observable<BaseResponse<RankEntity>> getMemberRankListData(Integer userId, Integer partyMemberId, Integer sortType, Integer limitNum) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .listPartyMemberRank(userId,  partyMemberId,  sortType,  limitNum)
                .map(new Func1<BaseResponse<RankEntity>, BaseResponse<RankEntity>>() {
                    @Override
                    public BaseResponse<RankEntity> call(BaseResponse<RankEntity> rankEntityBaseResponse) {
                        return rankEntityBaseResponse;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<BaseResponse<RankEntity>>io_main());
    }

    @Override
    public Observable<BaseResponse<RankEntity>> getBranchRankListData(Integer userId, Integer partyBranchId, Integer sortType, Integer limitNum) {
        return Api.getDefault(AppApplication.getAppContext(), AppConstant.HOST_URL)
                .listPartyBranchRank(userId,  partyBranchId,  sortType,  limitNum)
                .map(new Func1<BaseResponse<RankEntity>, BaseResponse<RankEntity>>() {
                    @Override
                    public BaseResponse<RankEntity> call(BaseResponse<RankEntity> rankEntityBaseResponse) {
                        return rankEntityBaseResponse;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<BaseResponse<RankEntity>>io_main());
    }
}
