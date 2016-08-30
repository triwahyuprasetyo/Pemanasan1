package com.sebangsa.pemanasan1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sebangsa.pemanasan1.R;
import com.sebangsa.pemanasan1.model.Community;
import com.sebangsa.pemanasan1.model.User;

import java.util.List;

/**
 * Created by sebangsa on 8/30/16.
 */
public class SebangsaRecyclerViewAdapter extends RecyclerView.Adapter<SebangsaRecyclerViewAdapter.SebangsaRecyclerViewHolder> {

    private List<User> listUser;
    private List<Community> listCommunities;
    private LayoutInflater inflater;
    private Context c;
    private String type;

    public SebangsaRecyclerViewAdapter(List list, Context c, String type) {
        inflater = LayoutInflater.from(c);
        if (type.equals("User")) {
            listUser = (List<User>) list;
        } else {
            listCommunities = (List<Community>) list;
        }

        this.c = c;
        this.type = type;
    }

    @Override
    public SebangsaRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new SebangsaRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SebangsaRecyclerViewHolder holder, final int position) {
        if (type.equals("User")) {
            final User user = listUser.get(position);
            holder.username.setText("@" + user.getUsername());
            holder.name.setText(user.getName());
            holder.description.setVisibility(View.INVISIBLE);
            Glide.with(c)
                    .load(user.getAvatar().getMedium().trim())
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .crossFade()
                    .into(holder.imageAvatar);
            holder.buttonFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                ((ImageButton) v).setImageResource(R.drawable.i_followed);
                    User.Action a = new User.Action();

                    if (user.getAction().isFollow()) {
                        a.setFollow(false);
                        user.setAction(a);
                    } else {
                        a.setFollow(true);
                        user.setAction(a);
                    }
                    setImageButtonUser(user, holder);
                }
            });

            setImageButtonUser(user, holder);
        } else if (type.equals("Community")) {
            final Community community = listCommunities.get(position);
            holder.username.setText("+" + community.getName());
            holder.name.setText(community.getDescription());
            holder.description.setVisibility(View.INVISIBLE);
            Glide.with(c)
                    .load(community.getAvatar().getMedium().trim())
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .crossFade()
                    .into(holder.imageAvatar);
            holder.buttonFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                ((ImageButton) v).setImageResource(R.drawable.i_followed);
                    Community.Action a = new Community.Action();

                    if (community.getAction().isMember()) {
                        a.setMember(false);
                        community.setAction(a);
                    } else {
                        a.setMember(true);
                        community.setAction(a);
                    }
                    setImageButtonCommunity(community, holder);
                }
            });
            setImageButtonCommunity(community, holder);
        }

    }

    private void setImageButtonUser(User user, SebangsaRecyclerViewHolder holder) {
        if (user.getAction().isFollow()) {
            holder.buttonFollow.setImageResource(R.drawable.i_followed);
            holder.buttonFollow.setBackgroundColor(Color.parseColor("#FF94c84B"));
        } else {
            holder.buttonFollow.setImageResource(R.drawable.i_follow);
            holder.buttonFollow.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        }
    }

    private void setImageButtonCommunity(Community community, SebangsaRecyclerViewHolder holder) {
        if (community.getAction().isMember()) {
            holder.buttonFollow.setImageResource(R.drawable.i_joined);
            holder.buttonFollow.setBackgroundColor(Color.parseColor("#FF94c84B"));
        } else {
            holder.buttonFollow.setImageResource(R.drawable.i_join);
            holder.buttonFollow.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        if (type.equals("User")) {
            return listUser.size();
        } else {
            return listCommunities.size();
        }
    }

    class SebangsaRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView username;
        private TextView name;
        private ImageView imageAvatar;
        private ImageButton buttonFollow;
        private TextView description;

        public SebangsaRecyclerViewHolder(View itemView) {
            super(itemView);

            username = (TextView) itemView.findViewById(R.id.textView_username);
            name = (TextView) itemView.findViewById(R.id.textView_name);
            imageAvatar = (ImageView) itemView.findViewById(R.id.imageView_avatar);
            buttonFollow = (ImageButton) itemView.findViewById(R.id.imageButton_follow);
            description = (TextView) itemView.findViewById(R.id.textView_description);
        }
    }
}