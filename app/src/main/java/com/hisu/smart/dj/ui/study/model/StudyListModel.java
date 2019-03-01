package com.hisu.smart.dj.ui.study.model;

import com.hisu.smart.dj.api.Api;
import com.hisu.smart.dj.app.AppApplication;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.StudyListEntity;
import com.hisu.smart.dj.entity.StudyPlanRespone;
import com.hisu.smart.dj.ui.study.contract.StudyListContract;
import com.hisu.smart.dj.ui.study.contract.StudyPlanContract;
import com.jaydenxiao.common.baserx.RxSchedulers;

import rx.Observable;
import rx.functions.Func1;


/**
 * des:新闻列表model
 * Created by xsf
 * on 2016.09.14:54
 * @author lichee
 */
public class StudyListModel implements StudyListContract.Model {
    @Override
    public Observable<StudyListEntity> getListMemberActionWithPulled(Integer partyMemberId, Integer cateId, String cateCode, Integer id, String publishTime, Integer pageSize) {
        return Api.getDefault(AppApplication.getAppContext(),AppConstant.HOST_URL)
                .listMemberActionWithPulled(partyMemberId,cateId,cateCode,id,publishTime,pageSize)
                .map(new Func1<StudyListEntity, StudyListEntity>() {
                    @Override
                    public StudyListEntity call(StudyListEntity studyListEntity) {
                        return studyListEntity;
                    }
                })
                .compose(RxSchedulers.<StudyListEntity>io_main());
    }
}
