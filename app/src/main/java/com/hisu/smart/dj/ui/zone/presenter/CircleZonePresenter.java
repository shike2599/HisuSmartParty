package com.hisu.smart.dj.ui.zone.presenter;

import android.util.Log;
import android.view.View;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;

import com.hisu.smart.dj.entity.CommentEntity;
import com.hisu.smart.dj.entity.Result;

import com.hisu.smart.dj.entity.StudyListEntity;
import com.hisu.smart.dj.entity.ThumbsUpEntity;
import com.hisu.smart.dj.entity.VisitNumResponse;
import com.hisu.smart.dj.ui.zone.bean.CircleItem;
import com.hisu.smart.dj.ui.zone.bean.CommentConfig;
import com.hisu.smart.dj.ui.zone.bean.CommentItem;
import com.hisu.smart.dj.ui.zone.bean.FavortItem;
import com.hisu.smart.dj.ui.zone.contract.CircleZoneContract;
import com.hisu.smart.dj.ui.zone.widget.GoodView;
import com.jaydenxiao.common.baseapp.AppCache;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.baserx.RxSubscriber;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.wevey.selector.dialog.DialogOnClickListener;
import com.wevey.selector.dialog.MDAlertDialog;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.functions.Action1;


/**
 * des:朋友圈presenter
 * Created by xsf
 * on 2016.07.15:57
 */
public class CircleZonePresenter extends CircleZoneContract.Presenter {

    private static final String TAG = "CircleZonePresenter";



    //点赞效果
    private GoodView mGoodView;

    /**
     * 监听
     */
    @Override
    public void onStart() {
        super.onStart();
        //新增说说监听
        mRxManage.on(AppConstant.ZONE_PUBLISH_ADD, new Action1<CircleItem>() {
            @Override
            public void call(CircleItem circleItem) {
                if (circleItem != null) {
                    mView.setOnePublishData(circleItem);
                }
            }
        });
    }

    /**
     * 获取未读总数
     */
    @Override
    public void getNotReadNewsCount() {
        mRxManage.add(mModel.getZoneNotReadNews().subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String icon) {
                mView.updateNotReadNewsCount(10, icon);
            }
        }));
    }

    /**
     * 获取列表
     */
    @Override
    public void getListData(Integer partyMemberId, final Integer cateId, final String cateCode, Integer id, String publishTime, Integer pageSize) {
        mRxManage.add(mModel.getListDatas(partyMemberId, cateId, cateCode, id, publishTime, pageSize).subscribe(new RxSubscriber<StudyListEntity>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("");
            }
            @Override
            protected void _onNext(StudyListEntity studyListEntity) {
                List<StudyListEntity.DataListBean> dataList = studyListEntity.getDataList();
                String outServer = studyListEntity.getOutServer();
                if (dataList != null) {
                    final List<CircleItem> circleItems = new ArrayList<>();
                    for (StudyListEntity.DataListBean bean : dataList) {
                        final CircleItem circleItem = new CircleItem();
                        circleItem.setId(bean.getId());
                        circleItem.setAppointUserid(bean.getPartyMemberId());
                        circleItem.setUserId(bean.getPartyMemberId()+"");
                        circleItem.setAppointUserNickname(bean.getPartyMemberName());
                        circleItem.setContent(bean.getName());
                        circleItem.setIcon(outServer + "/" + bean.getPartyMemberPhoto());
                        circleItem.setCreateTime(bean.getPublishTime());
                        circleItem.setNickName(bean.getPartyMemberName());
                        List<String> images = bean.getImages();
                        if (images != null) {
                            for (String image : images) {
                                circleItem.addPictures(outServer + "/" + image);
                            }
                        }
                        circleItems.add(circleItem);
                    }
                    mView.setListData(circleItems);
                    if (circleItems.size() > 0) {
                        mView.stopLoading("");
                    } else {
                        mView.stopLoading("EMPTY");
                    }
                } else {
                    mView.stopLoading("EMPTY");
                }
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message, "");
            }
        }));
    }

    @Override
    public void getTheThumbsUpData(final Integer resId, Integer userId, Integer partyMemberId) {
        mRxManage.add(mModel.listTheThumbsUp(resId, userId, partyMemberId).subscribe(new RxSubscriber<BaseResponse<ThumbsUpEntity>>(mContext, false) {
            @Override
            protected void _onNext(BaseResponse<ThumbsUpEntity> response) {
                mView.returnTheThumbsUpData(resId,response.getDataList());
            }
            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void getTheCommentData(final Integer resId, Integer userId, Integer partyMemberId) {
        mRxManage.add(mModel.listTheComment(resId, userId, partyMemberId).subscribe(new RxSubscriber<BaseResponse<CommentEntity>>(mContext, false) {
            @Override
            protected void _onNext(BaseResponse<CommentEntity> response) {
                mView.returnTheCommentData(resId,response.getDataList());
            }
            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void addComment(final String content, final CommentConfig config) {
        if (config == null) {
            return;
        }

        int userId = AppConfig.getInstance().getInt(AppConstant.USER_ID,-1);
        int memberId = AppConfig.getInstance().getInt(AppConstant.MEMBER_ID,-1);
        Log.i("addComment","resId:"+config.getPublishId()+",userId:"+userId+",memberId:"+memberId+",name:"+config.getName());
        mRxManage.add(mModel.addComment(Integer.parseInt(config.getPublishId()),userId,memberId ,content).subscribe(new RxSubscriber<VisitNumResponse>(mContext,false) {
            @Override
            protected void _onNext(VisitNumResponse visitNumResponse) {
                int id = visitNumResponse.getData();
                mView.update2AddComment(config.circlePosition, new CommentItem(id+"",config.getName(),config.getPublishUserId(), content, config.getPublishId(), config.getPublishUserId(), config.getName()));
            }

            @Override
            protected void _onError(String message) {
                ToastUitl.showToastWithImg(mContext.getString(R.string.net_error), R.drawable.ic_wrong);
            }
        }));
    }

    @Override
    public void deleteComment(final int circlePosition, final int commentId, final int commentPosition) {
        mRxManage.add(mModel.deleteComment(commentId).subscribe(new RxSubscriber<VisitNumResponse>(mContext,false) {
            @Override
            protected void _onNext(VisitNumResponse visitNumResponse) {
                    mView.update2DeleteComment(circlePosition, commentId+"", commentPosition);
            }
            @Override
            protected void _onError(String message) {
            }
        }));
    }


    /**
     * 删除朋友圈
     *
     * @param circleId
     */
    MDAlertDialog mdAlertDialog;

    @Override
    public void deleteCircle(final Integer resId, final Integer userId, final Integer partyMemberId, final int position) {
        mdAlertDialog = new MDAlertDialog.Builder(mContext)
                .setHeight(0.25f)  //屏幕高度*0.3
                .setWidth(0.7f)  //屏幕宽度*0.7
                .setTitleVisible(true)
                .setTitleText("温馨提示")
                .setTitleTextColor(R.color.black_light)
                .setContentText("确定删除该条说说吗？")
                .setContentTextColor(R.color.black_light)
                .setLeftButtonText("不删除")
                .setLeftButtonTextColor(R.color.black_light)
                .setRightButtonText("删除")
                .setRightButtonTextColor(R.color.gray)
                .setTitleTextSize(16)
                .setContentTextSize(14)
                .setButtonTextSize(14)
                .setOnclickListener(new DialogOnClickListener() {
                    @Override
                    public void clickLeftButton(View view) {
                        mdAlertDialog.dismiss();
                    }
                    @Override
                    public void clickRightButton(View view) {
                        mdAlertDialog.dismiss();
                        mView.startProgressDialog();
                        mRxManage.add(mModel.deleteCircle(resId,userId,partyMemberId).subscribe(new RxSubscriber<VisitNumResponse>(mContext,false) {
                            @Override
                            public void onCompleted() {
                                mView.stopProgressDialog();
                            }

                            @Override
                            protected void _onNext(VisitNumResponse visitNumResponse) {
                                mView.update2DeleteCircle("", position);
                            }

                            @Override
                            protected void _onError(String message) {
                                mView.startProgressDialog();
                                ToastUitl.showToastWithImg(mContext.getString(R.string.net_error), R.drawable.ic_wrong);
                            }
                        }));
                    }
                })
                .build();
        mdAlertDialog.show();
    }


    /**
     * 显示输入框
     *
     * @param commentConfig
     */
    @Override
    public void showEditTextBody(CommentConfig commentConfig) {
        mView.updateEditTextBodyVisible(View.VISIBLE, commentConfig);
    }





    /**
     * 点赞
     *
     * @param circlePosition
     */

    @Override
    public void addFavort(Integer resId, Integer userId, Integer partyMemberId, final int circlePosition, final View view) {
        mView.startProgressDialog();
        mRxManage.add(mModel.addFavort(resId,userId,partyMemberId).subscribe(new RxSubscriber<VisitNumResponse>(mContext,false) {
            @Override
            public void onCompleted() {
                mView.stopProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                ToastUitl.showToastWithImg(mContext.getString(R.string.net_error), R.drawable.ic_wrong);
            }

            @Override
            protected void _onNext(VisitNumResponse visitNumResponse) {
                if (visitNumResponse != null) {
                    if (mGoodView == null) {
                        mGoodView = new GoodView(mContext);
                    }
                    //mGoodView.setTextInfo("点赞成功", ContextCompat.getColor(mContext, R.color.main_color), 12);
                    mGoodView.setImage(R.mipmap.dianzan);
                    mGoodView.show(view);
                    FavortItem item = new FavortItem(visitNumResponse.getData()+"", AppConfig.getInstance().getInt(AppConstant.USER_ID,-1)+"", AppConfig.getInstance().getString(AppConstant.NICK_NAME,""));
                    mView.update2AddFavorite(circlePosition, item);
                }
            }

            @Override
            protected void _onError(String message) {

            }

        }));
    }


    /**
     * 取消点赞
     *
     * @param circlePosition
     */
    @Override
    public void deleteFavort(final Integer id, final int circlePosition) {
        mView.startProgressDialog();
        mRxManage.add(mModel.deleteFavort(id).subscribe(new RxSubscriber<VisitNumResponse>(mContext,false) {
            @Override
            public void onCompleted() {
                mView.stopProgressDialog();
            }


            @Override
            protected void _onNext(VisitNumResponse visitNumResponse) {
                if (visitNumResponse != null) {
                    mView.update2DeleteFavort(circlePosition, AppConfig.getInstance().getInt(AppConstant.USER_ID,-1)+"");
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUitl.showToastWithImg(mContext.getString(R.string.net_error), R.drawable.ic_wrong);
            }
        }));
    }



}