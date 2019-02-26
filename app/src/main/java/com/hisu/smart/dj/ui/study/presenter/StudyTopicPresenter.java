package com.hisu.smart.dj.ui.study.presenter;

import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.entity.StudyPlanEntity;
import com.hisu.smart.dj.ui.study.contract.StudyTopicContract;
import com.jaydenxiao.common.baserx.RxSubscriber;

import java.util.List;

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
        mRxManage.add(mModel.listMemberTopicResPlan(userId,pageNo,pageSize).subscribe(new RxSubscriber<InformationResponse<StudyPlanEntity>>(mContext,false)  {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("");
            }

            @Override
            protected void _onNext(InformationResponse<StudyPlanEntity> topicPlanEntityResponse) {
                List<StudyPlanEntity> informationEntityList = topicPlanEntityResponse.getDataList();
                mView.returnMemberTopicData(topicPlanEntityResponse);
                if(informationEntityList != null && informationEntityList.size() > 0){
                    mView.stopLoading("");
                }else {
                    mView.stopLoading("EMPTY");
                }
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,null);
            }
        }));
    }

    @Override
    public void getBranchTopicDataRequest(Integer userId, Integer pageNo, Integer pageSize) {
        mRxManage.add(mModel.listBranchTopicResPlan(userId,pageNo,pageSize).subscribe(new RxSubscriber<InformationResponse<StudyPlanEntity>>(mContext,false)  {

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            protected void _onNext(InformationResponse<StudyPlanEntity> topicPlanEntityResponse) {
                List<StudyPlanEntity> informationEntityList = topicPlanEntityResponse.getDataList();
                mView.returnBranchTopicData(topicPlanEntityResponse);
                if(informationEntityList != null && informationEntityList.size() > 0){
                    mView.stopLoading("");
                }else {
                    mView.stopLoading("EMPTY");
                }
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,null);
            }
        }));
    }
}
