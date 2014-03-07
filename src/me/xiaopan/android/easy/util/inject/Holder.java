package me.xiaopan.android.easy.util.inject;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.util.List;

public class Holder {
	SoftReference<Class<?>> classReference;
	SoftReference<List<Field>> fieldsReference;
}
