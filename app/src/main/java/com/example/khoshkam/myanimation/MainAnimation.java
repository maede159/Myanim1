package com.example.khoshkam.myanimation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.Toast;


public class MainAnimation extends AppCompatActivity implements View.OnTouchListener {
    Animation fadeOut, fadein, fade,fade1;
    AppCompatImageButton ibtn_voice, ibtn_more, ibtn_camera, ibtn_add;
    AppCompatTextView tv_textView;
    Chronometer counter_tv;
    AppCompatImageView iv_trash, iv_voice, arrow;
    float f;
    private float tv;
    AppCompatTextView tv_arrow;
    private long startTime=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_animation);
        initView();
        fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out_animation);
        fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_animation);
        fade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        fade1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade1);

    }

    public void initView() {
        ibtn_voice = findViewById(R.id.ibtn_voice);
        ibtn_voice.setOnTouchListener(this);
        ibtn_voice.bringToFront();
        ibtn_more = findViewById(R.id.ibtn_more);
        ibtn_more.bringToFront();
        ibtn_camera = findViewById(R.id.ibtn_camera);
        ibtn_camera.bringToFront();
        ibtn_add = findViewById(R.id.ibtn_add);
        tv_textView = findViewById(R.id.tv_textView);
        counter_tv = findViewById(R.id.counter_tv);
        iv_trash = findViewById(R.id.iv_trash);
        iv_voice = findViewById(R.id.iv_voice);
        iv_voice.setVisibility(View.INVISIBLE);
        tv_arrow = findViewById(R.id.tv_arrow);
        arrow = findViewById(R.id.arrow);

    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setActionDown();
                break;
            case MotionEvent.ACTION_MOVE:
                setActionMove();
                break;
            case MotionEvent.ACTION_UP:
                setActionUp();
                break;
        }
        return false;
    }

    private void setActionUp() {

        iv_voice.clearAnimation();
        iv_voice.setColorFilter(Color.BLACK);
        counter_tv.setVisibility(View.GONE);

        ibtn_voice.startAnimation(fadein);
        ibtn_more.startAnimation(fadein);
        ibtn_camera.startAnimation(fadein);
        iv_voice.setVisibility(View.GONE);
        ObjectAnimator translateVoice=ObjectAnimator.ofFloat(iv_voice,"translationX",0);
        translateVoice.setDuration(200);
        translateVoice.start();

        ObjectAnimator translateAdd=ObjectAnimator.ofFloat(ibtn_add,"translationX",0);
        translateAdd.setDuration(200);
        translateAdd.start();

        ObjectAnimator translateTextView=ObjectAnimator.ofFloat(tv_textView,"translationX",0);
        translateTextView.setDuration(200);
        translateTextView.start();


    }

    private void setActionMove() {
        ObjectAnimator translateTextView=ObjectAnimator.ofFloat(iv_voice,"translationY",0);
        translateTextView.setDuration(200);
        translateTextView.start();
    }

    private void setActionDown() {
        ibtn_voice.startAnimation(fadeOut);
        ibtn_more.startAnimation(fade1);
        ibtn_camera.startAnimation(fade1);
        iv_voice.setVisibility(View.VISIBLE);
        float x=iv_voice.getX();
        ObjectAnimator translateVoice=ObjectAnimator.ofFloat(iv_voice,"translationX",-x);
        translateVoice.setDuration(200);
        translateVoice.start();
        translateVoice.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                counter_tv.setVisibility(View.VISIBLE);
                counter_tv.setBase(SystemClock.elapsedRealtime());
                startTime = System.currentTimeMillis();
                counter_tv.start();
                iv_voice.startAnimation(fade);
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iv_voice.setColorFilter(Color.RED);
                    }
                },200);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        ObjectAnimator translateAdd=ObjectAnimator.ofFloat(ibtn_add,"translationX",-100);
        translateAdd.setDuration(200);
        translateAdd.start();

        ObjectAnimator translateTextView=ObjectAnimator.ofFloat(tv_textView,"translationX",-900);
        translateTextView.setDuration(200);
        translateTextView.start();



    }

}
