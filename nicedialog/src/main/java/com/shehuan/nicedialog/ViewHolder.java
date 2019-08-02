package com.shehuan.nicedialog;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

public class ViewHolder {

    private SparseArray<View> views;
    private View convertView;

    private ViewHolder(View view) {
        convertView = view;
        views = new SparseArray<>();
    }

    public static ViewHolder create(View view) {
        return new ViewHolder(view);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return convertView;
    }

    public void setText(int viewId, String text) {
        View view = getView(viewId);
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        }
    }

    public void setText(int viewId, @StringRes int textId) {
        View view = getView(viewId);
        if (view instanceof TextView) {
            ((TextView) view).setText(textId);
        }
    }

    public void setTextColor(int viewId, @ColorInt int colorId) {
        View view = getView(viewId);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(colorId);
        }
    }

    public void setOnClickListener(int viewId, View.OnClickListener clickListener) {
        View view = getView(viewId);
        view.setOnClickListener(clickListener);
    }

    public void setBackgroundResource(int viewId, @DrawableRes int resId) {
        View view = getView(viewId);
        view.setBackgroundResource(resId);
    }

    public void setBackgroundColor(int viewId, @ColorInt int colorId) {
        View view = getView(viewId);
        view.setBackgroundColor(colorId);
    }
}
