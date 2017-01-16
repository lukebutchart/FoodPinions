package com.stand_still.foodpinions.classes;

import android.content.Context;
import android.widget.EditText;


public class ExtEditText extends EditText {

    private boolean isMandatory = false;

    public ExtEditText(Context context) {
        super(context);
    }

    public void setMandatory(boolean setting){
        isMandatory = setting;
    }

    public boolean getMandatory(){
        return isMandatory;
    }
}
