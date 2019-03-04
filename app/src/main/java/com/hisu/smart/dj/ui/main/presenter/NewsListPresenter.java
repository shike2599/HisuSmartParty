package com.hisu.smart.dj.ui.main.presenter;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.entity.VisitNumEntity;
import com.hisu.smart.dj.entity.VisitNumResponse;
import com.hisu.smart.dj.ui.main.contract.NewsListContract;
import com.jaydenxiao.common.basebean.BaseResponse;
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
    public void getNewsListDataRequest(final String cateCode, String keywords, Integer pageNo, Integer pageSize) {

         mRxManage.add(mModel.getNewsListData(cateCode,keywords,pageNo,pageSize).subscribe(new RxSubscriber<InformationResponse<InformationEntity>>(mContext,false) {
             @Override
             public void onStart() {
                 super.onStart();
                 mView.showLoading(cateCode);
             }
             //请求完成
             @Override
             protected void _onNext(InformationResponse<InformationEntity> informations) {
                 mView.returnNewsListData(informations,cateCode);
                 mView.stopLoading(cateCode);
             }

             @Override
             protected void _onError(String message) {
                 mView.showErrorTip(message,cateCode);
             }
         }));
    }
     //三会一课
    @Override
    public void getTopicListContentRequest(final String cateCode, String keywords, Integer pageNo, Integer pageSize) {
        mRxManage.add(mModel.getTopicContentData(cateCode,keywords,pageNo,pageSize)
                .subscribe(new RxSubscriber<InformationResponse<InformationEntity>>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(cateCode);
            }
            //请求完成
            @Override
            protected void _onNext(InformationResponse<InformationEntity> informations) {
                mView.returnNewsListData(informations,cateCode);
                mView.stopLoading(cateCode);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,cateCode);
            }
        }));
    }
    //践行活动列表
    @Override
    public void getListActionContentRequest(final String cateCode, String keywords, Integer pageNo, Integer pageSize) {
        mRxManage.add(mModel.getListActionContentData(cateCode,keywords,pageNo,pageSize)
        .subscribe(new RxSubscriber<InformationResponse<InformationEntity>>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(cateCode);
            }

            @Override
            protected void _onNext(InformationResponse<InformationEntity> informations) {
                mView.returnNewsListData(informations,cateCode);
                mView.stopLoading(cateCode);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,cateCode);
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
    public void getAllResVisitNumRequest(final String cateCode, Integer resType, String resIds) {
        mRxManage.add(mModel.getAllResVisitNum(resType,resIds)
                .subscribe(new RxSubscriber<BaseResponse<VisitNumEntity>>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading(cateCode);
                    }

                    @Override
                    protected void _onNext(BaseResponse<VisitNumEntity> baseResponse) {
                        mView.returnAllResVisitNum(baseResponse,cateCode);
                        mView.stopLoading(cateCode);
                    }
                    @Override
                    protected void _onError(String message) {
                        mView.showErrorTip(message,cateCode);
                    }
                }));
    }


}
