package com.zdtc.ue.school.yw.network.rx

import android.annotation.SuppressLint
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.components.support.RxFragment
import com.zdtc.ue.school.yw.network.model.HttpResponse
import com.zdtc.ue.school.yw.network.function.HttpResultFunction
import com.zdtc.ue.school.yw.network.function.ServerResultFunction
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 适用Retrofit网络请求Observable(被监听者)
 *
 */
object RxObservable {

    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 无管理生命周期,容易导致内存溢出
     *
     * @author ZhongDaFeng
     */
    fun<T> getObservable(apiObservable: Observable<HttpResponse<T>>): Observable<T> {
        return apiObservable
                .flatMap(ServerResultFunction())
                .onErrorResumeNext(HttpResultFunction())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }


    /**
     * 获取被监听者
     * 备注:网络请求Observable构建
     * data:网络请求参数
     * <h1>补充说明</h1>
     * 传入LifecycleProvider<FragmentEvent>手动管理生命周期,避免内存溢出
     * 备注:需要继承RxFragment,RxDialogFragment
     * */
    @SuppressLint("CheckResult")
    fun<T,E> getObservable(observable: Observable<HttpResponse<T>>, lifecycle: LifecycleProvider<E>): Observable<T> {

        // 请求绑定生命周期，防止内存泄漏，同时返回回调之后页面已销毁造成的空指针错误
        if (lifecycle is RxAppCompatActivity) {
            val rxAppCompatActivity = lifecycle as RxAppCompatActivity
            observable.compose(rxAppCompatActivity.bindUntilEvent(ActivityEvent.DESTROY))
        } else if (lifecycle is RxFragment) {
            val rxFragment = lifecycle as RxFragment
            observable.compose(rxFragment.bindUntilEvent(FragmentEvent.DESTROY))
        }

        return observable
                .flatMap(ServerResultFunction())
                .onErrorResumeNext(HttpResultFunction())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
    }


}
