package com.hisu.smart.dj.ui.zone.contract;


import com.hisu.smart.dj.entity.CommentEntity;
import com.hisu.smart.dj.entity.Result;
import com.hisu.smart.dj.entity.StudyListEntity;
import com.hisu.smart.dj.entity.ThumbsUpEntity;
import com.hisu.smart.dj.entity.VisitNumResponse;
import com.hisu.smart.dj.ui.zone.bean.CircleItem;
import com.hisu.smart.dj.ui.zone.bean.CommentConfig;
import com.hisu.smart.dj.ui.zone.bean.CommentItem;
import com.hisu.smart.dj.ui.zone.bean.FavortItem;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.jaydenxiao.common.basebean.BaseResponse;

import java.util.List;

import rx.Observable;

/**
 * des:朋友圈契约接口
 * Created by xsf
 * on 2016.07.15:52
 */
public interface CircleZoneContract {

    interface Model extends BaseModel {

        //获取党员圈列表数据
        Observable<StudyListEntity> getListDatas(Integer partyMemberId, Integer cateId,String cateCode, Integer id,String publishTime,Integer pageSize);
        //获取点赞记录
        Observable<BaseResponse<ThumbsUpEntity>> listTheThumbsUp(Integer resId, Integer userId, Integer partyMemberId);
        //获取评论记录
        Observable<BaseResponse<CommentEntity>> listTheComment(Integer resId, Integer userId, Integer partyMemberId);
        //添加评论
        Observable<VisitNumResponse> addComment(Integer resId,Integer userId, Integer partyMemberId,String comment);
        //删除评论
        Observable<VisitNumResponse> deleteComment(Integer id);
        //删除说说
        Observable<VisitNumResponse> deleteCircle(Integer resId, Integer userId, Integer partyMemberId);//int position

        Observable<VisitNumResponse> addFavort(Integer resId, Integer userId, Integer partyMemberId);

        Observable<VisitNumResponse> deleteFavort(Integer id);


        //todo
        Observable<String> getZoneNotReadNews();

    }

    interface View extends BaseView {
        //获取点赞记录
        void returnTheThumbsUpData(int resId,List<ThumbsUpEntity> thumbsUpEntityList);
        //获取评论记录
        void returnTheCommentData(int resId,List<CommentEntity> commentEntityList);
        //获取党员圈列表数据
        void setListData(List<CircleItem> circleItems);

        //获取未读总数
        void updateNotReadNewsCount(int count, String icon);

        void setOnePublishData(CircleItem circleItem);

        void update2DeleteCircle(String circleId, int position);

        void update2AddFavorite(int circlePosition, FavortItem addItem);

        void update2DeleteFavort(int circlePosition, String userId);

        void update2AddComment(int circlePosition, CommentItem addItem);

        void update2DeleteComment(int circlePosition, String commentId, int commentPosition);

        void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig);

        void startProgressDialog();

        void stopProgressDialog();
    }

    abstract static class Presenter extends BasePresenter<View, Model> {

        //获取党员圈列表数据
        public abstract void getListData(Integer partyMemberId, Integer cateId,String cateCode, Integer id,String publishTime,Integer pageSize);
        //获取点赞记录
        public abstract void getTheThumbsUpData(Integer resId, Integer userId, Integer partyMemberId);
        //获取评论记录
        public abstract void getTheCommentData(Integer resId, Integer userId, Integer partyMemberId);
        //增加评论
        public abstract void addComment(final String content, final CommentConfig config);
        //删除评论
        public abstract void deleteComment(final int circlePosition, final int commentId, final int commentPosition);
        //删除
        public abstract void deleteCircle(Integer resId, Integer userId, Integer partyMemberId, int position);

        //点赞
        public abstract void addFavort(Integer resId, Integer userId, Integer partyMemberId, final int circlePosition, final android.view.View view);

        //取消点赞
        public abstract void deleteFavort(Integer id, final int circlePosition);


        //显示评论输入框
        public abstract void showEditTextBody(CommentConfig commentConfig);


        //获取未读总数
        public abstract void getNotReadNewsCount();


    }


}
