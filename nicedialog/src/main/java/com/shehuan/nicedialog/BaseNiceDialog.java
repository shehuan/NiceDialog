package com.shehuan.nicedialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public abstract class BaseNiceDialog extends DialogFragment {
    private static final String MARGIN = "margin";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String DIM = "dim_amount";
    private static final String GRAVITY = "gravity";
    private static final String TOUCH_OUT_CANCELABLE = "touch_out_cancelable";
    private static final String BACK_CANCELABLE = "back_cancelable";
    private static final String THEME = "theme";
    private static final String ANIM = "anim_style";
    private static final String LAYOUT = "layout_id";

    private int margin;//左右边距
    private int width;//宽度
    private int height;//高度
    private float dimAmount = 0.5f;//灰度深浅
    private int gravity = Gravity.CENTER;//显示的位置
    private boolean touchoutCancelable = false;//是否点击外部取消
    private boolean backCancelable = true;
    @StyleRes
    protected int theme = R.style.NiceDialogStyle; // dialog主题
    @StyleRes
    private int animStyle;
    @LayoutRes
    protected int layoutId;

    private static final int DIALOG_DEFAULT_SIZE_FLAG = 0;
    private static final int DIALOG_WRAP_CONTENT_FLAG = -1;
    private static final int DIALOG_MATCH_PARENT_FLAG = -2;


    public abstract int getLayoutId();

    public abstract void convertView(ViewHolder holder, BaseNiceDialog dialog);

    public int getDialogTheme() {
        return theme;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, getDialogTheme());

        //恢复保存的数据
        if (savedInstanceState != null) {
            margin = savedInstanceState.getInt(MARGIN);
            width = savedInstanceState.getInt(WIDTH);
            height = savedInstanceState.getInt(HEIGHT);
            dimAmount = savedInstanceState.getFloat(DIM);
            gravity = savedInstanceState.getInt(GRAVITY);
            touchoutCancelable = savedInstanceState.getBoolean(TOUCH_OUT_CANCELABLE);
            backCancelable = savedInstanceState.getBoolean(BACK_CANCELABLE);
            theme = savedInstanceState.getInt(THEME);
            animStyle = savedInstanceState.getInt(ANIM);
            layoutId = savedInstanceState.getInt(LAYOUT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutId = getLayoutId();
        View view = inflater.inflate(layoutId, container, false);
        convertView(ViewHolder.create(view), this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initParams();
    }

    /**
     * 屏幕旋转等导致DialogFragment销毁后重建时保存数据
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MARGIN, margin);
        outState.putInt(WIDTH, width);
        outState.putInt(HEIGHT, height);
        outState.putFloat(DIM, dimAmount);
        outState.putInt(GRAVITY, gravity);
        outState.putBoolean(TOUCH_OUT_CANCELABLE, touchoutCancelable);
        outState.putBoolean(BACK_CANCELABLE, backCancelable);
        outState.putInt(THEME, theme);
        outState.putInt(ANIM, animStyle);
        outState.putInt(LAYOUT, layoutId);
    }

    private void initParams() {
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            //调节灰色背景透明度[0-1]，默认0.5f
            lp.dimAmount = dimAmount;
            if (gravity != 0) {
                lp.gravity = gravity;
            }
            switch (gravity) {
                case Gravity.START:
                case (Gravity.START | Gravity.BOTTOM):
                case (Gravity.START | Gravity.TOP):
                    if (animStyle == 0) {
                        animStyle = R.style.LeftAnimation;
                    }
                    break;
                case Gravity.TOP:
                    if (animStyle == 0) {
                        animStyle = R.style.TopAnimation;
                    }
                    break;
                case Gravity.END:
                case (Gravity.END | Gravity.BOTTOM):
                case (Gravity.END | Gravity.TOP):
                    if (animStyle == 0) {
                        animStyle = R.style.RightAnimation;
                    }
                    break;
                case Gravity.BOTTOM:
                    if (animStyle == 0) {
                        animStyle = R.style.BottomAnimation;
                    }
                    break;
                default:
                    break;

            }

            //设置dialog宽度
            if (width == DIALOG_DEFAULT_SIZE_FLAG) {
                lp.width = getScreenWidth(getContext()) - 2 * dp2px(getContext(), margin);
            } else if (width == DIALOG_WRAP_CONTENT_FLAG) {
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            } else if (width == DIALOG_MATCH_PARENT_FLAG) {
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            } else {
                lp.width = dp2px(getContext(), width);
            }

            //设置dialog高度
            if (height == DIALOG_DEFAULT_SIZE_FLAG) {
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            } else if (height == DIALOG_MATCH_PARENT_FLAG) {
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            } else {
                lp.height = dp2px(getContext(), height);
            }

            //设置dialog进入、退出的动画
            window.setWindowAnimations(animStyle);
        }
        setCancelable(backCancelable);
        getDialog().setCanceledOnTouchOutside(touchoutCancelable);
    }

    public BaseNiceDialog setMargin(int marginDp) {
        this.margin = marginDp;
        return this;
    }

    public BaseNiceDialog setWidth(int widthDp) {
        this.width = widthDp;
        return this;
    }

    public BaseNiceDialog setHeight(int heightDp) {
        this.height = heightDp;
        return this;
    }

    /**
     * 设置Dialog的高度为Match_Parent
     *
     * @return BaseNiceDialog
     */
    public BaseNiceDialog setHeightMatchParent() {
        this.height = DIALOG_MATCH_PARENT_FLAG;
        return this;
    }

    /**
     * 设置Dialog的宽度为Match_Parent
     *
     * @return BaseNiceDialog
     */
    public BaseNiceDialog setWidthMatchParent() {
        this.width = DIALOG_MATCH_PARENT_FLAG;
        return this;
    }

    public BaseNiceDialog setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }

    public BaseNiceDialog setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    /**
     * @param backCancelable 返回键是否可取消Dialog，默认值true
     * @return BaseNiceDialog
     */
    public BaseNiceDialog setBackCancelable(boolean backCancelable) {
        this.backCancelable = backCancelable;
        return this;
    }

    /**
     * @param touchoutCancelable 点击屏幕是否可以取消dialog，默认值false
     * @return BaseNiceDialog
     */
    public BaseNiceDialog setCanceledOnTouchOutside(boolean touchoutCancelable) {
        this.touchoutCancelable = touchoutCancelable;
        return this;
    }

    public BaseNiceDialog setAnimStyle(@StyleRes int animStyle) {
        this.animStyle = animStyle;
        return this;
    }

    public BaseNiceDialog show(FragmentManager manager) {
        FragmentTransaction ft = manager.beginTransaction();
        if (this.isAdded()) {
            ft.remove(this).commit();
        }
        ft.add(this, String.valueOf(System.currentTimeMillis()));
        ft.commitAllowingStateLoss();
        return this;
    }


    public int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }
}
