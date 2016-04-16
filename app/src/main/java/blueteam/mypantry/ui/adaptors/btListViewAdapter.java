//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//>> CSC 3330 Group Term Project
//>>    Blue Team: Brandon, Chris, Nathan.
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
package blueteam.mypantry.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import blueteam.mypanty.R;

public class btListViewAdapter extends BaseAdapter {

    public btListViewAdapter(Context context, List< btListViewAdapterData > data ) {
        this.InternalContext = context;
        this.Data = data;

        Inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Data.size();
    }

    @Override
    public Object getItem( int Position ) {
        return Data.get( Position );
    }

    @Override
    public long getItemId( int Position ) {
        return Position;
    }

    @Override
    public View getView( int Position, View ConvertView, ViewGroup Parent ) {
        View Vi = ConvertView;
        if( Vi == null ) {
            Vi = Inflator.inflate( R.layout.ui_control_listview_row, null );
        }

        TextView Quantity = (TextView)Vi.findViewById( R.id.textview_quantity );
        Quantity.setText( Data.get( Position ).Quantity );

        TextView description = (TextView)Vi.findViewById( R.id.textview_description );
        description.setText( Data.get( Position ).Description );

        return Vi;
    }

    private static LayoutInflater Inflator = null;

    private Context InternalContext = null;
    private List< btListViewAdapterData > Data = null;
}