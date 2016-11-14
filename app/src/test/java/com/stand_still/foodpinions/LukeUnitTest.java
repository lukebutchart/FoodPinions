package com.stand_still.foodpinions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.Context;
import android.content.SharedPreferences;

import com.stand_still.foodpinions.activities.MainActivity;
import com.stand_still.foodpinions.classes.DatabaseHandler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(MockitoJUnitRunner.class)
public class LukeUnitTest {


//    @Mock
//    Context mMockContext;
//
//    @Test
//    public void test1() throws Exception {
//
//        DatabaseHandler databaseHandler = new DatabaseHandler(mMockContext);
//
//        boolean testBool = databaseHandler.getAllFoodPinions().size() > 0;
//
//        assertThat("reason", testBool);
//
//
//        // Given a mocked Context injected into the object under test...
//        when(mMockContext.getString(R.string.hello_word))
//                .thenReturn(FAKE_STRING);
//        ClassUnderTest myObjectUnderTest = new ClassUnderTest(mMockContext);
//
//        // ...when the string is returned from the object under test...
//        String result = myObjectUnderTest.getHelloWorldString();
//
//        // ...then the result should be the expected one.
//        assertThat(result, is(FAKE_STRING));
//    }
}