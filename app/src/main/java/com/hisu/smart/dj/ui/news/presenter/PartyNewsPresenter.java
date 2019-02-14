package com.hisu.smart.dj.ui.news.presenter;


import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.ui.news.contract.PartyNewsContract;
import com.jaydenxiao.common.baserx.RxSubscriber;

import java.util.List;

/**
 *
 * @author lichee
 * @date 2019/1/30
 */

public class PartyNewsPresenter extends PartyNewsContract.Presenter{

    @Override
    public void listInformationRequest(Integer cateId, String cateCode, String codeKeywords, Integer pageNo, Integer pageSize) {
        mRxManage.add(mModel.listInformation( cateId,cateCode,codeKeywords,pageNo,pageSize).subscribe(new RxSubscriber<InformationResponse<InformationEntity>>(mContext,false)  {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("");
            }

            @Override
            protected void _onNext(InformationResponse<InformationEntity> informationResponse) {
                List<InformationEntity> informationEntityList = informationResponse.getDataList();
                mView.returnlistInformation(informationResponse);
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
