package com.hisu.smart.dj.ui.news.presenter;

import com.hisu.smart.dj.entity.CateEntity;
import com.hisu.smart.dj.ui.news.contract.PartyNewsMainContract;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSubscriber;

/**
 *
 * @author lichee
 * @date 2019/1/30
 */

public class PartyNewsMainPresenter extends PartyNewsMainContract.Presenter{

    @Override
    public void listInfoCateRequest(Integer parentId, String parentCode, String codeKeywords) {
        mRxManage.add(mModel.listInfoCate( parentId,parentCode,codeKeywords).subscribe(new RxSubscriber<BaseResponse<CateEntity>>(mContext,false)  {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            protected void _onNext(BaseResponse<CateEntity> response) {
                mView.returnListInfoCate(response.getDataList());
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,"");
            }
        }));
    }
}
