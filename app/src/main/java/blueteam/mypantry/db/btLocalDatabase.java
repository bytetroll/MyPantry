package blueteam.mypantry.db;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import blueteam.mypantry.core.btProduct;

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

    public void SaveProductInPantry( btProduct Product ) {
        final SQLiteDatabase Database = this.getWritableDatabase();

        final ContentValues Values = new ContentValues();
        Values.put( ProductName, Product.Name );
        Values.put( ProductCategory, Product.Category );
        Values.put( ProductQuantity, Product.Quantity );
        Values.put( ProductPrice, Product.Price );
        Values.put( ProductPurchaseDate, Product.PurchaseDate );
        Values.put( ProductPerishable, Product.Perishable );
        Values.put( ProductPerishDate, Product.PerishDate );
        Values.put( PendingSync, "true" );

        Database.insert( PantryTableName, null, Values );
    }

    public void SaveProductInShoppingList( btProduct Product ) {
        final SQLiteDatabase Database = this.getWritableDatabase();

        final ContentValues Values = new ContentValues();
        Values.put( ProductName, Product.Name );
        Values.put( ProductCategory, Product.Category );
        Values.put( ProductQuantity, Product.Quantity );
        Values.put( ProductPrice, Product.Price );
        Values.put( ProductPurchaseDate, Product.PurchaseDate );
        Values.put( ProductPerishable, Product.Perishable );
        Values.put( ProductPerishDate, Product.PerishDate );
        Values.put( PendingSync, "true" );

        Database.insert( ShoppingListTableName, null, Values );
    }

    public void DeleteProductFromPantry( String ProductName ) {
        final SQLiteDatabase Database = this.getWritableDatabase();
        Database.delete( PantryTableName, "ProductName = ? ", new String[] { ProductName } );
    }

    public void DeleteProductFromShoppingList( String ProductName ) {
        final SQLiteDatabase Database = this.getWritableDatabase();
        Database.delete( ShoppingListTableName, "ProductName = ? ", new String[] { ProductName } );
    }

    public ArrayList< btProduct > QueryPantry() {
        final SQLiteDatabase Database = this.getReadableDatabase();
        final ArrayList< btProduct > Products = new ArrayList< btProduct>();
        final Cursor Cur = Database.rawQuery( "SELECT * FROM Pantry", null );

        Cur.moveToFirst();
        while( Cur.isAfterLast() == false ) {
            btProduct Product = new btProduct();
            Product.Name = Cur.getString( Cur.getColumnIndex( ProductName ) );
            Product.Category = Cur.getString( Cur.getColumnIndex( ProductCategory ) );
            Product.Quantity = Cur.getInt( Cur.getColumnIndex( ProductQuantity ) );
            Product.Price = Cur.getFloat( Cur.getColumnIndex( ProductPrice ) );
            Product.PurchaseDate = Cur.getString( Cur.getColumnIndex( ProductPurchaseDate ) );
            Product.Perishable = Boolean.parseBoolean( Cur.getString( Cur.getColumnIndex( ProductPerishable ) ) );
            Product.PerishDate = Cur.getString( Cur.getColumnIndex( ProductPerishDate ) );
            Product.PendingSync = Boolean.parseBoolean( Cur.getString( Cur.getColumnIndex( PendingSync ) ) );

            Products.add( Product );

            Cur.moveToNext();
        }

        return Products;
    }

    public ArrayList< btProduct > QueryShoppingList() {
        final SQLiteDatabase Database = this.getReadableDatabase();
        final ArrayList< btProduct > Products = new ArrayList< btProduct>();
        final Cursor Cur = Database.rawQuery( "SELECT * FROM ShoppingList", null );

        Cur.moveToFirst();
        while( Cur.isAfterLast() == false ) {
            btProduct Product = new btProduct();
            Product.Name = Cur.getString( Cur.getColumnIndex( ProductName ) );
            Product.Category = Cur.getString( Cur.getColumnIndex( ProductCategory ) );
            Product.Quantity = Cur.getInt( Cur.getColumnIndex( ProductQuantity ) );
            Product.Price = Cur.getFloat( Cur.getColumnIndex( ProductPrice ) );
            Product.PurchaseDate = Cur.getString( Cur.getColumnIndex( ProductPurchaseDate ) );
            Product.Perishable = Boolean.parseBoolean( Cur.getString( Cur.getColumnIndex( ProductPerishable ) ) );
            Product.PerishDate = Cur.getString( Cur.getColumnIndex( ProductPerishDate ) );
            Product.PendingSync = Boolean.parseBoolean( Cur.getString( Cur.getColumnIndex( PendingSync ) ) );

            Products.add( Product );

            Cur.moveToNext();
        }

        return Products;
    }
}