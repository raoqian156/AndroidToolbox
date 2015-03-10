package me.xiaopan.android.toolbox.sample;

import android.support.v4.app.Fragment;
import android.util.Log;

public class MyFragment extends Fragment {
    private String pageName;

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageName() {
        return pageName;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(getUserVisibleHint()){
            onVisibilityChangedToUser(true, false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if(getUserVisibleHint()){
            onVisibilityChangedToUser(false, false);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isResumed()){
            onVisibilityChangedToUser(isVisibleToUser, true);
        }
    }

    /**
     * 当Fragment对用户的可见性发生了改变的时候就会回调此方法
     * @param isVisibleToUser true：用户能看见当前Fragment；false：用户看不见当前Fragment
     * @param isHappenedInSetUserVisibleHintMethod true：本次回调发生在setUserVisibleHintMethod方法里；false：发生在onResume或onPause方法里
     */
    protected void onVisibilityChangedToUser(boolean isVisibleToUser, boolean isHappenedInSetUserVisibleHintMethod){
        if(isVisibleToUser){
            if(pageName != null){
                Log.i("UmengPageTrack", pageName + " - display - " + (isHappenedInSetUserVisibleHintMethod ? "setUserVisibleHint" : "onResume"));
            }
        }else{
            if(pageName != null){
                Log.w("UmengPageTrack", pageName + " - hidden - "+(isHappenedInSetUserVisibleHintMethod?"setUserVisibleHint":"onPause"));
            }
        }
    }
}
