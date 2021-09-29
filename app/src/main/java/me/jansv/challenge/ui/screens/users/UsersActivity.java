package me.jansv.challenge.ui.screens.users;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import me.jansv.challenge.R;
import me.jansv.challenge.api.GithubService;
import me.jansv.challenge.databinding.ActivityUsersBinding;
import me.jansv.challenge.model.User;
import me.jansv.challenge.network.NetworkManager;
import me.jansv.challenge.ui.screens.repos.ReposActivity;
import me.jansv.challenge.util.schedulers.SchedulerProviders;

@AndroidEntryPoint
public class UsersActivity extends AppCompatActivity implements UsersContract.View {

    // injected components
    @Inject
    GithubService api;

    @Inject
    NetworkManager connectivity; // optional component

    @Inject
    SchedulerProviders schedulers; // optional component

    @Inject
    UsersPresenter mPresenter;

    ActivityUsersBinding binding;
    View contentView;
    RecyclerView userList;

    // private components
    private CompositeDisposable subscriptions = new CompositeDisposable();

    private boolean mIsActive;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        contentView = binding.contentMain;
        userList = binding.userList;

        setupViews();
        //scheduleForTitleChange();

        /*userList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listedRepositories(view);
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsActive = true;
        mPresenter.bind(this);

        // this call is totally optional, you can without any damage comment it and uncomment the below one
        fetchUsersWhenReady();
        //mPresenter.fetchUserList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIsActive = false;
        mPresenter.unbind();
        subscriptions.clear();
    }

    private void setupViews() {
        userList.setHasFixedSize(true);
        userList.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
    }

    @Override
    public void showNoNetworkMessage() {
        Snackbar.make(contentView, R.string.network_error, Snackbar.LENGTH_LONG)
                .setAction(R.string.retry, v ->
                    mPresenter.fetchUserList()
                )
                .show();
    }

    @Override
    public void showNetworkErrorMessage() {
        Snackbar.make(contentView, R.string.no_network, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showUserList(List<User> users) {
        userList.setAdapter(new UsersAdapter(users));
    }

    /*public void listedRepositories(View v){
        Intent intent = new Intent(v.getContext(), ReposActivity.class);
        intent.putExtra(ReposActivity.USER_REPO, "moyheen");
        startActivity(intent);
    }*/

    @Override
    public boolean isActive() {
        return mIsActive;
    }

    private void fetchUsersWhenReady() {
        subscriptions.clear();
        Observable<Boolean> isConnected = connectivity
                .connectivityOn(this)
                .subscribeOn(schedulers.io())
                .share();

        // handle initial disconnection
        Disposable d1 = isConnected
                .take(1)
                .filter(connected -> !connected) // if disconnected show "no connection message"
                .observeOn(schedulers.ui())
                .subscribe(connected -> showNoNetworkMessage() , error -> Log.e("***", "", error), () -> {});

        // handle initial connection
        Disposable d2 = isConnected
                .startWith(connectivity.isNetworkAvailable())
                .skipWhile(connected -> !connected)
                .take(1)
                .observeOn(schedulers.ui())
                .subscribe(connected -> mPresenter.fetchUserList(), error -> Log.e("***", "", error), () -> {});

        subscriptions.add(d1);
        subscriptions.add(d2);
    }

    /*private void scheduleForTitleChange() {
        final boolean delayTitleChange = getResources().getBoolean(R.bool.allowsDelayedTitleChange);

        if (delayTitleChange) {
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                setTitle(R.string.users_title);
            }).start();
        }
    }*/
}
