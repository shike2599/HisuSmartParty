package com.hisu.smart.dj.ui.news.contract;

import com.hisu.smart.dj.entity.CateEntity;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.jaydenxiao.common.basebean.BaseResponse;

import java.util.List;

import rx.Observable;

/**
 *
 * @author lichee
 * @date 2019/1/30
 */

public interface PartyNewsMainContract {

    interface Model extends BaseModel {
        Observable<BaseResponse<CateEntity>> listInfoCate(Integer parentId, String parentCode, String codeKeywords);
    }

    interface View extends BaseView {
        void returnListInfoCate(List<CateEntity> cateEntitys);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void listInfoCateRequest(Integer parentId, String parentCode,String codeKeywords);
    }
}
