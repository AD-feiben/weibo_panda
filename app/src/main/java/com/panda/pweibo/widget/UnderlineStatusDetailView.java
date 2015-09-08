package com.panda.pweibo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.panda.pweibo.R;

public class UnderlineStatusDetailView extends LinearLayout {

    private int mCurrentPosition;

    public UnderlineStatusDetailView(Context context) {
    this(context, null);
}

    public UnderlineStatusDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOrientation(HORIZONTAL);

        int count = 3;
        for (int i = 0; i < count; i++) {
            View view = new View(context);
            LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT);
            params.weight = 1;
            view.setLayoutParams(params);
            view.setBackgroundResource(R.color.transparent);
            addView(view);
        }
    }

    /**
     * �Գ�ɫ�������ֵ��л������ö���Ч��
     * @param item radioGroup���±ꡢ��Ӧ���Ǹ�layout�ĵ�item�±�
     */
    public void setCurrentItemWithoutAnim(int item) {
        final View oldChild = getChildAt(mCurrentPosition);
        final View newChild = getChildAt(item);

        oldChild.setBackgroundResource(R.color.transparent);
        newChild.setBackgroundResource(R.color.orange);

        mCurrentPosition = item;
        invalidate();
    }

    /**
     * ��ѡ�еĳ�ɫ�ײ����û�������
     * @param item radioGroup���±ꡢ��Ӧ���Ǹ�layout�ĵ�item�±�
     */
    public void setCurrentItem(int item) {
        final View oldChild = getChildAt(mCurrentPosition);
        final View newChild = getChildAt(item);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, item - mCurrentPosition,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0);

        translateAnimation.setDuration(200);
        translateAnimation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                oldChild.setBackgroundResource(R.color.transparent);
                newChild.setBackgroundResource(R.color.orange);
            }
        });
        oldChild.setAnimation(translateAnimation);

        mCurrentPosition = item;
        invalidate();
    }
}