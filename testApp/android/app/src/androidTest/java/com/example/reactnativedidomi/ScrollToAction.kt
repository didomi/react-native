package com.example.reactnativedidomi

import android.graphics.Rect
import android.util.Log
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.util.HumanReadables
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher

class ScrollToAction : ViewAction {
    override fun getConstraints(): Matcher<View> {
        return CoreMatchers.allOf(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE), isDescendantOfA(CoreMatchers.anyOf(
                ViewMatchers.isAssignableFrom(ScrollView::class.java),
                ViewMatchers.isAssignableFrom(HorizontalScrollView::class.java),
                ViewMatchers.isAssignableFrom(NestedScrollView::class.java)))
        )
    }

    override fun perform(uiController: UiController, view: View) {
        if (isDisplayingAtLeast(80).matches(view)) {
            Log.i(TAG, "View is already displayed. Returning.")
            return
        }
        val rect = Rect()
        view.getDrawingRect(rect)
        if (!view.requestRectangleOnScreen(rect, true /* immediate */)) {
            Log.w(TAG, "Scrolling to view was requested, but none of the parents scrolled.")
        }
        uiController.loopMainThreadUntilIdle()
        if (!isDisplayingAtLeast(80).matches(view)) {
            throw PerformException.Builder()
                    .withActionDescription(this.description)
                    .withViewDescription(HumanReadables.describe(view))
                    .withCause(RuntimeException(
                            "Scrolling to view was attempted, but the view is not displayed"))
                    .build()
        }
    }

    override fun getDescription(): String {
        return "scroll to"
    }

    companion object {
        private val TAG = ScrollToAction::class.java.simpleName
        fun betterScrollTo(): ViewAction {
            return ViewActions.actionWithAssertions(ScrollToAction())
        }
    }
}
