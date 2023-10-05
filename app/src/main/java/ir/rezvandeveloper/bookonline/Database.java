package ir.rezvandeveloper.bookonline;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    public static final String TABLE_PRESCRIPTION = "titr";
    private static final String DATABASE_NAME = "titr.db";
    public static final int DATABASE_VERSION = 1;

    private static final String[] allColumns = {ModelDB.KEY_ID, ModelDB.KEY_LESSON,
            ModelDB.KEY_NUMBER, ModelDB.KEY_TITR};

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public List<ModelDB> getAllPrescription() {
        SQLiteDatabase db = getReadableDatabase();
        List<ModelDB> prescriptionList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM '" + TABLE_PRESCRIPTION + "'", null);
Log.i("TAGid1", "Returned " + cursor.getCount() + " rows.");
        if (cursor.moveToFirst()) {
            do {
                // process for each row
                ModelDB modelDB = ModelDB.cursorToPrescription(cursor);
                prescriptionList.add(modelDB);
Log.i("TAGid2", "Returned: " + modelDB.getTitr());
            } while (cursor.moveToNext());
        }
        cursor.close();
        if (db.isOpen()) db.close();
        return prescriptionList;
    }

    public void getPrescriptions(List<ModelDB> prescriptionList, String selection, String[] selArgs) {
        SQLiteDatabase db = getReadableDatabase();
        prescriptionList.clear();

        Cursor cursor = db.query(TABLE_PRESCRIPTION, allColumns, selection, selArgs, null,null, null, "1000");

        if (cursor.moveToFirst()) {
            do {
                ModelDB modelDB = ModelDB.cursorToPrescription(cursor);
                prescriptionList.add(modelDB);
            } while (cursor.moveToNext());
        }
    }

    public ModelDB getPrescription(int number) {
        SQLiteDatabase db = getReadableDatabase();
        ModelDB modelDB = new ModelDB();
        //List<Prescription> prescriptionList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM '" + TABLE_PRESCRIPTION + "' WHERE number = '" + number + "'", null);
        if (cursor.moveToFirst()) {
            modelDB = ModelDB.cursorToPrescription(cursor);
        }
        cursor.close();
        if (db.isOpen()) db.close();
        return modelDB;
    }
}