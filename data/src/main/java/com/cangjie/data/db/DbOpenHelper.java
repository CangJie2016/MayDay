package com.cangjie.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Debug;
import android.util.Log;

import com.anye.greendao.gen.DaoMaster;
import com.anye.greendao.gen.PasswordDao;

/**
 * Created by 李振强 on 2017/8/24.
 */

public class DbOpenHelper extends DaoMaster.OpenHelper {

//    private static final String DATABASE_CREATE_TEAM = "create table "
//            + TABLE_TEAM + "(" + COLUMN_ID + " integer primary key autoincrement, "
//            + COLUMN_NAME + " string, "
//            + COLUMN_MASCOT + " string, "
//            + COLUMN_COACH + " string, "
//            + COLUMN_STADIUM + " string, "
//            + COLUMN_CITY + " string);";

    public DbOpenHelper(Context context, String name) {
        super(context, name);
    }

    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        Log.w("Log", oldVersion + "oldVersion");
//        if (oldVersion <= 2){
//            db.execSQL("CREATE TABLE " + "\"PASSWORD\" (" + //
//                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
//                "\"TITLE\" TEXT," + // 1: title
//                "\"USERNAME\" TEXT," + // 2: username
//                "\"PASSWORD\" TEXT," + // 3: password
//                "\"REMARKS\" TEXT," + // 4: remarks
//                "\"DATE\" INTEGER," + // 5: date
//                "\"IS_DELE\" INTEGER NOT NULL );"); // 6: isDele
//        }
        super.onUpgrade(db, oldVersion, newVersion);

    }
}
