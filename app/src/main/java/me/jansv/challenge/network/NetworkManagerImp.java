package me.jansv.challenge.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import me.jansv.challenge.util.Utils;

@Singleton
final class NetworkManagerImp implements NetworkManager {

    private Subject<Boolean> mNetworkChanges;

    private final BroadcastReceiver mNetworkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mNetworkChanges.onNext(isNetworkAvailableValue());
        }
    };

    Context mContext;

    @Inject
    public NetworkManagerImp(@ApplicationContext  Context context) {
        this.mContext = context;
    }

    @Override
    public boolean isNetworkAvailableValue() {
        return Utils.isNetworkAvailable(mContext);
    }

    @Override
    public Single<Boolean> isNetworkAvailable() {
        return Single.just(isNetworkAvailableValue());
    }

    @Override
    public Observable<Boolean> connectivityOn(Context context) {
        if (null == mNetworkChanges)
            mNetworkChanges = PublishSubject.create();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(mNetworkChangeReceiver, intentFilter);

        return Observable.create(observer -> {
            Disposable d = mNetworkChanges.subscribe(observer::onNext, throwable -> {
            }, () -> {
            });
            observer.setDisposable(Disposable.fromAction(() -> {
                stopConnectivityOn(context);
                d.dispose();
            }));
        });
    }

    private void stopConnectivityOn(Context context) {
        try {
            context.unregisterReceiver(mNetworkChangeReceiver);
        } catch (Exception ex) {
            // ignore
        }
    }
}