package com.iloomo.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.iloomo.adapter.PMyAdapter;
import com.iloomo.paysdk.R;
import com.iloomo.photoview.PPhotoView;
import com.iloomo.utils.PActivitySupport;
import com.iloomo.utils.PImageLoaderUtils;

import java.util.ArrayList;


/**
 * Created by wpt on 2015/8/19.
 */
public class PViewPagerPActivity extends PActivitySupport {

    private ViewPager mPager;
    private ArrayList<String> photoStrings;
    private int index;
    private ImageView lc_right_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pactivity_view_pager);
        setRemoveTitle();
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        lc_right_menu = (ImageView) findViewById(R.id.lc_right_menu);
        photoStrings = getIntent().getStringArrayListExtra("photos");
        index = getIntent().getIntExtra("index", 0);
        mPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return photoStrings.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                PPhotoView view = new PPhotoView(PViewPagerPActivity.this);
                view.enable();
                view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//                Glide.with(context)
//                        .load(photoStrings.get(position))
//                        .placeholder(R.drawable.pictures_no)
//                        .error(R.drawable.loading_fild)
//                        .crossFade()
//                        .into(view);
                PImageLoaderUtils.displayLocalImage(photoStrings.get(position), view, null);
                container.addView(view);
                lc_right_menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 已经选择过该图片
                        if (PMyAdapter.mSelectedImage.contains(photoStrings.get(position - 1))) {
                            PMyAdapter.mSelectedImage.remove(photoStrings.get(position - 1));
                            lc_right_menu.setImageResource(R.drawable.picture_unselected);
                        } else
                        // 未选择该图片
                        {
                            if (PMyPhoto.SINGLE)
                                PMyAdapter.mSelectedImage.clear();
                            PMyAdapter.mSelectedImage.add(photoStrings.get(position - 1));
                            lc_right_menu.setImageResource(R.drawable.pictures_selected);
                        }
                    }
                });
                /**
                 * 已经选择过的图片，显示出选择过的效果
                 */
                if (PMyAdapter.mSelectedImage.contains(photoStrings.get(position))) {
                    lc_right_menu.setImageResource(R.drawable.pictures_selected);
                } else {
                    lc_right_menu.setImageResource(R.drawable.picture_unselected);
                }
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                View object1 = (View) object;
                unbindDrawables(object1);
                container.removeView(object1);
                container = null;
                object1 = null;
                System.gc();
            }
        });
        mPager.setCurrentItem(index);
    }

    private void unbindDrawables(View view) {
        if (view != null) {
            return;
        }
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }

        if (view instanceof ImageView) {
            releaseImageView((ImageView) view);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }

    public void releaseImageView(ImageView imageView) {
        Drawable d = imageView.getDrawable();
        if (d != null)
            d.setCallback(null);
        imageView.setImageDrawable(null);
        imageView.setBackgroundDrawable(null);
    }


    public void onBack(View view) {
        onBackPressed();
    }

}
