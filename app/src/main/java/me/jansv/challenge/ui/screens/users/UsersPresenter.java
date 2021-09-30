package me.jansv.challenge.ui.screens.users;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityScoped;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;
import me.jansv.challenge.api.GithubService;
import me.jansv.challenge.model.Repos;
import me.jansv.challenge.model.User;
import me.jansv.challenge.model.UserList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@ActivityScoped
public class UsersPresenter implements UsersContract.Presenter {

    private UsersContract.View mView;

    GithubService api;

    @Inject UsersPresenter(GithubService api) {
        this.api = api;
    }

    @Override
    public void bind(UsersContract.View view) {
        mView = view;
    }

    @Override
    public void unbind() {
        mView = null;
    }

    @Override
    public void fetchUserList() {
        final String filter = "language:java location:lagos";

        /*api.getUserList(filter).enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                if(!mView.isActive())
                    return;
                if (response.isSuccessful()) {
                    mView.showUserList(response.body().getItems());
                } else {
                    mView.showNetworkErrorMessage();
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                if (mView.isActive()) {
                    mView.showNetworkErrorMessage();
                }
            }
        });*/

        /*Observable<UserList> userList = api.getUserList(filter);

        userList.subscribe(new Observer<UserList>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull UserList userList) {
                for (User user: userList.getItems()) {
                    mView.showUserList(userList.getItems());
                }

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d("Complete", "El pedido esta completo");
            }
        });*/
    }
}
