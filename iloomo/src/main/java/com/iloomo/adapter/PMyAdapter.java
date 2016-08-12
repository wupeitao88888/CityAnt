package com.iloomo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.iloomo.paysdk.R;
import com.iloomo.ui.PMyPhoto;
import com.iloomo.ui.PViewPagerPActivity;
import com.iloomo.utils.PCommonAdapter;
import com.iloomo.utils.PViewHolder;


public class PMyAdapter extends PCommonAdapter<String> {

    /**
     * 用户选择的图片，存储为图片的完整路径
     */
    public static ArrayList<String> mSelectedImage = new ArrayList<>();

    /**
     * 文件夹路径
     */
    private String mDirPath;
    private Button choose_photo;

    public PMyAdapter(Context context, List<String> mDatas, int itemLayoutId,
                      String dirPath, Button choose_photo) {

        super(context, mDatas, itemLayoutId);
        this.mDirPath = dirPath;
        this.choose_photo = choose_photo;
    }

    @Override
    public void convert(final PViewHolder helper, final String item, final int position) {

        //设置no_pic
        helper.setImageResource(R.id.id_item_image, R.drawable.pictures_no);
        //设置no_selected
        helper.setImageResource(R.id.id_item_select,
                R.drawable.picture_unselected);
        //设置图片
        helper.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item);

        final ImageView mImageView = helper.getView(R.id.id_item_image);
        final ImageView mSelect = helper.getView(R.id.id_item_select);
        mImageView.setVisibility(View.VISIBLE);
        mImageView.setColorFilter(null);
        //设置ImageView的点击事件
        mSelect.setOnClickListener(new OnClickListener() {
            //选择，则将图片变暗，反之则反之
            @Override
            public void onClick(View v) {

                // 已经选择过该图片
                if (mSelectedImage.contains(mDirPath + "/" + item)) {
                    mSelectedImage.remove(mDirPath + "/" + item);
                    mSelect.setImageResource(R.drawable.picture_unselected);
                    mImageView.setColorFilter(null);
                } else
                // 未选择该图片
                {

                    if (PMyPhoto.SINGLE) {
                        mSelectedImage.clear();
                        notifyDataSetChanged();
                    }
                    mSelectedImage.add(mDirPath + "/" + item);
                    mSelect.setImageResource(R.drawable.pictures_selected);
                    mImageView.setColorFilter(Color.parseColor("#77000000"));

                }
                choose_photo.setText("预览(" + mSelectedImage.size() + "/张)");
            }
        });

        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 已经选择过的图片，显示出选择过的效果
                 */
                if (mSelectedImage.contains(mDirPath + "/" + item)) {
                    Intent intent = new Intent(mContext, PViewPagerPActivity.class);
                    intent.putStringArrayListExtra("photos", PMyAdapter.mSelectedImage);
                    intent.putExtra("index", PMyAdapter.mSelectedImage.indexOf(mDirPath + "/" + item));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                } else {
                    getArrayList(position);
                }
            }
        });


        /**
         * 已经选择过的图片，显示出选择过的效果
         */
        if (mSelectedImage.contains(mDirPath + "/" + item)) {
            mSelect.setImageResource(R.drawable.pictures_selected);
            mImageView.setColorFilter(Color.parseColor("#77000000"));
        } else {
            mSelect.setImageResource(R.drawable.picture_unselected);
            mImageView.setColorFilter(null);
        }
    }

    Handler handler = new Handler();

    public void getArrayList(final int position) {
        final ArrayList<String> strings = new ArrayList<>();
        new Thread() {
            @Override
            public void run() {
                super.run();

                for (int i = 0; i < mDatas.size(); i++) {
                    strings.add(mDirPath + "/" + mDatas.get(i));
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(mContext, PViewPagerPActivity.class);
                        intent.putStringArrayListExtra("photos", strings);
                        intent.putExtra("index", position);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                });
            }
        }.start();
    }


}
