package cz.kofron.foodinventory.client;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

/**
 * Created by kofee on 3/1/14.
 */
public class NowLikeListView extends ListView implements ViewTreeObserver.OnGlobalLayoutListener {

        public NowLikeListView(Context context, AttributeSet attrs) {
            super(context, attrs);
            setDividerHeight(0);
            initLayoutObserver();

        }

        public NowLikeListView(Context context) {
            super(context);
            initLayoutObserver();
        }

        private void initLayoutObserver() {
            getViewTreeObserver().addOnGlobalLayoutListener(this);
        }

        @Override
        public void onGlobalLayout() {
            getViewTreeObserver().removeGlobalOnLayoutListener(this);

            final int heightPx = getContext().getResources().getDisplayMetrics().heightPixels;

            boolean inversed = false;
            final int childCount = getChildCount();

            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);

                int[] location = new int[2];

                child.getLocationOnScreen(location);

                if (location[1] > heightPx) {
                    break;
                }

                if (!inversed) {
                    child.startAnimation(AnimationUtils.loadAnimation(getContext(),
                            R.anim.slide_up_left));
                } else {
                    child.startAnimation(AnimationUtils.loadAnimation(getContext(),
                            R.anim.slide_up_right));
                }

                inversed = !inversed;
            }

        }
}
