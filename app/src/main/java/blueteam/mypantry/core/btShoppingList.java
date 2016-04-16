package blueteam.mypantry.core;

import blueteam.mypantry.db.btLocalDatabase;

import java.util.ArrayList;
import java.util.List;


public class btShoppingList {
    public static void Restore() {
        // Restore from the local DB here.
        List< btProduct > Products = btLocalDatabase.QueryShoppingList() ;
        for( btProduct Product : Products ) {
            Contents.add( Product );
        }
    }

    public static List< btProduct > Contents = new ArrayList< btProduct >();
}