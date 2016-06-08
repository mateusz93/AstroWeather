package dmcs.astroWeather.exception;

import android.widget.ExpandableListView;

/**
 * Created by Mateusz on 2016-06-08.
 */
public class IncorrectLocalizationException extends Exception {

    public IncorrectLocalizationException() {
        super();
    }

    public IncorrectLocalizationException(String message) {
        super(message);
    }
}
