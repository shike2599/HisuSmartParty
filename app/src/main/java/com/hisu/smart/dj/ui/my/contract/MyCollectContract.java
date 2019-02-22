package com.hisu.smart.dj.ui.my.contract;

import com.hisu.smart.dj.entity.CollectEntity;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.jaydenxiao.common.basebean.BaseResponse;

import java.util.List;

import rx.Observable;

/**
 * Created by lichee on 2019/2/21.
 */

public interface MyCollectContract {
    interface Model extends BaseModel {
        Observable<BaseResponse<CollectEntity>> getCollectListData(Integer userId,Integer id, Integer pageSize);
    }

    interface View extends BaseView {
        void returnCollectListData(List<CollectEntity> collectList);
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getCollectListDataRequest(Integer userId,Integer id, Integer pageSize);
    }
}
