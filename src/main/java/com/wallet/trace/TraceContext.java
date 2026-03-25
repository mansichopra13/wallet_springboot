package com.wallet.trace;

public class TraceContext {
	private static final ThreadLocal<String> traceId = new ThreadLocal<>();
	private static final ThreadLocal<Long> startTime = new ThreadLocal<>();
	
	public static void setTraceId(String id) {
		traceId.set(id);
	}
	
	public static String getTraceId() {
		return traceId.get();
	}
	
	public static void setStartTime(Long time) {
		startTime.set(time);
	}
	
	public static Long getStartTime() {
		return startTime.get();
	}
	
	public static void clear() {
		traceId.remove();
		startTime.remove();
	}
}
