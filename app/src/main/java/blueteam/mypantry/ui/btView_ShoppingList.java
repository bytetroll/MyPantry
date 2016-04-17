package blueteam.mypantry.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import blueteam.mypantry.core.btInventoryHandler;
import blueteam.mypantry.core.btProduct;
import blueteam.mypantry.runtime.btLocalScopeAccessor;
import blueteam.mypantry.ui.adapters.btListViewAdapter;
import blueteam.mypantry.ui.adapters.btListViewAdapterData;
import blueteam.mypantry.ui.helpers.btActivityHelpers;
import blueteam.mypantry.ui.helpers.btActivityPersistence;
import blueteam.mypantry.ui.shared.btAddMethod;
import blueteam.mypanty.R;

import java.util.ArrayList;

public class btView_ShoppingList extends Activity {
    @Override
    protected void onCreate( Bundle SavedInstanceState ) {
        super.onCreate( SavedInstanceState );
        setContentView( R.layout.btui_view_shopping_list );

        TextViewToHome = (TextView)findViewById( R.id.TextViewShoppingListToHome );
        TextViewToHome.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View CallingView ) {
                Intent NewIntent = new Intent( CallingView.getContext(), btView_Home.class );
                startActivity( NewIntent );
            }
        } );

        ListViewShoppingListContents = (ListView)findViewById( R.id.ListViewShoppingListContents );
        Adapter = new btListViewAdapter( this, ShoppingListContents );
        ListViewShoppingListContents.setAdapter( Adapter );

        for( btProduct Product : btInventoryHandler.PantryContents() ) {
            btListViewAdapterData ProductData = new btListViewAdapterData( Product.Name, String.valueOf( Product.Quantity ) );
            ShoppingListContents.add( ProductData );
        }

        // Force dynamic reload.
        Adapter.notifyDataSetChanged();

        ListViewShoppingListContents.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView< ? > Parent, View CallingView, int Position, long ID ) {
                btActivityPersistence.AddKeyValuePair( "SelectedPantryItem", Position );
                btActivityHelpers.SwitchView( CallingView.getContext(), btView_ProductDetails.class );
            }
        } );

        ListViewShoppingListContents.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick( AdapterView< ? > Parent, View CallingView, int Position, long ID ) {
                btListViewAdapterData Data = (btListViewAdapterData)ListViewShoppingListContents.getItemAtPosition( Position );
                btInventoryHandler.RemoveProductFromPantry( Data.Description );
                Adapter.Remove( Position );
                Adapter.notifyDataSetChanged();

                return false;
            }
        } );
    }

    private ArrayList< btListViewAdapterData > ShoppingListContents = new ArrayList<>();
    private btListViewAdapter Adapter;

    private TextView TextViewToHome;
    private ListView ListViewShoppingListContents = null;
}
