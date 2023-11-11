package ir.rezvandeveloper.bookonline.DB;

import android.database.Cursor;

public class ModelDB {
    public static final String KEY_ID = "id";
    public static final String KEY_NUMBER = "number";
    public static final String KEY_LESSON = "lesson";
    public static final String KEY_TITR = "titr";
    public static final String KEY_LINK = "link";

    private int id;
    private String lesson;
    private String number;
    private String titr;
    private String link;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public String getLesson() {
        return lesson;
    }
    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getTitr() {
        return titr;
    }
    public void setTitr(String titr) {
        this.titr = titr;
    }

    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }


    public static ModelDB cursorToPrescription(Cursor cursor){

        ModelDB modelDB = new ModelDB();
        modelDB.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        modelDB.setLesson(cursor.getString(cursor.getColumnIndex(KEY_LESSON)));
        modelDB.setNumber(cursor.getString(cursor.getColumnIndex(KEY_NUMBER)));
        modelDB.setTitr(cursor.getString(cursor.getColumnIndex(KEY_TITR)));
        modelDB.setLink(cursor.getString(cursor.getColumnIndex(KEY_LINK)));
        return modelDB;
    }
}
