package com.iloomo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.iloomo.paysdk.R;
import com.iloomo.utils.L;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by wupeitao on 16/8/16.
 */
public class MSwitchButton extends FrameLayout {
    private Context mContext;

    private LinearLayout mswitch;
    private LinearLayout mswitch_select_left;
    //    private LinearLayout mswitch_select_right;
    int w = 200;
    boolean b = true;

    boolean open = false;

    public MSwitchButton(Context context) {
        super(context);
        init(context);
    }

    public MSwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        View inflate = View.inflate(context, R.layout.layout_mswitchbutton, null);
        mswitch = (LinearLayout) inflate.findViewById(R.id.mswitch);
        L.e(w + "控件的宽" + inflate.getWidth() + "/" + mswitch.getWidth());
        mswitch_select_left = new LinearLayout(mContext);
        mswitch_select_left.setOrientation(LinearLayout.VERTICAL);// 设置布局LinearLayout的属性
        mswitch_select_left.setBackgroundResource(R.drawable.mswitch_button_select);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
                w / 2,
                ViewGroup.LayoutParams.MATCH_PARENT);

        mswitch.addView(mswitch_select_left, lp1);

        mswitch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!open){
                    open=true;
                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(
                            ObjectAnimator.ofFloat(mswitch_select_left, "translationX", w / 2)
                    );
                    set.setDuration(500).start();
                }else{
                    open=false;
                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(
                            ObjectAnimator.ofFloat(mswitch_select_left, "translationX", 0)
                    );
                    set.setDuration(500).start();
                }
            }
        });
        addView(inflate);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //设置一个默认值，就是这个View的默认宽度为500，这个看我们自定义View的要求
        int result = 500;
        if (specMode == MeasureSpec.AT_MOST) {//相当于我们设置为wrap_content
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {//相当于我们设置为match_parent或者为一个具体的值
            result = specSize;
        }
        L.e(result + "............");

        if (b) {
            b = false;
            w = result;
            init(mContext);
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result = 500;
        if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        return result;
    }

    //
}
