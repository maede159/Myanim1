package com.maede.recordview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RecordView1 extends RelativeLayout implements View.OnTouchListener {
    private AppCompatImageButton ibtn_voice, ibtn_more, ibtn_camera, ibtn_add;
    private LinearLayout lin_arrow;
    private AppCompatTextView tv_textView, tv_arrow;
    private Chronometer counter_tv;
    private AppCompatImageView iv_trash, iv_voice, arrow, iv_upper_lock, iv_lower_lock, iv_arrowup;
    private long startTime = 0;
    private AnimatedVectorDrawableCompat animatedVectorDrawable;
    private RelativeLayout relaytivelayout_lock;
    private Context context;
    private boolean isSwiping, validate, count;
    private String mFileName;
    private MediaRecorder mRecorder;

    public RecordView1(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public RecordView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public RecordView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RecordView1(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    private void init() {
        View view = View.inflate(context, R.layout.main, null);
        addView(view);
        setFileName();
        relaytivelayout_lock = view.findViewById(R.id.relaytivelayout_lock);
        relaytivelayout_lock.setY(dp(330));
        ibtn_voice = view.findViewById(R.id.ibtn_voice);
        ibtn_voice.setOnTouchListener(this);
        ibtn_voice.bringToFront();
        ibtn_more = view.findViewById(R.id.ibtn_more);
        ibtn_more.bringToFront();
        ibtn_camera = view.findViewById(R.id.ibtn_camera);
        ibtn_camera.bringToFront();
        ibtn_add = view.findViewById(R.id.ibtn_add);
        tv_textView = view.findViewById(R.id.tv_textView);
        counter_tv = view.findViewById(R.id.counter_tv);
        iv_trash = view.findViewById(R.id.iv_trash);
        iv_voice = view.findViewById(R.id.iv_voice);
        iv_voice.setVisibility(View.INVISIBLE);
        tv_arrow = view.findViewById(R.id.tv_arrow);
        lin_arrow = view.findViewById(R.id.lin_arrow);
        arrow = view.findViewById(R.id.arrow);
        iv_trash.setY(tv_textView.getY() + dp(100));
        animatedVectorDrawable = AnimatedVectorDrawableCompat.create(context, R.drawable.basket_animated);
        iv_upper_lock = view.findViewById(R.id.iv_upper_lock);
        iv_lower_lock = view.findViewById(R.id.iv_lower_lock);
        iv_arrowup = view.findViewById(R.id.iv_arrowup);
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
                setActionUp(motionEvent);
                break;
        }
        return true;
    }

    public void setActionDown() {
        if (!isSwiping) {
            isSwiping = true;
            startRecording(mFileName);
            AlphaAnim.fadeButtonVoice(context, ibtn_voice);
            AlphaAnim.fadeIn(context, lin_arrow);
            AlphaAnim.fadeOut(context, ibtn_more);
            AlphaAnim.fadeOut(context, ibtn_camera);
            iv_voice.setVisibility(View.VISIBLE);
            TranslateAnim.startTranslateX(lin_arrow, -dp(100));
            lin_arrow.setVisibility(View.VISIBLE);
            count = true;
            iv_voice.setVisibility(View.VISIBLE);
            float x = iv_voice.getX();
            ObjectAnimator translateVoice = ObjectAnimator.ofFloat(iv_voice, "translationX", -x);
            translateVoice.setDuration(300);
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
                    AlphaAnim.fadeRepeat(context, iv_voice);
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
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator translateAdd = ObjectAnimator.ofFloat(ibtn_add, "translationX", -100);
            ObjectAnimator translateTextView = ObjectAnimator.ofFloat(tv_textView, "translationX", -900);
            ObjectAnimator translateLock = ObjectAnimator.ofFloat(relaytivelayout_lock, "translationY", 0);
            animatorSet.setDuration(300);
            animatorSet.setInterpolator(new AccelerateInterpolator());
            animatorSet.playTogether(translateAdd, translateTextView, translateLock);
            animatorSet.start();
            validate = true;
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

    public void setActionUp(MotionEvent motionEvent) {
        validate = false;
        stopRecording();
        lin_arrow.setVisibility(View.GONE);
        TranslateAnim.startTranslateX(lin_arrow, 0);
        TranslateAnim.startTranslateY(relaytivelayout_lock, 330);
        AlphaAnim.fadeIn(context, ibtn_voice);
        AlphaAnim.fadeIn(context, ibtn_more);
        AlphaAnim.fadeIn(context, ibtn_camera);
        if (motionEvent.getRawX() >= ibtn_voice.getX() - 5) {
            isSwiping = false;
            counter_tv.stop();
            counter_tv.setVisibility(View.GONE);
            iv_voice.clearAnimation();
            iv_voice.setVisibility(View.GONE);
            TranslateAnim.startTranslateX(iv_voice, 0);
            TranslateAnim.startTranslateX(ibtn_add, 0);
            TranslateAnim.startTranslateX(tv_textView, 0);
        }
    }

    public void setActionMove(MotionEvent move) {
        if (move.getRawX() <= ibtn_voice.getX() - 5 && count) {
            TranslateAnim.startTranslateY(relaytivelayout_lock, 330);
            AlphaAnim.fadeTranslate(context, lin_arrow);
            counter_tv.stop();
            counter_tv.setVisibility(View.INVISIBLE);
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator translateMicToTop = ObjectAnimator.ofFloat(iv_voice, "translationY", -150);
            ObjectAnimator translateVoiceRotation = ObjectAnimator.ofFloat(iv_voice, "rotation", 0, 180);
            ObjectAnimator translateVoiceToBellow = ObjectAnimator.ofFloat(iv_voice, "translationY", 0);
            animatorSet.setDuration(300);
            animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
            animatorSet.playSequentially(translateMicToTop, translateVoiceRotation, translateVoiceToBellow);
            animatorSet.start();
            iv_trash.setVisibility(View.VISIBLE);
            TranslateAnim.startTranslateY(iv_trash, -tv_textView.getY() + 20);
            Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    iv_trash.setImageDrawable(animatedVectorDrawable);
                    animatedVectorDrawable.start();
                }
            }, 650);
            AnimatorSet animatorSet1 = new AnimatorSet();
            ObjectAnimator translatetrashToBellow = ObjectAnimator.ofFloat(iv_trash, "translationY", tv_textView.getY() + 100);
            ObjectAnimator translateVoice = ObjectAnimator.ofFloat(iv_voice, "translationY", tv_textView.getY() + 100);
            animatorSet1.setInterpolator(new AccelerateInterpolator());
            animatorSet1.setStartDelay(900);
            animatorSet1.setDuration(300);
            animatorSet1.playTogether(translatetrashToBellow, translateVoice);
            animatorSet1.start();
            iv_voice.clearColorFilter();
            iv_voice.clearAnimation();
            Handler handler2 = new Handler();
            handler2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    iv_voice.setVisibility(View.GONE);
                }
            }, 900);
            AnimatorSet animatorSet2 = new AnimatorSet();
            ObjectAnimator backRotationVoice = ObjectAnimator.ofFloat(iv_voice, "rotation", 180, 0);
            ObjectAnimator backTranslateXVoice = ObjectAnimator.ofFloat(iv_voice, "translationX", 0);
            ObjectAnimator backTranslateYVoice = ObjectAnimator.ofFloat(iv_voice, "translationY", 0);
            animatorSet2.setInterpolator(new AccelerateInterpolator());
            animatorSet2.setDuration(300);
            animatorSet2.setStartDelay(1200);
            animatorSet2.playTogether(backRotationVoice, backTranslateXVoice, backTranslateYVoice);
            animatorSet2.start();
            AnimatorSet animatorSet3 = new AnimatorSet();
            ObjectAnimator translateAdd = ObjectAnimator.ofFloat(ibtn_add, "translationX", 0);
            ObjectAnimator translateTextView1 = ObjectAnimator.ofFloat(tv_textView, "translationX", 0);
            animatorSet3.setInterpolator(new AccelerateInterpolator());
            animatorSet3.setDuration(300);
            animatorSet3.setStartDelay(1200);
            animatorSet3.playSequentially(translateTextView1, translateAdd);
            animatorSet3.start();
            AlphaAnim.fadeIn(context, ibtn_voice);
            AlphaAnim.fadeIn(context, ibtn_more);
            AlphaAnim.fadeIn(context, ibtn_camera);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isSwiping = false;
                }
            }, 1800);
            count = false;
        }
    }

    public void animationLock() {
        if (validate) {
            AnimatorSet animatorSet10 = new AnimatorSet();
            ObjectAnimator translatetrash1 = ObjectAnimator.ofFloat(iv_upper_lock, "translationY", -10);
            ObjectAnimator translatetrash0 = ObjectAnimator.ofFloat(iv_lower_lock, "translationY", 10);
            ObjectAnimator translatetrash00 = ObjectAnimator.ofFloat(iv_arrowup, "translationY", 10);
            animatorSet10.setDuration(300);
            animatorSet10.setInterpolator(new AccelerateInterpolator());
            animatorSet10.playTogether(translatetrash1, translatetrash0, translatetrash00);
            animatorSet10.start();
            translatetrash00.addListener(new Animator.AnimatorListener() {
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
    }

    public void animationLock1() {
        if (validate) {
            AnimatorSet animatorSet0 = new AnimatorSet();
            ObjectAnimator translatetrash11 = ObjectAnimator.ofFloat(iv_upper_lock, "translationY", 0);
            ObjectAnimator translatetrash000 = ObjectAnimator.ofFloat(iv_lower_lock, "translationY", -15);
            ObjectAnimator translatetrash0000 = ObjectAnimator.ofFloat(iv_arrowup, "translationY", -15);
            animatorSet0.setDuration(300);
            animatorSet0.setInterpolator(new AccelerateInterpolator());
            animatorSet0.playTogether(translatetrash11, translatetrash000, translatetrash0000);
            animatorSet0.start();
            animate1(0, 300);
            animate2(4, 600);
            animate1(0, 900);
            AnimatorSet animatorSet4 = new AnimatorSet();
            ObjectAnimator translatetrashupper3 = ObjectAnimator.ofFloat(iv_upper_lock, "translationY", 4);
            ObjectAnimator translatetrash3 = ObjectAnimator.ofFloat(iv_lower_lock, "translationY", -4);
            ObjectAnimator translatetrasharrow3 = ObjectAnimator.ofFloat(iv_arrowup, "translationY", -4);
            animatorSet4.setInterpolator(new AccelerateInterpolator());
            animatorSet4.setDuration(300);
            animatorSet4.setStartDelay(1200);
            animatorSet4.playTogether(translatetrashupper3, translatetrash3, translatetrasharrow3);
            animatorSet4.start();
            translatetrasharrow3.addListener(new Animator.AnimatorListener() {
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

    private void animate2(float translate, long delay) {
        AnimatorSet animatorSet2 = new AnimatorSet();
        ObjectAnimator translatetrashupper0 = ObjectAnimator.ofFloat(iv_upper_lock, "translationY", translate);
        ObjectAnimator translatetrash20 = ObjectAnimator.ofFloat(iv_lower_lock, "translationY", -translate);
        ObjectAnimator translatetrasharrow0 = ObjectAnimator.ofFloat(iv_arrowup, "translationY", -translate);
        animatorSet2.setInterpolator(new AccelerateInterpolator());
        animatorSet2.setDuration(300);
        animatorSet2.setStartDelay(delay);
        animatorSet2.playTogether(translatetrashupper0, translatetrash20, translatetrasharrow0);
        animatorSet2.start();
    }

    private void animate1(float translate, long delay) {
        AnimatorSet animatorSet1 = new AnimatorSet();
        ObjectAnimator translatetrashupper = ObjectAnimator.ofFloat(iv_upper_lock, "translationY", translate);
        ObjectAnimator translatetrash10 = ObjectAnimator.ofFloat(iv_lower_lock, "translationY", translate);
        ObjectAnimator translatetrasharrow = ObjectAnimator.ofFloat(iv_arrowup, "translationY", translate);
        animatorSet1.setInterpolator(new AccelerateInterpolator());
        animatorSet1.setDuration(300);
        animatorSet1.playTogether(translatetrashupper, translatetrash10, translatetrasharrow);
        animatorSet1.setStartDelay(delay);
        animatorSet1.start();
    }

    private int dp(float value) {
        if (value == 0) {
            return 0;
        }
        float density = context.getResources().getDisplayMetrics().density;
        return (int) Math.ceil(density * value);
    }

    private void startRecording(String m) {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(m);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e("bbbbb", "prepare() failed");
        }
        mRecorder.start();
        Toast.makeText(context, "Recording started", Toast.LENGTH_LONG).show();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        Toast.makeText(context, "Record stop", Toast.LENGTH_LONG).show();
    }

    public void setFileName() {
        mFileName = context.getExternalCacheDir().getAbsolutePath();
        mFileName += "/a.3gp";
    }
}


