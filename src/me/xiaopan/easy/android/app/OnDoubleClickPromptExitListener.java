package me.xiaopan.easy.android.app;

/**
 * 双击提示退出程序监听器
 */
public interface OnDoubleClickPromptExitListener {
	/**
	 * 第一击之后会执行此方法来提示用户
	 */
	public void onPrompt();
}