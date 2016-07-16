package com.cn.goldenjobs.presenter;

import android.os.Bundle;

import com.cn.goldenjobs.bean.CityList;
import com.cn.goldenjobs.bean.ImageUrlList;
import com.cn.goldenjobs.bean.RespUser;
import com.cn.goldenjobs.httptask.API;
import com.cn.goldenjobs.httptask.APIStoreTask;
import com.cn.goldenjobs.ui.activity.LoginActivity;
import com.cn.goldenjobs.ui.fragment.ModuleFragment;
import com.iyiyo.nucleus.presenter.RxPresenter;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liu-feng on 2016/7/8.
 * 邮箱:w710989327@foxmail.com
 */
public class ModulePresenter extends RxPresenter<ModuleFragment> {

    private static final int REQUEST_LOGIN = 1;  // 登录

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        add(afterTakeView()
                .observeOn(Schedulers.io())
                .flatMap((view) -> {
                    return requestCityListDetail(view);
                })
                .filter((article -> article != null))
                .compose(this.<CityList>deliverFirst())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(split(
                        (view, detail) -> {
                            if (detail != null && detail.getRetData() != null)
                                view.onLoadFinished(detail.getRetData());
                            else
                                view.onLoadFailure(new Throwable("-1"));
                        },
                        (view, error) -> {
                            error.printStackTrace();
                            view.onLoadFailure(error);
                        })
                )
        );
    }

    /**
     * 请求城市列表
     *
     * @param view
     * @return
     */
    private Observable<CityList> requestCityListDetail(ModuleFragment view) {
        return APIStoreTask.getInstance().getCityList().map(new Func1<CityList, CityList>() {
            @Override
            public CityList call(CityList cityList) {
                return cityList;
            }
        });
    }

    /**
     * 请求城市列表
     *
     * @param view
     * @return
     */
    private Observable<ImageUrlList> requestImageListDetail(ModuleFragment view) {
        return APIStoreTask.getInstance().getImageList().map(new Func1<ImageUrlList, ImageUrlList>() {
            @Override
            public ImageUrlList call(ImageUrlList cityList) {
                return cityList;
            }
        });
    }
}
