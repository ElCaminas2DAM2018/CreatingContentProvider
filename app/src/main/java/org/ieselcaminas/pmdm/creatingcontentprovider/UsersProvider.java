package org.ieselcaminas.pmdm.creatingcontentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

public class UsersProvider extends ContentProvider {


    public static final class Users implements BaseColumns {
        private Users() {    }
        public static final String COL_USER = "user";
        public static final String COL_PASSWORD = "password";
        public static final String COL_EMAIL = "email";
    }

    public static final Uri CONTENT_URI = Uri.parse(
            "content://net.victoralonso.unit7.creatingcontentprovider/Users");
    private UsersSqliteHelper usersHelper;
    private static final String DB_USERS = "DBUsers";
    private static final int DB_VERSION = 1;
    private static final String TABLE_USERS = "Users";

    private static final int USERS = 1;
    private static final int USERS_ID = 2;
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("net.victoralonso.unit7.creatingcontentprovider","Users", USERS);
        uriMatcher.addURI("net.victoralonso.unit7.creatingcontentprovider","Users/#", USERS_ID);
    }

    @Override
    public boolean onCreate() {
        usersHelper = new UsersSqliteHelper(getContext(), DB_USERS, null, DB_VERSION);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String orderBy) {
        String where = selection;
        if (uriMatcher.match(uri) == USERS_ID) {
            where = "_id="+uri.getLastPathSegment();
        }
        SQLiteDatabase db = usersHelper.getWritableDatabase();
        Cursor c = db.query(TABLE_USERS, projection, where, selectionArgs, null, null, orderBy);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        String where = selection;
        int count;
        if (uriMatcher.match(uri) == USERS_ID) {
            where = "_id="+uri.getLastPathSegment();
        }
        SQLiteDatabase db = usersHelper.getWritableDatabase();
        count = db.update(TABLE_USERS, contentValues, where, selectionArgs);
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String where = selection;
        int count;
        if (uriMatcher.match(uri) == USERS_ID) {
            where = "_id="+uri.getLastPathSegment();
        }
        SQLiteDatabase db = usersHelper.getWritableDatabase();
        count = db.delete(TABLE_USERS, where, selectionArgs);
        return count;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long regId = 1;
        SQLiteDatabase db = usersHelper.getWritableDatabase();
        regId = db.insert(TABLE_USERS, null, contentValues);
        Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);
        return newUri;
    }

    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match) {
            case USERS:
                return "vnd.android.cursor.dir/vnd.aguilar.user";
            case USERS_ID:
                return "vnd.android.cursor.item/vnd.aguilar.user";
        }
        return null;
    }
}
