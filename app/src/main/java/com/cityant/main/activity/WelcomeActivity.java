package com.cityant.main.activity;

import android.content.Intent;
import android.os.Bundle;

import com.cityant.main.R;


import com.cityant.main.model.onPermissionsMsg;
import com.cityant.main.utlis.PermissionsUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.ActivitySupport;
import com.iloomo.threadpool.MyThreadPool;
import com.iloomo.utils.L;
import com.iloomo.widget.StartPic;
import com.nineoldandroids.animation.Animator;


/**
 * Created by wupeitao on 16/3/7.
 */
public class WelcomeActivity extends ActivitySupport {
    private StartPic welcome;
    private static final int sleepTime = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_welcome);
        setRemoveTitle();

        welcome = (StartPic) findViewById(R.id.welcome);
        PermissionsUtils.getPermissionsUtils(context).setOnPermissionsMsg(new onPermissionsMsg() {
            @Override
            public void onWRITE_EXTERNAL_STORAGE(boolean bool) {
                L.e(bool+"1");
                if (bool)
                    showPic();
                else
                    PermissionsUtils.getPermissionsUtils(context).getPermissions();
            }
        });
        PermissionsUtils.getPermissionsUtils(context).getPermissions();


    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    private void showPic() {
        welcome.setStartPicImage(R.drawable.white);
        welcome.setAnimationListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {


                MyThreadPool.getInstance().submit(new Runnable() {
                    public void run() {
                        if (MYAppconfig.loginUserInfoData == null) {
                            EMClient.getInstance().logout(true, new EMCallBack() {

                                @Override
                                public void onSuccess() {
                                    // TODO Auto-generated method stub
                                    startActivity(new Intent(context, LoginActivity.class));
                                    finish();
                                }

                                @Override
                                public void onProgress(int progress, String status) {
                                    // TODO Auto-generated method stub
                                }

                                @Override
                                public void onError(int code, String message) {
                                    // TODO Auto-generated method stub

                                }
                            });
                        } else {
                            if (EMClient.getInstance().isLoggedInBefore()) {
                                // auto login mode, make sure all group and conversation is loaed before enter the main screen
                                long start = System.currentTimeMillis();
                                EMClient.getInstance().groupManager().loadAllGroups();
                                EMClient.getInstance().chatManager().loadAllConversations();
                                long costTime = System.currentTimeMillis() - start;
                                //wait
                                if (sleepTime - costTime > 0) {
                                    try {
                                        Thread.sleep(sleepTime - costTime);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                startActivity(new Intent(context, IndexFragment.class));
                                finish();
                            } else {
                                try {
                                    Thread.sleep(sleepTime);
                                } catch (InterruptedException e) {
                                }
                                startActivity(new Intent(context, LoginActivity.class));
                                finish();
                            }
                        }
                    }
                });
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }


}
