package me.jansv.challenge.ui.screens.repos;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import me.jansv.challenge.api.GithubService;
import me.jansv.challenge.databinding.ActivityReposBinding;
import me.jansv.challenge.model.Repos;
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

        String intent = getIntent().getStringExtra(USER_REPO);
        Log.d("Intent", getIntent().getStringExtra(USER_REPO));
        repositoryList(intent);
        setupViews();
    }

    private void setupViews() {
        repoList.setHasFixedSize(true);
        repoList.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
    }

    public void repositoryList(String userLogin) {

        api.getRepoList(userLogin).enqueue(new Callback<List<Repos>>() {
            @Override
            public void onResponse(Call<List<Repos>> call, Response<List<Repos>> response) {
                if(response.isSuccessful()) {
                    List<Repos> repos = response.body();

                    for(int i = 0; i < repos.size(); i++) {
                        showRepoList(repos);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Repos>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    public void showRepoList(List<Repos> repos) {
        repoList.setAdapter(new ReposAdapter(repos));
        Log.d("Data", repos.toString());
    }
}
