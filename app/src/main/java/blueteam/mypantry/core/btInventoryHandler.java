package blueteam.mypantry.core;

import android.content.Context;
import blueteam.mypantry.db.btLocalDatabase;

import java.util.ArrayList;
import java.util.List;

public class btInventoryHandler {
    // Public API
    public static void AddProductToPantry( btProduct Product ) {
        AddProductToLocation( Product, ProductOperationDestination.Pantry );
    }

    public static void AddProductToShoppingList( btProduct Product ) {
        AddProductToLocation( Product, ProductOperationDestination.ShoppingList );
    }

    public static void RemoveProductFromPantry( String ProductName ) {
        RemoveProductFromLocation( ProductName, ProductOperationDestination.Pantry );
    }

    public static void RemoveProductFromShoppingList( String ProductName ) {
        RemoveProductFromLocation( ProductName, ProductOperationDestination.ShoppingList );
    }

    public static void Startup( Context Ctx ) {
        LocalDB = new btLocalDatabase( Ctx );

        PantryInventory = LocalDB.QueryPantry();
        if( PantryInventory == null ) {
            PantryInventory = new ArrayList<>();
        }

        ShoppingListInventory = LocalDB.QueryShoppingList();
        if( ShoppingListInventory == null ) {
            ShoppingListInventory = new ArrayList<>();
        }
    }

    // Private API
    private enum ProductOperationDestination {
        Pantry,
        ShoppingList
    }

    private static void AddProductToLocation( btProduct Product, ProductOperationDestination Location ) {
        assert( Product == null );

        switch( Location ) {
            case Pantry:
                LocalDB.SaveProductInPantry( Product );
                PantryInventory.add( Product );
                break;
            case ShoppingList:
                LocalDB.SaveProductInShoppingList( Product );
                PantryInventory.add( Product );
                break;
        }
    }

    private static void RemoveProductFromLocation( String ProductName, ProductOperationDestination Location ) {
        switch( Location ) {
            case Pantry:
                LocalDB.DeleteProductFromPantry( ProductName );

                for( int Index = 0; Index < PantryInventory.size(); Index++ ) {
                    if( PantryInventory.get( Index ).Name.equals( ProductName ) ) {
                        PantryInventory.remove( Index );
                        break;
                    }
                }

                break;
            case ShoppingList:
                LocalDB.DeleteProductFromShoppingList( ProductName );

                for( int Index = 0; Index < ShoppingListInventory.size(); Index++ ) {
                    if( ShoppingListInventory.get( Index ).Name.equals( ProductName ) ) {
                        ShoppingListInventory.remove( Index );
                        break;
                    }
                }

                break;
        }
    }

    public static List< btProduct > PantryContents() {
        return PantryInventory;
    }

    public static List< btProduct > ShoppingListContents() {
        return ShoppingListInventory;
    }

    // Allocating the lists ensures that we have valid lists, even if no local data is stored.
    private static List< btProduct > PantryInventory = null;
    private static List< btProduct > ShoppingListInventory = null;

    private static btLocalDatabase LocalDB = null;
}
