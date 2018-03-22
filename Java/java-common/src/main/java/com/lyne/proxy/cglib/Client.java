package com.lyne.proxy.cglib;

/**
 * @author nn_liu
 * @Created 2018-03-22-10:14
 */

public class Client {

    public static void main(String[] args) {
        TableDAO tableDAO = TableDAOFactory.getAuthInstanceByFilter(new AuthProxy("f"));
        doMethod(tableDAO);
    }

    /**
     * result:
     *
     * No Permission!
     * query() is running !
     * No Permission!
     * No Permission!
     *
     * @param dao
     */
    public static void doMethod(TableDAO dao){
        dao.create();
        dao.query();
        dao.update();
        dao.delete();
    }

}
