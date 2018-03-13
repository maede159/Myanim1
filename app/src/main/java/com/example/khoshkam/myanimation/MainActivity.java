package com.example.khoshkam.myanimation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private Animation fadeOut, fadein, fade, fade1, fade2, fade3, transelate, translate1;
    private AppCompatImageButton ibtn_voice, ibtn_more, ibtn_camera, ibtn_add;
    private LinearLayout lin_arrow;
    private AppCompatTextView tv_textView;
    private Chronometer counter_tv;
    private AppCompatImageView iv_trash, iv_voice, arrow, iv_upper_lock, iv_lower_lock,iv_arrowup;
    private float f;
    private float tv;
    private AppCompatTextView tv_arrow;
    private long startTime = 0;
    private AnimatedVectorDrawableCompat animatedVectorDrawable;
    private boolean count = false;
    private boolean count1 = true;
    private boolean invalidate = true;
    RelativeLayout relaytivelayout_lock;
    float x, y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        x = iv_upper_lock.getY() - 5;
        y = iv_upper_lock.getY() + 5;
        fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out_animation);
        fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_animation);
        fade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        fade1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade1);
        fade2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_voice);
        fade3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.transition_fade);
        transelate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        translate1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.transelate1);
    }

    public void initView() {
        relaytivelayout_lock = findViewById(R.id.relaytivelayout_lock);
        relaytivelayout_lock.setY(330);
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
        lin_arrow = findViewById(R.id.lin_arrow);
        arrow = findViewById(R.id.arrow);
        iv_trash.setY(tv_textView.getY() + 100);
        animatedVectorDrawable = AnimatedVectorDrawableCompat.create(getApplicationContext(), R.drawable.basket_animated);
        iv_upper_lock = findViewById(R.id.iv_upper_lock);
        iv_lower_lock = findViewById(R.id.iv_lower_lock);
        iv_arrowup= findViewById(R.id.iv_arrowup);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setActionDown();
                break;
            case MotionEvent.ACTION_MOVE:
                setActionMove(motionEvent);
                break;
            case MotionEvent.ACTION_UP:
                setActionUp();
                break;
        }
        return true;
    }

    private void setActionDown() {
        if (count1) {
            ibtn_voice.startAnimation(fadeOut);
            lin_arrow.startAnimation(fadein);
            ibtn_more.startAnimation(fade1);
            ibtn_camera.startAnimation(fade1);
            iv_voice.setVisibility(View.VISIBLE);

            ObjectAnimator translateTextView1 = ObjectAnimator.ofFloat(lin_arrow, "translationX", -300);
            translateTextView1.setDuration(100);
            translateTextView1.start();
            lin_arrow.setVisibility(View.VISIBLE);

            count = true;
            count1 = true;
            float x = iv_voice.getX();
            ObjectAnimator translateVoice = ObjectAnimator.ofFloat(iv_voice, "translationX", -x);
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
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            iv_voice.setColorFilter(Color.RED);
                        }
                    }, 200);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            ObjectAnimator translateAdd = ObjectAnimator.ofFloat(ibtn_add, "translationX", -100);
            translateAdd.setDuration(200);
            translateAdd.start();

            ObjectAnimator translateTextView = ObjectAnimator.ofFloat(tv_textView, "translationX", -900);
            translateTextView.setDuration(200);
            translateTextView.start();

            ObjectAnimator translateLock = ObjectAnimator.ofFloat(relaytivelayout_lock, "translationY", 0);
            translateLock.setDuration(200);
            translateLock.start();
            translateLock.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    animationLock();
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

    private void setAnimationLock() {
        iv_upper_lock.startAnimation(transelate);
        iv_lower_lock.startAnimation(translate1);
        iv_arrowup.startAnimation(translate1);
        transelate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setAnimationLock();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void setActionUp() {
        if (count1) {

            iv_voice.clearAnimation();
            iv_voice.setColorFilter(Color.BLACK);
            counter_tv.setVisibility(View.GONE);
            ibtn_voice.startAnimation(fadein);
            ibtn_more.startAnimation(fadein);
            ibtn_camera.startAnimation(fadein);
            iv_voice.setVisibility(View.GONE);

            ObjectAnimator translateTextView1 = ObjectAnimator.ofFloat(lin_arrow, "translationX", 0);
            translateTextView1.setDuration(100);
            translateTextView1.start();
            lin_arrow.setVisibility(View.GONE);

            ObjectAnimator translateLock = ObjectAnimator.ofFloat(relaytivelayout_lock, "translationY", 330);
            translateLock.setDuration(200);
            translateLock.start();

            ObjectAnimator translateVoice = ObjectAnimator.ofFloat(iv_voice, "translationX", 0);
            translateVoice.setDuration(200);
            translateVoice.start();
            ObjectAnimator translateAdd = ObjectAnimator.ofFloat(ibtn_add, "translationX", 0);
            translateAdd.setDuration(200);
            translateAdd.start();
            ObjectAnimator translateTextView = ObjectAnimator.ofFloat(tv_textView, "translationX", 0);
            translateTextView.setDuration(200);
            translateTextView.start();
            ObjectAnimator translate = ObjectAnimator.ofFloat(iv_voice, "translationY", 0);
            translate.setDuration(200);
            translate.start();
            translate.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {

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


    private void setActionMove(MotionEvent move) {

        if (move.getRawY() <= 1800 && count1) {
            count1 = false;
            invalidate = false;
        }

        if (move.getRawX() <= ibtn_voice.getX() - 5 && count && count1) {
            counter_tv.stop();
            ObjectAnimator translateLock = ObjectAnimator.ofFloat(relaytivelayout_lock, "translationY", 330);
            translateLock.setDuration(200);
            translateLock.start();
            lin_arrow.startAnimation(fade3);
            fade3.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    counter_tv.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            iv_voice.clearAnimation();
            ObjectAnimator translateTextView = ObjectAnimator.ofFloat(iv_voice, "translationY", -150);
            translateTextView.setDuration(50);
            translateTextView.start();

            translateTextView.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    ObjectAnimator translate = ObjectAnimator.ofFloat(iv_voice, "rotation", 0, 180);
                    translate.setDuration(200);
                    translate.start();
                    translate.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            iv_trash.setImageDrawable(animatedVectorDrawable);
                            animatedVectorDrawable.start();
                            ObjectAnimator translateTextView = ObjectAnimator.ofFloat(iv_voice, "translationY", 10);
                            translateTextView.setDuration(300);
                            translateTextView.start();
                            translateTextView.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    ObjectAnimator translatetrash1 = ObjectAnimator.ofFloat(iv_trash, "translationY", tv_textView.getY() + 100);
                                    translatetrash1.setDuration(200);
                                    translatetrash1.start();
                                    ObjectAnimator translatetrash = ObjectAnimator.ofFloat(iv_voice, "translationY", tv_textView.getY() + 100);
                                    translatetrash.setDuration(200);
                                    translatetrash.start();


                                    translatetrash1.addListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animator) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animator) {
                                            ObjectAnimator translate = ObjectAnimator.ofFloat(iv_voice, "rotation", 180, 0);
                                            translate.setDuration(200);
                                            translate.start();
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
                                public void onAnimationCancel(Animator animator) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {

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
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
//            ObjectAnimator translate = ObjectAnimator.ofFloat(iv_voice, "rotation", 0,180);
//            translate.setDuration(10);
//            translate.setRepeatCount(1);
//            translate.start();


            iv_trash.setVisibility(View.VISIBLE);
            ObjectAnimator translatetrash1 = ObjectAnimator.ofFloat(iv_trash, "translationY", -tv_textView.getY() + 20);
            translatetrash1.setDuration(50);
            translatetrash1.start();
            count = false;
        }
    }


    public void animationLock() {
        ObjectAnimator translatetrash1 = ObjectAnimator.ofFloat(iv_upper_lock, "translationY", -10);
        translatetrash1.setDuration(300);
        translatetrash1.start();
        ObjectAnimator translatetrash0 = ObjectAnimator.ofFloat(iv_lower_lock, "translationY", 0);
        translatetrash0.setDuration(300);
        translatetrash0.start();
        ObjectAnimator translatetrash00 = ObjectAnimator.ofFloat(iv_arrowup, "translationY", 0);
        translatetrash00.setDuration(300);
        translatetrash00.start();
        translatetrash1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animationLock1();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public void animationLock1() {
        ObjectAnimator translatetrash1 = ObjectAnimator.ofFloat(iv_upper_lock, "translationY", 0);
        translatetrash1.setDuration(300);
        translatetrash1.start();
        ObjectAnimator translatetrash0 = ObjectAnimator.ofFloat(iv_lower_lock, "translationY", -10);
        translatetrash0.setDuration(300);
        translatetrash0.start();
        ObjectAnimator translatetrash00 = ObjectAnimator.ofFloat(iv_arrowup, "translationY", -10);
        translatetrash00.setDuration(300);
        translatetrash00.start();
        translatetrash0.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animationLock();
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
