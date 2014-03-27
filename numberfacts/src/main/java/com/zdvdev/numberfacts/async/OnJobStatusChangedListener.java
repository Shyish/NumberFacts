package com.zdvdev.numberfacts.async;

/**
 * Created with Android Studio.
 *
 * @author Shyish
 *         Date: 27/03/14
 */
public interface OnJobStatusChangedListener<T>  {
    void onCompleted(T t);
    void onUpdate(int progress);
    void onError(Throwable throwable);
}
