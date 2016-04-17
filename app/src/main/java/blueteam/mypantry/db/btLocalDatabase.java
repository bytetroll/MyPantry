package blueteam.mypantry.db;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class btLocalDatabase extends SQLiteOpenHelper {
    public static final String DatabaseName = "MyPantry.db";
    public static final int DatabaseVersion = 1;

    // Pantry
    public static final String PantryTableName = "Pantry";

    // Shopping List
    public static final String ShoppingListTableName = "ShoppingList";

    // Generic.
    public static final String ProductName = "ProductName";
    public static final String ProductCategory = "ProductCategory";
    public static final String ProductQuantity = "ProductQuantity";
    public static final String ProductPrice = "ProductPrice";
    public static final String ProductPurchaseDate = "ProductPurchaseDate";
    public static final String ProductPerishable = "ProductPerishable";
    public static final String ProductPerishDate = "ProductPerishDate";
    public static final String PendingSync = "PendingSync";

    public btLocalDatabase( Context Ctx ) {
        super( Ctx, DatabaseName, null, DatabaseVersion );
    }
//http://www.tutorialspoint.com/android/android_sqlite_database.htm
    @Override
    public void onCreate( SQLiteDatabase Database ) {
        Database.execSQL( "CREATE TABLE Pantry( ProductName TEXT, ProductCategory TEXT, ProductQuantity TEXT, ProductPrice TEXT, ProductPurchaseDate TEXT, ProductPerishable TEXT, ProductPerishDate TEXT, PendingSync TEXT )" );
        Database.execSQL( "CREATE TABLE ShoppingList( ProductName TEXT, ProductCategory TEXT, ProductQuantity TEXT, ProductPrice TEXT, ProductPurchaseDate TEXT, ProductPerishable TEXT, ProductPerishDate TEXT, PendingSync TEXT )" );
    }

    @Override
    public void onUpgrade( SQLiteDatabase Database, int OldVersion, int NewVersion ) {
        Database.execSQL( "DROP TABLE IF EXISTS Pantry" );
        Database.execSQL( "DROP TABLE IF EXISTS ShoppingList" );
    }


}