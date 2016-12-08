/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.cityant.main.adapter.recycleview.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cityant.main.R;
import com.cityant.main.adapter.recycleview.CommonAdapter;
import com.cityant.main.adapter.recycleview.DividerItemDecoration;
import com.cityant.main.widget.CenteredImageSpan;
import com.cityant.main.widget.CircleTransform;
import com.cityant.main.widget.RoundTransform;

public class ViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "ViewHolder";

    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

    public ViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }


    public static ViewHolder createViewHolder(Context context, View itemView) {
        ViewHolder holder = new ViewHolder(context, itemView);
        return holder;
    }

    public static ViewHolder createViewHolder(Context context,
                                              ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        ViewHolder holder = new ViewHolder(context, itemView);
        return holder;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }


    /****以下为辅助方法*****/

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text) {
        if (TextUtils.isEmpty(text)) {
            Log.i(TAG, "setText: text = " + text);
            text = "";
        }
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public ViewHolder setText(int viewId, String text, int drawable, int start, int end) {
        TextView tv = getView(viewId);
        Bitmap b = BitmapFactory.decodeResource(mContext.getResources(), drawable);

        ImageSpan imgSpan = new ImageSpan(mContext, b);
        SpannableString spanString = new SpannableString("icon");
        spanString.setSpan(imgSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spanString);
        tv.append(text);
        return this;
    }

    public ViewHolder setText(int viewId, String text, int drawable) {
        TextView tv = getView(viewId);
//        Bitmap b = BitmapFactory.decodeResource(mContext.getResources(), drawable);
//        ImageSpan imgSpan = new ImageSpan(mContext, b);
        Drawable d = mContext.getResources().getDrawable(drawable);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
//        ImageSpan imgSpan = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        CenteredImageSpan imgSpan = new CenteredImageSpan(mContext, drawable);
        SpannableString spanString = new SpannableString("icon ");
        spanString.setSpan(imgSpan, 0, "icon ".length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spanString);
        tv.append(text);
        return this;
    }

    public ViewHolder setText(int viewId, SpannableString text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public ViewHolder setImageUrl(int viewId, String url) {
        ImageView view = getView(viewId);
        Glide.with(view.getContext())
                .load(url)
                .error(R.drawable.button_normal_l)
                .placeholder(R.drawable.button_normal_l)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
        return this;
    }

    public ViewHolder setImageUrl(int viewId, String url, int error) {
        ImageView view = getView(viewId);
        Glide.with(view.getContext())
                .load(url)
                .error(error)
                .placeholder(error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
        return this;
    }

    public ViewHolder setCircleImageUrl(int viewId, String url) {
        ImageView view = getView(viewId);
        Glide.with(view.getContext())
                .load(url)
                .error(R.drawable.button_normal_l)
                .placeholder(R.drawable.button_normal_l)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new CircleTransform(view.getContext()))
                .into(view);
        return this;
    }

    public ViewHolder setCircleImage(int viewId, int normal) {
        ImageView view = getView(viewId);
        Glide.with(view.getContext())
                .load(normal)
                .error(R.drawable.button_normal_l)
                .placeholder(R.drawable.button_normal_l)
                .bitmapTransform(new CircleTransform(view.getContext()))
                .into(view);
        return this;
    }

    public ViewHolder setRoundImageUrl(int viewId, String url) {
        ImageView view = getView(viewId);
        Glide.with(view.getContext())
                .load(url)
                .error(R.drawable.button_normal_l)
                .placeholder(R.drawable.button_normal_l)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new RoundTransform(view.getContext(), 64))
                .into(view);
        return this;
    }

    public ViewHolder setRoundImageUrl(int viewId, String url, int normal) {
        ImageView view = getView(viewId);
        Glide.with(view.getContext())
                .load(url)
                .error(normal)
                .placeholder(normal)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new RoundTransform(view.getContext(), 64))
                .into(view);
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public ViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public ViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public ViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public ViewHolder setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    @SuppressLint("NewApi")
    public ViewHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public ViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public ViewHolder linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public ViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public ViewHolder setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public ViewHolder setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public ViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public ViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public ViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public ViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    public ViewHolder setEnabled(int viewId, boolean enable) {
        View view = (View) getView(viewId);
        view.setEnabled(enable);
        return this;
    }

    public ViewHolder setRecyclerAdapter(int viewId, CommonAdapter commonAdapter) {
        RecyclerView view = (RecyclerView) getView(viewId);
        view.setLayoutManager(new GridLayoutManager(mContext, 3));
        view.setAdapter(commonAdapter);
        return this;
    }

    public ViewHolder setRecyclerLinearAdapter(int viewId, CommonAdapter commonAdapter) {
        RecyclerView view = (RecyclerView) getView(viewId);
        view.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        view.setLayoutManager(new LinearLayoutManager(mContext));
        view.setAdapter(commonAdapter);
        return this;
    }

    /**
     * 关于事件的
     */
    public ViewHolder setOnClickListener(int viewId,
                                         View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolder setOnTouchListener(int viewId,
                                         View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public ViewHolder setOnLongClickListener(int viewId,
                                             View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public ViewHolder setOnCheckedChangeListener(int viewId,
                                                 CompoundButton.OnCheckedChangeListener listener) {
        CheckBox checkBox = (CheckBox) getView(viewId);
        checkBox.setOnCheckedChangeListener(listener);
        return this;
    }


}
