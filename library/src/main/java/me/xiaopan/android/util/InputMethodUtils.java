package me.xiaopan.android.util;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class InputMethodUtils {

    public static void showSoftInput(EditText editText) {
        // 这一步是必须的，且必须在前
        editText.requestFocus();

        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

        // 定位光标到已输入文本的最后
        Editable editable = editText.getEditableText();
        Selection.setSelection(editable, editable.toString().length());
    }

    public static void hideSoftInput(EditText editText) {
        if (editText == null || editText.getWindowToken() == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static void hideSoftInput(Activity activity) {
        View currentFocusView = activity != null ? activity.getCurrentFocus() : null;
        if (currentFocusView == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(currentFocusView.getWindowToken(), 0);
    }

    @SuppressWarnings("unused")
    public static void hideSoftInput(Fragment fragment) {
        hideSoftInput(fragment != null ? fragment.getActivity() : null);
    }
}
