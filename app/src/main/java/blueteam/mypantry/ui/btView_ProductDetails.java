package blueteam.mypantry.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import blueteam.mypanty.R;

public class btView_ProductDetails extends Activity {
    @Override
    protected void onCreate( Bundle SavedState ) {
        super.onCreate( SavedState );
        setContentView( R.layout.btui_view_product_details );
    }

    private Button ButtonAdd = null;
    private Button ButtonRemove = null;
    private EditText EditTextPerishDate = null;
    private EditText ExitTextProductQuantity = null;
    private EditText EditTextProductName = null;
    private CheckBox CheckBoxPerishable = null;
    private LinearLayout LinearLayoutNav = null;
}
