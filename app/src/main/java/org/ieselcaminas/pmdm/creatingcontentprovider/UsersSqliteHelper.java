package org.ieselcaminas.pmdm.creatingcontentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UsersSqliteHelper extends SQLiteOpenHelper {

    String SQL_SENTENCE = "CREATE TABLE Users "+
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "user TEXT, password TEXT, email TEXT)";

    public UsersSqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                             int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_SENTENCE);

        for (int i=1; i<=10; i++) {
            String user = "user" + i;
            String password = "passwd" + i;
            String email = "email" + i + "@ieselcaminas.org";
            sqLiteDatabase.execSQL("INSERT INTO Users (user, password, email) " +
                    "VALUES ('" + user + "', '" + password + "', '" + email + "')");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Users");
        sqLiteDatabase.execSQL(SQL_SENTENCE);
    }

}
