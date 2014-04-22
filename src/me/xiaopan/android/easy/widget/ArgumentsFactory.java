package me.xiaopan.android.easy.widget;

import android.os.Bundle;

public interface ArgumentsFactory<T>{
    public Bundle onCreateArguments(T item);
}