package com.hisu.smart.dj.ui.news.presenter;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.NewsInfoResponse;
import com.hisu.smart.dj.entity.NotingResponse;
import com.hisu.smart.dj.entity.UserCollectionEntity;
import com.hisu.smart.dj.ui.news.contract.NewsInfoContract;
import com.jaydenxiao.common.baserx.RxSubscriber;

/**
 * des:
 * Created by xsf
 * on 2016.09.14:53
 * @author lichee
 */
public class NewInfoPresenter extends NewsInfoContract.Presenter {
    /**
     * 资讯类
     * @param id
     */
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
    /**
     * 践行详情
     * @param id
     */
    @Override
    public void getFollowInfoDataRequest(Integer id) {
        mRxManage.add(mModel.getFollowInfoData(id)
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
    /**
     * 专题活动详情（三会一课）
     * @param id
     */
    @Override
    public void getTopicInfoDataRequest(Integer id) {
        mRxManage.add(mModel.getTopicInfoData(id)
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

    /**
     * 常规学习
     * @param id
     */
    @Override
    public void getCommonInfoDataRequest(Integer id) {
        mRxManage.add(mModel.getCommonInfoData(id)
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
                        mView.showErrorTip(message,"EMPTY");
                    }
                }));
    }
    //添加收藏
    @Override
    public void addCollectionDataRequest(Integer id, Integer partyBranchId, Integer resType, Integer resId) {
        mRxManage.add(mModel.addCollectionData(id,partyBranchId,resType,resId)
            .subscribe(new RxSubscriber<UserCollectionEntity>(mContext,false) {
                @Override
                public void onStart() {
                    super.onStart();
                    mView.showLoading(mContext.getString(R.string.loading));
                }

                @Override
                protected void _onNext(UserCollectionEntity userCollectionEntity) {
                    mView.stopLoading("collection");
                    mView.returnCollectionData(userCollectionEntity,AppConstant.ADD_COLLECTION_TAG);
                }

                @Override
                protected void _onError(String message) {
                    mView.showErrorTip(message,"collection");
                }
            }));
}
    //取消收藏
    @Override
    public void cancelCollectionRequest(Integer id) {
        mRxManage.add(mModel.cancelCollection(id)
                .subscribe(new RxSubscriber<NotingResponse>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading(mContext.getString(R.string.loading));
                    }

                    @Override
                    protected void _onNext(NotingResponse notingResponse) {
                        mView.stopLoading("collection");
                        mView.returnCancelCollectionData(notingResponse);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.showErrorTip(message,"collection");
                    }
                }));
    }
    //收藏状态
    @Override
    public void getUserCollectionDataRequest(Integer id, Integer partyBranchId, Integer resType, Integer resId) {
        mRxManage.add(mModel.getUserCollectionData(id,partyBranchId,resType,resId)
                .subscribe(new RxSubscriber<UserCollectionEntity>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading(mContext.getString(R.string.loading));
                    }

                    @Override
                    protected void _onNext(UserCollectionEntity userCollectionEntity) {
                        mView.stopLoading("UserCollection");
                        mView.returnCollectionData(userCollectionEntity, AppConstant.QUERY_COLLECTION_TAG);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.showErrorTip(message,"UserCollection");
                    }
                }));
    }
}
