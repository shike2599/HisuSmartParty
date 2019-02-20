package com.hisu.smart.dj.ui.main.presenter;

import com.hisu.smart.dj.entity.InformationEntity;
import com.hisu.smart.dj.entity.InformationResponse;
import com.hisu.smart.dj.entity.NoticeInfoEntity;
import com.hisu.smart.dj.entity.UnReadSizeEntity;
import com.hisu.smart.dj.entity.UserCollectionEntity;
import com.hisu.smart.dj.ui.main.contract.HomeInfoContract;
import com.jaydenxiao.common.baserx.RxSubscriber;


/**
 * des:
 * Created by xsf
 * on 2016.09.14:53
 */
public class HomeInfoPresenter extends HomeInfoContract.Presenter {

    @Override
    public void onStart() {
        super.onStart();
    }
    //通知消息内容
    @Override
    public void getListNoticeByTimeRequest(Integer userId, Integer partyBranchId, String publishTime, Integer limitNum) {
        mRxManage.add(mModel.getListNoticeByTime(userId,partyBranchId,publishTime,limitNum)
        .subscribe(new RxSubscriber<NoticeInfoEntity>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(null);
            }
            @Override
            protected void _onNext(NoticeInfoEntity noticeInfoEntity) {
                mView.returnListNoticeByTime(noticeInfoEntity,null);
                mView.showLoading(null);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message,null);
            }
        }));
    }

    //未读通知消息个数
    @Override
    public void getUnReadNoticeNuRequest(Integer userId, Integer partyBranchId) {
        mRxManage.add(mModel.getUnReadNoticeNum(userId,partyBranchId)
                .subscribe(new RxSubscriber<UserCollectionEntity>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading(null);
                    }
                    @Override
                    protected void _onNext(UserCollectionEntity userCollectionEntity) {
                        mView.returnUnReadNoticeNum(userCollectionEntity,null);
                        mView.showLoading(null);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.showErrorTip(message,null);
                    }
                }));
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
}
