package dmcs.astroWeather.db;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Mateusz Wieczorek on 2016-07-11.
 */
public class QueryProvider implements IQuery {

    public static String DATABASE_NAME = "androidDatabase.db1";
    private final String CREATE_PARAMETER_TABLE = "CREATE TABLE IF NOT EXISTS Parameter(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "paramName VARCHAR, paramValue VARCHAR);";
    private final String CREATE_LOCALIZATION_TABLE = "CREATE TABLE IF NOT EXISTS Localization(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "woeid VARCHAR, latitude VARCHAR, longitude VARCHAR, city VARCHAR, country VARCHAR, name VARCHAR, " +
            "lastWeatherUpdate VARCHAR, weather VARCHAR, forecast, VARCHAR);";
    private final String DROP_LOCALIZATION_TABLE = "DROP TABLE IF EXISTS Localization;";
    private final String DROP_PARAMETER_TABLE = "DROP TABLE IF EXISTS Parameter;";

    @Override
    public List<String> getCreateTablesQueries() {
        List<String> queries = new ArrayList<>();
        queries.add(CREATE_LOCALIZATION_TABLE);
        queries.add(CREATE_PARAMETER_TABLE);
        return queries;
    }

    @Override
    public List<String> getDropTablesQueries() {
        List<String> queries = new ArrayList<>();
        queries.add(DROP_LOCALIZATION_TABLE);
        queries.add(DROP_PARAMETER_TABLE);
        return queries;
    }

    @Override
    public List<String> getDataQueries() {
        List<String> queries = new ArrayList<>();
        queries.add("INSERT INTO Parameter (paramName, paramValue) VALUES ('REFRESH_INTERVAL_IN_SEC', '10');");
        queries.add("INSERT INTO Parameter (paramName, paramValue) VALUES ('LOCALIZATION_NAME', 'Lodz');");
        queries.add("INSERT INTO Parameter (paramName, paramValue) VALUES ('SPEED_UNIT', 'km/h');");
        queries.add("INSERT INTO Parameter (paramName, paramValue) VALUES ('TEMPERATURE_UNIT', '°C')");
        queries.add("INSERT INTO Parameter (paramName, paramValue) VALUES ('PRESSURE_UNIT', 'mb');");
        queries.add("INSERT INTO localization (name, latitude, longitude, city, country) VALUES ('Lodz', '51', '19.5', 'lodz', 'pl');");
        return queries;
    }
}
