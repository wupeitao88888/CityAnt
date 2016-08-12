package com.iloomo.utils;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public abstract class PCommonAdapter<T> extends BaseAdapter
{
	protected LayoutInflater mInflater;
	protected Context mContext;
	public List<T> mDatas;
	protected final int mItemLayoutId;

	public PCommonAdapter(Context context, List<T> mDatas, int itemLayoutId)
	{
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mDatas = mDatas;
		this.mItemLayoutId = itemLayoutId;
	}

	@Override
	public int getCount()
	{
		return mDatas.size();
	}

	@Override
	public T getItem(int position)
	{
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		final PViewHolder PViewHolder = getViewHolder(position, convertView,
				parent);
		convert(PViewHolder, getItem(position),position);
		return PViewHolder.getConvertView();

	}

	public abstract void convert(PViewHolder helper, T item,int position);

	private PViewHolder getViewHolder(int position, View convertView,
			ViewGroup parent)
	{
		return PViewHolder.get(mContext, convertView, parent, mItemLayoutId,
				position);
	}

}
