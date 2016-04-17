package blueteam.mypantry.ui;

import android.app.Activity;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import blueteam.mypantry.core.btInventoryHandler;
import blueteam.mypantry.core.btProduct;
import blueteam.mypanty.R;

public class btView_ProductDetails extends Activity {
    @Override
    protected void onCreate( Bundle SavedState ) {
        super.onCreate( SavedState );
        setContentView( R.layout.btui_view_product_details );

        ButtonAdd = (Button)findViewById( R.id.ButtonAdd );
        ButtonDelete = (Button)findViewById( R.id.ButtonDelete );

        EditTextProductName = (EditText)findViewById( R.id.EditTextProductName );
        EditTextProductQuantity = (EditText)findViewById( R.id.EditTextProductQuantity );

        CheckBoxPerishable = (CheckBox)findViewById( R.id.CheckBoxProductPerishable );
        CheckBoxPerishable.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged( CompoundButton ButtonView, boolean Checked ) {
                if( Checked ) {
                    EditTextProductPerishDate.setVisibility( View.VISIBLE );
                } else {
                    EditTextProductPerishDate.setVisibility( View.INVISIBLE );
                }
            }
        } );

        EditTextProductPerishDate = (EditText)findViewById( R.id.EditTextProductPerishDate );

        // Insert Checkbox restore code here.  Otherwise first check sends the wrong signal
        // to the event handlers.

        EditTextProductPurchaseDate = (EditText)findViewById( R.id.EditTextProductPurchaseDate );

        EditTextProductCategory = (EditText)findViewById( R.id.EditTextProductCategory );
        EditTextProductPrice = (EditText)findViewById( R.id.EditTextProductPrice );

        ButtonAdd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View CallingView ) {
                btProduct Product = new btProduct();
                Product.Name = EditTextProductName.getText().toString();
                Product.Category = EditTextProductCategory.getText().toString();
                Product.Quantity = Integer.parseInt( EditTextProductQuantity.getText().toString() );
                Product.Perishable = CheckBoxPerishable.isChecked();
                Product.PerishDate = EditTextProductPerishDate.getText().toString();
                Product.Price = Float.parseFloat( EditTextProductPrice.getText().toString() );
                Product.PurchaseDate = EditTextProductPurchaseDate.getText().toString();

                btInventoryHandler.AddProductToPantry( Product );
            }
        } );
    }

    private Button ButtonAdd = null;
    private Button ButtonDelete = null;

    private EditText EditTextProductPerishDate = null;
    private EditText EditTextProductQuantity = null;
    private EditText EditTextProductName = null;
    private EditText EditTextProductPurchaseDate = null;
    private EditText EditTextProductCategory = null;
    private EditText EditTextProductPrice = null;

    private CheckBox CheckBoxPerishable = null;

    private LinearLayout LinearLayoutNav = null;
}
