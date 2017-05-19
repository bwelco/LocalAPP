package com.bwelco.localapp;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.bwelco.localapp.adapter.DoorEventAdapter;
import com.bwelco.localapp.bean.DoorEventBean;
import com.bwelco.localapp.http.DoorService;
import com.bwelco.localapp.utils.HttpUtil;
import com.bwelco.localapp.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoorEventActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    DoorEventAdapter adapter;
    List<DoorEventBean> doorEventList;
    DoorService doorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        doorService = HttpUtil.getRetrofitInstance().create(DoorService.class);
        doorEventList = new ArrayList<>();
        adapter = new DoorEventAdapter(this, doorEventList);
        recyclerView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
        onRefresh();
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_door_event;
    }

    @Override
    public boolean needBack() {
        return true;
    }

    @Override
    public String toolBarTitle() {
        return "开锁事件";
    }

    @Override
    public void onRefresh() {
        doorService.getDoorEventList()
                .enqueue(new Callback<List<DoorEventBean>>() {
                    @Override
                    public void onResponse(Call<List<DoorEventBean>> call, Response<List<DoorEventBean>> response) {
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.setRefreshing(false);
                        }
                        doorEventList.clear();
                        doorEventList.addAll(response.body());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<DoorEventBean>> call, Throwable t) {
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.setRefreshing(false);
                        }
                        ToastUtil.showMessage("刷新失败");
                    }
                });
    }
}
