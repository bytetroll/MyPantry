package blueteam.mypantry.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.*;
import android.widget.Button;

import android.widget.TextView;
import blueteam.mypantry.async.btConnectionWatcher;
import blueteam.mypantry.async.btLocalDatabaseSyncer;
import blueteam.mypantry.core.btInventoryHandler;
import blueteam.mypantry.db.btLocalDatabase;
import blueteam.mypantry.runtime.btLocalScopeAccessor;
import blueteam.mypantry.ui.helpers.btActivityHelpers;
import blueteam.mypantry.ui.helpers.btActivityPersistence;
import blueteam.mypantry.ui.shared.btAddMethod;
import com.google.zxing.integration.android.IntentIntegrator;


import blueteam.mypanty.R;


public class btView_Home extends Activity {
    @Override
    protected void onCreate( Bundle SavedState ) {
        super.onCreate( SavedState );

        setContentView( R.layout.btui_view_home );

        ButtonAdd = (Button)findViewById( R.id.ButtonAdd );
        ButtonAdd.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View CallingView ) {
                OnClick_AddButton( CallingView );
            }
        } );

        TextViewToPantry = (TextView)findViewById( R.id.TextViewHomeToPantry );
        TextViewToPantry.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View CallingView ) {
                Intent NewIntent = new Intent( CallingView.getContext(), btView_Pantry.class );
                startActivity( NewIntent );
            }
        } );

        TextViewToShoppingList = (TextView)findViewById( R.id.TextViewHomeToShoppingList );
        TextViewToShoppingList.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick( View CallingView ) {
                Intent NewIntent = new Intent( CallingView.getContext(), btView_ShoppingList.class );
                startActivity( NewIntent );
            }
        } );

        // Start async threads.
        new btConnectionWatcher().start();
        new btLocalDatabaseSyncer().start();

        btInventoryHandler.Startup( this );
    }

    private void OnClick_AddButton( View CallingView ) {
        final btLocalScopeAccessor Accessor = new btLocalScopeAccessor();
        Accessor.Bind( "ThisView", this );

        new AlertDialog.Builder( CallingView.getContext() )
                .setTitle( "Add" )
                .setMessage( "You may select 'Scan' to scan a barcode, or 'Manual' for manual product entry" )
                .setPositiveButton( "Manual", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialog, int Witch ) {
                        btActivityPersistence.AddKeyValuePair( "AddMethod", btAddMethod.Manual );

                        btActivityHelpers.SwitchView( (Activity)Accessor.Access( "ThisView" ).InternalObject, btView_ProductDetails.class );
                    }
                } )
                .setNegativeButton( "Scan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface Dialog, int Which ) {
                        Intent NewIntent = new Intent( "com.google.zxing.client.android.SCAN" );
                        NewIntent.putExtra( "SCAN_MODE", "QR_CODE_MODE" );

                        startActivityForResult( NewIntent, 0 );
                        IntentIntegrator Integrator = new IntentIntegrator( (Activity)Accessor.Access( "ThisView" ).InternalObject );

                        Integrator.setDesiredBarcodeFormats( IntentIntegrator.ONE_D_CODE_TYPES );
                        Integrator.setPrompt( "Scan a UPC" );
                        Integrator.setCameraId( 0 );  // Use a specific camera of the device
                        Integrator.setBeepEnabled( false );

                        Integrator.initiateScan();
                    }
                } )
                .setIcon( android.R.drawable.ic_dialog_alert )
                .show();
    }

    private Button ButtonAdd;
    private TextView TextViewToPantry;
    private TextView TextViewToShoppingList;

    /*
    @Override
    public boolean onKeyDown( int Keycode, KeyEvent Event ) {
        switch( Keycode ) {
            case KeyEvent.KEYCODE_MENU: {
                OnClick_HardwareMenuButton();
                break;
            }
        }

        return super.onKeyDown( Keycode, Event );
    }

    @Override
    public void onActivityResult( int RequestCode, int ResultCode, Intent CallingIntent ) {
        if( RequestCode == 0 ) {
            if( ResultCode == RESULT_OK ) {

                String Contents = CallingIntent.getStringExtra( "SCAN_RESULT" );
                String Format = CallingIntent.getStringExtra( "SCAN_RESULT_FORMAT" );

            } else if( ResultCode == RESULT_CANCELED ) {
                btRuntime.LogMessage( "scan result was canceled." );
            }
        }
    }

    // @Nathan: This will create and fill our list view with some test data.  Once this test data
    //          is no longer needed (i.e., we are fetching it from a database), delete this routine.
    private void CreateTestData() {
        for( int Index = 0; Index < 50; Index++ ) {
            btListViewAdapterData Data = new btListViewAdapterData( ( "Object" + Index ), ( new String( "[" + Index + "]" ) ) );
            PantryContents.add( Data );
        }

        // Force dynamic reload.
        PantryListViewAdapter.notifyDataSetChanged();
    }

    private void BindInterface() {
        // "Add" button.
        ButtonAdd = (Button)findViewById( R.id.button_add );
        assert( ButtonAdd == null );
        ButtonAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick( View view ) {
                OnClick_AddButton( view );
            }
        } );

        // Pantry contents list view.
        ListViewPantryContents = (ListView)findViewById( R.id.listview_pantry_contents );
        assert( ListViewPantryContents == null );
        PantryListViewAdapter = new btListViewAdapter( this, PantryContents);
        ListViewPantryContents.setAdapter(PantryListViewAdapter);
        ListViewPantryContents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView< ? > Parent, View CallingView, int Position, long ID) {
                OnClick_ListViewItem( Position );
            }
        });
    }

    // control event callbacks
    private void OnClick_AddButton( View CallingView ) {
        final btLocalScopeAccessor Accessor = new btLocalScopeAccessor();
        Accessor.Bind( "ThisView", this );

        new AlertDialog.Builder( CallingView.getContext() )
                .setTitle( "Add" )
                .setMessage( "You may select 'Scan' to scan a barcode, or 'Manual' for manual product entry" )
                .setPositiveButton( "Manual", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialog, int Witch ) {
                        btActivityPersistence.AddKeyValuePair( "AddMethod", btAddMethod.Manual );
                        btActivityHelpers.SwitchView( (Activity)Accessor.Access( "ThisView " ).InternalObject, btView_ProductDetails.class );
                    }
                })
                .setNegativeButton( "Scan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface Dialog, int Which ) {
                        Intent NewIntent = new Intent( "com.google.zxing.client.android.SCAN" );
                        NewIntent.putExtra( "SCAN_MODE", "QR_CODE_MODE" );

                        startActivityForResult( NewIntent, 0 );
                        IntentIntegrator Integrator = new IntentIntegrator( (Activity)Accessor.Access( "ThisView" ).InternalObject );

                        Integrator.setDesiredBarcodeFormats( IntentIntegrator.ONE_D_CODE_TYPES );
                        Integrator.setPrompt( "Scan a UPC" );
                        Integrator.setCameraId( 0 );  // Use a specific camera of the device
                        Integrator.setBeepEnabled( false );

                        Integrator.initiateScan();
                    }
                } )
                .setIcon( android.R.drawable.ic_dialog_alert )
                .show();
    }

    private void event_callback_button_scan_clicked( View view ) {
        //Intent intent = new Intent( "com.google.zxing.client.android.SCAN" );
        //intent.putExtra( "SCAN_MODE", "QR_CODE_MODE" );

        //startActivityForResult( intent, 0 );
        IntentIntegrator integrator = new IntentIntegrator( this );

        integrator.setDesiredBarcodeFormats( IntentIntegrator.ONE_D_CODE_TYPES );
        integrator.setPrompt( "Scan a UPC" );
        integrator.setCameraId( 0 );  // Use a specific camera of the device
        integrator.setBeepEnabled( false );

        integrator.initiateScan();
    }


    private void OnClick_HardwareMenuButton() {
    }

    private void OnClick_ListViewItem( int SelectionIndex ) {

    }

    private Button ButtonAdd = null;
    private ListView ListViewPantryContents = null;

    private btListViewAdapter PantryListViewAdapter = null;

    private ArrayList< btListViewAdapterData > PantryContents = new ArrayList<>();

    */

}
