package com.cityant.main.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.cityant.main.R;
import com.cityant.main.fragment.FragmentAdd;
import com.cityant.main.fragment.FragmentHome;
import com.cityant.main.fragment.FragmentKnock;
import com.cityant.main.fragment.FragmentMessage;
import com.cityant.main.fragment.FragmentMy;
import com.cityant.main.global.MyConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.iloomo.base.TabFragmentActivity;
import com.iloomo.widget.LCDialog;
import com.iloomo.widget.MainTabHost;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by wupeitao on 16/3/8.
 */
public class IndexFragment extends TabFragmentActivity implements View.OnTouchListener {
    private MainTabHost mainTabSupport;
    private View mPanelView;
    private RelativeLayout mCloseButton;
    private View mIdeaButton;


    private View mLbsButton;
    private View mReviewButton;
    private View mMoreButton;

    private Animation mButtonInAnimation;
    private Animation mButtonOutAnimation;
    private Animation mButtonScaleLargeAnimation;
    private Animation mButtonScaleSmallAnimation;
    private Animation mCloseRotateAnimation;

    //布局
    private Integer[] imgTab = {R.layout.tab_main_home, R.layout.tab_main_knock, R.layout.tab_main_add,
            R.layout.tab_main_message, R.layout.tab_main_my};
    //Fragment使用
    private Class[] classTab = {FragmentHome.class, FragmentKnock.class, FragmentAdd.class, FragmentMessage.class,
            FragmentMy.class};
    // tab选中背景 drawable 样式图片 背景都是同一个,背景颜色都是 白色。。。
    private Integer[] styleTab = {R.color.white, R.color.white, R.color.white, R.color.white, R.color.white,
            R.color.white};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mainTabSupport = new MainTabHost(IndexFragment.this, IndexFragment.this);

        mainTabSupport.setTag(5);
        mainTabSupport.setTabDrawable(imgTab);
        mainTabSupport.setTabFragment(classTab);
        mainTabSupport.setTabBackground(styleTab);
        mainTabSupport.setCenter_menuOnClickLen(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showDialog();
                showPopup(view);
            }
        });
        setContentView(mainTabSupport);


        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener(context));
        EMClient.getInstance().contactManager().setContactListener(emContactListener);

    }

    EMContactListener emContactListener = new EMContactListener() {
        @Override
        public void onContactAgreed(String username) {
            //好友请求被同意
        }

        @Override
        public void onContactRefused(String username) {
            //好友请求被拒绝
        }

        @Override
        public void onContactInvited(String username, String reason) {
            //收到好友邀请
        }

        @Override
        public void onContactDeleted(String username) {
            //被删除时回调此方法
        }


        @Override
        public void onContactAdded(String username) {
            //增加了联系人时回调此方法
        }
    };
    PopupWindow popWindow;

    private void showPopup(View parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.view_add, null);
        // 创建一个PopuWidow对象
        popWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        initView(view);
        initAnimation();
        openPanelView();


        //popupwindow弹出时的动画		popWindow.setAnimationStyle(R.style.popupWindowAnimation);
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        popWindow.setFocusable(true);
        // 设置允许在外点击消失
        popWindow.setOutsideTouchable(false);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popWindow.setBackgroundDrawable(new LevelListDrawable());

        //软键盘不会挡着popupwindow
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置菜单显示的位置
        popWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);

        //监听菜单的关闭事件
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        //监听触屏事件
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {

                return false;
            }
        });
    }


    private void initView(View view) {
        mPanelView = view.findViewById(R.id.panel);
        mCloseButton = (RelativeLayout) view.findViewById(R.id.close);
        mIdeaButton = view.findViewById(R.id.idea_btn);

        mLbsButton = view.findViewById(R.id.lbs_btn);
        mReviewButton = view.findViewById(R.id.review_btn);
        mMoreButton = view.findViewById(R.id.more_btn);

        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closePanelView();
            }
        });

        mIdeaButton.setOnTouchListener(this);
        mLbsButton.setOnTouchListener(this);
        mReviewButton.setOnTouchListener(this);
        mMoreButton.setOnTouchListener(this);

    }

    // 初始化动画
    private void initAnimation() {
        mButtonInAnimation = AnimationUtils.loadAnimation(this, R.anim.button_in);
        mButtonOutAnimation = AnimationUtils.loadAnimation(this, R.anim.button_out);
        mButtonScaleLargeAnimation = AnimationUtils.loadAnimation(this, R.anim.button_scale_to_large);
        mButtonScaleSmallAnimation = AnimationUtils.loadAnimation(this, R.anim.button_scale_to_small);
        mCloseRotateAnimation = AnimationUtils.loadAnimation(this, R.anim.close_rotate);

        mButtonOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 6个按钮的退出动画执行完毕后，将面板隐藏
                mPanelView.setVisibility(View.GONE);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

    }

    // 打开面板视图
    private void openPanelView() {
        mPanelView.setVisibility(View.VISIBLE);

        mIdeaButton.startAnimation(mButtonInAnimation);
        mLbsButton.startAnimation(mButtonInAnimation);
        mReviewButton.startAnimation(mButtonInAnimation);
        mMoreButton.startAnimation(mButtonInAnimation);

        mCloseButton.startAnimation(mCloseRotateAnimation);
    }

    // 关闭面板视图
    private void closePanelView() {
        // 给6个按钮添加退出动画
        mIdeaButton.startAnimation(mButtonOutAnimation);
        mLbsButton.startAnimation(mButtonOutAnimation);
        mReviewButton.startAnimation(mButtonOutAnimation);
        mMoreButton.startAnimation(mButtonOutAnimation);
        if (popWindow != null) {
            popWindow.dismiss();
        }

    }

    @Override
    public void onBackPressed() {
        if (mPanelView.getVisibility() == View.VISIBLE) {
            closePanelView();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onTouch(final View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下，按钮执行放大动画
                v.startAnimation(mButtonScaleLargeAnimation);

                break;
            case MotionEvent.ACTION_UP:
                switch (v.getId()) {
                    case R.id.idea_btn:
                        CreateFriendshipActivity.startActivity(context);
                        break;
                    case R.id.lbs_btn:
                        CreateSmallGrabActivity.startActivity(context);
                        break;
                    case R.id.review_btn:
                        CreateDemandActivity.startActivity(context);
                        break;
                    case R.id.more_btn:
                        CreateAntGuessActivity.startActivity(context);
                        break;
                }
            case MotionEvent.ACTION_CANCEL:
                // 手指移开，按钮执行缩小动画
                v.startAnimation(mButtonScaleSmallAnimation);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 缩小动画执行完毕后，将按钮的动画清除。这里的150毫秒是缩小动画的执行时间。
                        v.clearAnimation();
                    }
                }, 150);

                break;
        }
        return true;
    }


}
