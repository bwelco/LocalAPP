package com.bwelco.localapp;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bwelco.localapp.adapter.ExceptionListAdapter;
import com.bwelco.localapp.bean.ExceptionBean;
import com.bwelco.localapp.http.ExceptionService;
import com.bwelco.localapp.utils.HttpUtil;
import com.bwelco.localapp.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExceptionListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    ExceptionListAdapter adapter;
    List<ExceptionBean> exceptionList;
    ExceptionService exceptionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        exceptionService = HttpUtil.getRetrofitInstance().create(ExceptionService.class);
        exceptionList = new ArrayList<>();
        adapter = new ExceptionListAdapter(this, exceptionList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        refreshLayout.setOnRefreshListener(this);
        onRefresh();
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_exception_list;
    }

    @Override
    public boolean needBack() {
        return true;
    }

    @Override
    public String toolBarTitle() {
        return "异常事件";
    }

    @Override
    public void onRefresh() {
        exceptionService.getExceptionList()
                .enqueue(new Callback<List<ExceptionBean>>() {
                    @Override
                    public void onResponse(Call<List<ExceptionBean>> call, Response<List<ExceptionBean>> response) {
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.setRefreshing(false);
                        }
                        exceptionList.clear();
                        exceptionList.addAll(response.body());
                        Collections.sort(exceptionList);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<ExceptionBean>> call, Throwable t) {
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.setRefreshing(false);
                        }
                        ToastUtil.showMessage("刷新失败");
                        t.printStackTrace();
                    }
                });
    }
}
