package pl.surecase.eu.greendaoexample.backend.repositories;

import android.content.Context;

import java.util.List;

import greendao.Box;
import greendao.BoxDao;
import pl.surecase.eu.greendaoexample.DaoExampleApplication;

/**
 * Created by surecase on 19/03/14.
 */
public class BoxRepository {

    public static void insertOrUpdate(Context context, Box box) {
        getBoxDao(context).insertOrReplace(box);
    }

    public static void clearBoxes(Context context) {
        getBoxDao(context).deleteAll();
    }

    public static void deleteBoxWithId(Context context, long id) {
        getBoxDao(context).delete(getBoxForId(context, id));
    }

    public static Box getBoxForId(Context context, long id) {
        return getBoxDao(context).load(id);
    }

    public static List<Box> getAllBoxes(Context context) {
        return getBoxDao(context).loadAll();
    }

    private static BoxDao getBoxDao(Context c) {
        return ((DaoExampleApplication) c.getApplicationContext()).getDaoSession().getBoxDao();
    }
}
