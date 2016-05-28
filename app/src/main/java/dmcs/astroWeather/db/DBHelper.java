package dmcs.astroWeather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz on 2016-05-28.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "androidDatabase.db";
    public static final String LOCATION_TABLE_NAME = "location";
    public static final String LOCATION_COLUMN_ID = "id";
    public static final String LOCATION_COLUMN_WOEID = "woeid";
    public static final String LOCATION_COLUMN_LATITUDE = "latitude";
    public static final String LOCATION_COLUMN_LONGITUDE = "longitude";
    public static final String LOCATION_COLUMN_CITY = "city";
    public static final String LOCATION_COLUMN_COUNTRY = "country";
    public static final String LOCATION_COLUMN_NAME = "name";

    private final String CREATE_TABLES = "CREATE TABLE IF NOT EXISTS location(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "woeid VARCHAR, latitude VARCHAR, longitude VARCHAR, city VARCHAR, country VARCHAR, name VARCHAR);";
    private final String DROP_TABLES = "DROP TABLE IF EXISTS location;";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLES);
        generateData(db);
    }

    private void generateData(SQLiteDatabase db) {
        db.execSQL("INSERT INTO location (name, city, country) VALUES ('Lodz', 'lodz', 'pl')");
        db.execSQL("INSERT INTO location (name, city, country) VALUES ('Poznan', 'poznan', 'pl')");
        db.execSQL("INSERT INTO location (name, city, country) VALUES ('Wroclaw', 'wroclaw', 'pl')");
        db.execSQL("INSERT INTO location (name, city, country) VALUES ('Warszawa', 'warszawa', 'pl')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLES);
        onCreate(db);
    }

    public boolean insertLocation(String name, String woeid, String latitude, String longitude, String city, String country) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("woeid", woeid);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        contentValues.put("city", city);
        contentValues.put("country", country);
        db.insert("location", null, contentValues);
        return true;
    }

    public boolean updateLocation(Integer id, String name, String woeid, String latitude, String longitude, String city, String country) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("woeid", woeid);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        contentValues.put("city", city);
        contentValues.put("country", country);
        db.update("location", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Location findLocationById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from location where id=" + id + "", null);
        cursor.moveToFirst();

        Location location = new Location();
        location.setId(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_ID)));
        location.setName(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_NAME)));
        location.setCity(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_CITY)));
        location.setCountry(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_COUNTRY)));
        location.setLatitude(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LATITUDE)));
        location.setLongitude(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LONGITUDE)));
        location.setWoeid(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_WOEID)));

        return location;
    }

    public List<Location> findAllLocation() {
        List<Location> locations = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("select * from location", null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Location location = new Location();
            location.setId(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_ID)));
            location.setName(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_NAME)));
            location.setCity(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_CITY)));
            location.setCountry(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_COUNTRY)));
            location.setLatitude(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LATITUDE)));
            location.setLongitude(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LONGITUDE)));
            location.setWoeid(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_WOEID)));
            locations.add(location);
            cursor.moveToNext();
        }
        return locations;
    }

    public Integer deleteLocation(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("location", "id = ? ", new String[]{Integer.toString(id)});
    }

    public int getNumberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, LOCATION_TABLE_NAME);
    }
}
