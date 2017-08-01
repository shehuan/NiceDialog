package com.othershe.nicedialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public abstract class BaseNiceDialog extends DialogFragment {

    private int margin;
    private int width;
    private int height;
    private float dimAmount = 0.4f;
    private boolean showBottom;
    private boolean outCancel = true;
    @StyleRes
    private int animStyle;

    public abstract int intLayoutId();

    public abstract void convertView(ViewHolder holder, DialogFragment dialog);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.NiceDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(intLayoutId(), container, false);
        convertView(ViewHolder.create(view), this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initParams();
    }

    private void initParams() {
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setWindowAnimations(animStyle);
            WindowManager.LayoutParams lp = window.getAttributes();
            if (showBottom) {
                lp.gravity = Gravity.BOTTOM;
            }

            if (width == 0) {
                lp.width = Utils.getScreenWidth(getContext()) - 2 * Utils.dp2px(getContext(), margin);
            } else {
                lp.width = Utils.dp2px(getContext(), width);
            }

            lp.height = height == 0 ? WindowManager.LayoutParams.WRAP_CONTENT : Utils.dp2px(getContext(), height);

            lp.dimAmount = dimAmount;//调节灰色背景透明度[0-1]，默认0.4f
            window.setAttributes(lp);
        }
        setCancelable(outCancel);
    }


    public BaseNiceDialog setMargin(int margin) {
        this.margin = margin;
        return this;
    }

    public BaseNiceDialog setWidth(int width) {
        this.width = width;
        return this;
    }

    public BaseNiceDialog setHeight(int height) {
        this.height = height;
        return this;
    }

    public BaseNiceDialog setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }

    public BaseNiceDialog setShowBottom(boolean showBottom) {
        this.showBottom = showBottom;
        return this;
    }

    public BaseNiceDialog setOutCancel(boolean outCancel) {
        this.outCancel = outCancel;
        return this;
    }

    public BaseNiceDialog setAnimStyle(@StyleRes int animStyle) {
        this.animStyle = animStyle;
        return this;
    }

    public void show(FragmentManager manager) {
        super.show(manager, String.valueOf(System.currentTimeMillis()));
    }
}
