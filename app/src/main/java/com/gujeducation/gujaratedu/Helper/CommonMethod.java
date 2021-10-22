package com.gujeducation.gujaratedu.Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class CommonMethod {
    private static AlertDialog dialog;

    //TODO - Make Status bar Transparent and bottom navigation view(back arrow,home,recent tab) as default
    public static void ManageScreenView(Activity activity) {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);

        }
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    //TODO - Redirect from One Activity to Another Activity
    public static void GoFrom(Context sourceActivity, AppCompatActivity destinationActivity, Bundle bundle) {
        Intent intent = new Intent(sourceActivity, destinationActivity.getClass());
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        sourceActivity.startActivity(intent);

    }

    //TODO - Common Method to Display toast
    public static void ShowToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    //TODO - Common Alert Dialog for whole app
    /*public static void AlertMe(Context context, String message, String firstBtnText, String secondBtn, final Runnable runnable, Boolean isFirstBtn, Boolean isSecondBtn) {
        try {
            dismissAlertDialog();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layoutInflateView = layoutInflater.inflate(R.layout.custom_toast_layout, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(layoutInflateView);
            dialog = builder.create();
            TextView toastmsg;
            Button btn_ok, btn_cancel;
            toastmsg = layoutInflateView.findViewById(R.id.toastmsg);
            btn_ok = layoutInflateView.findViewById(R.id.btn_ok);
            btn_cancel = layoutInflateView.findViewById(R.id.btn_cancel);

            if (!firstBtnText.equals("")) {
                btn_ok.setText(firstBtnText);
            }
            if (!secondBtn.equals("")) {
                btn_cancel.setText(secondBtn);
            }
            if (isFirstBtn) {
                btn_ok.setVisibility(View.VISIBLE);
            } else {
                btn_ok.setVisibility(View.GONE);
            }
            toastmsg.setText(message);
            if (isSecondBtn) {
                btn_cancel.setVisibility(View.VISIBLE);
            } else {
                btn_cancel.setVisibility(View.GONE);
            }
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (runnable == null) {
                        dialog.dismiss();
                    } else {
                        runnable.run();
                        dialog.dismiss();
                    }
                }
            });
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static void dismissAlertDialog() {
        try {
            if (dialog != null) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO - Use to hide Softkeyboard from any activity.
    public static void HideMyKeyboard(Context context) {
        if (((AppCompatActivity) context).getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) ((AppCompatActivity) context).getSystemService(((Activity) context).INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(((AppCompatActivity) context).getCurrentFocus().getWindowToken(), 0);
        }
    }


    public static boolean IsEmpty(EditText etText) {
        return etText.getText().toString().trim().length() <= 0;
    }
}
