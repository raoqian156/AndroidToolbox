package test.widget;

import me.xiaopan.easyandroid.widget.ViewPlayer;
import android.content.Context;
import android.util.AttributeSet;

public class PointViewPlayer extends ViewPlayer {
	public PointViewPlayer(Context context) {
		super(context);
		setViewPlayIndicator(new PointPlayIndicator(context));
	}
	
	public PointViewPlayer(Context context, AttributeSet attrs) {
		super(context, attrs);
		setViewPlayIndicator(new PointPlayIndicator(context));
	}
}