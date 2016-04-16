package blueteam.mypantry.ui.viewcontainer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import blueteam.mypantry.ui.adapters.btListViewAdapterData;
import blueteam.mypanty.R;
import blueteam.mypantry.ui.adapters.btListViewAdapter;

public class btView_Pantry extends Activity {
    @Override
    protected void onCreate( Bundle SavedInstanceState ) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.btui_view_pantry);

        ListView view = (ListView)findViewById(R.id.PantryList);
        Adapter = new btListViewAdapter( this, PantryContents);
        view.setAdapter(Adapter);

        for( int Index = 0; Index < 50; Index++ ) {
            btListViewAdapterData Data = new btListViewAdapterData( ( "Object" + Index ), ( new String( "[" + Index + "]" ) ) );
            PantryContents.add( Data );
        }

        // Force dynamic reload.
        Adapter.notifyDataSetChanged();
    }

    private ArrayList< btListViewAdapterData > PantryContents = new ArrayList<>();
    private btListViewAdapter Adapter;
}
