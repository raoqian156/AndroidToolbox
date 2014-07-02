package me.xiaopan.android.happy.sample;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.android.happy.R;
import me.xiaopan.android.happy.sample.EntryFragment.EntryItem;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.google.gson.Gson;

public class MainActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		List<EntryItem> entryItems = new ArrayList<EntryItem>();
		
		List<EntryItem> appItems = new ArrayList<EntryItem>();
		appItems.add(new EntryItem("ActivityPool", null));
		appItems.add(new EntryItem("ActivityUtils", null));
		appItems.add(new EntryItem("DialogUtils", null));
		appItems.add(new EntryItem("DownloadManagerUtils", null));
		appItems.add(new EntryItem("FragmentBuilder", null));
		appItems.add(new EntryItem("FragmentListPagerAdapter", null));
		appItems.add(new EntryItem("FragmentUtils", null));
		appItems.add(new EntryItem("MessageDialogFragment", null));
		appItems.add(new EntryItem("ProgressDialogFragment", null));
		appItems.add(new EntryItem("SimpleFragmentPagerAdapter", null));
		appItems.add(new EntryItem("SimpleFragmentStatePagerAdapter", null));
		entryItems.add(new EntryItem("app", EntryFragment.class, appItems));
		
		List<EntryItem> contentItems = new ArrayList<EntryItem>();
		contentItems.add(new EntryItem("BroadcastUtils", null));
		contentItems.add(new EntryItem("ContentUtils", null));
		contentItems.add(new EntryItem("FileUtils", null));
		contentItems.add(new EntryItem("IntentUtils", null));
		contentItems.add(new EntryItem("StartAppReceiver", null));
		contentItems.add(new EntryItem("UriUtils", null));
		entryItems.add(new EntryItem("content", EntryFragment.class, contentItems));
		
		List<EntryItem> contentpmItems = new ArrayList<EntryItem>();
		contentpmItems.add(new EntryItem("VersionManager", null));
		entryItems.add(new EntryItem("content.pm", EntryFragment.class, contentpmItems));
		
		List<EntryItem> contentresItems = new ArrayList<EntryItem>();
		contentresItems.add(new EntryItem("AssetsUtils", null));
		contentresItems.add(new EntryItem("DimensUtils", null));
		contentresItems.add(new EntryItem("RUtils", null));
		entryItems.add(new EntryItem("content.res", EntryFragment.class, contentresItems));
		
		List<EntryItem> graphicsItems = new ArrayList<EntryItem>();
		graphicsItems.add(new EntryItem("BitmapDecoder", null));
		graphicsItems.add(new EntryItem("BitmapUtils", null));
		graphicsItems.add(new EntryItem("Colors", null));
		graphicsItems.add(new EntryItem("RectUtils", null));
		graphicsItems.add(new EntryItem("TextUtils", null));
		entryItems.add(new EntryItem("graphics", EntryFragment.class, graphicsItems));
		
		List<EntryItem> graphicsdrawableItems = new ArrayList<EntryItem>();
		graphicsdrawableItems.add(new EntryItem("DrawabLevelController", null));
		entryItems.add(new EntryItem("graphics.drawable", EntryFragment.class, graphicsdrawableItems));
		
		List<EntryItem> hardwareItems = new ArrayList<EntryItem>();
		hardwareItems.add(new EntryItem("DeviceUtils", null));
		entryItems.add(new EntryItem("hardware", EntryFragment.class, hardwareItems));
		
		List<EntryItem> hardwarecameraItems = new ArrayList<EntryItem>();
		hardwarecameraItems.add(new EntryItem("BasePreviewAndPictureSizeCalculator", null));
		hardwarecameraItems.add(new EntryItem("BestPreviewSizeCalculator", null));
		hardwarecameraItems.add(new EntryItem("CameraManager", null));
		hardwarecameraItems.add(new EntryItem("CameraUtils", null));
		hardwarecameraItems.add(new EntryItem("LoopFocusManager", null));
		entryItems.add(new EntryItem("hardware.camera", EntryFragment.class, hardwarecameraItems));
		
		List<EntryItem> netItems = new ArrayList<EntryItem>();
		netItems.add(new EntryItem("NetworkUtils", null));
		entryItems.add(new EntryItem("net", EntryFragment.class, netItems));
		
		List<EntryItem> osItems = new ArrayList<EntryItem>();
		osItems.add(new EntryItem("OSUtils", null));
		osItems.add(new EntryItem("SDCardUtils", null));
		entryItems.add(new EntryItem("os", EntryFragment.class, osItems));
		
		List<EntryItem> preferenceItems = new ArrayList<EntryItem>();
		preferenceItems.add(new EntryItem("PreferencesUtils", null));
		entryItems.add(new EntryItem("preference", EntryFragment.class, preferenceItems));
		
		List<EntryItem> providerItems = new ArrayList<EntryItem>();
		providerItems.add(new EntryItem("PhoneUtils", null));
		providerItems.add(new EntryItem("SettingsUtils", null));
		entryItems.add(new EntryItem("provider", EntryFragment.class, providerItems));
		
		List<EntryItem> utilItems = new ArrayList<EntryItem>();
		utilItems.add(new EntryItem("AndroidLogger", null));
		utilItems.add(new EntryItem("Countdown", null));
		utilItems.add(new EntryItem("DoubleClickExitDetector", null));
		utilItems.add(new EntryItem("GeometryUtils", null));
		utilItems.add(new EntryItem("InputVerifyUtils", null));
		utilItems.add(new EntryItem("LoopTimer", null));
		utilItems.add(new EntryItem("OtherUtils", null));
		utilItems.add(new EntryItem("RebootThreadExceptionHandler", null));
		entryItems.add(new EntryItem("util", EntryFragment.class, utilItems));
		
		List<EntryItem> viewItems = new ArrayList<EntryItem>();
		viewItems.add(new EntryItem("ViewListPagerAdapter", null));
		viewItems.add(new EntryItem("ViewRefreshHandler", null));
		viewItems.add(new EntryItem("ViewUtils", null));
		viewItems.add(new EntryItem("WindowUtils", null));
		entryItems.add(new EntryItem("view", EntryFragment.class, viewItems));
		
		List<EntryItem> viewanimationItems = new ArrayList<EntryItem>();
		viewanimationItems.add(new EntryItem("AnimationUtils", null));
		viewanimationItems.add(new EntryItem("ViewAnimationUtils", null));
		entryItems.add(new EntryItem("view.animation", EntryFragment.class, viewanimationItems));
		
		List<EntryItem> viewinputmethodItems = new ArrayList<EntryItem>();
		viewinputmethodItems.add(new EntryItem("InputMethodUtils", null));
		entryItems.add(new EntryItem("view.inputmethod", EntryFragment.class, viewinputmethodItems));
		
		List<EntryItem> webkitItems = new ArrayList<EntryItem>();
		webkitItems.add(new EntryItem("WebViewManager", null));
		entryItems.add(new EntryItem("webkit", EntryFragment.class, webkitItems));
		
		List<EntryItem> widgetItems = new ArrayList<EntryItem>();
		widgetItems.add(new EntryItem("CheckAdapter", CheckAdapterSampleFragment.class));
		widgetItems.add(new EntryItem("DepthPageTransformer", null));
		widgetItems.add(new EntryItem("NestedGridView", null));
		widgetItems.add(new EntryItem("NestedListView", null));
		widgetItems.add(new EntryItem("ToastUtils", null));
		widgetItems.add(new EntryItem("ViewAdapter", null));
		widgetItems.add(new EntryItem("ZoomOutPageTransformer", null));
		entryItems.add(new EntryItem("widget", EntryFragment.class, widgetItems));
		
		Fragment fragment = new EntryFragment();
		Bundle bundle = new Bundle();
		bundle.putString(EntryFragment.PARAM_STRING_JSON_ITEMS, new Gson().toJson(entryItems));
		fragment.setArguments(bundle);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.frame_main_content, fragment);
		transaction.commitAllowingStateLoss();
	}
}
