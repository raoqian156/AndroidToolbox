package me.xiaopan.android.toolbox.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.android.toolbox.sample.EntryFragment.EntryItem;
import me.xiaopan.android.toolbox.sample.android.widget.CheckAdapterSampleFragment;

public class MainActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }
        toolbar.setLogo(R.mipmap.ic_launcher);

		Fragment fragment = new EntryFragment();
		Bundle bundle = new Bundle();
		bundle.putString(EntryFragment.PARAM_STRING_JSON_ENTRY_ITEM, new Gson().toJson(generateMainEntryItem()));
		fragment.setArguments(bundle);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.frame_main_content, fragment);
		transaction.commitAllowingStateLoss();
	}

    private EntryItem generateMainEntryItem(){
        List<EntryItem> entryItems = new ArrayList<EntryItem>();

        entryItems.add(generateAppEntry());
        entryItems.add(generateContentEntry());
        entryItems.add(generateContentPmEntry());
        entryItems.add(generateContentResEntry());
        entryItems.add(generateGraphicsEntry());
        entryItems.add(generateGraphicsDrawableEntry());
        entryItems.add(generateHardwareEntry());
        entryItems.add(generateHardwareCameraEntry());
        entryItems.add(generateNetEntry());
        entryItems.add(generateOsEntry());
        entryItems.add(generateOsStorageEntry());
        entryItems.add(generatePreferenceEntry());
        entryItems.add(generateProviderEntry());
        entryItems.add(generateUtilEntry());
        entryItems.add(generateViewEntry());
        entryItems.add(generateViewAnimationEntry());
        entryItems.add(generateViewInputmethodEntry());
        entryItems.add(generateWebkitEntry());
        entryItems.add(generateWidgetEntry());

        return new EntryItem(getResources().getString(R.string.app_name), null, entryItems);
    }

    private EntryItem generateAppEntry(){
        List<EntryItem> appItems = new ArrayList<EntryItem>();
        appItems.add(new EntryItem("DialogUtils", null));
        appItems.add(new EntryItem("DownloadManagerUtils", null));
        appItems.add(new EntryItem("FragmentBuilder", null));
        appItems.add(new EntryItem("FragmentListPagerAdapter", null));
        appItems.add(new EntryItem("FragmentUtils", null));
        appItems.add(new EntryItem("MessageDialogFragment", null));
        appItems.add(new EntryItem("ProgressDialogFragment", null));
        appItems.add(new EntryItem("SimpleFragmentPagerAdapter", null));
        appItems.add(new EntryItem("SimpleFragmentStatePagerAdapter", null));
        return new EntryItem("app", EntryFragment.class, appItems);
    }

    private EntryItem generateContentEntry(){
        List<EntryItem> contentItems = new ArrayList<EntryItem>();
        contentItems.add(new EntryItem("BroadcastUtils", null));
        contentItems.add(new EntryItem("ContentUtils", null));
        contentItems.add(new EntryItem("FileUtils", null));
        contentItems.add(new EntryItem("IntentUtils", null));
        contentItems.add(new EntryItem("LaunchAppReceiver", null));
        contentItems.add(new EntryItem("UriUtils", null));
        return new EntryItem("content", EntryFragment.class, contentItems);
    }

    private EntryItem generateContentPmEntry(){
        List<EntryItem> contentPmItems = new ArrayList<EntryItem>();
        contentPmItems.add(new EntryItem("VersionManager", null));
        return new EntryItem("content.pm", EntryFragment.class, contentPmItems);
    }

    private EntryItem generateContentResEntry(){
        List<EntryItem> contentResItems = new ArrayList<EntryItem>();
        contentResItems.add(new EntryItem("AssetUtils", null));
        contentResItems.add(new EntryItem("DimenUtils", null));
        return new EntryItem("content.res", EntryFragment.class, contentResItems);
    }

    private EntryItem generateGraphicsEntry(){
        List<EntryItem> graphicsItems = new ArrayList<EntryItem>();
        graphicsItems.add(new EntryItem("BitmapDecoder", null));
        graphicsItems.add(new EntryItem("BitmapUtils", null));
        graphicsItems.add(new EntryItem("Colors", null));
        graphicsItems.add(new EntryItem("ImageProcessor", null));
        graphicsItems.add(new EntryItem("RectUtils", null));
        graphicsItems.add(new EntryItem("TextUtils", null));
        return new EntryItem("graphics", EntryFragment.class, graphicsItems);
    }

    private EntryItem generateGraphicsDrawableEntry(){
        List<EntryItem> graphicsDrawableItems = new ArrayList<EntryItem>();
        graphicsDrawableItems.add(new EntryItem("DrawableLevelController", null));
        return new EntryItem("graphics.drawable", EntryFragment.class, graphicsDrawableItems);

    }

    private EntryItem generateHardwareEntry(){
        List<EntryItem> hardwareItems = new ArrayList<EntryItem>();
        hardwareItems.add(new EntryItem("DeviceUtils", null));
        return new EntryItem("hardware", EntryFragment.class, hardwareItems);
    }

    private EntryItem generateHardwareCameraEntry(){
        List<EntryItem> hardwareCameraItems = new ArrayList<EntryItem>();
        hardwareCameraItems.add(new EntryItem("BasePreviewAndPictureSizeCalculator", null));
        hardwareCameraItems.add(new EntryItem("BestPreviewSizeCalculator", null));
        hardwareCameraItems.add(new EntryItem("CameraManager", null));
        hardwareCameraItems.add(new EntryItem("CameraUtils", null));
        hardwareCameraItems.add(new EntryItem("LoopFocusManager", null));
        return new EntryItem("hardware.camera", EntryFragment.class, hardwareCameraItems);
    }

    private EntryItem generateNetEntry(){
        List<EntryItem> netItems = new ArrayList<EntryItem>();
        netItems.add(new EntryItem("NetworkUtils", null));
        return new EntryItem("net", EntryFragment.class, netItems);
    }

    private EntryItem generateOsEntry(){
        List<EntryItem> osItems = new ArrayList<EntryItem>();
        osItems.add(new EntryItem("OSUtils", null));
        osItems.add(new EntryItem("SDCardUtils", null));
        osItems.add(new EntryItem("StatFsCompat", null));
        return new EntryItem("os", EntryFragment.class, osItems);
    }

    private EntryItem generateOsStorageEntry(){
        List<EntryItem> osItems = new ArrayList<EntryItem>();
        osItems.add(new EntryItem("StorageUtils", null));
        return new EntryItem("os.storage", EntryFragment.class, osItems);
    }

    private EntryItem generatePreferenceEntry(){
        List<EntryItem> preferenceItems = new ArrayList<EntryItem>();
        preferenceItems.add(new EntryItem("PreferencesUtils", null));
        return new EntryItem("preference", EntryFragment.class, preferenceItems);
    }

    private EntryItem generateProviderEntry(){
        List<EntryItem> providerItems = new ArrayList<EntryItem>();
        providerItems.add(new EntryItem("PhoneUtils", null));
        providerItems.add(new EntryItem("SettingsUtils", null));
        return new EntryItem("provider", EntryFragment.class, providerItems);
    }

    private EntryItem generateUtilEntry(){
        List<EntryItem> utilItems = new ArrayList<EntryItem>();
        utilItems.add(new EntryItem("ALog", null));
        utilItems.add(new EntryItem("Countdown", null));
        utilItems.add(new EntryItem("DoubleClickExitDetector", null));
        utilItems.add(new EntryItem("InputVerifyUtils", null));
        utilItems.add(new EntryItem("LoopTimer", null));
        utilItems.add(new EntryItem("MessageDigestUtils", null));
        utilItems.add(new EntryItem("OtherUtils", null));
        utilItems.add(new EntryItem("RebootThreadExceptionHandler", null));
        return new EntryItem("util", EntryFragment.class, utilItems);
    }

    private EntryItem generateViewEntry(){
        List<EntryItem> viewItems = new ArrayList<EntryItem>();
        viewItems.add(new EntryItem("ViewListPagerAdapter", null));
        viewItems.add(new EntryItem("ViewRefreshHandler", null));
        viewItems.add(new EntryItem("ViewUtils", null));
        viewItems.add(new EntryItem("WindowUtils", null));
        return new EntryItem("view", EntryFragment.class, viewItems);
    }

    private EntryItem generateViewAnimationEntry(){
        List<EntryItem> viewanimationItems = new ArrayList<EntryItem>();
        viewanimationItems.add(new EntryItem("AnimationUtils", null));
        viewanimationItems.add(new EntryItem("ViewAnimationUtils", null));
        return new EntryItem("view.animation", EntryFragment.class, viewanimationItems);

    }

    private EntryItem generateViewInputmethodEntry(){
        List<EntryItem> viewInputmethodItems = new ArrayList<EntryItem>();
        viewInputmethodItems.add(new EntryItem("InputMethodUtils", null));
        return new EntryItem("view.inputmethod", EntryFragment.class, viewInputmethodItems);
    }

    private EntryItem generateWebkitEntry(){
        List<EntryItem> webkitItems = new ArrayList<EntryItem>();
        webkitItems.add(new EntryItem("WebViewManager", null));
        return new EntryItem("webkit", EntryFragment.class, webkitItems);
    }

    private EntryItem generateWidgetEntry(){
        List<EntryItem> widgetItems = new ArrayList<EntryItem>();
        widgetItems.add(new EntryItem("CheckAdapter", CheckAdapterSampleFragment.class));
        widgetItems.add(new EntryItem("DepthPageTransformer", null));
        widgetItems.add(new EntryItem("NestedGridView", null));
        widgetItems.add(new EntryItem("NestedListView", null));
        widgetItems.add(new EntryItem("ToastUtils", null));
        widgetItems.add(new EntryItem("ViewAdapter", null));
        widgetItems.add(new EntryItem("ZoomOutPageTransformer", null));
        return new EntryItem("widget", EntryFragment.class, widgetItems);
    }
}
