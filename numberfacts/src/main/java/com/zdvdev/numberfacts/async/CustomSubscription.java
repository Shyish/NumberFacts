package com.zdvdev.numberfacts.async;

/**
 * Created with Android Studio.
 *
 * @author Shyish
 *         Date: 27/03/14
 */
public class CustomSubscription implements Subscription {
		private final rx.Subscription mSubscription;

		public CustomSubscription(rx.Subscription subscription) {
			this.mSubscription = subscription;
		}

		@Override
		public void unsubscribe() {
			if (mSubscription != null) {
				mSubscription.unsubscribe();
			}
		}
	}