package com.aaron.me.bannerviewpager;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private BannerViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (BannerViewPager) findViewById(R.id.banner);
        BannerPagerAdapter adapter = new BannerPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(15);//设置个缓存，不要每次都新建view。否则手动滑动时，左右imageview的展示会有问题
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2, false);
        viewPager.startRoll();
    }

    private class BannerPagerAdapter extends PagerAdapter {
        private int[] imgIds = {R.mipmap.c, R.mipmap.d, R.mipmap.e, R.mipmap.f};

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setImageResource(imgIds[position % getRealCount()]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        public int getRealCount() {
            return imgIds.length;
        }
    }
}
