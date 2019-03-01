package com.hisu.smart.dj.ui.study.presenter;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.entity.StudyListEntity;
import com.hisu.smart.dj.entity.StudyPlanRespone;
import com.hisu.smart.dj.ui.study.contract.StudyListContract;
import com.hisu.smart.dj.ui.study.contract.StudyPlanContract;
import com.jaydenxiao.common.baserx.RxSubscriber;

/**
 * des:
 * Created by xsf
 * on 2016.09.14:53
 * @author lichee
 */
public class StudyListPresenter extends StudyListContract.Presenter {

    @Override
    public void getListMemberActionWithPulledRequest(Integer partyMemberId, Integer cateId, String cateCode, Integer id, String publishTime, Integer pageSize) {
        mRxManage.add(mModel.getListMemberActionWithPulled(partyMemberId,cateId,cateCode,id,publishTime,pageSize)
                .subscribe(new RxSubscriber<StudyListEntity>(mContext,false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading(mContext.getString(R.string.loading));
                    }

                    @Override
                    protected void _onNext(StudyListEntity studyListEntity) {
                        mView.returnMemberActionWithPulled(studyListEntity);
                        mView.stopLoading(null);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.showErrorTip(message,null);
                    }
                }));
    }
}
