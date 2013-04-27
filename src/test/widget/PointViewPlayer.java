package test.widget;

import me.xiaopan.androidlibrary.widget.ViewPlayer;
import android.content.Context;
import android.util.AttributeSet;

public class PointViewPlayer extends ViewPlayer {
	public PointViewPlayer(Context context) {
		super(context);
		setPlayerIndicator(new PointPlayIndicator(context));
	}
	
	public PointViewPlayer(Context context, AttributeSet attrs) {
		super(context, attrs);
		setPlayerIndicator(new PointPlayIndicator(context));
	}
}