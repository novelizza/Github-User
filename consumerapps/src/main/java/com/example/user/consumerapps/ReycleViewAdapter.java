package com.example.user.consumerapps;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

class ReycleViewAdapter extends RecyclerView.Adapter<ReycleViewAdapter.MyViewHolder> {
    ArrayList<String> dataUsername;
    ArrayList<String> dataURL;
    ArrayList<String> dataAvatar;

    Context context;

    public ReycleViewAdapter(Context ct, ArrayList<String> username, ArrayList<String> URL, ArrayList<String> Avatar){
        context = ct;
        dataUsername = username;
        dataURL = URL;
        dataAvatar = Avatar;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cards_layout_account, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tvUsername.setText(dataUsername.get(position));
        holder.tvURL.setText(dataURL.get(position));

        Picasso.get()
                .load(dataAvatar.get(position))
                .resize(50,50)
                .centerCrop()
                .into(holder.ivPP);

        holder.cardAccount.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Account account = new Account();

                        account.setUsername("https://api.github.com/users/" + dataUsername.get(position));

                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra(DetailActivity.EXTRA_ACCOUNT, account);

                        context.startActivity(intent);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return dataUsername.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvUsername;
        TextView tvURL;
        ImageView ivPP;
        ConstraintLayout cardAccount;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvURL = itemView.findViewById(R.id.tvURL);
            ivPP = itemView.findViewById(R.id.ivPP);
            cardAccount = itemView.findViewById(R.id.main_card_account);
        }
    }
}