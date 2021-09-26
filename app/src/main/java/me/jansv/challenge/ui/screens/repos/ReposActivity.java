package me.jansv.challenge.ui.screens.repos;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import me.jansv.challenge.R;
import me.jansv.challenge.api.GithubService;
import me.jansv.challenge.databinding.ActivityReposBinding;
import me.jansv.challenge.model.User;
import me.jansv.challenge.model.UserList;
import me.jansv.challenge.ui.screens.users.UsersAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@AndroidEntryPoint
public class ReposActivity extends AppCompatActivity {
    public static final String USER_REPO = "repoList";
    ActivityReposBinding binding;

    @Inject
    GithubService api;

    RecyclerView repoList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReposBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repoList = binding.rvUserRepositorie;

        setupViews();

        String intent = getIntent().getExtras().getString(USER_REPO);
        Log.d("Intent1", getIntent().getExtras().getString(USER_REPO));
        repoList(intent);

    }

    private void setupViews() {
        repoList.setHasFixedSize(true);
        repoList.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
    }

    public void repoList(String userLong) {
        api.getRepoList(userLong).enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                if (response.isSuccessful()) {
                    showRepoList(response.body().getItems());
                    Log.d("Repos", response.body().getItems().toString());
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                Log.d("Repos1", t.getMessage());
            }
        });
    }

    public void showRepoList(List<User> repos) {
        repoList.setAdapter(new ReposAdapter(repos));
        Log.d("Data", repos.toString());
    }
}
