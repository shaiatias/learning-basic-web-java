package repositories;

import models.Category;

/**
 * Created by shai on 11/15/2017.
 */
public class CategoriesRepository extends FileRepository<Category, Long> {

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


}
