package com.hisu.smart.dj.ui.study.presenter;


import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.entity.StudyPlanEntity;
import com.hisu.smart.dj.ui.study.contract.StudyCommonContract;

import com.jaydenxiao.common.baserx.RxSubscriber;

import java.util.List;

/**
 *
 * @author lichee
 * @date 2019/1/30
 */

public class StudyCommonPresenter extends StudyCommonContract.Presenter{

    @Override
    public void listCommonContentRequest(Integer cateId, String cateCode, String codeKeywords, Integer pageNo, Integer pageSize) {
        mRxManage.add(mModel.listCommonContent( cateId,cateCode,codeKeywords,pageNo,pageSize).subscribe(new RxSubscriber<InformationResponse<StudyPlanEntity>>(mContext,false)  {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("");
            }

            @Override
            protected void _onNext(InformationResponse<StudyPlanEntity> informationResponse) {
                List<StudyPlanEntity> informationEntityList = informationResponse.getDataList();
                mView.returnlistCommonContent(informationResponse);
                if(informationEntityList !=null && informationEntityList.size() > 0){
                    mView.stopLoading("");
                }else{
                    mView.stopLoading("EMPTY");
                }

            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,"");
            }
        }));
    }
}
