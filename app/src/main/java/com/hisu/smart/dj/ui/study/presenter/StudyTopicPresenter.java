package com.hisu.smart.dj.ui.study.presenter;


import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.entity.TopicPlanEntity;
import com.hisu.smart.dj.ui.study.contract.StudyTopicContract;
import com.jaydenxiao.common.baserx.RxSubscriber;

/**
 * des:
 * Created by xsf
 * on 2016.09.14:53
 * @author lichee
 */
public class StudyTopicPresenter extends StudyTopicContract.Presenter {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void getMemberTopicDataRequest(Integer userId, Integer pageNo, Integer pageSize) {
        mRxManage.add(mModel.listMemberTopicResPlan(userId,pageNo,pageSize).subscribe(new RxSubscriber<InformationResponse<TopicPlanEntity>>(mContext,false)  {

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            protected void _onNext(InformationResponse<TopicPlanEntity> topicPlanEntityResponse) {
                mView.returnMemberTopicData(topicPlanEntityResponse.getDataList());
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,null);
            }
        }));
    }

    @Override
    public void getBranchTopicDataRequest(Integer userId, Integer pageNo, Integer pageSize) {
        mRxManage.add(mModel.listBranchTopicResPlan(userId,pageNo,pageSize).subscribe(new RxSubscriber<InformationResponse<TopicPlanEntity>>(mContext,false)  {

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            protected void _onNext(InformationResponse<TopicPlanEntity> topicPlanEntityResponse) {
                mView.returnBranchTopicData(topicPlanEntityResponse.getDataList());
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,null);
            }
        }));
    }
}
