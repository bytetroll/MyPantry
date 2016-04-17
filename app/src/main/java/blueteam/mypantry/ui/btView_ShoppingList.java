package blueteam.mypantry.ui;

import android.app.Activity;
import android.os.Bundle;

import blueteam.mypanty.R;

/**
 * Created by nathan on 4/16/16.
 */
public class btView_ShoppingList extends Activity {
    @Override
    protected void onCreate( Bundle SavedInstanceState ) {
        super.onCreate( SavedInstanceState );
        setContentView( R.layout.btui_view_pantry );
    }
}
