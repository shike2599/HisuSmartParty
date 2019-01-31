package com.hisu.smart.dj.ui.news.presenter;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.entity.NewsInfoResponse;
import com.hisu.smart.dj.entity.StudyPlanRespone;
import com.hisu.smart.dj.ui.news.contract.NewsInfoContract;
import com.hisu.smart.dj.ui.study.contract.StudyPlanContract;
import com.jaydenxiao.common.baserx.RxSubscriber;

/**
 * des:
 * Created by xsf
 * on 2016.09.14:53
 * @author lichee
 */
public class NewInfoPresenter extends NewsInfoContract.Presenter {

    @Override
    public void getNewsInfoDataRequest(Integer id) {
        mRxManage.add(mModel.getNewsInfoData(id)
                 .subscribe(new RxSubscriber<NewsInfoResponse>(mContext,false) {
                     @Override
                     public void onStart() {
                         super.onStart();
                         mView.showLoading(mContext.getString(R.string.loading));
                     }

                     @Override
                     protected void _onNext(NewsInfoResponse newsInfoResponse) {
                         mView.returnNewsInfoData(newsInfoResponse);
                         mView.stopLoading(null);
                     }

                     @Override
                     protected void _onError(String message) {
                         mView.showErrorTip(message,null);

                     }
                 }));
    }
}
