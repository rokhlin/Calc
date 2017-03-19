package custom.selfapps.rav.calc.utils;


import android.animation.AnimatorListenerAdapter;
import android.view.View;

public class Animator {
    /**
     * Animation changing status of the View
     * @param view which need to change visibility
     * @param visibility to this value GONE or VISIBLE
     */
    public static void animateShowHide(final View view, final int visibility) {
        float alfa = visibility == View.GONE ? 0f : 1f;
        view.animate()
                .alpha(alfa)
                .setDuration(150)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(android.animation.Animator animation) {
                        view.setVisibility(visibility);
                    }
                });
    }
}
