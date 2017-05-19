package com.bwelco.localapp.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwelco.localapp.R;
import com.bwelco.localapp.bean.ExceptionBean;
import com.github.vipulasri.timelineview.TimelineView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by bwelco on 2017/5/19.
 */

public class ExceptionListAdapter extends RecyclerView.Adapter<ExceptionListAdapter.ViewHolder> {


    private List<ExceptionBean> eventList;
    private Context context;
    private LayoutInflater layoutInflater;

    SimpleDateFormat myFmt;


    public ExceptionListAdapter(Context context, List<ExceptionBean> eventList) {
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
        View view = layoutInflater.inflate(R.layout.item_exception, null, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!TextUtils.isEmpty(eventList.get(position).userName)) {
            holder.userName.setVisibility(View.VISIBLE);
            holder.userName.setText("用户：" + eventList.get(position).userName);
        } else {
            holder.userName.setVisibility(View.GONE);
        }

        String time = myFmt.format(new Date(Long.valueOf(eventList.get(position).time)));
        holder.openTime.setText(time);

        holder.timeLineView.setMarker(ContextCompat.getDrawable(context, R.drawable.ic_marker), ContextCompat.getColor(context, R.color.colorPrimary));
        holder.errType.setText(eventList.get(position).errType + "");
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView errType;
        TextView openTime;

        TimelineView timeLineView;


        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            timeLineView = (TimelineView) itemView.findViewById(R.id.time_marker);
            timeLineView.initLine(viewType);

            userName = (TextView) itemView.findViewById(R.id.userName);
            openTime = (TextView) itemView.findViewById(R.id.time);
            errType = (TextView) itemView.findViewById(R.id.err_type);
        }
    }
}
