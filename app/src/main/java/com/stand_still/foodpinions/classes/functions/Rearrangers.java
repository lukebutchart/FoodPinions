package com.stand_still.foodpinions.classes.functions;

import android.view.View;
import android.widget.LinearLayout;

import com.stand_still.foodpinions.R;
import com.stand_still.foodpinions.exceptions.MissingListAndHeadersLayoutException;
import com.stand_still.foodpinions.exceptions.MissingSearchAndButtonLayoutException;

public class Rearrangers {
    void moveSearchToTop(LinearLayout mainActivity) throws MissingSearchAndButtonLayoutException, MissingListAndHeadersLayoutException {
        View child0 = mainActivity.getChildAt(0);
        View child1 = mainActivity.getChildAt(1);

        View searchAndButton = getSearchAndButtonLayout(child0, child1);
        View listAndHeaders = getListAndHeadersLayout(child0, child1);

        if (child0 != searchAndButton) {
            mainActivity.removeAllViews();

            mainActivity.addView(searchAndButton);
            mainActivity.addView(listAndHeaders);
        }
    }

    void moveListToTop(LinearLayout mainActivity) throws MissingSearchAndButtonLayoutException, MissingListAndHeadersLayoutException {
        View child0 = mainActivity.getChildAt(0);
        View child1 = mainActivity.getChildAt(1);

        View searchAndButton = getSearchAndButtonLayout(child0, child1);
        View listAndHeaders = getListAndHeadersLayout(child0, child1);

        mainActivity.removeAllViews();

        mainActivity.addView(listAndHeaders);
        mainActivity.addView(searchAndButton);
    }

    private View getSearchAndButtonLayout(View child0, View child1) throws MissingSearchAndButtonLayoutException {
        if (child0.getId() == R.id.search_and_button)
            return child0;
        else if (child1.getId() == R.id.search_and_button)
            return child1;
        else throw new MissingSearchAndButtonLayoutException();
    }

    private View getListAndHeadersLayout(View child0, View child1) throws MissingListAndHeadersLayoutException {
        if (child0.getId() == R.id.list_and_headers)
            return child0;
        else if (child1.getId() == R.id.list_and_headers)
            return child1;
        else throw new MissingListAndHeadersLayoutException();
    }

    private void moveToPageCenter(LinearLayout linearLayout, LinearLayout mainActivity) {
        float parentCenterX = mainActivity.getX() + mainActivity.getWidth() / 2;
        float parentCenterY = mainActivity.getY() + mainActivity.getHeight() / 2;
        int linearLayoutWidth = linearLayout.getWidth();
        int linearLayoutHeight = linearLayout.getHeight();

        float toCenterPageX = parentCenterX
                - linearLayoutWidth / 2;
        float toCenterPageY = parentCenterY
                - linearLayoutHeight / 2;

        linearLayout.animate().translationX(
                toCenterPageX
        ).translationY(
                toCenterPageY
        );
    }

    private void moveToPageMiddle(LinearLayout linearLayout, LinearLayout mainActivity) {
        float parentCenterY = mainActivity.getY() + mainActivity.getHeight() / 2;
        int linearLayoutHeight = linearLayout.getHeight();

        float toCenterPageY = parentCenterY
                - linearLayoutHeight / 2;

        linearLayout.animate().translationY(toCenterPageY);
    }

    private void moveToPageTop(LinearLayout linearLayout) {
        float toTopCenterPageY = 0;

        linearLayout.animate().translationY(toTopCenterPageY);
    }

    private void moveToPageTopCenter(LinearLayout linearLayout, LinearLayout mainActivity) {
        float parentCenterX = mainActivity.getX() + mainActivity.getWidth() / 2;

        float toCenterPageX = parentCenterX
                - linearLayout.getWidth() / 2;
        float toTopCenterPageY = 0;

        linearLayout.animate().translationX(toCenterPageX)
                .translationY(toTopCenterPageY);
    }

    private void moveXY(LinearLayout linearLayout, int x, int y) {
        linearLayout.animate().translationX(x).translationY(y);
    }
}
