package com.cityant.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.cityant.main.R;
import com.cityant.main.db.DBControl;
import com.cityant.main.db.DbHelper;
import com.hyphenate.chat.EMClient;
import com.iloomo.base.ActivitySupport;
import com.iloomo.threadpool.MyThreadPool;
import com.iloomo.utils.L;
import com.iloomo.utils.LCSharedPreferencesHelper;
import com.iloomo.widget.StartPic;
import com.nineoldandroids.animation.Animator;


/**
 * Created by wupeitao on 16/3/7.
 */
public class WelcomeActivity extends ActivitySupport {
    private StartPic welcome;
    private static final int sleepTime = 1000;
    private LCSharedPreferencesHelper lcSharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_welcome);
        setRemoveTitle();
        checkDb();
        welcome = (StartPic) findViewById(R.id.welcome);
        welcome.setStartPicImage(R.drawable.white);
        welcome.setAnimationListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {


                MyThreadPool.getInstance().submit(new Runnable() {
                    public void run() {
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
                            startActivity(new Intent(context, LoginActivity.class));
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

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void checkDb() {
        lcSharedPreferencesHelper = LCSharedPreferencesHelper.instance(context, LCSharedPreferencesHelper.ILOOMO);
        DbHelper dbHelper = new DbHelper(context);
        DBControl dbControl = new DBControl(context, dbHelper);
        try {
            if (TextUtils.isEmpty(lcSharedPreferencesHelper.getValue(LCSharedPreferencesHelper.UPDATE_DB))) {
                lcSharedPreferencesHelper.putValue(LCSharedPreferencesHelper.UPDATE_DB, DBControl.DB_VERSION);
                dbControl.deleteAllTab();
                dbControl.createAllTab();
            } else {
                if (!DBControl.DB_VERSION.equals(lcSharedPreferencesHelper.getValue(LCSharedPreferencesHelper.UPDATE_DB))) {
                    lcSharedPreferencesHelper.putValue(LCSharedPreferencesHelper.UPDATE_DB, DBControl.DB_VERSION);
                    dbControl.deleteAllTab();
                    dbControl.createAllTab();
                }
            }
        } catch (Exception e) {
        }
    }
}
