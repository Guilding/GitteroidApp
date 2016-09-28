package com.amatkivskiy.gitteroid.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amatkivskiy.gitteroid.R;
import com.amatkivskiy.gitteroid.model.RoomModel;
import com.amatkivskiy.gitteroid.util.BadgeUtils;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

@FragmentWithArgs
public class AllRoomsFragmentDialog extends DialogFragment {

    public interface RoomSelectionListener {

        void onRoomSelected(int position);
    }

    private RoomSelectionListener listener;

    @Arg
    ArrayList<RoomModel> rooms;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (RoomSelectionListener) activity;
        } catch (ClassCastException e) {
            Log.e("Gitteroid", activity.getClass().getName() + " should implement RoomSelectionListener.");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new MaterialDialog.Builder(this.getActivity())
                .title("Select room")
                .adapter(new RoomsRecyclerAdapter(rooms, getActivity()), null)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        listener.onRoomSelected(position);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private static class RoomsRecyclerAdapter extends RecyclerView.Adapter<RoomViewHolder> {
        private ArrayList<RoomModel> userRooms = new ArrayList<>();
        private Context context;

        RoomsRecyclerAdapter(ArrayList<RoomModel> userRooms, Context context) {
            this.userRooms = userRooms;
            this.context = context;
        }

        @Override
        public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.material_drawer_item_primary, parent, false);

            return new RoomViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RoomViewHolder holder, int position) {
            RoomModel room = userRooms.get(position);

            holder.name.setEllipsize(TextUtils.TruncateAt.END);
            holder.description.setEllipsize(TextUtils.TruncateAt.END);

            holder.name.setText(room.name);
            holder.description.setText(room.groupName);

            holder.badge.setPadding(0, 0, 0, 0);

            BadgeStyle mentionedBadgeStyle = BadgeUtils.getMentionedBadgeStyle(context);
            BadgeStyle unReadItemsBadgeStyle = BadgeUtils.getUnReadBadgeStyle(context);

            if (room.mentions > 0) {
                holder.badge.setVisibility(View.VISIBLE);
                mentionedBadgeStyle.style(holder.badge);

                holder.badge.setText(BadgeUtils.getMentionedItemsText(room.mentions));
            } else if (room.unreadItems > 0) {
                holder.badge.setVisibility(View.VISIBLE);
                unReadItemsBadgeStyle.style(holder.badge);

                holder.badge.setText(BadgeUtils.getUnreadItemsText(room.unreadItems));
            } else {
                holder.badge.setVisibility(View.GONE);
            }

            DrawerImageLoader.getInstance().getImageLoader()
                    .set(holder.roomIcon, Uri.parse(room.roomIconUrl), null);
        }

        @Override
        public int getItemCount() {
            return userRooms.size();
        }
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.material_drawer_name)
        TextView name;
        @BindView(R.id.material_drawer_description)
        TextView description;
        @BindView(R.id.material_drawer_icon)
        ImageView roomIcon;
        @BindView(R.id.material_drawer_badge)
        TextView badge;

        RoomViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
