package com.vj.pokemon.scene.pokehome


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.vj.pokemon.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class PokeHomeActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(PokeHomeActivity::class.java)

    @Test
    fun pokeHomeActivityTest() {

        //progress dialog is now shown
        Thread.sleep(3000);

        val recyclerView = onView(withId(R.id.rvPokemon)).check(matches(isDisplayed()))

//        val recyclerView = onView(
//            allOf(
//                withId(R.id.rvPokemon),
//                childAtPosition(
//                    withId(R.id.activity_pokemon),
//                    0
//                )
//            )
//        )


        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(5, click()))

        //progress dialog is now shown
        Thread.sleep(3000);

        val textView = onView(
            allOf(
                withId(R.id.titleTextView), withText("charizard"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("charizard")))

        val textView2 = onView(
            allOf(
                withId(R.id.subTitleTextView),
                withText("Height: 17 Metre\n\nWeight: 905 Kilogram\n\nBase experience: 240"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Height: 17 Metre\n\nWeight: 905 Kilogram\n\nBase experience: 240")))

        val appCompatButton = onView(
            allOf(
                withId(R.id.bottomAlertDialogButton),
                withText("OK"),
                withContentDescription("bottomAlertDialogButton_0"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.baseBottomAlertDialogRecyclerView),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
