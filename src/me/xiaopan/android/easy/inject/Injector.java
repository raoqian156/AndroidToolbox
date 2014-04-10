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
	private InjectExtraInterpolator injectExtraInterpolator;
	private InjectViewInterpolator injectViewInterpolator;
	private InjectKnownInterpolator injectKnownInterpolator;
	private InjectResourceInterpolator injectResourceInterpolator;
	private InjectExtraJsonInterpolator injectExtraJsonInterpolator;
	private InjectFragmentInterpolator injectFragmentInterpolator;
	private InjectPreferencesInterpolator injectPreferencesInterpolator;
	private InjectPreferencesJsonInterpolator injectPreferencesJsonInterpolator;
	
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
			this.injectViewInterpolator = new InjectViewInterpolator((Activity) object);
			if(FragmentActivity.class.isAssignableFrom(object.getClass())){
				this.injectFragmentInterpolator = new InjectFragmentInterpolator((FragmentActivity) object);
			}
		}else if(Fragment.class.isAssignableFrom(object.getClass())){
			this.injectViewInterpolator = new InjectViewInterpolator((Fragment) object);
		}
		
		if(context != null){
			this.injectKnownInterpolator = new InjectKnownInterpolator(object, context);
			this.injectResourceInterpolator = new InjectResourceInterpolator(object, context);
			this.injectPreferencesInterpolator = new InjectPreferencesInterpolator(object, context);
			this.injectPreferencesJsonInterpolator = new InjectPreferencesJsonInterpolator(object, context);
		}
		
		if(bundle != null){
			this.injectExtraInterpolator = new InjectExtraInterpolator(object, bundle);
			this.injectExtraJsonInterpolator = new InjectExtraJsonInterpolator(object, bundle);
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
				if(injectViewInterpolator != null && field.isAnnotationPresent(InjectView.class)){
					injectViewInterpolator.onInject(field);
					continue;
				}
				if(injectFragmentInterpolator != null && field.isAnnotationPresent(InjectFragment.class) && Fragment.class.isAssignableFrom(field.getType())){
					injectFragmentInterpolator.onInject(field);
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
				if(injectExtraInterpolator != null && field.isAnnotationPresent(InjectExtra.class)){
					injectExtraInterpolator.onInject(field);
					continue;
				}
				if(injectExtraJsonInterpolator != null && field.isAnnotationPresent(InjectExtraJson.class)){
					injectExtraJsonInterpolator.onInject(field);
					continue;
				}
				if(injectResourceInterpolator != null && field.isAnnotationPresent(InjectResource.class)){
					injectResourceInterpolator.onInject(field);
					continue;
				}
				if(injectKnownInterpolator != null && field.isAnnotationPresent(Inject.class)){
					injectKnownInterpolator.onInject(field);
					continue;
				}
				if(injectPreferencesInterpolator != null && field.isAnnotationPresent(InjectPreference.class)){
					injectPreferencesInterpolator.onInject(field);
					continue;
				}
				if(injectPreferencesJsonInterpolator != null && field.isAnnotationPresent(InjectPreferenceJson.class)){
					injectPreferencesJsonInterpolator.onInject(field);
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