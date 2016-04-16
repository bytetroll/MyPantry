package blueteam.mypantry.ui.helpers;

// @Nathan: Android has their own system for handling data persistence between activities,
//          however, I've never found it to work well.

import java.util.HashMap;
import java.util.Map;

public class btActivityPersistence {
    public static void AddKeyValuePair( String Key, Object Value ) {
        final Object Obj = ValueFromKey( Key );

        if( Obj != null ) {
            UpdateValueFromKey( Key, Value );
        }

        Data.put( Key, Value );
    }

    public static Object ValueFromKey( String Key ) {
        final Object Value = Data.get( Key );
        Reset( Key );

        return Value;
    }

    public static void Reset( String Key ) {
        Data.remove( Key );
    }

    private static void UpdateValueFromKey( String Key, Object Value ) {
        for( int Index = 0; Index < Data.size(); Index++ ) {
            if( Data.get( Key ).equals( Value ) ) {
                Data.remove( Key );
                Data.put( Key, Value );

                return;
            }
        }
    }

    public static Map< String, Object > Data = new HashMap<>();
}
