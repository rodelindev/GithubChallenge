package me.jansv.challenge.util.schedulers;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class SchedulerProviders {

    @Inject SchedulerProviders() {}
    public Scheduler io() {
        return Schedulers.io();
    }

    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    public Scheduler computation() {
        return Schedulers.computation();
    }
}
