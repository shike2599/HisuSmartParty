package com.hisu.smart.dj.ui.study.presenter;

import com.hisu.smart.dj.entity.CateEntity;

import com.hisu.smart.dj.ui.study.contract.StudyCommonMainContract;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSubscriber;

/**
 *
 * @author lichee
 * @date 2019/1/30
 */

public class StudyCommonMainPresenter  extends StudyCommonMainContract.Presenter{

    @Override
    public void listCommonCateRequest(Integer parentId, String parentCode, String codeKeywords) {
        mRxManage.add(mModel.listCommonCate( parentId,parentCode,codeKeywords).subscribe(new RxSubscriber<BaseResponse<CateEntity>>(mContext,false)  {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            protected void _onNext(BaseResponse<CateEntity> rankEntityBaseResponse) {
                mView.returnListCommonCate(rankEntityBaseResponse.getDataList());
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,"");
            }
        }));
    }
}
