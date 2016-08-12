package com.iloomo.ui;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iloomo.adapter.PMyAdapter;
import com.iloomo.bean.PImageFloder;
import com.iloomo.paysdk.R;
import com.iloomo.utils.PActivitySupport;


public class PMyPhoto extends PActivitySupport implements PListImageDirPopupWindowP.OnImageDirSelected {
    private ProgressDialog mProgressDialog;

    /**
     * 存储文件夹中的图片数量
     */
    private int mPicsSize;
    /**
     * 图片数量最多的文件夹
     */
    private File mImgDir;
    /**
     * 所有的图片
     */
    private List<String> mImgs=new ArrayList<>();

    private GridView mGirdView;
    private PMyAdapter mAdapter;
    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<String>();

    /**
     * 扫描拿到所有的图片文件夹
     */
    private List<PImageFloder> mPImageFloders = new ArrayList<PImageFloder>();

    private RelativeLayout mBottomLy;

    private TextView mChooseDir;
    private TextView mImageCount;
    private Button choose_photo;
    int totalCount = 0;

    private int mScreenHeight;
    public static boolean SINGLE=true;//是否选择单张

    private PListImageDirPopupWindowP mPListImageDirPopupWindow;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mProgressDialog.dismiss();
            // 为View绑定数据
            data2View();
            // 初始化展示文件夹的popupWindw
            initListDirPopupWindw();
        }
    };

    /**
     * 为View绑定数据
     */
    private void data2View() {
        if (mImgDir == null) {
            Toast.makeText(getApplicationContext(), "擦，一张图片没扫描到",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        mImgs.clear();
        mImgs.addAll(Arrays.asList(mImgDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (filename.endsWith(".jpg") || filename.endsWith(".png")
                        || filename.endsWith(".jpeg")) {
                    if (dir.exists()) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        })));

        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */
        mAdapter = new PMyAdapter(getApplicationContext(), mImgs,
                R.layout.pgrid_item, mImgDir.getAbsolutePath(), choose_photo);
        mGirdView.setAdapter(mAdapter);
        mImageCount.setText(totalCount + "张");
    }




    /**
     * 初始化展示文件夹的popupWindw
     */
    private void initListDirPopupWindw() {
        mPListImageDirPopupWindow = new PListImageDirPopupWindowP(
                LayoutParams.MATCH_PARENT, (int) (mScreenHeight * 0.7),
                mPImageFloders, LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.plist_dir, null));

        mPListImageDirPopupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        // 设置选择文件夹的回调
        mPListImageDirPopupWindow.setOnImageDirSelected(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pactivity_main);
        setCtenterTitle("图片");

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;

        initView();
        getImages();
        initEvent();

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter!=null){
            mAdapter.notifyDataSetChanged();
        }
        if(choose_photo!=null){
            choose_photo.setText("预览(" + PMyAdapter.mSelectedImage.size() + "/张)");
        }
    }

    public void openChoosePhoto(View view) {
        if(PMyAdapter.mSelectedImage.size()<=0)
            return;
        Intent intent = new Intent(this, PViewPagerPActivity.class);
        intent.putStringArrayListExtra("photos", PMyAdapter.mSelectedImage);
        intent.putExtra("index",0);
        startActivity(intent);
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable() {
            @Override
            public void run() {

                String firstImage = null;

                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = PMyPhoto.this
                        .getContentResolver();

                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);

                Log.e("TAG", mCursor.getCount() + "");
                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));

                    Log.e("TAG", path);
                    // 拿到第一张图片的路径
                    if (firstImage == null)
                        firstImage = path;
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;
                    String dirPath = parentFile.getAbsolutePath();
                    PImageFloder PImageFloder = null;
                    // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        // 初始化imageFloder
                        PImageFloder = new PImageFloder();
                        PImageFloder.setDir(dirPath);
                        PImageFloder.setFirstImagePath(path);
                    }

                    int picSize = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            if (filename.endsWith(".jpg") || filename.endsWith(".png")
                                    || filename.endsWith(".jpeg")) {
                                if(dir.exists()){
                                    return true;
                                }else{
                                    return false;
                                }
                            }else{
                                return false;
                            }

                        }
                    }).length;
                    totalCount += picSize;

                    PImageFloder.setCount(picSize);
                    mPImageFloders.add(PImageFloder);

                    if (picSize > mPicsSize) {
                        mPicsSize = picSize;
                        mImgDir = parentFile;
                    }
                }
                mCursor.close();

                // 扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;

                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(0x110);

            }
        }).start();

    }

    /**
     * 初始化View
     */
    private void initView() {
        mGirdView = (GridView) findViewById(R.id.id_gridView);
        mChooseDir = (TextView) findViewById(R.id.id_choose_dir);
        mImageCount = (TextView) findViewById(R.id.id_total_count);
        choose_photo = (Button) findViewById(R.id.choose_photo);
        mBottomLy = (RelativeLayout) findViewById(R.id.id_bottom_ly);

    }

    private void initEvent() {
        /**
         * 为底部的布局设置点击事件，弹出popupWindow
         */
        mBottomLy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPListImageDirPopupWindow
                        .setAnimationStyle(R.style.anim_popup_dir);
                mPListImageDirPopupWindow.showAsDropDown(mBottomLy, 0, 0);

                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = .3f;
                getWindow().setAttributes(lp);

            }
        });
    }

    @Override
    public void selected(PImageFloder floder) {

        mImgDir = new File(floder.getDir());
        mImgs.clear();
        mImgs.addAll(Arrays.asList(mImgDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (filename.endsWith(".jpg") || filename.endsWith(".png")
                        || filename.endsWith(".jpeg")) {
                    if (dir.exists()) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }

            }
        })));



        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */
        mAdapter = new PMyAdapter(getApplicationContext(), mImgs,
                R.layout.pgrid_item, mImgDir.getAbsolutePath(), choose_photo);
        mGirdView.setAdapter(mAdapter);
        // mAdapter.notifyDataSetChanged();
        mImageCount.setText(floder.getCount() + "张");
        mChooseDir.setText(floder.getName());
        mPListImageDirPopupWindow.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PMyAdapter.mSelectedImage.clear();

    }
}
