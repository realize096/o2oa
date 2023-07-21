package com.x.query.assemble.surface;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.x.base.core.project.Context;
import com.x.base.core.project.cache.CacheManager;
import com.x.query.assemble.surface.queue.QueueImportData;

public class ThisApplication {

	private ThisApplication() {
		// nothing
	}

	private static ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
			new ThreadFactoryBuilder().setNameFormat(ThisApplication.class.getPackageName() + "-threadpool-%d")
					.build());

	public static ExecutorService threadPool() {
		return threadPool;
	}

	protected static Context context;

	public static void setContext(Context context) {
		ThisApplication.context = context;
	}

	public static final QueueImportData queueImportData = new QueueImportData();

	public static Context context() {
		return context;
	}

	public static void init() {
		try {
			CacheManager.init(context.clazz().getSimpleName());
			context().startQueue(queueImportData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void destroy() {
		try {
			threadPool.shutdown();
			CacheManager.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
