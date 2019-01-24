package com.hisu.smart.dj.ui.main.presenter;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.ui.main.contract.NewsListContract;
import com.jaydenxiao.common.baserx.RxSubscriber;



/**
 * des:
 * Created by xsf
 * on 2016.09.14:53
 */
public class NewsListPresenter extends NewsListContract.Presenter {

    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * 请求获取列表数据
     * @param cateCode
     * @param keywords
     * @param pageNo
     * @param pageSize
     */
    @Override
    public void getNewsListDataRequest(String cateCode,String keywords,Integer pageNo,Integer pageSize) {
         mRxManage.add(mModel.getNewsListData(cateCode,keywords,pageNo,pageSize).subscribe(new RxSubscriber<InformationResponse<InformationEntity>>(mContext,false) {
             @Override
             public void onStart() {
                 super.onStart();
                 mView.showLoading(mContext.getString(R.string.loading));
             }

             @Override
             protected void _onNext(InformationResponse<InformationEntity> informations) {
                 mView.returnNewsListData(informations.getDataList());
                 mView.stopLoading();
             }

             @Override
             protected void _onError(String message) {
                 mView.showErrorTip(message);
             }
         }));
    }
}
