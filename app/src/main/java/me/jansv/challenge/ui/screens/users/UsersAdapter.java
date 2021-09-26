package me.jansv.challenge.ui.screens.users;

import static androidx.core.content.ContextCompat.startActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.jansv.challenge.R;
import me.jansv.challenge.databinding.UserItemBinding;
import me.jansv.challenge.model.User;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.Holder>{

    UserItemBinding binding;
    private List<User> users;

    public UsersAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        binding = UserItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()));
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.bind(users.get(i));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView userState;
        ImageView userImage;

        public Holder(UserItemBinding binding) {
            super(binding.getRoot());
            userName = binding.userName;
            userState = binding.userState;
            userImage = binding.profileImage;
        }

        private void bind(User user) {
            userName.setText(user.getLogin());
            userState.setText("Lagos");
            Glide.with(itemView.getContext()).load(user.getAvatarUrl()).into(userImage);
        }
    }
}
