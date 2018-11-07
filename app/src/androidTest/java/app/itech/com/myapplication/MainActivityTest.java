package app.itech.com.myapplication;

import android.app.Activity;
import android.support.annotation.CheckResult;
import android.support.test.espresso.AmbiguousViewMatcherException;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.FailureHandler;
import android.support.test.espresso.NoMatchingRootException;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.core.AllOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Map;
import java.util.logging.Logger;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.*;



@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mMain = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void ListViewPositionClick(){

        delay(2000);

     onData(anything())
                .inAdapterView(withId(R.id.listView))
                .atPosition(5)
                .perform(click());


        onView(withText(startsWith("postion:"))).
                inRoot(withDecorView(
                        not(is(mMain.getActivity().
                                getWindow().getDecorView())))).
                check(matches(isDisplayed()));



        delay(2000);
    }


    @Test
    public void ListItemTextClick(){

        delay(2000);
        ListView listView = (ListView)mMain.getActivity().findViewById(R.id.listView);

        /*here total item is 23 */
        /*how may list are availble in the list*/
        assertThat(listView.getCount(),is(23));

        delay(2000);
    }

    @Test
    public void showTextView(){

        delay(2000);
        onView(withId(R.id.editText))
                .perform(setTextInTextView("my text"));

        mMain.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mMain.getActivity(), "shoew message", Toast.LENGTH_SHORT).show();
            }
        });



        delay(2000);
    }

    public void toast(String message)
    {
        Toast toast = new Toast(mMain.getActivity());
        View view = LayoutInflater.from(mMain.getActivity()).inflate(R.layout.custom_toast, null);
        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText(message);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    /*set text view in textView */

    public static ViewAction setTextInTextView(final String value){
        return new ViewAction() {
            @SuppressWarnings("unchecked")
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(TextView.class));
//                                            ^^^^^^^^^^^^^^^^^^^
// To check that the found view is TextView or it's subclass like EditText
// so it will work for TextView and it's descendants
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((TextView) view).setText(value);
            }

            @Override
            public String getDescription() {
                return "replace text";
            }
        };
    }


    @Test
    public void testTheItemNameOfTheList() throws Exception{

        delay(1000);

        ListView listView = (ListView)mMain.getActivity().findViewById(R.id.listView);

        /*check item string of the specific position of the list*/
        assertThat(listView.getItemAtPosition(4), CoreMatchers.<Object>is("WebOS"));

    /*    onView(withText(startsWith("postion:"))).
                inRoot(withDecorView(
                        not(is(mMain.getActivity().
                                getWindow().getDecorView())))).
                check(matches(isDisplayed()));
*/
        /* casting String */
      //   assertThat((String)listView.getItemAtPosition(1), is("Ubuntu"));

    }


    @Test
    public void ensureListViewIsPresent() throws Exception {
        delay(2000);
        onData(hasToString(containsString("iPhone"))).perform(click());
        onView(withText(startsWith("postion:"))).
                inRoot(withDecorView(
                        not(is(mMain.getActivity().
                                getWindow().getDecorView())))).
                check(matches(isDisplayed()));
        delay(2000);
    }


    @Test
    public void testCompareTest(){
        onData(allOf(is(instanceOf(Map.class)),
                hasEntry(equalTo(MainActivity.TEXT_ROW), is("Android"))));

    }

    @Test
    public void shouldShowToast() {
        ViewInteraction view = onView(withText("Click on this button")).perform(click());


        if(exists(view)){

            Toast toast = new Toast(mMain.getActivity());
            toast.setText("hello bangladesh");
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();

            view.perform((ViewAction) withId(R.id.editText)).check((ViewAssertion) click());
        }
        onView(withText("this is ")).inRoot(withDecorView(not(is(mMain.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    //start

//https://stackoverflow.com/questions/20807131/espresso-return-boolean-if-view-exists
    @CheckResult
    public static boolean exists(ViewInteraction interaction) {
        try {
            interaction.perform(new ViewAction() {
                @Override public Matcher<View> getConstraints() {
                    return any(View.class);
                }
                @Override public String getDescription() {
                    return "check for existence";
                }
                @Override public void perform(UiController uiController, View view) {
                    // no op, if this is run, then the execution will continue after .perform(...)
                }
            });
            return true;
        } catch (AmbiguousViewMatcherException ex) {
            // if there's any interaction later with the same matcher, that'll fail anyway
            return true; // we found more than one
        } catch (NoMatchingViewException ex) {
            return false;
        } catch (NoMatchingRootException ex) {
            // optional depending on what you think "exists" means
            return false;
        }
    }


    //end

    /*testing toast message show*/

    @Test
    public void ShowToastMessage() {

        try {
            onView(withText("my button")).check(matches(isDisplayed()));


            //view is displayed logic
        } catch (NoMatchingViewException e) {
            //view not displayed logic
        }



/*        try {
            onView(withText("my button")).check(matches(isDisplayed()));
            //view is displayed logic
        } catch (NoMatchingViewException e) {

            Log.e("probekasdf",e.toString());
            //view not displayed logic
        }*/

 /*       onView(withText("OK")).withFailureHandler(new FailureHandler() {
            @Override
            public void handle(Throwable error, Matcher<View> viewMatcher){
                onView(withId(R.id.editText))
                        .perform(setTextInTextView("True"));

            }
        }).check(matches(isDisplayed())).perform(click());
*/

   /*     if(onView(withText("click OK to Continue")).equals("sdfg")){
            Toast.makeText(mMain.getActivity(), "this test", Toast.LENGTH_SHORT).show();
            onView(withId(R.id.editText))
                    .perform(setTextInTextView("True"));
        } else {
            onView(withId(R.id.editText))
                    .perform(setTextInTextView("False"));
            Toast.makeText(mMain.getActivity(), "not nulll", Toast.LENGTH_SHORT).show();
        }*/

        delay(3000);
    }



    /* delay checking of this position */
    private void delay(long i) {

        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }






 /*   onData(allOf(is(instanceOf(String.class)), is("text")))
            .inAdapterView(withId(R.id.listView))
            .perform(click());*/

}