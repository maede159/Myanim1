package com.maede.recordview;

import android.animation.ObjectAnimator;
import android.view.View;

public class TranslateAnim {
    public static void startTranslateX(View view,float translate){
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(view,"translationX",translate);
        objectAnimator.setDuration(300);
        objectAnimator.start();
    }
    public static void startTranslateY(View view,float translate){
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(view,"translationY",translate);
        objectAnimator.setDuration(300);
        objectAnimator.start();
    }
}
