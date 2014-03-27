package com.zdvdev.numberfacts.async;

import rx.Observer;

import java.lang.ref.WeakReference;

/**
 * Created with Android Studio.
 *
 * @author Shyish
 *         Date: 27/03/14
 */
public class CustomObserver<T> implements Observer<T> {

	public WeakReference<OnJobStatusChangedListener<T>> observer;

	public CustomObserver(OnJobStatusChangedListener<T> observer) {
		this.observer = new WeakReference<OnJobStatusChangedListener<T>>(observer);
	}

	public void onCompleted(T o) {
		if (observer != null && observer.get() != null) {
			// treat internal errors (if applicable)
			observer.get().onCompleted(o);
		}
	}

	@Override public void onCompleted() {}

	@Override
	public void onError(Throwable throwable) {
		if (observer != null && observer.get() != null) {
			// treat internal errors (if applicable)
			observer.get().onError(throwable);
		}
	}

	@Override
	public void onNext(T o) {
		if (observer != null && observer.get() != null) {
			// treat internal errors (if applicable)
			observer.get().onCompleted(o);
		}
	}

	public void onUpdate(int progress) {
		if (observer != null && observer.get() != null) {
			observer.get().onUpdate(progress);
		}
	}
}