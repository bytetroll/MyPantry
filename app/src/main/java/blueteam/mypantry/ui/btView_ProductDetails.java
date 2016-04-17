package blueteam.mypantry.ui;

import android.app.Activity;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import blueteam.mypanty.R;

public class btView_ProductDetails extends Activity {
    @Override
    protected void onCreate( Bundle SavedState ) {
        super.onCreate( SavedState );
        setContentView( R.layout.btui_view_product_details );

        ButtonAdd = (Button)findViewById( R.id.ButtonAdd );
        ButtonAdd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View CallingView ) {

            }
        } );

        ButtonDelete = (Button)findViewById( R.id.ButtonDelete );

        EditTextProductName = (EditText)findViewById( R.id.EditTextProductName );
        EditTextProductQuantity = (EditText)findViewById( R.id.EditTextProductQuantity );

        CheckBoxPerishable = (CheckBox)findViewById( R.id.CheckBoxProductPerishable );
        CheckBoxPerishable.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged( CompoundButton ButtonView, boolean Checked ) {
                if( Checked ) {
                    EditTextPerishDate.setVisibility( View.VISIBLE );
                } else {
                    EditTextPerishDate.setVisibility( View.INVISIBLE );
                }
            }
        } );

        EditTextPerishDate = (EditText)findViewById( R.id.EditTextProductPerishDate );

        // Insert Checkbox restore code here.  Otherwise first check sends the wrong signal
        // to the event handlers.

        EditTextProductPurhcaseDate = (EditText)findViewById( R.id.EditTextProductPurchaseDate );
    }

    private Button ButtonAdd = null;
    private Button ButtonDelete = null;

    private EditText EditTextPerishDate = null;
    private EditText EditTextProductQuantity = null;
    private EditText EditTextProductName = null;
    private EditText EditTextProductPurhcaseDate = null;

    private CheckBox CheckBoxPerishable = null;

    private LinearLayout LinearLayoutNav = null;
}
