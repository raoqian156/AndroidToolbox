package me.xiaopan.android.widget;

import android.os.Bundle;

public interface ArgumentsFactory<T>{
    public Bundle onCreateArguments(T item);
}