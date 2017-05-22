package me.xiaopan.android.app.dialog;

import android.app.Activity;
import android.widget.Toast;

import me.xiaopan.android.toolbox.library.R;

/**
 * Created by raoqian on 2017/5/21.
 */

public class SpecialDialog extends BaseDialog implements BaseDialog.OnBaseClickListener {
    public SpecialDialog(Activity context) {
        super(context);
    }

    @Override
    protected void onInitView() {
        setButtonText(R.id.tv_btn_left, "取消", this)
                .setButtonText(R.id.tv_btn_right, "确认", this).setAnimRes(R.style.default_dialog_style_dialog);
    }

    @Override
    protected int onGetLayout() {
        return R.layout.dialog_show;
    }


    @Override
    public void onDialogClick(int vId) {
        if (vId == R.id.tv_btn_left) {
            Toast.makeText(getContext(), "点击了取消", Toast.LENGTH_LONG).show();
        } else if (vId == R.id.tv_btn_right) {
            Toast.makeText(getContext(), "点击了确认", Toast.LENGTH_LONG).show();
        }
    }
}
