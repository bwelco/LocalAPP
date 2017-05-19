package com.bwelco.localapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwelco.localapp.R;
import com.bwelco.localapp.bean.DoorEventBean;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

/**
 * Created by bwelco on 2017/5/19.
 */

public class DoorEventAdapter extends RecyclerView.Adapter<DoorEventAdapter.ViewHolder> {

    private TimelineView mTimelineView;
    private List<DoorEventBean> eventList;
    private Context context;
    private LayoutInflater layoutInflater;

    public DoorEventAdapter(Context context, List<DoorEventBean> eventList) {
        this.context = context;
        this.eventList = eventList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_door_event, null);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
            mTimelineView.initLine(viewType);
        }
    }
}

