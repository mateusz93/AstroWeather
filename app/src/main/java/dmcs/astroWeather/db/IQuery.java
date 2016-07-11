package dmcs.astroWeather.db;

import java.util.List;

/**
 * @Author Mateusz Wieczorek on 2016-07-11.
 */
public interface IQuery {

    List<String> getCreateTablesQueries();
    List<String> getDataQueries();
    List<String> getDropTablesQueries();
}
