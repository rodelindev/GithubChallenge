package me.jansv.challenge.ui.screens.repos;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import dagger.hilt.android.scopes.ActivityScoped;
import me.jansv.challenge.R;
import me.jansv.challenge.databinding.UserRepositoriesItemBinding;
import me.jansv.challenge.model.Repos;
import me.jansv.challenge.model.User;

import java.util.List;

import javax.inject.Inject;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.RepostHolder>{

    UserRepositoriesItemBinding binding;
    private List<Repos> repos;

    public ReposAdapter(List<Repos> repos) {
        this.repos = repos;
    }

    @NonNull
    @Override
    public RepostHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        binding = UserRepositoriesItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()));
        return new RepostHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RepostHolder repostHolder, int i) {
        repostHolder.bind(repos.get(i));
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public class RepostHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView userRepositories;
        ImageView userImage;

        public RepostHolder(UserRepositoriesItemBinding binding) {
            super(binding.getRoot());
            userName = binding.tvUsername;
            userRepositories = binding.tvUserRepositorie;
            userImage = binding.imgPerfilPhoto;
        }

        private void bind(Repos repos) {
            userName.setText(repos.getOwner().getLogin());
            userRepositories.setText(repos.getHtmlUrl());
            Glide.with(itemView.getContext()).load(repos.getOwner().getAvatarUrl()).into(userImage);
        }
    }
}
