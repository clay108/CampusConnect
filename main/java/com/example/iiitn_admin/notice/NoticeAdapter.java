package com.example.iiitn_admin.notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iiitn_admin.NoticeData;
import com.example.iiitn_admin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewAdapter> {
    private Context context;
    private ArrayList<NoticeData>list;

    public NoticeAdapter(Context context, ArrayList<NoticeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.newsfeed_item_layout,parent,false);
        return new NoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdapter holder, int position) {
        NoticeData currentItem=list.get(position);
        holder.deleteNoticeTitle.setText(currentItem.getTitle());
        try {
            if(currentItem.getImage()!=null)
            Picasso.with(context.getApplicationContext()).load(currentItem.getImage()).into(holder.deleteNoticeImage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeViewAdapter extends RecyclerView.ViewHolder {
private Button deleteNotice;
private TextView deleteNoticeTitle;
        private ImageView deleteNoticeImage;
        public NoticeViewAdapter(@NonNull View itemView) {
            super(itemView);
            deleteNotice=itemView.findViewById(R.id.deleteNotice);
            deleteNoticeTitle=itemView.findViewById(R.id.deleteNoticeTitle);
            deleteNoticeImage=itemView.findViewById(R.id.deleteNoticeImage);

        }
    }
}
