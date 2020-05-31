package com.softxpert.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Objects;

public class Utils {

    public static final String TAG = Utils.class.getSimpleName();
    private static Location mLocation = null;
    private static boolean islocationLoaded;
    private static AlertDialog alert;


    public static void replaceFragmentWithBundle(Fragment fragment, FragmentManager manager, Bundle bundle,
                                                 int containerViewId, String TAG, boolean isAddStack) {
        if (manager == null) {
            return;
        }

        fragment.setArguments(bundle);

        FragmentTransaction transaction = manager.beginTransaction();
        if (isAddStack) {
            transaction.addToBackStack(null);
        }
        transaction.replace(containerViewId, fragment, TAG).commit();
    }

    public static void replaceFragment(Fragment fragment, FragmentManager manager, int containerViewId,
                                       String TAG, boolean isAddStack) {
        if (manager == null) {
            return;
        }

        FragmentTransaction transaction = manager.beginTransaction();
        //   transaction.setCustomAnimations(R.anim.enter_left, R.anim.exit_right);
        if (isAddStack) {
            transaction.addToBackStack(null);
        }
        transaction.replace(containerViewId, fragment, TAG).commit();
    }

    public static void addFragmentWithBundle(Fragment fragment, FragmentManager manager, int containerViewId,
                                             String hideTAG, Bundle bundle) {
        if (manager == null) {
            return;
        }
        fragment.setArguments(bundle);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(Objects.requireNonNull(manager.findFragmentByTag(hideTAG)));
        transaction.add(containerViewId, fragment);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }


    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager manager = (ConnectivityManager)
                Objects.requireNonNull(activity).getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    public static void startXShowAnimation(View v) {
        TranslateAnimation animate = new TranslateAnimation(
                v.getWidth(),
                0,
                0,
                0
        );

        animate.setDuration(500);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(animate);
    }

    public static void startXHideAnimation(View v) {
        TranslateAnimation animate = new TranslateAnimation(
                0,
                v.getWidth(),
                0,
                0
        );

        animate.setDuration(100);
        animate.setFillAfter(true);
        v.startAnimation(animate);

    }

    public static void startYShowAnimation(View v) {
        TranslateAnimation animate = new TranslateAnimation(
                0,
                0,
                -v.getHeight(),
                0
        );

        animate.setDuration(500);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(animate);
    }

    public static void startYHideAnimation(View v) {
        TranslateAnimation animate = new TranslateAnimation(
                0,
                0,
                0,
                -v.getHeight()
        );

        animate.setDuration(100);
        animate.setFillAfter(true);
        v.startAnimation(animate);
    }

}
