package me.xiaopan.easy.java.util;

import java.util.Collection;
import java.util.LinkedList;

/**
 * 这是一个圆圈
 * @param <T>
 */
public class CircleList<E> extends LinkedList<E>{
	private static final long serialVersionUID = 1L;
	/**
	 * 最大容量
	 */
	private int maxSize;
	
	/**
	 * 创建一个圆圈，同时你必须指定它的最大容量
	 * @param maxSize 最大容量
	 */
	public CircleList(int maxSize){
		setMaxSize(maxSize);
	}
	
	private void check(int addSize){
		if((size()+addSize) > maxSize){
			for(int w =0, size = ((size()+addSize) - maxSize);w < size; w++){
				poll();
			}
		}
	}
	
	public boolean isFull(){
		return size() >= maxSize;
	}
	
	@Override
	public void addFirst(E e) {
		if(e != null){
			check(1);
		}
		super.addFirst(e);
	}

	@Override
	public void addLast(E e) {
		if(e != null){
			check(1);
		}
		super.addLast(e);
	}

	@Override
	public boolean add(E e) {
		if(e != null){
			check(1);
		}
		return super.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new IllegalAccessError("此方法已经被抛弃");
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new IllegalAccessError("此方法已经被抛弃");
	}

	@Override
	public void add(int index, E element) {
		if(element != null){
			check(1);
		}
		super.add(index, element);
	}

	/**
	 * 获取最大容量
	 * @return 最大容量
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * 设置最大容量
	 * @param maxSize 最大容量
	 */
	public void setMaxSize(int maxSize) {
		if(maxSize < 1){
			throw new IllegalArgumentException("maxSize not less than 1");
		}
		this.maxSize = maxSize;
		check(0);
	}
}