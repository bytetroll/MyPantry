package blueteam.mypantry.runtime;


public class btRuntimeCast {
    public static boolean AsBoolean( String BoolString ) {
        if( "1".equalsIgnoreCase( BoolString ) || "yes".equalsIgnoreCase( BoolString ) ||
                "true".equalsIgnoreCase( BoolString ) || "on".equalsIgnoreCase( BoolString ) ) {
            return true;
        }

        return false;
    }

    public static boolean AsBoolean( btRuntimeObject Obj ) {
        return AsBoolean( AsString( Obj ) );
    }

    public static String AsString( btRuntimeObject Obj ) {
        return String.valueOf( Obj.InternalObject );
    }
}
