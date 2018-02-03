package repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import models.Category;

/**
 * Created by shai on 11/15/2017.
 */
public class CategoriesRepository extends DBRepository<Category, Long> {

    private static CategoriesRepository instance = null;

    public static CategoriesRepository getInstance() {

        if (instance != null){
            return instance;
        }

        else {
            instance = new CategoriesRepository();
            return instance;
        }
    }

    private CategoriesRepository() {
        super("categories");
    }

    @Override
    protected Category resultSetToEntity(ResultSet rs) throws SQLException {
        
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        
        return new Category(id, name);
    }


}
