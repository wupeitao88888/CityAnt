/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.cityant.main.adapter.recycleview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cityant.main.adapter.recycleview.base.ViewHolder;
import com.cityant.main.adapter.recycleview.utils.WrapperUtils;


/**
 * Created by zhy on 16/6/23.
 */
public class EmptyWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    public static final int ITEM_TYPE_EMPTY = Integer.MAX_VALUE - 1;

    private RecyclerView.Adapter mInnerAdapter;
    private View mEmptyView;
    private int mEmptyLayoutId;


    public EmptyWrapper(RecyclerView.Adapter adapter)
    {
        mInnerAdapter = adapter;
    }

    private boolean isEmpty()
    {
        return (mEmptyView != null || mEmptyLayoutId != 0) && mInnerAdapter.getItemCount() == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (isEmpty())
        {
            ViewHolder holder;
            if (mEmptyView != null)
            {
                holder = ViewHolder.createViewHolder(parent.getContext(), mEmptyView);
            } else
            {
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, mEmptyLayoutId);
            }
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback()
        {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position)
            {
                if (isEmpty())
                {
                    return gridLayoutManager.getSpanCount();
                }
                if (oldLookup != null)
                {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });


    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder)
    {
        mInnerAdapter.onViewAttachedToWindow(holder);
        if (isEmpty())
        {
            WrapperUtils.setFullSpan(holder);
        }
    }


    @Override
    public int getItemViewType(int position)
    {
        if (isEmpty())
        {
            return ITEM_TYPE_EMPTY;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (isEmpty())
        {
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount()
    {
        if (isEmpty()) return 1;
        return mInnerAdapter.getItemCount();
    }



    public void setEmptyView(View emptyView)
    {
        mEmptyView = emptyView;
    }

    public void setEmptyView(int layoutId)
    {
        mEmptyLayoutId = layoutId;
    }

}
