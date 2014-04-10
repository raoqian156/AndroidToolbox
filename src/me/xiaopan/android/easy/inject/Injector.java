package me.xiaopan.android.easy.inject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * 注入器
 */
public class Injector {
	private List<Field> viewFields;
	private List<Field> otherFields;
	private ViewInjectInterpolator viewInjectInterpolator;
	private FragmentInjectInterpolator fragmentInjectInterpolator;
	private ExtraInjectInterpolator extraInjectInterpolator;
	private ResourceInjectInterpolator resourceInjectInterpolator;
	private SimpleInjectInterpolator simpleInjectInterpolator;
	private SharedPreferencesInjectInterpolator sharedPreferencesInjectInterpolator;
	
	public Injector(Activity activity){
		this(activity, activity.getBaseContext(), activity.getIntent().getExtras());
	}
	
	public Injector(Fragment fragment){
		this(fragment, fragment.getActivity()!=null?fragment.getActivity().getBaseContext():null, fragment.getArguments());
	}
	
	public Injector(Object object, Context context, Bundle bundle){
		List<List<Field>> fieldsList = getFields(object.getClass());
		this.viewFields = fieldsList.get(0);
		this.otherFields = fieldsList.get(1);
		
		if(Activity.class.isAssignableFrom(object.getClass())){
			this.viewInjectInterpolator = new ViewInjectInterpolator((Activity) object);
			if(FragmentActivity.class.isAssignableFrom(object.getClass())){
				this.fragmentInjectInterpolator = new FragmentInjectInterpolator((FragmentActivity) object);
			}
		}else if(Fragment.class.isAssignableFrom(object.getClass())){
			this.viewInjectInterpolator = new ViewInjectInterpolator((Fragment) object);
		}
		
		if(context != null){
			this.simpleInjectInterpolator = new SimpleInjectInterpolator(object, context);
			this.resourceInjectInterpolator = new ResourceInjectInterpolator(object, context);
			this.sharedPreferencesInjectInterpolator = new SharedPreferencesInjectInterpolator(object, context);
		}
		
		if(bundle != null){
			this.extraInjectInterpolator = new ExtraInjectInterpolator(object, bundle);
		}
	}
	
	/**
	 * 注入View和Fragment字段
	 */
	public void injectViewMembers(){
		if(viewFields.size() > 0){
			Iterator<Field> fieldIterator = viewFields.iterator();
			Field field = null;
			while(fieldIterator.hasNext()){
				field = fieldIterator.next();
				if(viewInjectInterpolator != null && field.isAnnotationPresent(InjectView.class)){
					viewInjectInterpolator.onInject(field);
					continue;
				}
				if(fragmentInjectInterpolator != null && field.isAnnotationPresent(InjectFragment.class) && Fragment.class.isAssignableFrom(field.getType())){
					fragmentInjectInterpolator.onInject(field);
					continue;
				}
			}
		}
	}
	
	/**
	 * 注入其他成员，例如Resource、Extra等
	 * @param object
	 * @param context
	 * @param bundle
	 */
	public void injectOtherMembers(){
		if(otherFields.size() > 0){
			Iterator<Field> fieldIterator = otherFields.iterator();
			Field field = null;
			while(fieldIterator.hasNext()){
				field = fieldIterator.next();
				if(extraInjectInterpolator != null && field.isAnnotationPresent(InjectExtra.class)){
					extraInjectInterpolator.onInject(field);
					continue;
				}
				if(resourceInjectInterpolator != null && field.isAnnotationPresent(InjectResource.class)){
					resourceInjectInterpolator.onInject(field);
					continue;
				}
				if(simpleInjectInterpolator != null && field.isAnnotationPresent(Inject.class)){
					simpleInjectInterpolator.onInject(field);
					continue;
				}
				if(sharedPreferencesInjectInterpolator != null && field.isAnnotationPresent(InjectPreference.class)){
					sharedPreferencesInjectInterpolator.onInject(field);
					continue;
				}
			}
		}
	}
	
	/**
	 * 获取可注入的Fields
	 * @param classs
	 * @return
	 */
	public static List<List<Field>> getFields(Class<?> classs){
		List<Field> viewFields = new LinkedList<Field>();
		List<Field> otherMemberfFelds = new LinkedList<Field>();
		int modifiers;
		while(true){
			if(classs != null){
				for(Field field : classs.getDeclaredFields()){
					modifiers = field.getModifiers();
					if(!Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers)){
						if(field.isAnnotationPresent(InjectView.class) || field.isAnnotationPresent(InjectFragment.class)){
							viewFields.add(field);
						}else{
							otherMemberfFelds.add(field);
						}
					}
				}
				
				if(classs.isAnnotationPresent(InjectParentMember.class)){
					classs = classs.getSuperclass();
				}else{
					break;
				}
			}else{
				break;
			}
		}
		
		List<List<Field>> lists = new ArrayList<List<Field>>(2);
		lists.add(viewFields);
		lists.add(otherMemberfFelds);
		return lists;
	}
}