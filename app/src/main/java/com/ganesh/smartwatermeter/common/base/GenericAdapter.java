package com.ganesh.smartwatermeter.common.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public abstract class GenericAdapter<T, V extends ViewDataBinding> extends RecyclerView.Adapter<GenericViewHolder> implements Filterable {

    private List<T> listItems;
    private LayoutInflater mInflater;

    public GenericAdapter(Context _context) {
        this.listItems = new ArrayList<>();
        mInflater = LayoutInflater.from(_context);
    }

    public void updateData(ArrayList<T> _listItems) {
        if (_listItems == null) {
            _listItems = new ArrayList<>();
        }
        listItems = _listItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GenericViewHolder<V>(DataBindingUtil.inflate(mInflater, getLayoutId(), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder holder, int position) {
        onBindView((V) holder.mBinding, listItems.get(position), position);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public abstract int getLayoutId();

    public abstract void onBindView(V binding, T item, int position);

    protected Filter getGenericFilter() {
        return null;
    }

    @Override
    public Filter getFilter() {
        return getGenericFilter();
    }


}
