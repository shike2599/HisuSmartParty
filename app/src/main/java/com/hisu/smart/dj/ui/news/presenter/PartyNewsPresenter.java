package com.hisu.smart.dj.ui.news.presenter;


import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.entity.VisitNumEntity;
import com.hisu.smart.dj.entity.VisitNumResponse;
import com.hisu.smart.dj.ui.news.contract.PartyNewsContract;
import com.jaydenxiao.common.basebean.BaseResponse;
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

    @Override
    public void getResVisitNumRequest(Integer resType, Integer resId) {
        mRxManage.add(mModel.getResVisitNum(resType,resId)
                .subscribe(new RxSubscriber<VisitNumResponse>(mContext,false) {
                    @Override
                    protected void _onNext(VisitNumResponse visitNumResponse) {
                        mView.returnResVisitNum(visitNumResponse);
                    }
                    @Override
                    protected void _onError(String message) {
                    }
                }));
    }


    @Override
    public void getAddResVisitNumRequest(Integer resType, Integer resId) {
        mRxManage.add(mModel.addResVisitNum(resType,resId)
                .subscribe(new RxSubscriber<BaseResponse>(mContext,false) {
                    @Override
                    protected void _onNext(BaseResponse baseResponse) {
                        mView.returnAddResVisitNum(baseResponse);
                    }
                    @Override
                    protected void _onError(String message) {
                    }
                }));
    }

    @Override
    public void getAllResVisitNumRequest(final String code,Integer resType, String resIds) {
        mRxManage.add(mModel.getAllResVisitNum(resType,resIds)
                .subscribe(new RxSubscriber<BaseResponse<VisitNumEntity>>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    protected void _onNext(BaseResponse<VisitNumEntity> baseResponse) {
                        mView.returnAllResVisitNum(baseResponse,code);
                    }
                    @Override
                    protected void _onError(String message) {
                        mView.showErrorTip(message,"");
                    }
                }));
    }
}
