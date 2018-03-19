package com.maede.recordview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;

/**
 * Created by Khoshkam on 3/17/2018.
 */

public class TransitionAnim {

    public  static void startTransitionY(View view,float size) {
        ObjectAnimator transitionY = ObjectAnimator.ofFloat(view, "translationY",size );
        transitionY.setDuration(300);
        transitionY.setInterpolator(new AccelerateDecelerateInterpolator());
        transitionY.start();
    }

    public  static void startTransitionX(View view,float size){
        ObjectAnimator transitionX = ObjectAnimator.ofFloat(view, "translationX", size);
        transitionX.setDuration(300);
        transitionX.setInterpolator(new AccelerateDecelerateInterpolator());
        transitionX.start();
    }


}
