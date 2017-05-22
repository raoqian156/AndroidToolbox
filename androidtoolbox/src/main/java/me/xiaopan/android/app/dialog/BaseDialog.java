package me.xiaopan.android.app.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.android.toolbox.library.R;

import static android.view.View.GONE;

/**
 * Created by liuyichuanmei on 2016/12/21.
 * 非登录页面弹窗基类
 */

public abstract class BaseDialog extends Dialog implements View.OnClickListener {

    private static BaseDialog mDialog;

    public static BaseDialog instance(Activity activity, final int layRes) {
        mDialog = new BaseDialog(activity) {

            @Override
            protected void onInitView() {

            }

            @Override
            protected int onGetLayout() {
                return layRes;
            }
        };
        Window window = mDialog.getWindow();
        window.setGravity(mDialog.getGravity());
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = mDialog.isNeedBackground() ? 0.7f : 0.0f;
        if (mDialog.getAnimRes() > 0) {
            window.setWindowAnimations(mDialog.getAnimRes());
        }
        Log.e("BaseDialog", "anim = " + mDialog.getAnimRes());
        return mDialog;
    }

    public static BaseDialog instance(Activity activity, final int layRes, int animRes) {
        mDialog = new BaseDialog(activity) {

            @Override
            protected void onInitView() {

            }

            @Override
            protected int onGetLayout() {
                return layRes;
            }
        };
        mDialog.getWindow().setWindowAnimations(animRes);
        return mDialog;
    }

    //可用context ACtivity最佳
    protected BaseDialog(Activity context) {
        super(context, R.style.default_dialog_style);
    }

    protected BaseDialog(Activity context, int themeResId) {
        super(context, themeResId);
    }

    private boolean mCancelAble = true;
    private int mGravity = Gravity.CENTER;
    private boolean needBackground = true;
    private int mAnimRes = -1;

    public boolean isCancelAble() {
        return mCancelAble;
    }

    public BaseDialog setCancelAble(boolean mCancelAble) {
        this.mCancelAble = mCancelAble;
        return this;
    }

    public int getGravity() {
        return mGravity;
    }

    public BaseDialog setGravity(int mGravity) {
        this.mGravity = mGravity;
        return this;
    }

    public boolean isNeedBackground() {
        return needBackground;
    }

    public BaseDialog setNeedBackground(boolean needBackground) {
        this.needBackground = needBackground;
        return this;
    }

    public int getAnimRes() {
        return mAnimRes;
    }

    public BaseDialog setAnimRes(int animRes) {
        this.mAnimRes = animRes;
        return this;
    }

    private List<Integer> childControlShowTextViewIdList = new ArrayList<>();
    private List<String> childControlShowTextViewStringList = new ArrayList<>();
    private List<Integer> childControlClickTextViewIdList = new ArrayList<>();
    private List<Integer> childClickDismissIdList = new ArrayList<>();
    private OnBaseClickListener childControlShowTextViewClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("BaseDialog", "onCreate size = " + (childControlShowTextViewStringList.size()));
        setContentView(onGetLayout());
        onInitView();
        commentInit();
    }

    /**
     * 基础设置
     */
    protected void commentInit() {
        if (this.childControlShowTextViewIdList.size() > 0) {
            for (int i = 0; i < this.childControlShowTextViewIdList.size(); i++) {
                int viewId = this.childControlShowTextViewIdList.get(i);
                if (findViewById(viewId) instanceof TextView) {
                    TextView tv = (TextView) findViewById(viewId);
                    if (!TextUtils.isEmpty(this.childControlShowTextViewStringList.get(i))) {
                        tv.setText(this.childControlShowTextViewStringList.get(i));
                    } else {
                        tv.setVisibility(GONE);
                    }
                    if (this.childControlClickTextViewIdList.contains(viewId)) {
                        tv.setOnClickListener(this);
                    }
                }
            }
        }
        this.setCancelable(mCancelAble);
        this.setCanceledOnTouchOutside(mCancelAble);
        if (getAnimRes() > 0) {
            this.getWindow().setWindowAnimations(getAnimRes());
        }
        this.getWindow().setGravity(Gravity.CENTER);
        this.getWindow().getAttributes().dimAmount = isNeedBackground() ? 0.7f : 0.0f;
    }

    protected abstract void onInitView();

    protected abstract int onGetLayout();

    protected String getString(int res) {
        if (res <= 0) {
            return null;
        }
        return getContext().getString(res);
    }

    public BaseDialog setButtonText(int textViewId, String showString, OnBaseClickListener click) {
        return setButtonText(false, textViewId, showString, click);
    }

    /**
     * @param isClickDismiss 是否点击当前Id以关闭Dialog
     * @param textViewId     需要特殊设置的文本框ID
     * @param showString     null则隐藏该View
     * @param click          由于本人编码习惯，此处所有需要clickListener的请使用同一个，否则以第一个为准
     * @return
     */
    public BaseDialog setButtonText(boolean isClickDismiss, int textViewId, String showString, OnBaseClickListener click) {
        Log.e("BaseDialog", "setButtonText - " + showString);
        this.childControlShowTextViewIdList.add(textViewId);
        this.childControlShowTextViewStringList.add(showString);
        if (TextUtils.isEmpty(showString)) {
            return this;
        }
        if (this.childControlShowTextViewClick == null) {
            this.childControlShowTextViewClick = click;
        }
        if (click != null) {
            this.childControlClickTextViewIdList.add(textViewId);
        }
        if (isClickDismiss) {
            this.childClickDismissIdList.add(textViewId);
        }
        return this;
    }

    @Override
    public void show() {
        Log.e("BaseDialog", "show");
        try {
            super.show();
        } catch (Exception e) {
        }
    }

    @Override
    public void onClick(View v) {
        this.childControlShowTextViewClick.onDialogClick(v.getId());
        if (this.childClickDismissIdList.contains(v.getId())) {
            dismiss();
        }
    }

    public interface OnBaseClickListener {
        void onDialogClick(int vId);
    }
}
