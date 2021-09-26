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
import me.jansv.challenge.model.User;

import java.util.List;

import javax.inject.Inject;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.RepostHolder>{

    UserRepositoriesItemBinding binding;
    private List<User> users;


    public ReposAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public RepostHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        binding = UserRepositoriesItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()));
        return new RepostHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RepostHolder repostHolder, int i) {
        User item = users.get(i);
        repostHolder.bind(users.get(i));
        repostHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ReposActivity.class);
                intent.putExtra(ReposActivity.USER_REPO, item.getLogin());
                repostHolder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
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

        private void bind(User user) {
            userName.setText(user.getLogin());
            userRepositories.setText(user.getHtmlUrl());
            Glide.with(itemView.getContext()).load(user.getAvatarUrl()).into(userImage);
        }
    }
}
