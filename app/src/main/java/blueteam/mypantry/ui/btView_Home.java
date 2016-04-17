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

    @Override
    public void onActivityResult( int RequestCode, int ResultCode, Intent CallingIntent ) {
        if( RequestCode == 0 ) {
            if( ResultCode == RESULT_OK ) {
                String Contents = CallingIntent.getStringExtra( "SCAN_RESULT" );
                String Format = CallingIntent.getStringExtra( "SCAN_RESULT_FORMAT" );
                // Handle successful scan
            } else if( ResultCode == RESULT_CANCELED ) {
                // Handle cancel
            }
        }
    }

    private void OnClick_AddButton( View CallingView ) {
        final btLocalScopeAccessor Accessor = new btLocalScopeAccessor();
        Accessor.Bind( "ThisView", this );

        new AlertDialog.Builder( CallingView.getContext() )
                .setTitle( "Add" )
                .setMessage( "You may select 'Scan' to scan a barcode, or 'Manual' for manual product entry" )
                .setPositiveButton( "Manual", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialog, int Which ) {
                        btActivityPersistence.AddKeyValuePair( "AddMethod", btAddMethod.Manual );

                        btActivityHelpers.SwitchView( (Activity)Accessor.Access( "ThisView" ).InternalObject, btView_ProductDetails.class );
                    }
                } )
                .setNegativeButton( "Scan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface Dialog, int Which ) {
                        Intent ScanIntent = new Intent( "com.google.zxing.client.android.SCAN" );
                        ScanIntent.putExtra( "SCAN_MODE", "QR_CODE_MODE" );
                        startActivityForResult( ScanIntent, 0 );
                    }
                } )
                .setIcon( android.R.drawable.ic_dialog_alert )
                .show();
    }

    private Button ButtonAdd;
    private TextView TextViewToPantry;
    private TextView TextViewToShoppingList;
}
