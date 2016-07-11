package dmcs.astroWeather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Mateusz Wieczorek on 2016-05-28.
 */
public class DBLocalization extends SQLiteOpenHelper {

    public static final String LOCATION_TABLE_NAME = "Localization";
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
    private QueryProvider queryProvider;

    public DBLocalization(Context context) {
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

    public List<Localization> findAllLocation() {
        List<Localization> localizations = new ArrayList<>();

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

}
