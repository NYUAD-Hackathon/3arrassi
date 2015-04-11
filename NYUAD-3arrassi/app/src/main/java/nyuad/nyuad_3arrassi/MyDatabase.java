package nyuad.nyuad_3arrassi;

/**
 * Created by Kong on 4/11/2015.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "word.sqlite3";
    private static final int DATABASE_VERSION = 2;

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        // you can use an alternate constructor to specify a database location
        // (such as a folder on the sd card)
        // you must ensure that this folder is available and you have permission
        // to write to it
        //super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);

        // call this method to force a database overwrite every time the version number increments:
        //setForcedUpgrade();

        // call this method to force a database overwrite if the version number
        // is below a certain threshold:
        //setForcedUpgrade(2);
    }

    public Cursor getWords() {

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM word" , null);

        c.moveToFirst();
        return c;

    }

}