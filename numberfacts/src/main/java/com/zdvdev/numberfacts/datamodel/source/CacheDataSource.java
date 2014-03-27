package com.zdvdev.numberfacts.datamodel.source;

import android.support.v4.util.LruCache;
import com.zdvdev.numberfacts.async.OnJobStatusChangedListener;

/**
 * Created with Android Studio.
 *
 * @author Shyish
 *         Date: 27/03/14
 */
public class CacheDataSource {

	private static final int CACHE_SIZE = 4 * 1024 * 1024; // 4MB
	static final LruCache<String, Object> REQUEST_CACHE = new LruCache<String, Object>(CACHE_SIZE);

	public static Object get(String tag){
		return REQUEST_CACHE.get(tag);
	}

	public static class CachedOnJobFinishedListenerWrapper<T> implements OnJobStatusChangedListener<T> {
		private final String mTag;
		private final OnJobStatusChangedListener mListener;

		public CachedOnJobFinishedListenerWrapper(String tag, OnJobStatusChangedListener listener) {
			mTag = tag;
			mListener = listener;}

		@Override public void onCompleted(T result) {
			mListener.onCompleted(result);
			REQUEST_CACHE.put(mTag, result);
		}

		@Override public void onUpdate(int progress) {
			mListener.onUpdate(progress);
		}

		@Override public void onError(Throwable throwable) {
			mListener.onError(throwable);
		}
	}
}
