package com.hisu.smart.dj.ui.study.presenter;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.entity.CateEntity;
import com.hisu.smart.dj.entity.NotingResponse;
import com.hisu.smart.dj.entity.UpLoadFileResponse;
import com.hisu.smart.dj.ui.study.contract.StudyCommonMainContract;
import com.hisu.smart.dj.ui.study.contract.UpLoadFileContract;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSubscriber;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

/**
 *
 * @author lichee
 * @date 2019/1/30
 */

public class UpLoadFilePresenter extends UpLoadFileContract.Presenter{

    @Override
    public void submitActionContentRequest(Integer userId, Integer partyMemberId,
                                           Integer cateId, String cateCode, String name,
                                           String icon, String imgPaths,
                                           Integer mediaType, String content, String url,
                                           String publishTime, Boolean isNeedSign) {
        mRxManage.add(mModel.submitActionContent(userId,partyMemberId,cateId,cateCode,name,icon,imgPaths,
                mediaType,content,url,publishTime,isNeedSign).subscribe(new RxSubscriber<NotingResponse>(mContext,false) {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(NotingResponse notingResponse) {
                mView.returnSubmitResponse(notingResponse);
                mView.stopLoading("");
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,"");
            }
        }));
    }

    @Override
    public void upLoadFileRequest(Map<String, RequestBody> parmas) {
        mRxManage.add(mModel.uploadFile(parmas).subscribe(new RxSubscriber<UpLoadFileResponse>(mContext,false) {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(UpLoadFileResponse upLoadFileResponse) {
                mView.returnUpLoadFile(upLoadFileResponse);
                mView.stopLoading("");
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,"");
            }
        }));
    }
}
