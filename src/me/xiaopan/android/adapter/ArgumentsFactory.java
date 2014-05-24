package me.xiaopan.android.adapter;

import android.os.Bundle;

public interface ArgumentsFactory<T>{
    public Bundle onCreateArguments(T item);
}