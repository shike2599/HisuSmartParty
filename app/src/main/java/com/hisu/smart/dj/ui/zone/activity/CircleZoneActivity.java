package com.hisu.smart.dj.ui.zone.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.animation.ScaleInAnimation;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.hisu.smart.dj.R;

import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.CommentEntity;
import com.hisu.smart.dj.entity.ThumbsUpEntity;
import com.hisu.smart.dj.ui.zone.adapter.CircleAdapter;
import com.hisu.smart.dj.ui.zone.bean.CircleItem;
import com.hisu.smart.dj.ui.zone.bean.CommentConfig;
import com.hisu.smart.dj.ui.zone.bean.CommentItem;
import com.hisu.smart.dj.ui.zone.bean.FavortItem;
import com.hisu.smart.dj.ui.zone.contract.CircleZoneContract;
import com.hisu.smart.dj.ui.zone.model.ZoneModel;
import com.hisu.smart.dj.ui.zone.presenter.CircleZonePresenter;
import com.hisu.smart.dj.ui.zone.widget.CommentListView;
import com.hisu.smart.dj.ui.zone.widget.ZoneHeaderView;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.DisplayUtil;
import com.jaydenxiao.common.commonutils.KeyBordUtil;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.commonwidget.LoadingTip;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * des:朋友圈
 * Created by xsf
 * on 2016.07.11:19
 */
public class CircleZoneActivity extends BaseActivity<CircleZonePresenter, ZoneModel> implements CircleZoneContract.View, View.OnClickListener {

    @Bind(R.id.title_TextView)
    TextView title;
    @Bind(R.id.back_imageView)
    ImageView back_img;
    @Bind(R.id.irc)
    IRecyclerView irc;
    @Bind(R.id.loadedTip)
    LoadingTip loadedTip;
    @Bind(R.id.circleEt)
    EditText circleEt;
    @Bind(R.id.sendIv)
    ImageView sendIv;
    @Bind(R.id.editTextBodyLl)
    LinearLayout editTextBodyLl;
    //朋友圈头部
    ZoneHeaderView zoneHeaderView;
    private CircleAdapter mAdapter;
    private CommentConfig mCommentConfig;

    private int mScreenHeight;
    private int mEditTextBodyHeight;
    private int mCurrentKeyboardH;
    private int mSelectCircleItemH;
    private int mSelectCommentItemOffset;
    private LinearLayoutManager linearLayoutManager;

    private int pageSize = 3;
    private int cid = -1;
    private String publishTime = "";

    private Integer userId;
    private Integer partyMemberId;

    private List<CircleItem> circleItemList = new ArrayList<>();

    private String TAG = "CircleZoneActivity";

    /**
     * 启动入口
     *
     * @param context
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, CircleZoneActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fra_circle_list;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    /**
     * 初始化
     */
    public void initView() {

        title.setText("党员圈");
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //滑动列表关闭输入框
        irc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (editTextBodyLl.getVisibility() == View.VISIBLE)
                    updateEditTextBodyVisible(View.GONE, null);
                return false;
            }
        });

        //初始化头部未读消息
        zoneHeaderView = new ZoneHeaderView(this);
        String photo = AppConfig.getInstance().getString(AppConstant.USER_PHOTO,"");
        Log.i("photo",photo);
        if(!"".equals(photo)){
            zoneHeaderView.setAvater(photo);
        }else{
            zoneHeaderView.setAvater(R.mipmap.icon_zone_user);
        }

        irc.addHeaderView(zoneHeaderView);

        mAdapter = new CircleAdapter(this, mPresenter);
        mAdapter.openLoadAnimation(new ScaleInAnimation());
        linearLayoutManager = new LinearLayoutManager(this);
        irc.setLayoutManager(linearLayoutManager);
        irc.setAdapter(mAdapter);
        //监听recyclerview滑动
        setViewTreeObserver();
        //上拉刷新
        irc.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.getPageBean().setRefresh(true);
                //发起请求
                irc.setRefreshing(true);
                mPresenter.getListData(null, null, "5007", null, null, pageSize);
            }
        });
        //下拉加载更多
        irc.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(View loadMoreView) {
                mAdapter.getPageBean().setRefresh(false);
                Log.i(TAG, "onLoadMore========================" + cid);
                if (cid > 0) {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
                    mPresenter.getListData(null, null, "5007", cid, publishTime, pageSize);
                } else {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        });
       userId = AppConfig.getInstance().getInt(AppConstant.USER_ID,-1);
       partyMemberId = AppConfig.getInstance().getInt(AppConstant.MEMBER_ID,-1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //首次加载数据
        mPresenter.getListData(null, null, "5007", null, null, pageSize);
    }

    /**
     * 监听recyclerview滑动
     */
    private void setViewTreeObserver() {
        final ViewTreeObserver swipeRefreshLayoutVTO = irc.getViewTreeObserver();
        swipeRefreshLayoutVTO.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                irc.getWindowVisibleDisplayFrame(r);
                int statusBarH = getStatusBarHeight();//状态栏高度
                int screenH = irc.getRootView().getHeight();
                if (r.top != statusBarH) {
                    //在这个demo中r.top代表的是状态栏高度，在沉浸式状态栏时r.top＝0，通过getStatusBarHeight获取状态栏高度
                    r.top = statusBarH;
                }
                int keyboardH = screenH - (r.bottom - r.top);
                LogUtils.logd("screenH＝ " + screenH + " &keyboardH = " + keyboardH + " &r.bottom=" + r.bottom + " &top=" + r.top + " &statusBarH=" + statusBarH);
                if (keyboardH == mCurrentKeyboardH) {//有变化时才处理，否则会陷入死循环
                    return;
                }
                mCurrentKeyboardH = keyboardH;
                mScreenHeight = screenH;//应用屏幕的高度
                mEditTextBodyHeight = editTextBodyLl.getHeight();

                //偏移listview
                if (irc != null && mCommentConfig != null) {
                    int index = mCommentConfig.circlePosition + irc.getHeaderContainer().getChildCount() + 1;
                    linearLayoutManager.scrollToPositionWithOffset(index, getListviewOffset(mCommentConfig));
                }
            }
        });
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.sendIv})
    public void onClick(View view) {
        switch (view.getId()) {
            //评论
            case R.id.sendIv:
                if (mPresenter != null) {
                    //发布评论
                    String content = circleEt.getText().toString().trim();
                    if (TextUtils.isEmpty(content)) {
                        ToastUitl.showToastWithImg("评论内容不能为空", R.drawable.ic_warm);
                        return;
                    }
                    Log.i(TAG,mCommentConfig.toString());
                    mPresenter.addComment(content, mCommentConfig);
                }
                updateEditTextBodyVisible(View.GONE, null);
                break;
//            case R.id.menuRed:
//                CirclePublishActivity.startAction(this);
//                break;
        }
    }


    /**
     * 未读消息总数
     *
     * @param count
     */
    @Override
    public void updateNotReadNewsCount(int count, String icon) {
    }


    @Override
    public void returnTheThumbsUpData(int resId, List<ThumbsUpEntity> thumbsUpEntityList) {
        if (circleItemList != null && thumbsUpEntityList != null) {
            Log.i(TAG, "returnTheThumbsUpData=========================resId:" + resId + ",size:" + thumbsUpEntityList.size());
            int size = circleItemList.size();
            for (int i = 0; i < size; i++) {
                if (circleItemList.get(i).getId() == resId) {
                    if (thumbsUpEntityList != null) {
                        List<FavortItem> goodjobs = new ArrayList<>();
                        for (ThumbsUpEntity entity : thumbsUpEntityList) {
                            FavortItem item = new FavortItem();
                            Log.i(TAG,"returnTheThumbsUpData::::::"+entity.toString());
                            item.setId(entity.getId() + "");
                            item.setUserId(entity.getUserId() + "");
                            item.setPublishId(entity.getId() + "");
                            item.setUserNickname(entity.getPartyMemberName());
                            item.setCreateTime(entity.getCreateTime());
                            goodjobs.add(item);
                        }
                        circleItemList.get(i).getGoodjobs().addAll(goodjobs);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void returnTheCommentData(int resId, List<CommentEntity> commentEntityList) {
        if (circleItemList != null && commentEntityList != null) {
            Log.i(TAG, "returnTheCommentData=========================resId:" + resId + ",size:" + commentEntityList.size());
            int size = circleItemList.size();
            for (int i = 0; i < size; i++) {
                if (circleItemList.get(i).getId() == resId) {
                    List<CommentItem> replys = new ArrayList<>();
                    if (commentEntityList != null) {
                        for (CommentEntity entity : commentEntityList) {
                            CommentItem item = new CommentItem();
                            item.setResId(resId+"");
                            item.setId(entity.getId() + "");
                            item.setUserId(entity.getUserId() + "");
                            item.setAppointUserid(entity.getUserId() + "");
                            item.setUserNickname(entity.getPartyMemberName());
                            item.setPublishId(entity.getId() + "");
                            item.setContent(entity.getComment());
                            item.setCreateTime(entity.getCreateTime());
                            replys.add(item);
                        }
                        circleItemList.get(i).getReplys().addAll(replys);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void setListData(List<CircleItem> circleItems) {
        int size = circleItems.size();
        Log.i(TAG, "setListData=================" + size);
        if (size > 0) {
            circleItemList = circleItems;
            cid = circleItemList.get(size - 1).getId();
            publishTime = circleItemList.get(size - 1).getCreateTime();
            for (CircleItem circleItem : circleItemList) {
                mPresenter.getTheThumbsUpData(circleItem.getId(), userId, partyMemberId);
                mPresenter.getTheCommentData(circleItem.getId(), userId, partyMemberId);
            }
        } else {
            cid = -1;
            publishTime = "";
            return;
        }
        if (mAdapter.getPageBean().isRefresh()) {
            irc.setRefreshing(false);
            mAdapter.reset(circleItemList);
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
        } else {
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
            mAdapter.addAll(circleItemList);
        }
    }

    @Override
    public void setOnePublishData(CircleItem circleItem) {
        mAdapter.add(0, circleItem);
    }

    @Override
    public void update2DeleteCircle(String circleId, int position) {
        mAdapter.remove(position);
    }

    @Override
    public void update2AddFavorite(int circlePosition, FavortItem addItem) {
        if (addItem != null) {
            mAdapter.getData().get(circlePosition).getGoodjobs().add(addItem);
            mAdapter.notifyItemChanged(circlePosition);
        }
    }

    @Override
    public void update2DeleteFavort(int circlePosition, String userId) {
        List<FavortItem> items = mAdapter.getData().get(circlePosition).getGoodjobs();
        for (int i = 0; i < items.size(); i++) {
            if (userId.equals(items.get(i).getUserId())) {
                items.remove(i);
                mAdapter.notifyItemChanged(circlePosition);
                return;
            }
        }
    }

    @Override
    public void update2AddComment(int circlePosition, CommentItem addItem) {
        if (addItem != null) {
            mAdapter.get(circlePosition).getReplys().add(addItem);
            mAdapter.notifyDataSetChanged();
        }
        //清空评论文本
        circleEt.setText("");
    }

    @Override
    public void update2DeleteComment(int circlePosition, String commentId, int commentPosition) {
        List<CommentItem> items = mAdapter.getData().get(circlePosition).getReplys();
        items.remove(commentPosition);
        mAdapter.notifyDataSetChanged();
        //调接口情况建议用id判断删除
//        for (int i = 0; i < items.size(); i++) {
//            if (commentId.equals(items.get(i).getId())) {
//                items.remove(i);
//                mAdapter.notifyDataSetChanged();
//                return;
//            }
//        }
    }

    @Override
    public void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig) {
        mCommentConfig = commentConfig;
        editTextBodyLl.setVisibility(visibility);

        measureCircleItemHighAndCommentItemOffset(commentConfig);

        if (commentConfig != null && CommentConfig.Type.REPLY.equals(commentConfig.getCommentType())) {
            circleEt.setHint("回复" + commentConfig.getName() + ":");
        } else {
            circleEt.setHint("说点什么吧");
        }
        if (View.VISIBLE == visibility) {
            circleEt.requestFocus();
            //弹出键盘
            KeyBordUtil.showSoftKeyboard(circleEt);
            //隐藏菜单
        } else if (View.GONE == visibility) {
            //隐藏键盘
            KeyBordUtil.hideSoftKeyboard(circleEt);
        }
    }

    /**
     * 测量偏移量
     *
     * @param commentConfig
     * @return
     */
    private int getListviewOffset(CommentConfig commentConfig) {
        if (commentConfig == null)
            return 0;
        //这里如果你的listview上面还有其它占高度的控件，则需要减去该控件高度，listview的headview除外。
        int listviewOffset = mScreenHeight - mSelectCircleItemH - mCurrentKeyboardH - mEditTextBodyHeight - back_img.getMeasuredHeight();
        if (commentConfig.commentType == CommentConfig.Type.REPLY) {
            //回复评论的情况
            listviewOffset = listviewOffset + mSelectCommentItemOffset - back_img.getMeasuredHeight();
        }
        return listviewOffset;
    }

    private void measureCircleItemHighAndCommentItemOffset(CommentConfig commentConfig) {
        if (commentConfig == null)
            return;

        int headViewCount = irc.getHeaderContainer().getChildCount();
        //当前选中的view
        int selectPostion = commentConfig.circlePosition + headViewCount + 1;
        View selectCircleItem = linearLayoutManager.findViewByPosition(selectPostion);

        if (selectCircleItem != null) {
            mSelectCircleItemH = selectCircleItem.getHeight() - DisplayUtil.dip2px(48);
            //获取评论view,计算出该view距离所属动态底部的距离
            if (commentConfig.commentType == CommentConfig.Type.REPLY) {
                //回复评论的情况
                CommentListView commentLv = (CommentListView) selectCircleItem.findViewById(R.id.commentList);
                if (commentLv != null) {
                    //找到要回复的评论view,计算出该view距离所属动态底部的距离
                    View selectCommentItem = commentLv.getChildAt(commentConfig.commentPosition);
                    if (selectCommentItem != null) {
                        //选择的commentItem距选择的CircleItem底部的距离
                        mSelectCommentItemOffset = 0;
                        View parentView = selectCommentItem;
                        do {
                            int subItemBottom = parentView.getBottom();
                            parentView = (View) parentView.getParent();
                            if (parentView != null) {
                                mSelectCommentItemOffset += (parentView.getHeight() - subItemBottom);
                            }
                        }
                        while (parentView != null && parentView != selectCircleItem);
                    }
                }
            }
        }

    }

    @Override
    public void showLoading(String title) {
        if (mAdapter.getPageBean().isRefresh()) {
            if (mAdapter.getItemCount() <= 0) {
                loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
            }
        }
    }

    @Override
    public void stopLoading(String msg) {
        Log.i(TAG, "stopLoading=======================" + msg);
        if ("".equals(msg)) {
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
        } else {
            if (mAdapter.getPageBean().isRefresh()) {
                irc.setRefreshing(false);
            }
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
        }
    }

    @Override
    public void showErrorTip(String msg, String tag) {
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
        loadedTip.setTips(msg);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (editTextBodyLl != null && editTextBodyLl.getVisibility() == View.VISIBLE) {
                editTextBodyLl.setVisibility(View.GONE);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

