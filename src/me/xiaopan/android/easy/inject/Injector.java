package me.xiaopan.android.easy.inject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
	private List<Field> injectFields;
	private ViewInjectInterpolator viewInjectInterpolator;
	private FragmentInjectInterpolator fragmentInjectInterpolator;
	private ExtraInjectInterpolator extraInjectInterpolator;
	private ResourceInjectInterpolator resourceInjectInterpolator;
	private SimpleInjectInterpolator simpleInjectInterpolator;
	private SharedPreferencesInjectInterpolator sharedPreferencesInjectInterpolator;
	
	public Injector(Activity activity){
		this.injectFields = getFields(activity.getClass());
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
		this.injectFields = getFields(fragment.getClass());
		this.viewInjectInterpolator = new ViewInjectInterpolator(fragment);
		this.extraInjectInterpolator = new ExtraInjectInterpolator(fragment, fragment.getArguments());
		Context context = fragment.getActivity().getBaseContext();
		this.resourceInjectInterpolator = new ResourceInjectInterpolator(fragment, context);
		this.simpleInjectInterpolator = new SimpleInjectInterpolator(fragment, context);
		this.sharedPreferencesInjectInterpolator = new SharedPreferencesInjectInterpolator(fragment, context);
	}
	
	public Injector(Object object, Context context){
		this.injectFields = getFields(object.getClass());
		this.resourceInjectInterpolator = new ResourceInjectInterpolator(object, context);
		this.simpleInjectInterpolator = new SimpleInjectInterpolator(object, context);
		this.sharedPreferencesInjectInterpolator = new SharedPreferencesInjectInterpolator(object, context);
	}
	
	/**
	 * 注入View和Fragment字段
	 * @param isInjectOtherMembers 是否同时注入其他成员
	 */
	public void injectViewAndFragmentMembers(boolean isInjectOtherMembers){
		if(injectFields.size() > 0){
			Iterator<Field> fieldIterator = injectFields.iterator();
			Field field = null;
			while(fieldIterator.hasNext()){
				field = fieldIterator.next();
				if(field.isAnnotationPresent(InjectView.class)){
					if(viewInjectInterpolator != null){
						viewInjectInterpolator.onInject(field);
					}
					fieldIterator.remove();
				}else if(field.isAnnotationPresent(InjectFragment.class) && Fragment.class.isAssignableFrom(field.getType())){
					if(fragmentInjectInterpolator != null){
						fragmentInjectInterpolator.onInject(field);
					}
					fieldIterator.remove();
				}else if(isInjectOtherMembers){
					if(field.isAnnotationPresent(InjectExtra.class)){
						if(extraInjectInterpolator != null){
							extraInjectInterpolator.onInject(field);
						}
						fieldIterator.remove();
					}else if(field.isAnnotationPresent(InjectResource.class)){
						if(resourceInjectInterpolator != null){
							resourceInjectInterpolator.onInject(field);
						}
						fieldIterator.remove();
					}else if(field.isAnnotationPresent(Inject.class)){
						if(simpleInjectInterpolator != null){
							simpleInjectInterpolator.onInject(field);
						}
						fieldIterator.remove();
					}else if(field.isAnnotationPresent(InjectPreference.class)){
						if(sharedPreferencesInjectInterpolator != null){
							sharedPreferencesInjectInterpolator.onInject(field);
						}
						fieldIterator.remove();
					}
				}
			}
		}
	}
	
	/**
	 * 注入View和Fragment字段
	 */
	public void injectViewAndFragmentMembers(){
		injectViewAndFragmentMembers(false);
	}
	
	/**
	 * 注入其他成员，例如Resource、Extra等
	 * @param object
	 * @param context
	 * @param bundle
	 */
	public void injectOtherMembers(){
		if(injectFields.size() > 0){
			Iterator<Field> fieldIterator = injectFields.iterator();
			Field field = null;
			while(fieldIterator.hasNext()){
				field = fieldIterator.next();
				if(field.isAnnotationPresent(InjectExtra.class)){
					if(extraInjectInterpolator != null){
						extraInjectInterpolator.onInject(field);
					}
					fieldIterator.remove();
				}else if(field.isAnnotationPresent(InjectResource.class)){
					if(resourceInjectInterpolator != null){
						resourceInjectInterpolator.onInject(field);
					}
					fieldIterator.remove();
				}else if(field.isAnnotationPresent(Inject.class)){
					if(simpleInjectInterpolator != null){
						simpleInjectInterpolator.onInject(field);
					}
					fieldIterator.remove();
				}else if(field.isAnnotationPresent(InjectPreference.class)){
					if(sharedPreferencesInjectInterpolator != null){
						sharedPreferencesInjectInterpolator.onInject(field);
					}
					fieldIterator.remove();
				}
			}
		}
	}
	
	/**
	 * 获取可注入的Fields
	 * @param classs
	 * @return
	 */
	public static List<Field> getFields(Class<?> classs){
		List<Field> fields = new LinkedList<Field>();
		int modifiers;
		while(true){
			if(classs != null){
				for(Field field : classs.getDeclaredFields()){
					modifiers = field.getModifiers();
					if(!Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers)){
						fields.add(field);
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
		return fields;
	}
}