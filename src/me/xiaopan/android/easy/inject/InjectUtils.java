package me.xiaopan.android.easy.inject;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

import me.xiaopan.java.easy.util.ReflectUtils;
import me.xiaopan.java.easy.util.SecondChronograph;
import me.xiaopan.java.easy.util.StringUtils;
import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 注入工具箱
 */
public class InjectUtils {
	/**
	 * 注入内容视图
	 * @param activity
	 */
	public static void injectContentView(Activity activity){
		InjectContentView injectContentView = activity.getClass().getAnnotation(InjectContentView.class);
		if(injectContentView != null && injectContentView.value() > 0){
			activity.setContentView(injectContentView.value());
		}
	}
	
	/**
	 * 注入内容视图
	 * @param fragment
	 * @param inflater
	 * @return
	 */
	public static View injectContentView(Fragment fragment, LayoutInflater inflater){
		InjectContentView injectContentView = fragment.getClass().getAnnotation(InjectContentView.class); 
		return injectContentView != null?inflater.inflate(injectContentView.value(), null):null;
	}
	
	/**
	 * 注入View字段
	 * @param activity
	 */
	public static void injectViewMembers(Activity activity){
		SecondChronograph secondChronograph = new SecondChronograph();
		for(Field field : ReflectUtils.getFields(activity.getClass(), true, activity.getClass().getAnnotation(InjectParentMember.class) != null, false, true)){
			InjectView injectView = field.getAnnotation(InjectView.class);
			if(injectView != null){
				field.setAccessible(true);
				try {
					field.set(activity, activity.findViewById(injectView.value()));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("注入View耗时："+secondChronograph.count().getIntervalMillis()+"毫秒");
	}
	
	/**
	 * 注入View字段
	 * @param fragment
	 */
	public static void injectViewMembers(Fragment fragment){
		for(Field field : ReflectUtils.getFields(fragment.getClass(), true, fragment.getClass().getAnnotation(InjectParentMember.class) != null, false, true)){
			InjectView injectView = field.getAnnotation(InjectView.class);
			if(injectView != null){
				field.setAccessible(true);
				try {
					field.set(fragment, fragment.getView().findViewById(injectView.value()));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 注入其他成员，例如Resource、Extra等
	 * @param activity
	 */
	public static void injectMembers(Activity activity){
		SecondChronograph secondChronograph = new SecondChronograph();
		for(Field field : ReflectUtils.getFields(activity.getClass(), true, activity.getClass().getAnnotation(InjectParentMember.class) != null, false, true)){
			if(!injectExtra(field, activity)){
				if(!injectResource(field, activity)){
					if(!injectPreference(field, activity)){
					}
				}
			}
		}
		System.out.println("注入成员变量耗时："+secondChronograph.count().getIntervalMillis()+"毫秒");
	}
	
	private static boolean injectExtra(Field field, Activity activity){
		InjectExtra injectExtra = field.getAnnotation(InjectExtra.class);
		if(injectExtra != null && StringUtils.isNotEmpty(injectExtra.value())){
			Class<?> fieldType = field.getType();
			try {
				field.setAccessible(true);
				if(ArrayList.class.isAssignableFrom(fieldType)){
					Class<?> first = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
					if(int.class.isAssignableFrom(first)){
						field.set(activity, activity.getIntent().getIntegerArrayListExtra(injectExtra.value()));
					}else if(String.class.isAssignableFrom(first)){
						field.set(activity, activity.getIntent().getStringArrayListExtra(injectExtra.value()));
					}else if(CharSequence.class.isAssignableFrom(first)){
						field.set(activity, activity.getIntent().getCharSequenceArrayListExtra(injectExtra.value()));
					}else if(Parcelable.class.isAssignableFrom(first)){
						field.set(activity, activity.getIntent().getParcelableArrayListExtra(injectExtra.value()));
					}
				}else{
					if(byte.class.isAssignableFrom(fieldType)){
						if(fieldType.isArray()){
							field.set(activity, activity.getIntent().getByteArrayExtra(injectExtra.value()));
						}else{
							field.set(activity, activity.getIntent().getByteExtra(injectExtra.value(), injectExtra.byteDefaultValue()));
						}
					}else if(short.class.isAssignableFrom(fieldType)){
						if(fieldType.isArray()){
							field.set(activity, activity.getIntent().getShortArrayExtra(injectExtra.value()));
						}else{
							field.set(activity, activity.getIntent().getShortExtra(injectExtra.value(), injectExtra.shortDefaultValue()));
						}
					}else if(int.class.isAssignableFrom(fieldType)){
						if(fieldType.isArray()){
							field.set(activity, activity.getIntent().getIntArrayExtra(injectExtra.value()));
						}else{
							field.set(activity, activity.getIntent().getIntExtra(injectExtra.value(), injectExtra.intDefaultValue()));
						}
					}else if(long.class.isAssignableFrom(fieldType)){
						if(fieldType.isArray()){
							field.set(activity, activity.getIntent().getLongArrayExtra(injectExtra.value()));
						}else{
							field.set(activity, activity.getIntent().getLongExtra(injectExtra.value(), injectExtra.longDefaultValue()));
						}
					}else if(float.class.isAssignableFrom(fieldType)){
						if(fieldType.isArray()){
							field.set(activity, activity.getIntent().getFloatArrayExtra(injectExtra.value()));
						}else{
							field.set(activity, activity.getIntent().getFloatExtra(injectExtra.value(), injectExtra.floatDefaultValue()));
						}
					}else if(double.class.isAssignableFrom(fieldType)){
						if(fieldType.isArray()){
							field.set(activity, activity.getIntent().getDoubleArrayExtra(injectExtra.value()));
						}else{
							field.set(activity, activity.getIntent().getDoubleExtra(injectExtra.value(), injectExtra.doubleDefaultValue()));
						}
					}else if(char.class.isAssignableFrom(fieldType)){
						if(fieldType.isArray()){
							field.set(activity, activity.getIntent().getCharArrayExtra(injectExtra.value()));
						}else{
							field.set(activity, activity.getIntent().getCharExtra(injectExtra.value(), injectExtra.charDefaultValue()));
						}
					}else if(boolean.class.isAssignableFrom(fieldType)){
						if(fieldType.isArray()){
							field.set(activity, activity.getIntent().getBooleanArrayExtra(injectExtra.value()));
						}else{
							field.set(activity, activity.getIntent().getBooleanExtra(injectExtra.value(), injectExtra.booleanDefaultValue()));
						}
					}else if(String.class.isAssignableFrom(fieldType)){
						if(fieldType.isArray()){
							field.set(activity, activity.getIntent().getStringArrayExtra(injectExtra.value()));
						}else{
							field.set(activity, activity.getIntent().getStringExtra(injectExtra.value()));
						}
					}else if(CharSequence.class.isAssignableFrom(fieldType)){
						if(fieldType.isArray()){
							field.set(activity, activity.getIntent().getCharSequenceArrayExtra(injectExtra.value()));
						}else{
							field.set(activity, activity.getIntent().getCharSequenceExtra(injectExtra.value()));
						}
					}else if(Parcelable.class.isAssignableFrom(fieldType)){
						if(fieldType.isArray()){
							field.set(activity, activity.getIntent().getParcelableArrayExtra(injectExtra.value()));
						}else{
							field.set(activity, activity.getIntent().getParcelableExtra(injectExtra.value()));
						}
					}else if(Serializable.class.isAssignableFrom(fieldType)){
						if(!fieldType.isArray()){
							field.set(activity, activity.getIntent().getSerializableExtra(injectExtra.value()));
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}else{
			return false;
		}
	}
	
	private static boolean injectResource(Field field, Activity activity){
		InjectResource injectResource = field.getAnnotation(InjectResource.class);
		if(injectResource != null){
			
			return true;
		}else{
			return false;
		}
	}
	
	private static boolean injectPreference(Field field, Activity activity){
		InjectPreference injectPreference = field.getAnnotation(InjectPreference.class);
		if(injectPreference != null){
			
			return true;
		}else{
			return false;
		}
	}
}