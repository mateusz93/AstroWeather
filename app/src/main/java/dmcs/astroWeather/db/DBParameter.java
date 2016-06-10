package dmcs.astroWeather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mateusz on 2016-06-10.
 */
public class DBParameter extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "androidDatabase.db3";
    public static final String PARAMETER_TABLE_NAME = "Parameter";
    public static final String PARAMETER_COLUMN_ID = "id";
    public static final String PARAMETER_COLUMN_PARAM_NAME = "paramName";
    public static final String PARAMETER_COLUMN_PARAM_VALUE = "paramValue";

    private final String CREATE_TABLES = "CREATE TABLE IF NOT EXISTS Parameter(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "paramName VARCHAR, paramValue VARCHAR);";
    private final String DROP_TABLES = "DROP TABLE IF EXISTS Parameter;";

    public DBParameter(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLES);
        generateData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLES);
        onCreate(db);
    }

    private void generateData(SQLiteDatabase db) {
        db.execSQL("INSERT INTO Parameter (paramName, paramValue) VALUES ('REFRESH_INTERVAL_IN_SEC', '10')");
        db.execSQL("INSERT INTO Parameter (paramName, paramValue) VALUES ('LOCALIZATION_NAME', 'Lodz')");
        db.execSQL("INSERT INTO Parameter (paramName, paramValue) VALUES ('SPEED_UNIT', 'km/h')");
        db.execSQL("INSERT INTO Parameter (paramName, paramValue) VALUES ('PRESSURE_UNIT', 'mb')");
        db.execSQL("INSERT INTO Parameter (paramName, paramValue) VALUES ('TEMPERATURE_UNIT', 'C')");
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

    public boolean insertParameter(String paramName, String paramValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PARAMETER_COLUMN_PARAM_NAME, paramName);
        contentValues.put(PARAMETER_COLUMN_PARAM_VALUE, paramValue);
        db.insert(PARAMETER_TABLE_NAME, null, contentValues);
        return true;
    }

    public String findParamValueByParamName(String paramName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PARAMETER_TABLE_NAME + " WHERE " +
                PARAMETER_COLUMN_PARAM_NAME + "='" + paramName + "'", null);
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex(PARAMETER_COLUMN_PARAM_VALUE));
    }

    public int findParamIdByParamName(String paramName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PARAMETER_TABLE_NAME + " WHERE " +
                PARAMETER_COLUMN_PARAM_NAME + "='" + paramName + "'", null);
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex(PARAMETER_COLUMN_ID));
    }

}
