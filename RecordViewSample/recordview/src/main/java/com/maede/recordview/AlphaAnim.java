package com.maede.recordview;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by Dear-user on 03/17/2018.
 */

public class AlphaAnim {
    public static void fadeOut(Context context, View view) {
        Animation fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out_animation);
        view.startAnimation(fadeOut);
    }

    public static void fadeIn(Context context, View view) {
        Animation fadein = AnimationUtils.loadAnimation(context, R.anim.fade_in_animation);
        view.startAnimation(fadein);
    }

    public static void fadeRepeat(Context context, View view) {
        Animation fade = AnimationUtils.loadAnimation(context, R.anim.fade);
        view.startAnimation(fade);
    }

    public static void fadeButtonVoice(Context context, View view) {
        Animation fade = AnimationUtils.loadAnimation(context, R.anim.fade_ibtnvoice);
        view.startAnimation(fade);
    }

    public static void fadeTranslate(Context context, View view) {
        Animation fade3 = AnimationUtils.loadAnimation(context, R.anim.transition_fade);
        view.startAnimation(fade3);
    }

}
