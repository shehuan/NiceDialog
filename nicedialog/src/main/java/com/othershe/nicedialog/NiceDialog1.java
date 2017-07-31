package com.othershe.nicedialog;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class NiceDialog1 extends DialogFragment {

    @LayoutRes
    private int layoutId;
    private ViewConvertListener convertListener;
    private int margin;
    private float dimAmount = 0.5f;
    private boolean showBottom;
    private boolean outCancel = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.NiceDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutId, container, false);
        convertListener.convertView(ViewHolder.create(view), this);
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
            WindowManager.LayoutParams lp = window.getAttributes();
            if (showBottom) {
                lp.gravity = Gravity.BOTTOM;
            }
            lp.dimAmount = dimAmount;//调节灰色蒙板透明度[0-1]，默认0.2f
            lp.width = Utils.getScreenWidth(getContext()) - 2 * Utils.dp2px(getContext(), margin);
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }

        setCancelable(outCancel);
    }

    public NiceDialog1 setLayoutId(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public NiceDialog1 setConvertListener(ViewConvertListener convertListener) {
        this.convertListener = convertListener;
        return this;
    }

    public NiceDialog1 setMargin(int margin) {
        this.margin = margin;
        return this;
    }

    public NiceDialog1 setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }

    public NiceDialog1 setShowBottom(boolean showBottom) {
        this.showBottom = showBottom;
        return this;
    }

    public NiceDialog1 setOutCancel(boolean outCancel) {
        this.outCancel = outCancel;
        return this;
    }

    public void show(FragmentManager manager) {
        super.show(manager, String.valueOf(System.currentTimeMillis()));
    }
}
