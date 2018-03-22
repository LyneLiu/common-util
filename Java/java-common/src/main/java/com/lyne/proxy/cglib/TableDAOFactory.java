package com.lyne.proxy.cglib;


import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * @author nn_liu
 * @Created 2018-03-22-10:13
 */

public class TableDAOFactory {

    private static TableDAO tableDAO = new TableDAO();


    public static TableDAO getInstance() {
        return tableDAO;
    }

    /**
     * Cglib代理模式
     * @param authProxy
     * @return
     */
    public static TableDAO getAuthInstance(AuthProxy authProxy) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TableDAO.class);
        enhancer.setCallback(authProxy);

        return (TableDAO)enhancer.create();
    }

    public static TableDAO getAuthInstanceByFilter(AuthProxy authProxy) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TableDAO.class);
        // Note:CallBacks数组是有序的，和CallbackFilter里边的顺序一致
        enhancer.setCallbacks(new Callback[]{authProxy, NoOp.INSTANCE});
        enhancer.setCallbackFilter(new AuthProxyFilter());

        return (TableDAO)enhancer.create();
    }
}
