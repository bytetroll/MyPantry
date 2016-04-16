package blueteam.mypantry.core;

import blueteam.mypantry.db.btLocalDatabase;

import java.util.ArrayList;
import java.util.List;


public class btPantry {
    public static void Restore() {
        // Restore from the local DB here.
        List< btProduct > Products = btLocalDatabase.QueryPantry() ;
        for( btProduct Product : Products ) {
            Contents.add( Product );
        }
    }

    public static List< btProduct > Contents = new ArrayList< btProduct >();
}