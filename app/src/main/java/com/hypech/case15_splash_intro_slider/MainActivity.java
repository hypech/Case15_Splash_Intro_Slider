/*
 * Copyright 2021 The Learning Android with Cases Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hypech.case15_splash_intro_slider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LinearLayout layoutDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MySP.getInstance().init(this);
        if(MySP.getInstance().getString("WELCOME").equals("yes")){
            Intent intent = new Intent(this, AfterSplash.class);
            startActivity(intent);
            finish();
        }

        ImageView image= findViewById(R.id.background);
        layoutDots = findViewById(R.id.layoutDots);
        Button btnSkip = findViewById(R.id.btnSkip);
        Button btnNext = findViewById(R.id.btnNext);

        Drawable[] backgrounds = new Drawable[4];
        backgrounds[0] = ContextCompat.getDrawable(this, R.drawable.welcome1);
        backgrounds[1] = ContextCompat.getDrawable(this, R.drawable.welcome2);
        backgrounds[2] = ContextCompat.getDrawable(this, R.drawable.welcome3);
        backgrounds[3] = ContextCompat.getDrawable(this, R.drawable.welcome4);

        ViewPagerAdapter adapter = new ViewPagerAdapter();
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        int itemLength = 4;

        showDots(viewPager.getCurrentItem());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                image.setImageDrawable(backgrounds[position]);
            }

            @Override
            public void onPageSelected(int position) {
                showDots(viewPager.getCurrentItem());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (viewPager.getCurrentItem() == itemLength - 1){
                    btnNext.setText("Enter");
                } else {
                    btnNext.setText("Next");
                }
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySP.getInstance().saveString("WELCOME", "ok");
                startActivity(new Intent(MainActivity.this, AfterSplash.class));
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() == itemLength - 1) {//got it
                    MySP.getInstance().saveString("WELCOME", "ok");
                    startActivity(new Intent(MainActivity.this, AfterSplash.class));
                    finish();
                } else {//next
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
            }
        });

    }

    private void showDots(int pageNumber) {
        TextView[] dots = new TextView[4];
        layoutDots.removeAllViews();
        for (int i = 0; i< dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
            dots[i].setTextColor(ContextCompat.getColor(this,
                    (i == pageNumber ? R.color.design_default_color_on_secondary : R.color.design_default_color_error)));
            layoutDots.addView(dots[i]);
        }
    }

    public class ViewPagerAdapter extends PagerAdapter {

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main, container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

}