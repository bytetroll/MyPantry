package blueteam.mypantry.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import blueteam.mypanty.R;

public class btView_ShoppingList extends Activity {
    @Override
    protected void onCreate( Bundle SavedInstanceState ) {
        super.onCreate( SavedInstanceState );
        setContentView( R.layout.btui_view_shopping_list);

        TextViewToHome = (TextView)findViewById( R.id.TextViewShoppingListToHome );
        TextViewToHome.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View CallingView ) {
                Intent NewIntent = new Intent( CallingView.getContext(), btView_Home.class );
                startActivity( NewIntent );
            }
        } );
    }

    private TextView TextViewToHome;
}
