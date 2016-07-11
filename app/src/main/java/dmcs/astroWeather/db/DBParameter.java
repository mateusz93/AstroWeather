package dmcs.astroWeather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @Author Mateusz Wieczorek on 2016-06-10.
 */
public class DBParameter extends SQLiteOpenHelper {

    public static final String PARAMETER_TABLE_NAME = "Parameter";
    public static final String PARAMETER_COLUMN_ID = "id";
    public static final String PARAMETER_COLUMN_PARAM_NAME = "paramName";
    public static final String PARAMETER_COLUMN_PARAM_VALUE = "paramValue";
    private QueryProvider queryProvider;

    public DBParameter(Context context) {
        super(context, QueryProvider.DATABASE_NAME, null, 1);
        queryProvider = new QueryProvider();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String query : queryProvider.getCreateTablesQueries()) {
            db.execSQL(query);
        }
        for (String query : queryProvider.getDataQueries()) {
            db.execSQL(query);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (String query : queryProvider.getDropTablesQueries()) {
            db.execSQL(query);
        }
        onCreate(db);
    }

    public boolean updateParameter(String paramName, String paramValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PARAMETER_COLUMN_PARAM_NAME, paramName);
        contentValues.put(PARAMETER_COLUMN_PARAM_VALUE, paramValue);
        String paramId = String.valueOf(findParamIdByParamName(paramName));
        db.update(PARAMETER_TABLE_NAME, contentValues, "id = ? ", new String[]{paramId});
        return true;
    }

    public String findParamValueByParamName(String paramName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PARAMETER_TABLE_NAME + " WHERE " +
                PARAMETER_COLUMN_PARAM_NAME + "='" + paramName + "'", null);
        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex(PARAMETER_COLUMN_PARAM_VALUE));
        }
        return "";
    }

    public int findParamIdByParamName(String paramName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PARAMETER_TABLE_NAME + " WHERE " +
                PARAMETER_COLUMN_PARAM_NAME + "='" + paramName + "'", null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex(PARAMETER_COLUMN_ID));
        }
        return -1;
    }
}
