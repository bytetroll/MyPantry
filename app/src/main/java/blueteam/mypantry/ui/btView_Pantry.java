package blueteam.mypantry.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import android.widget.TextView;

import blueteam.mypantry.core.btInventoryHandler;
import blueteam.mypantry.core.btProduct;
import blueteam.mypantry.ui.adapters.btListViewAdapterData;
import blueteam.mypantry.ui.helpers.btActivityHelpers;
import blueteam.mypantry.ui.helpers.btActivityPersistence;
import blueteam.mypanty.R;
import blueteam.mypantry.ui.adapters.btListViewAdapter;

public class btView_Pantry extends Activity {
    @Override
    protected void onCreate( Bundle SavedInstanceState ) {
        super.onCreate( SavedInstanceState );
        setContentView( R.layout.btui_view_pantry );

        TextViewToHome = (TextView)findViewById( R.id.TextViewPantryToHome );
        TextViewToHome.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View CallingView ) {
                Intent NewIntent = new Intent( CallingView.getContext(), btView_Home.class );
                startActivity( NewIntent );
            }
        } );

        ListViewPantryContents = (ListView)findViewById( R.id.PantryList );
        Adapter = new btListViewAdapter( this, PantryContents );
        ListViewPantryContents.setAdapter( Adapter );

        for( btProduct Product : btInventoryHandler.PantryContents() ) {
            btListViewAdapterData ProductData = new btListViewAdapterData( Product.Name, String.valueOf( Product.Quantity ) );
            PantryContents.add( ProductData );
        }

        // Force dynamic reload.
        Adapter.notifyDataSetChanged();

        ListViewPantryContents.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView< ? > Parent, View CallingView, int Position, long ID ) {
                btActivityPersistence.AddKeyValuePair( "SelectedPantryItem", Position );
                btActivityHelpers.SwitchView( CallingView.getContext(), btView_ProductDetails.class );
            }
        } );

        ListViewPantryContents.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick( AdapterView<?> Parent, View CallingView, int Position, long ID ) {
                btListViewAdapterData Data = (btListViewAdapterData)ListViewPantryContents.getItemAtPosition( Position );
                btInventoryHandler.RemoveProductFromPantry( Data.Description );
                Adapter.Remove( Position );
                Adapter.notifyDataSetChanged();

                return false;
            }
        } );
    }

    private ArrayList< btListViewAdapterData > PantryContents = new ArrayList<>();
    private btListViewAdapter Adapter;

    private TextView TextViewToHome = null;
    private ListView ListViewPantryContents = null;

}
