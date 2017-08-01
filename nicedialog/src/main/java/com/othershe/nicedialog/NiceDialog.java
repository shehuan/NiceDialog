package com.othershe.nicedialog;

import android.support.annotation.LayoutRes;
import android.support.v4.app.DialogFragment;

public class NiceDialog extends BaseNiceDialog {

    @LayoutRes
    private int layoutId;
    private ViewConvertListener convertListener;

    @Override
    public int intLayoutId() {
        return layoutId;
    }

    @Override
    public void convertView(ViewHolder holder, DialogFragment dialog) {
        if (convertListener != null){
            convertListener.convertView(holder, dialog);
        }
    }


    public NiceDialog setLayoutId(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public NiceDialog setConvertListener(ViewConvertListener convertListener) {
        this.convertListener = convertListener;
        return this;
    }
}
