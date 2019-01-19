package project.navigation.Listeners;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by VINCENT on 06/01/2019.
 *
 */
public abstract class AfterTextChangedWatcher implements TextWatcher {

    @Override
    public abstract void afterTextChanged(Editable s);


    @Override
    public final void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public final void onTextChanged(CharSequence s, int start, int before, int count) {
    }

}
