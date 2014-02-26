package me.xiaopan.java.easy.util;

/**
 * 秒表
 */
public class Stopwatch {
	private long firstMillis;	//第一次的时间
	private long lastFirstMillis;	//上一次的时间
	private Lap count;
	private long newMillis;
	
	public Stopwatch(){
		firstMillis = System.currentTimeMillis();
		lastFirstMillis = firstMillis;
	}
	
	/**
	 * 计次，返回上一次计次到当前的间隔时间和总用时
	 * @return
	 */
	public Lap lap(){
		newMillis = System.currentTimeMillis();
		count = new Lap(newMillis - firstMillis, newMillis - lastFirstMillis);
		lastFirstMillis = newMillis;
		return count;
	}
	
	/**
	 * 重置
	 */
	public void reset(){
		firstMillis = System.currentTimeMillis();
		lastFirstMillis = firstMillis;
	}
	
	/**
	 * 计次
	 */
	public class Lap{
		private long intervalMillis;
		private long useMillis;
		
		public Lap(long useMillis, long intervalMillis){
			this.useMillis = useMillis;
			this.intervalMillis = intervalMillis;
		}
		
		/**
		 * 获取距离上一次计次的间隔时间
		 * @return
		 */
		public long getIntervalMillis() {
			return intervalMillis;
		}
		
		/**
		 * 获取当前总用时
		 * @return
		 */
		public long getUseMillis() {
			return useMillis;
		}
	}
}