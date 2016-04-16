package blueteam.mypantry.ui.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import blueteam.mypanty.R;

public class btView_ProductDetails extends Activity {
    @Override
    protected void onCreate( Bundle saved_state ) {
        super.onCreate(saved_state);
        setContentView(R.layout.ui_product_detail);
    }

    private Button ButtonAdd = null;
    private Button ButtonRemove = null;
    private EditText EditTextPerishDate = null;
    private EditText ExitTextProductQuantity = null;
    private EditText EditTextProductName = null;
    private CheckBox CheckBoxPerishable = null;
    private LinearLayout LinearLayoutNav = null;
}
