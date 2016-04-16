package blueteam.mypantry.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import blueteam.mypantry.core.btProduct;
import blueteam.mypantry.runtime.btRuntime;
import blueteam.mypantry.runtime.btRuntimeCast;

import java.util.ArrayList;
import java.util.List;


public class btLocalDatabase extends SQLiteOpenHelper {
    public static final int DatabaseVersion = 1;
    public static final String DatabaseName = "mypantry.db";

    public btLocalDatabase( Context Ctx ) {
        super( Ctx, DatabaseName, null, DatabaseVersion );
        InternalContext = Ctx;
        BindReference( this );
    }

    @Override
    public void onCreate( SQLiteDatabase Db ) {
        Db.execSQL( Sql.TablePantry.CreateEntries);
        Db.execSQL( Sql.TableShoppingList.CreateEntries);
    }

    @Override
    public void onOpen( SQLiteDatabase Db ) {
    }

    @Override
    public void onUpgrade( SQLiteDatabase Db, int OldVersion, int NewVersion ) {
        Db.execSQL( Sql.TablePantry.DeleteEntries);
        Db.execSQL( Sql.TableShoppingList.DeleteEntries);

        onCreate( Db );
    }

    @Override
    public void onDowngrade( SQLiteDatabase db, int old_version, int new_version ) {
        onUpgrade(db, old_version, new_version);
    }

    public SQLiteDatabase FetchReadableHandle() {
        return super.getReadableDatabase();
    }

    public static void BindReference( btLocalDatabase Ref ) {
        InternalReference = Ref;
    }

    public static btLocalDatabase FetchReference() {
        return InternalReference != null ? InternalReference : null;
    }

    public static Cursor QueryTablePantry() {
        final btLocalDatabase Db = btLocalDatabase.FetchReference();
        assert( Db == null );

        String[] Columns = new String[] {
                Sql.TablePantry.ColumnProductName,
                Sql.TablePantry.ColumnProductCategory,
                Sql.TablePantry.ColumnProductPrice,
                Sql.TablePantry.ColumnProductQuantity,
                Sql.TablePantry.ColumnProductPurchaseDate,
                Sql.TablePantry.ColumnProductPerishable,
                Sql.TablePantry.ColumnProductPerishDate
        };

        return Db.FetchReadableHandle().query( Sql.TablePantry.TableName, Columns, null, null, null, null, null );
    }

    public static Cursor QueryTableShoppingList() {
        final btLocalDatabase Db = btLocalDatabase.FetchReference();
        assert( Db == null );

        String[] Columns = new String[] {
                Sql.TableShoppingList.ColumnProductName,
                Sql.TableShoppingList.ColumnProductCategory,
                Sql.TableShoppingList.ColumnProductPrice,
                Sql.TableShoppingList.ColumnProductQuantity,
                Sql.TableShoppingList.ColumnProductPurchaseDate,
                Sql.TableShoppingList.ColumnProductPerishable,
                Sql.TableShoppingList.ColumnProductPerishDate
        };

        return Db.FetchReadableHandle().query( Sql.TablePantry.TableName, Columns, null, null, null, null, null );
    }

    public static void SaveShoppingListProduct( btProduct Product ) {
        if( Product != null ) {
            final btLocalDatabase Db = btLocalDatabase.FetchReference();
            assert( Db == null );

            ContentValues Values = new ContentValues();
            Values.put( Sql.TableShoppingList.ColumnProductName, Product.Name );
            Values.put( Sql.TableShoppingList.ColumnProductCategory, Product.Category );
            Values.put( Sql.TableShoppingList.ColumnProductPrice, String.valueOf( Product.Price ) );
            Values.put( Sql.TableShoppingList.ColumnProductQuantity, String.valueOf( Product.Quantity ) );
            Values.put( Sql.TableShoppingList.ColumnProductPerishDate, Product.PurchaseDate );
            Values.put( Sql.TableShoppingList.ColumnProductPerishable, String.valueOf( Product.Perishable ) );
            Values.put( Sql.TableShoppingList.ColumnProductPerishDate, Product.PerishDate );

            long NewRow = Db.getWritableDatabase().insert( Sql.TableShoppingList.TableName, null, Values );
        }
    }

    public static void SavePantryProduct( btProduct Product ) {
        if( Product != null ) {
            final btLocalDatabase Db = btLocalDatabase.FetchReference();
            assert( Db == null );

            ContentValues Values = new ContentValues();
            Values.put( Sql.TablePantry.ColumnProductName, Product.Name );
            Values.put( Sql.TablePantry.ColumnProductCategory, Product.Category );
            Values.put( Sql.TablePantry.ColumnProductPrice, String.valueOf( Product.Price ) );
            Values.put( Sql.TablePantry.ColumnProductQuantity, String.valueOf( Product.Quantity ) );
            Values.put( Sql.TablePantry.ColumnProductPerishDate, Product.PurchaseDate );
            Values.put( Sql.TablePantry.ColumnProductPerishable, String.valueOf( Product.Perishable ) );
            Values.put( Sql.TablePantry.ColumnProductPerishDate, Product.PerishDate );

            long NewRow = Db.getWritableDatabase().insert( Sql.TablePantry.TableName, null, Values );
        }
    }

    public static List< btProduct > QueryPantry() {
        Cursor LocalCursor = btLocalDatabase.QueryTablePantry();
        List< btProduct > Products = new ArrayList< btProduct >();
        while( LocalCursor.isAfterLast() ) {
            final String[] Column = LocalCursor.getColumnNames();

            btProduct Product = new btProduct();
            Product.Name = LocalCursor.getString( LocalCursor.getColumnIndex( Sql.TablePantry.ColumnProductName ) );
            Product.Category = LocalCursor.getString( LocalCursor.getColumnIndex( Sql.TablePantry.ColumnProductCategory ) );
            Product.Price = Float.parseFloat( LocalCursor.getString( LocalCursor.getColumnIndex( Sql.TablePantry.ColumnProductPrice ) ) );
            Product.PurchaseDate = LocalCursor.getString( LocalCursor.getColumnIndex( Sql.TablePantry.ColumnProductPerishDate ) );
            Product.Perishable = btRuntimeCast.AsBoolean( LocalCursor.getString( LocalCursor.getColumnIndex( Sql.TablePantry.ColumnProductPerishable ) ) );
            Product.PerishDate = LocalCursor.getString( LocalCursor.getColumnIndex( Sql.TablePantry.ColumnProductPerishDate ) );
            Product.Quantity = Integer.parseInt( LocalCursor.getString( LocalCursor.getColumnIndex( Sql.TablePantry.ColumnProductQuantity ) ) );
            Product.PendingSync = btRuntimeCast.AsBoolean( LocalCursor.getString( LocalCursor.getColumnIndex( Sql.TablePantry.ColumnSyncPending ) ) );

            Products.add( Product );

            LocalCursor.moveToNext();
        }

        return Products;
    }

    public static List< btProduct > QueryShoppingList() {
        Cursor LocalCursor = btLocalDatabase.QueryTableShoppingList();
        List< btProduct > Products = new ArrayList< btProduct >();
        while( LocalCursor.isAfterLast() ) {
            final String[] Column = LocalCursor.getColumnNames();

            btProduct Product = new btProduct();
            Product.Name = LocalCursor.getString( LocalCursor.getColumnIndex( Sql.TableShoppingList.ColumnProductName ) );
            Product.Category = LocalCursor.getString( LocalCursor.getColumnIndex( Sql.TableShoppingList.ColumnProductCategory ) );
            Product.Price = Float.parseFloat( LocalCursor.getString( LocalCursor.getColumnIndex( Sql.TableShoppingList.ColumnProductPrice ) ) );
            Product.PurchaseDate = LocalCursor.getString( LocalCursor.getColumnIndex( Sql.TableShoppingList.ColumnProductPurchaseDate ) );
            Product.Perishable = btRuntimeCast.AsBoolean( LocalCursor.getString(LocalCursor.getColumnIndex(Sql.TableShoppingList.ColumnProductPerishable)));
            Product.PerishDate = LocalCursor.getString( LocalCursor.getColumnIndex( Sql.TableShoppingList.ColumnProductPerishDate ) );
            Product.Quantity = Integer.parseInt( LocalCursor.getString( LocalCursor.getColumnIndex( Sql.TableShoppingList.ColumnProductQuantity ) ) );
            Product.PendingSync = btRuntimeCast.AsBoolean( LocalCursor.getString( LocalCursor.getColumnIndex( Sql.TableShoppingList.ColumnSyncPending ) ) );

            Products.add( Product );

            LocalCursor.moveToNext();
        }

        return Products;
    }

    public static void RemoveProductFromPantry( String ProductName ) {
        String[] Names = new String[] {
                ProductName
        };

        final btLocalDatabase Db = FetchReference();
        assert( Db == null );
        try {
            Db.FetchReadableHandle().delete( Sql.TablePantry.TableName, Sql.TablePantry.ColumnProductName + " = ?", Names );
        } catch( Exception Except ) {
            btRuntime.HandleException( Except );
        }
    }

    public static void RemoveProductFromShoppingList( String ProductName ) {
        String[] Names = new String[] {
                ProductName
        };

        final btLocalDatabase db = FetchReference();
        assert( db == null );
        try {
            db.FetchReadableHandle().delete( Sql.TablePantry.TableName, Sql.TablePantry.ColumnProductName + " = ?", Names );
        } catch( Exception Except ) {
            btRuntime.HandleException( Except );
        }
    }

    private static Context InternalContext = null;
    private static btLocalDatabase InternalReference = null;

    public class Sql {
        public static final String TextType = " TEXT";
        public static final String CommaSeparator = ",";

        // @Nathan: I'm feeling way to lazy to handle other data types, so everything will be stored as strings
        //          in the database tables.  Make it easy on everyone.
        // @Nathan: Technically both tables share the same column names, but they are implemented in two separate classes
        //          to make the end API clean and easy to understand.
        public class TablePantry implements BaseColumns {
            public static final String TableName = "TablePantry";
            public static final String ColumnProductName = "btProduct_name";
            public static final String ColumnProductCategory = "btProduct_category";
            public static final String ColumnProductPrice = "btProduct_price";
            public static final String ColumnProductQuantity = "btProduct_quantity";
            public static final String ColumnProductPurchaseDate = "btProduct_purchase_date";
            public static final String ColumnProductPerishable = "btProduct_is_perishable";
            public static final String ColumnProductPerishDate = "btProduct_perish_date";

            // Boolean value.  If this is true, the btProduct entry is stored in the local databases, but
            // has not been sent to the remote server.  Once sending is complete, this will be changed
            // to false.
            public static final String ColumnSyncPending = "sync_is_pending";

            public static final String CreateEntries = "CREATE TABLE IF NOT EXISTS " + TableName +
                    " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ColumnProductName + TextType + CommaSeparator +
                    ColumnProductCategory + TextType + CommaSeparator +
                    ColumnProductPrice + TextType + CommaSeparator +
                    ColumnProductQuantity + TextType + CommaSeparator +
                    ColumnProductPurchaseDate + TextType + CommaSeparator +
                    ColumnProductPerishable + TextType + CommaSeparator +
                    ColumnProductPerishDate + TextType + CommaSeparator + " )";

            public static final String DeleteEntries = "DROP TABLE IF EXISTS " + TableName;
        }

        public class TableShoppingList implements BaseColumns {
            public static final String TableName = "TablePantry";
            public static final String ColumnProductName = "btProduct_name";
            public static final String ColumnProductCategory = "btProduct_category";
            public static final String ColumnProductPrice = "btProduct_price";
            public static final String ColumnProductQuantity = "btProduct_quantity";
            public static final String ColumnProductPurchaseDate = "btProduct_purchase_date";
            public static final String ColumnProductPerishable = "btProduct_is_perishable";
            public static final String ColumnProductPerishDate = "btProduct_perish_date";

            // See above note.
            public static final String ColumnSyncPending = "sync_is_pending";

            public static final String CreateEntries = "CREATE TABLE IF NOT EXISTS " + TableName +
                    " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ColumnProductName + TextType + CommaSeparator +
                    ColumnProductCategory + TextType + CommaSeparator +
                    ColumnProductPrice + TextType + CommaSeparator +
                    ColumnProductQuantity + TextType + CommaSeparator +
                    ColumnProductPurchaseDate + TextType + CommaSeparator +
                    ColumnProductPerishable + TextType + CommaSeparator +
                    ColumnProductPerishDate + TextType + CommaSeparator + " )";

            public static final String DeleteEntries = "DROP TABLE IF EXISTS " + TableName;
        }
    }
}
