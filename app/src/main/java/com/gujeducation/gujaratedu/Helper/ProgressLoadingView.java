package com.gujeducation.gujaratedu.Helper;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import com.gujeducation.R;


public class ProgressLoadingView extends DialogFragment {


    Handler handler = new Handler();
    ImageView mouse;
    Dialog mDialog;
    private AnimatorSet set;
    private final Runnable sendData = new Runnable() {
        public void run() {
            try {
                //prepare and send the data here..

                set.start();

                handler.postDelayed(this, 1600);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public ProgressLoadingView() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (mDialog == null) {
            try {
                mDialog = new Dialog(getActivity(), R.style.cat_dialog);
                mDialog.setContentView(R.layout.progress_loading_view);
                mDialog.setCanceledOnTouchOutside(true);
                mDialog.getWindow().setGravity(Gravity.CENTER);

                View view = mDialog.getWindow().getDecorView();
                mouse = view.findViewById(R.id.mouse);
                set = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.anim.flip);
                set.setTarget(mouse);
                handler.post(sendData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mDialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (set != null)
                set.start();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("onResume", "Exception:onResume " + e.getMessage());
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        try {
            // operatingAnim.reset();
            mouse.clearAnimation();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("onPause", "Exception: " + e.getMessage());
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        try {
            mDialog = null;
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("onDismiss", "Exception: " + e.getMessage());
        }
    }

}
