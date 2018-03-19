package com.maede.recordview;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by Khoshkam on 3/17/2018.
 */

public class AlphaAnim {

    public  static void startFadeOut(Context context){
        Animation fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out_animation);
    }
    public static  void startFadeIn(Context context,View view){
       Animation fadein = AnimationUtils.loadAnimation(context, R.anim.fade_in_animation);
       view.startAnimation(fadein);
    }

    public  static  void statdFadeAndTransition(Context context,View view){
       Animation fade_transition_arrow = AnimationUtils.loadAnimation(context, R.anim.transition_fade);
        view.startAnimation(fade_transition_arrow);
    }
    public  static  void startFadeRepeat(Context context,View view){
       Animation fade = AnimationUtils.loadAnimation(context, R.anim.fade);
       view.startAnimation(fade);
    }

    public  static  void startFadeOnce(Context context,View view){
        Animation fade1 = AnimationUtils.loadAnimation(context, R.anim.fade1);
        view.startAnimation(fade1);
    }
}
