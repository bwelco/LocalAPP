package com.bwelco.localapp.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwelco.localapp.R;
import com.bwelco.localapp.bean.DoorEventBean;
import com.bwelco.localapp.http.DoorService;
import com.github.vipulasri.timelineview.TimelineView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by bwelco on 2017/5/19.
 */

public class DoorEventAdapter extends RecyclerView.Adapter<DoorEventAdapter.ViewHolder> {

    private List<DoorEventBean> eventList;
    private Context context;
    private LayoutInflater layoutInflater;

    SimpleDateFormat myFmt;


    public DoorEventAdapter(Context context, List<DoorEventBean> eventList) {
        this.context = context;
        this.eventList = eventList;
        this.layoutInflater = LayoutInflater.from(context);
        myFmt = new SimpleDateFormat("yy/MM/dd    HH:mm:ss");
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_door_event, null, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.userName.setText("用户：" + eventList.get(position).userName);
        String time = myFmt.format(new Date(Long.valueOf(eventList.get(position).time)));
        holder.openTime.setText(time);
//
//        if(position == 0) {
//            holder.timeLineView.setMarker(VectorDrawableUtils.getDrawable(context, R.drawable.ic_marker_inactive, android.R.color.darker_gray));
//        } else if(position == 1) {
//            holder.timeLineView.setMarker(VectorDrawableUtils.getDrawable(context, R.drawable.ic_marker_active, R.color.colorPrimary));
//        } else {
//            holder.timeLineView.setMarker(ContextCompat.getDrawable(context, R.drawable.ic_marker), ContextCompat.getColor(context, R.color.colorPrimary));
//        }
        holder.timeLineView.setMarker(ContextCompat.getDrawable(context, R.drawable.ic_marker), ContextCompat.getColor(context, R.color.colorPrimary));


        if (eventList.get(position).openType.equals(DoorService.TYPE_NETWORK)) {
            holder.openType.setText("远程开锁");
        } else {
            holder.openType.setText("NFC开锁");
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView openType;
        TextView openTime;

        TimelineView timeLineView;


        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            timeLineView = (TimelineView) itemView.findViewById(R.id.time_marker);
            timeLineView.initLine(viewType);

            userName = (TextView) itemView.findViewById(R.id.userName);
            openTime = (TextView) itemView.findViewById(R.id.time);
            openType = (TextView) itemView.findViewById(R.id.open_type);
        }
    }
}

