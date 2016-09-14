package xyz.stepsecret.arrayproject3.Alert;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by stepsecret on 13/9/2559.
 */
public class AlertShow {

    public void show_failure(Context mContext, String message)
    {
        new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(message)
                .show();
    }

    public void show_success(Context mContext, String message)
    {
        new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(message)
                .show();
    }
}
