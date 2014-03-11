package me.xiaopan.android.easy.inject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
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
		List<List<Field>> fieldsList = getFields(activity.getClass());
		this.viewFields = fieldsList.get(0);
		this.otherFields = fieldsList.get(1);
		this.viewInjectInterpolator = new ViewInjectInterpolator(activity);
		this.extraInjectInterpolator = new ExtraInjectInterpolator(activity, activity.getIntent().getExtras());
		Context context = activity.getBaseContext();
		this.resourceInjectInterpolator = new ResourceInjectInterpolator(activity, context);
		this.simpleInjectInterpolator = new SimpleInjectInterpolator(activity, context);
		this.sharedPreferencesInjectInterpolator = new SharedPreferencesInjectInterpolator(activity, context);
		if(activity instanceof FragmentActivity){
			this.fragmentInjectInterpolator = new FragmentInjectInterpolator((FragmentActivity) activity);
		}
	}
	
	public Injector(Fragment fragment){
		List<List<Field>> fieldsList = getFields(fragment.getClass());
		this.viewFields = fieldsList.get(0);
		this.otherFields = fieldsList.get(1);
		this.viewInjectInterpolator = new ViewInjectInterpolator(fragment);
		this.extraInjectInterpolator = new ExtraInjectInterpolator(fragment, fragment.getArguments());
		Context context = fragment.getActivity().getBaseContext();
		this.resourceInjectInterpolator = new ResourceInjectInterpolator(fragment, context);
		this.simpleInjectInterpolator = new SimpleInjectInterpolator(fragment, context);
		this.sharedPreferencesInjectInterpolator = new SharedPreferencesInjectInterpolator(fragment, context);
	}
	
	public Injector(Object object, Context context){
		List<List<Field>> fieldsList = getFields(object.getClass());
		this.viewFields = fieldsList.get(0);
		this.otherFields = fieldsList.get(1);
		this.resourceInjectInterpolator = new ResourceInjectInterpolator(object, context);
		this.simpleInjectInterpolator = new SimpleInjectInterpolator(object, context);
		this.sharedPreferencesInjectInterpolator = new SharedPreferencesInjectInterpolator(object, context);
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
				if(field.isAnnotationPresent(InjectView.class)){
					if(viewInjectInterpolator != null){
						viewInjectInterpolator.onInject(field);
					}
				}else if(field.isAnnotationPresent(InjectFragment.class) && Fragment.class.isAssignableFrom(field.getType())){
					if(fragmentInjectInterpolator != null){
						fragmentInjectInterpolator.onInject(field);
					}
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
				if(field.isAnnotationPresent(InjectExtra.class)){
					if(extraInjectInterpolator != null){
						extraInjectInterpolator.onInject(field);
					}
				}else if(field.isAnnotationPresent(InjectResource.class)){
					if(resourceInjectInterpolator != null){
						resourceInjectInterpolator.onInject(field);
					}
				}else if(field.isAnnotationPresent(Inject.class)){
					if(simpleInjectInterpolator != null){
						simpleInjectInterpolator.onInject(field);
					}
				}else if(field.isAnnotationPresent(InjectPreference.class)){
					if(sharedPreferencesInjectInterpolator != null){
						sharedPreferencesInjectInterpolator.onInject(field);
					}
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