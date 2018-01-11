package repositories;

import java.io.Serializable;

/**
 * Created by shai on 11/19/2017.
 */
public interface Entity<T> extends Serializable {
    T id();
}
