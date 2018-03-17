package com.maede.recordview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Khoshkam on 3/15/2018.
 */

public class RecordView1 extends RelativeLayout implements View.OnTouchListener {

    private Animation fadeOut, fadein, fade, fade1, fade2, fade3, transelate, translate1;
    private AppCompatImageButton ibtn_voice, ibtn_more, ibtn_camera, ibtn_add;
    private LinearLayout lin_arrow;
    private AppCompatTextView tv_textView, tv_arrow;
    private Chronometer counter_tv;
    private AppCompatImageView iv_trash, iv_voice, arrow, iv_upper_lock, iv_lower_lock, iv_arrowup;
    private long startTime = 0;
    private AnimatedVectorDrawableCompat animatedVectorDrawable;
    private boolean count = false;
    private boolean count1 = true;
    private boolean invalidate = false;
    RelativeLayout relaytivelayout_lock;
    private ObjectAnimator translatetrash1, translatetrash0, translatetrash00, translatetrash11, translatetrash000, translatetrash0000;
    private Context context;
    boolean isSwiping = false;
    boolean validate = true;


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
        relaytivelayout_lock = view.findViewById(R.id.relaytivelayout_lock);
        relaytivelayout_lock.setY(330);
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
        iv_trash.setY(tv_textView.getY() + 100);
        animatedVectorDrawable = AnimatedVectorDrawableCompat.create(context, R.drawable.basket_animated);
        iv_upper_lock = view.findViewById(R.id.iv_upper_lock);
        iv_lower_lock = view.findViewById(R.id.iv_lower_lock);
        iv_arrowup = view.findViewById(R.id.iv_arrowup);
//
        fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out_animation);
        fadein = AnimationUtils.loadAnimation(context, R.anim.fade_in_animation);
        fade = AnimationUtils.loadAnimation(context, R.anim.fade);
        fade1 = AnimationUtils.loadAnimation(context, R.anim.fade1);
        fade2 = AnimationUtils.loadAnimation(context, R.anim.rotate_voice);
        fade3 = AnimationUtils.loadAnimation(context, R.anim.transition_fade);
        transelate = AnimationUtils.loadAnimation(context, R.anim.translate);
        translate1 = AnimationUtils.loadAnimation(context, R.anim.transelate1);
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
        if (count1 && !isSwiping) {
            isSwiping = true;
            ibtn_voice.startAnimation(fadeOut);
            lin_arrow.startAnimation(fadein);
            ibtn_more.startAnimation(fade1);
            ibtn_camera.startAnimation(fade1);
            iv_voice.setVisibility(View.VISIBLE);

            ObjectAnimator translateTextView1 = ObjectAnimator.ofFloat(lin_arrow, "translationX", -300);
            translateTextView1.setDuration(300);
            translateTextView1.start();
            lin_arrow.setVisibility(View.VISIBLE);

            count = true;
            count1 = true;
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
            translateAdd.setDuration(300);
            translateAdd.start();

            ObjectAnimator translateTextView = ObjectAnimator.ofFloat(tv_textView, "translationX", -900);
            translateTextView.setDuration(300);
            translateTextView.start();
            validate = true;
            ObjectAnimator translateLock = ObjectAnimator.ofFloat(relaytivelayout_lock, "translationY", 0);
            translateLock.setDuration(300);
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


    public void setActionUp(MotionEvent motionEvent) {
        if (count1) {
            validate = false;
            ObjectAnimator translateTextView1 = ObjectAnimator.ofFloat(lin_arrow, "translationX", 0);
            translateTextView1.setDuration(300);
            translateTextView1.start();
            lin_arrow.setVisibility(View.GONE);

            translatetrash1.cancel();
            translatetrash0.cancel();
            translatetrash00.cancel();
            translatetrash11.cancel();
            translatetrash000.cancel();
            translatetrash0000.cancel();
            ObjectAnimator translateLock = ObjectAnimator.ofFloat(relaytivelayout_lock, "translationY", 330);
            translateLock.setDuration(300);
            translateLock.start();

            ibtn_voice.startAnimation(fadein);
            ibtn_more.startAnimation(fadein);
            ibtn_camera.startAnimation(fadein);
            if (motionEvent.getRawX() >= ibtn_voice.getX() - 5) {
                isSwiping = false;
                counter_tv.stop();
                counter_tv.setVisibility(View.GONE);
                iv_voice.clearAnimation();
                iv_voice.setVisibility(View.GONE);
                ObjectAnimator translateVoice = ObjectAnimator.ofFloat(iv_voice, "translationX", 0);
                translateVoice.setDuration(200);
                translateVoice.start();

                ObjectAnimator translateAdd = ObjectAnimator.ofFloat(ibtn_add, "translationX", 0);
                translateAdd.setDuration(300);
                translateAdd.start();
                ObjectAnimator translateTextView = ObjectAnimator.ofFloat(tv_textView, "translationX", 0);
                translateTextView.setDuration(300);
                translateTextView.start();

            }

        }
    }


    public void setActionMove(MotionEvent move) {

        if (move.getRawX() <= ibtn_voice.getX() - 5 && count && count1) {

            //ghesmate ghofl mial bala
            final ObjectAnimator translateLock = ObjectAnimator.ofFloat(relaytivelayout_lock, "translationY", 330);
            translateLock.setDuration(300);
            translateLock.start();
            //arrow ofoghi harkat mikone va mahv mishe
            lin_arrow.startAnimation(fade3);
            fade3.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // vaghti arrow mahv shod chronometer az bein mire
                    counter_tv.stop();
                    counter_tv.setVisibility(View.INVISIBLE);
                    //animation microphon az bein mire
                    iv_voice.clearAnimation();
                    //microphon be samte bala harkat mikone
                    ObjectAnimator translateTextView = ObjectAnimator.ofFloat(iv_voice, "translationY", -150);
                    translateTextView.setDuration(300);
                    translateTextView.start();

                    translateTextView.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            // vaghti harkat microphon be bala tamon shod 180 daraje micharkhe
                            ObjectAnimator translate = ObjectAnimator.ofFloat(iv_voice, "rotation", 0, 180);
                            translate.setDuration(300);
                            translate.start();
                            translate.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    //microphon be samte payin harkat mikone
                                    ObjectAnimator translateTextView = ObjectAnimator.ofFloat(iv_voice, "translationY", 0);
                                    translateTextView.setDuration(300);
                                    translateTextView.start();
                                    //satl ashghal miad bala va daresh baz mishe
                                    iv_trash.setVisibility(View.VISIBLE);
                                    ObjectAnimator translatetrash1 = ObjectAnimator.ofFloat(iv_trash, "translationY", -tv_textView.getY() + 20);
                                    translatetrash1.setDuration(300);
                                    translatetrash1.start();
                                    iv_trash.setImageDrawable(animatedVectorDrawable);
                                    animatedVectorDrawable.start();
                                    translateTextView.addListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animator) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animator) {
                                            // vaghti microphon omad payin ham satl ashghal ham microphon hamzaman be samte payin mian
                                            ObjectAnimator translatetrash1 = ObjectAnimator.ofFloat(iv_trash, "translationY", tv_textView.getY() + 100);
                                            translatetrash1.setDuration(300);
                                            translatetrash1.start();
                                            ObjectAnimator translatetrash = ObjectAnimator.ofFloat(iv_voice, "translationY", tv_textView.getY() + 100);
                                            translatetrash.setDuration(300);
                                            translatetrash.start();

                                            translatetrash1.addListener(new Animator.AnimatorListener() {
                                                @Override
                                                public void onAnimationStart(Animator animator) {

                                                }

                                                @Override
                                                public void onAnimationEnd(Animator animator) {
                                                    iv_voice.setVisibility(View.GONE);

                                                    //inja vaghti ham iv_voice  va ham iv_trash payin raftan microphon bayad becharkhe va be halat avalie bargarde
                                                    ObjectAnimator translate = ObjectAnimator.ofFloat(iv_voice, "rotation", 180, 0);
                                                    translate.setDuration(200);
                                                    translate.start();
                                                    ObjectAnimator translateVoice = ObjectAnimator.ofFloat(iv_voice, "translationX", 0);
                                                    translateVoice.setDuration(200);
                                                    translateVoice.start();
                                                    ObjectAnimator translateVoiceY = ObjectAnimator.ofFloat(iv_voice, "translationY", 0);
                                                    translateVoiceY.setDuration(200);
                                                    translateVoiceY.start();

                                                    ObjectAnimator translateAdd = ObjectAnimator.ofFloat(ibtn_add, "translationX", 0);
                                                    translateAdd.setDuration(300);
                                                    translateAdd.start();
                                                    ObjectAnimator translateTextView = ObjectAnimator.ofFloat(tv_textView, "translationX", 0);
                                                    translateTextView.setDuration(300);
                                                    translateTextView.start();

                                                    ibtn_voice.startAnimation(fadein);
                                                    ibtn_more.startAnimation(fadein);
                                                    ibtn_camera.startAnimation(fadein);
                                                    invalidate = true;
                                                    isSwiping = false;

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
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            count = false;
        }
    }

    public void animationLock() {
        if (validate) {
            translatetrash1 = ObjectAnimator.ofFloat(iv_upper_lock, "translationY", -10);
            translatetrash1.setDuration(300);
            translatetrash1.start();
            translatetrash0 = ObjectAnimator.ofFloat(iv_lower_lock, "translationY", 10);
            translatetrash0.setDuration(300);
            translatetrash0.start();
            translatetrash00 = ObjectAnimator.ofFloat(iv_arrowup, "translationY", 10);
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
    }

    public void animationLock1() {
        if (validate) {
            translatetrash11 = ObjectAnimator.ofFloat(iv_upper_lock, "translationY", 0);
            translatetrash11.setDuration(300);
            translatetrash11.start();
            translatetrash000 = ObjectAnimator.ofFloat(iv_lower_lock, "translationY", -15);
            translatetrash000.setDuration(300);
            translatetrash000.start();
            translatetrash0000 = ObjectAnimator.ofFloat(iv_arrowup, "translationY", -15);
            translatetrash0000.setDuration(300);
            translatetrash0000.start();
            translatetrash000.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    ObjectAnimator translatetrashupper = ObjectAnimator.ofFloat(iv_upper_lock, "translationY", 0);
                    translatetrashupper.setDuration(300);
                    translatetrashupper.start();
                    ObjectAnimator translatetrash1 = ObjectAnimator.ofFloat(iv_lower_lock, "translationY", 0);
                    translatetrash1.setDuration(300);
                    translatetrash1.start();
                    ObjectAnimator translatetrasharrow = ObjectAnimator.ofFloat(iv_arrowup, "translationY", 0);
                    translatetrasharrow.setDuration(300);
                    translatetrasharrow.start();
                    translatetrash1.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            ObjectAnimator translatetrashupper = ObjectAnimator.ofFloat(iv_upper_lock, "translationY", 4);
                            translatetrashupper.setDuration(300);
                            translatetrashupper.start();
                            ObjectAnimator translatetrash2 = ObjectAnimator.ofFloat(iv_lower_lock, "translationY", -4);
                            translatetrash2.setDuration(300);
                            translatetrash2.start();
                            ObjectAnimator translatetrasharrow = ObjectAnimator.ofFloat(iv_arrowup, "translationY", -4);
                            translatetrasharrow.setDuration(300);
                            translatetrasharrow.start();
                            translatetrash2.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    ObjectAnimator translatetrashupper = ObjectAnimator.ofFloat(iv_upper_lock, "translationY", 0);
                                    translatetrashupper.setDuration(300);
                                    translatetrashupper.start();
                                    ObjectAnimator translatetrash10 = ObjectAnimator.ofFloat(iv_lower_lock, "translationY", 0);
                                    translatetrash10.setDuration(300);
                                    translatetrash10.start();
                                    ObjectAnimator translatetrasharrow = ObjectAnimator.ofFloat(iv_arrowup, "translationY", 0);
                                    translatetrasharrow.setDuration(300);
                                    translatetrasharrow.start();
                                    translatetrash10.addListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animator) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animator) {
                                            ObjectAnimator translatetrashupper = ObjectAnimator.ofFloat(iv_upper_lock, "translationY", 4);
                                            translatetrashupper.setDuration(300);
                                            translatetrashupper.start();
                                            ObjectAnimator translatetrash20 = ObjectAnimator.ofFloat(iv_lower_lock, "translationY", -4);
                                            translatetrash20.setDuration(300);
                                            translatetrash20.start();
                                            ObjectAnimator translatetrasharrow = ObjectAnimator.ofFloat(iv_arrowup, "translationY", -4);
                                            translatetrasharrow.setDuration(300);
                                            translatetrasharrow.start();
                                            translatetrash20.addListener(new Animator.AnimatorListener() {
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
        }
    }


}


