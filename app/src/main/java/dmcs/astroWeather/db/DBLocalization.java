package dmcs.astroWeather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mateusz on 2016-05-28.
 */
public class DBLocalization extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "androidDatabase.db25";
    public static final String LOCATION_TABLE_NAME = "localization";
    public static final String LOCATION_COLUMN_ID = "id";
    public static final String LOCATION_COLUMN_WOEID = "woeid";
    public static final String LOCATION_COLUMN_LATITUDE = "latitude";
    public static final String LOCATION_COLUMN_LONGITUDE = "longitude";
    public static final String LOCATION_COLUMN_CITY = "city";
    public static final String LOCATION_COLUMN_COUNTRY = "country";
    public static final String LOCATION_COLUMN_NAME = "name";
    public static final String LOCATION_COLUMN_WEATHER = "weather";
    public static final String LOCATION_COLUMN_FORECAST = "forecast";
    public static final String LOCATION_COLUMN_LAST_WEATHER_UPDATE = "lastWeatherUpdate";

    private final String CREATE_LOCALIZATION_TABLE = "CREATE TABLE IF NOT EXISTS Parameter(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "paramName VARCHAR, paramValue VARCHAR);";
    private final String CREATE_PARAMETER_TABLE = "CREATE TABLE IF NOT EXISTS localization(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "woeid VARCHAR, latitude VARCHAR, longitude VARCHAR, city VARCHAR, country VARCHAR, name VARCHAR, " +
            "lastWeatherUpdate VARCHAR, weather VARCHAR, forecast, VARCHAR);";
    private final String DROP_LOCALIZATION_TABLE = "DROP TABLE IF EXISTS localization;";
    private final String DROP_PARAMETER_TABLE = "DROP TABLE IF EXISTS Parameter;";

    public DBLocalization(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LOCALIZATION_TABLE);
        db.execSQL(CREATE_PARAMETER_TABLE);
        generateData(db);
    }

    private void generateData(SQLiteDatabase db) {
        db.execSQL("INSERT INTO Parameter (paramName, paramValue) VALUES ('REFRESH_INTERVAL_IN_SEC', '10')");
        db.execSQL("INSERT INTO Parameter (paramName, paramValue) VALUES ('LOCALIZATION_NAME', 'Lodz')");
        db.execSQL("INSERT INTO Parameter (paramName, paramValue) VALUES ('SPEED_UNIT', 'km/h')");
        db.execSQL("INSERT INTO Parameter (paramName, paramValue) VALUES ('PRESSURE_UNIT', 'mb')");
        db.execSQL("INSERT INTO Parameter (paramName, paramValue) VALUES ('TEMPERATURE_UNIT', '°C')");
        db.execSQL("INSERT INTO localization (name, latitude, longitude, city, country) VALUES ('Lodz', '51', '19.5', 'lodz', 'pl')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_LOCALIZATION_TABLE);
        db.execSQL(DROP_PARAMETER_TABLE);
        onCreate(db);
    }

    public boolean insertLocation(String name, String woeid, String latitude, String longitude,
                                  String city, String country, String weather, String forecast, String lastUpdate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOCATION_COLUMN_NAME, name);
        contentValues.put(LOCATION_COLUMN_WOEID, woeid);
        contentValues.put(LOCATION_COLUMN_LATITUDE, latitude);
        contentValues.put(LOCATION_COLUMN_LONGITUDE, longitude);
        contentValues.put(LOCATION_COLUMN_CITY, city);
        contentValues.put(LOCATION_COLUMN_COUNTRY, country);
        contentValues.put(LOCATION_COLUMN_WEATHER, weather);
        contentValues.put(LOCATION_COLUMN_FORECAST, forecast);
        contentValues.put(LOCATION_COLUMN_LAST_WEATHER_UPDATE, lastUpdate);
        db.insert(LOCATION_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean insertLocation(Localization localization) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOCATION_COLUMN_NAME, localization.getName());
        contentValues.put(LOCATION_COLUMN_WOEID, localization.getWoeid());
        contentValues.put(LOCATION_COLUMN_LATITUDE, localization.getLatitude());
        contentValues.put(LOCATION_COLUMN_LONGITUDE, localization.getLongitude());
        contentValues.put(LOCATION_COLUMN_CITY, localization.getCity());
        contentValues.put(LOCATION_COLUMN_COUNTRY, localization.getCountry());
        contentValues.put(LOCATION_COLUMN_WEATHER, localization.getWeather());
        contentValues.put(LOCATION_COLUMN_FORECAST, localization.getForecast());
        contentValues.put(LOCATION_COLUMN_LAST_WEATHER_UPDATE, localization.getLastUpdate());
        db.insert(LOCATION_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateLocation(Integer id, String name, String woeid, String latitude, String longitude,
                                  String city, String country, String weather, String forecast, String lastUpdate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOCATION_COLUMN_NAME, name);
        contentValues.put(LOCATION_COLUMN_WOEID, woeid);
        contentValues.put(LOCATION_COLUMN_LATITUDE, latitude);
        contentValues.put(LOCATION_COLUMN_LONGITUDE, longitude);
        contentValues.put(LOCATION_COLUMN_CITY, city);
        contentValues.put(LOCATION_COLUMN_COUNTRY, country);
        contentValues.put(LOCATION_COLUMN_WEATHER, weather);
        contentValues.put(LOCATION_COLUMN_FORECAST, forecast);
        contentValues.put(LOCATION_COLUMN_LAST_WEATHER_UPDATE, lastUpdate);
        db.update(LOCATION_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public boolean updateLocation(Localization localization) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOCATION_COLUMN_NAME, localization.getName());
        contentValues.put(LOCATION_COLUMN_WOEID, localization.getWoeid());
        contentValues.put(LOCATION_COLUMN_LATITUDE, localization.getLatitude());
        contentValues.put(LOCATION_COLUMN_LONGITUDE, localization.getLongitude());
        contentValues.put(LOCATION_COLUMN_CITY, localization.getCity());
        contentValues.put(LOCATION_COLUMN_COUNTRY, localization.getCountry());
        contentValues.put(LOCATION_COLUMN_WEATHER, localization.getWeather());
        contentValues.put(LOCATION_COLUMN_FORECAST, localization.getForecast());
        contentValues.put(LOCATION_COLUMN_LAST_WEATHER_UPDATE, localization.getLastUpdate());
        db.update(LOCATION_TABLE_NAME, contentValues, "id = ? ", new String[]{localization.getId()});
        return true;
    }

    public Localization findLocationById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + LOCATION_TABLE_NAME + " WHERE id=" + id + "", null);

        if (cursor.moveToFirst()) {
            Localization localization = new Localization();
            localization.setId(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_ID)));
            localization.setName(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_NAME)));
            localization.setCity(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_CITY)));
            localization.setCountry(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_COUNTRY)));
            localization.setLatitude(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LATITUDE)));
            localization.setLongitude(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LONGITUDE)));
            localization.setWoeid(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_WOEID)));
            localization.setWeather(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_WEATHER)));
            localization.setForecast(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_FORECAST)));
            localization.setLastUpdate(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LAST_WEATHER_UPDATE)));
            return localization;
        } else {
            return null;
        }
    }

    public Localization findLocationByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + LOCATION_TABLE_NAME + " WHERE name='" + name + "'", null);

        if (cursor.moveToFirst()) {
            Localization localization = new Localization();
            localization.setId(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_ID)));
            localization.setName(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_NAME)));
            localization.setCity(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_CITY)));
            localization.setCountry(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_COUNTRY)));
            localization.setLatitude(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LATITUDE)));
            localization.setLongitude(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LONGITUDE)));
            localization.setWoeid(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_WOEID)));
            localization.setWeather(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_WEATHER)));
            localization.setForecast(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_FORECAST)));
            localization.setLastUpdate(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LAST_WEATHER_UPDATE)));
            return localization;
        } else {
            return null;
        }
    }

    public Localization findLocationByWoeid(String woeid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + LOCATION_TABLE_NAME + " WHERE woeid='" + woeid + "'", null);

        if (cursor.moveToFirst()) {
            Localization localization = new Localization();
            localization.setId(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_ID)));
            localization.setName(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_NAME)));
            localization.setCity(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_CITY)));
            localization.setCountry(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_COUNTRY)));
            localization.setLatitude(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LATITUDE)));
            localization.setLongitude(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LONGITUDE)));
            localization.setWoeid(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_WOEID)));
            localization.setWeather(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_WEATHER)));
            localization.setForecast(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_FORECAST)));
            localization.setLastUpdate(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LAST_WEATHER_UPDATE)));
            return localization;
        } else {
            return null;
        }
    }

    public List<Localization> findAllLocation() {
        List<Localization> localizations = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + LOCATION_TABLE_NAME, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Localization localization = new Localization();
            localization.setId(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_ID)));
            localization.setName(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_NAME)));
            localization.setCity(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_CITY)));
            localization.setCountry(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_COUNTRY)));
            localization.setLatitude(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LATITUDE)));
            localization.setLongitude(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LONGITUDE)));
            localization.setWoeid(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_WOEID)));
            localization.setWeather(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_WEATHER)));
            localization.setForecast(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_FORECAST)));
            localization.setLastUpdate(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LAST_WEATHER_UPDATE)));
            localizations.add(localization);
            cursor.moveToNext();
        }
        return localizations;
    }

    public Integer deleteLocation(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(LOCATION_TABLE_NAME, "id = ? ", new String[]{Integer.toString(id)});
    }

    public int getNumberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, LOCATION_TABLE_NAME);
    }
}
