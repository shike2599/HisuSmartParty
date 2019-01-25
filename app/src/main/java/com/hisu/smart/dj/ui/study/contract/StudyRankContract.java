package com.hisu.smart.dj.ui.study.contract;

import com.hisu.smart.dj.entity.RankEntity;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.jaydenxiao.common.basebean.BaseResponse;

import java.util.List;

import rx.Observable;

/**
 * des:新闻列表contract
 * Created by xsf
 * on 2016.09.14:38
 * @author lichee
 */
public interface StudyRankContract {
    interface Model extends BaseModel {
        //请求获取党员排名
        Observable <BaseResponse<RankEntity>> getMemberRankListData(Integer userId, Integer  partyMemberId, Integer sortType, Integer limitNum);
        //请求获取支部排名
        Observable <BaseResponse<RankEntity>> getBranchRankListData(Integer userId, Integer  partyBranchId, Integer sortType, Integer limitNum);
    }

    interface View extends BaseView {
        //返回党员排名
        void returnMemberRankListData(List<RankEntity> rankEntities);
        //返回支部排名
        void returnBranchRankListData(List<RankEntity> rankEntities);
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        //发起获取党员排名请求
        public abstract void getMemberRankListDataRequest(Integer userId, Integer  partyMemberId, Integer sortType, Integer limitNum);
        //发起获取支部排名请求
        public abstract void getBranchRankListDataRequest(Integer userId, Integer  partyBranchId, Integer sortType, Integer limitNum);
    }
}
