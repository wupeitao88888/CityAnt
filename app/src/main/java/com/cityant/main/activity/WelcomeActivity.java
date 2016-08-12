package com.cityant.main.activity;

import android.os.Bundle;

import com.cityant.main.R;
import com.iloomo.base.ActivitySupport;
import com.iloomo.widget.StartPic;
import com.nineoldandroids.animation.Animator;


/**
 * Created by wupeitao on 16/3/7.
 */
public class WelcomeActivity extends ActivitySupport {
    private StartPic welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_welcome);
        setRemoveTitle();
        welcome = (StartPic) findViewById(R.id.welcome);
        welcome.setStartPicImage(R.drawable.white);
        welcome.setAnimationListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mIntent(WelcomeActivity.this, LoginActivity.class);
                finish();
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
