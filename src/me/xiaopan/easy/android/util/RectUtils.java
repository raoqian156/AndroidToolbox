/*
 * Copyright 2013 Peng fei Pan
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.xiaopan.easy.android.util;

import android.graphics.Point;
import android.graphics.Rect;

/**
 * 矩形操作工具箱
 */
public class RectUtils {
	/**
	 * 映射矩形，将源尺寸中的一个矩形映射到目标尺寸中
	 * @param sourceSize 源尺寸
	 * @param rectIntSourceSize 源尺寸中的一个矩形
	 * @param targetSize 目标尺寸
	 * @param isTargetSizeInterchange 是否将targetSize宽高互换
	 * @return 源尺寸中的矩形映射到目标尺寸中后的矩形
	 */
	public static Rect mappingRect(Point sourceSize, Rect rectIntSourceSize, Point targetSize, boolean isTargetSizeInterchange){
		Rect finalRect = new Rect(rectIntSourceSize);
		if(isTargetSizeInterchange){
			targetSize.x = targetSize.x + targetSize.y;
			targetSize.y = targetSize.x - targetSize.y;
			targetSize.x = targetSize.x - targetSize.y;
		}
		float xScale = (float) targetSize.x / sourceSize.x;
		float yScale = (float) targetSize.y / sourceSize.y;
		finalRect.left *= xScale;
		finalRect.top *= yScale;
		finalRect.right *= xScale;
		finalRect.bottom *= yScale;
		return finalRect;
	}
	
	/**
	 * 映射矩形，将源尺寸中的一个矩形映射到目标尺寸中
	 * @param sourceSize 源尺寸
	 * @param rectIntSourceSize 源尺寸中的一个矩形
	 * @param targetSize 目标尺寸
	 * @return 源尺寸中的矩形映射到目标尺寸中后的矩形
	 */
	public static Rect mappingRect(Point sourceSize, Rect rectIntSourceSize, Point targetSize){
		return mappingRect(sourceSize, rectIntSourceSize, targetSize, false);
	}
}