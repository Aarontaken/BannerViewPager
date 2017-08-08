package com.aaron.me.bannerviewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by chunyu on 2017/6/19.
 */

public class BannerViewPager extends ViewPager implements ViewPager.PageTransformer{
    private static final int MSG_START_ROLL = 1;
    private static final int MSG_STOP_ROLL = 0;
    private static final int ROLL_INTERVAL = 1000;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_START_ROLL:
                    int currentItem = getCurrentItem();
                    currentItem++;
                    setCurrentItem(currentItem);
                    startRoll();
                    break;

                case MSG_STOP_ROLL:
                    this.removeMessages(MSG_START_ROLL);
                    break;
            }
        }
    };

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPageTransformer(true, this);
    }

    private static final float SCALE_MIN = 0.9f;

    @Override
    public void transformPage(View page, float position) {
        if (position < -1) {
            page.setScaleX(SCALE_MIN);
            page.setScaleY(SCALE_MIN);
        } else if (position <= 1) {
            float scale = Math.max(SCALE_MIN, 1 - Math.abs(position));
            page.setScaleX(scale);
            page.setScaleY(scale);
        } else {
            page.setScaleX(SCALE_MIN);
            page.setScaleY(SCALE_MIN);
        }
    }

    public void startRoll() {
        handler.sendMessageDelayed(Message.obtain(handler, MSG_START_ROLL), ROLL_INTERVAL);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stopRoll();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                startRoll();
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void stopRoll() {
        handler.sendMessage(Message.obtain(handler, MSG_STOP_ROLL));
    }
}
