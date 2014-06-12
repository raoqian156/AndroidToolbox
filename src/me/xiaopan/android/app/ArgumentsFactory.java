package me.xiaopan.android.app;

import android.os.Bundle;

public interface ArgumentsFactory<T>{
    public Bundle onCreateArguments(T item);
}