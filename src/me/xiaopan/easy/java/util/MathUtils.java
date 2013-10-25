
package me.xiaopan.easy.java.util;

/**
 * 数学相关工具箱
 */
public class MathUtils {
	/**
	 * 勾股定理
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static final double pythagoreanProposition(double value1, double value2){
		return Math.sqrt((value1 * value1) + (value2 * value2));
	}
}