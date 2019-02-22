package com.hisu.smart.dj.ui.my.presenter;

import com.hisu.smart.dj.entity.CollectEntity;
import com.hisu.smart.dj.ui.my.contract.MyCollectContract;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSubscriber;

import java.util.List;

/**
 * Created by lichee on 2019/2/21.
 */

public class MyCollectPresenter extends MyCollectContract.Presenter {
    @Override
    public void getCollectListDataRequest(Integer userId, Integer id, Integer pageSize) {
        mRxManage.add(mModel.getCollectListData(userId,id,pageSize)
                .subscribe(new RxSubscriber<BaseResponse<CollectEntity>>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading("");
                    }
                    //请求完成
                    @Override
                    protected void _onNext(BaseResponse<CollectEntity> collects) {
                        List<CollectEntity> collectEntities = collects.getDataList();
                        if(collectEntities != null && collectEntities.size() > 0){
                            mView.returnCollectListData(collectEntities);
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
