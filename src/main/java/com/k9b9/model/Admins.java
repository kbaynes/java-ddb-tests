package com.k9b9.model;

import com.k9b9.dao.AdminDao;
import com.k9b9.entity.AdminItem;
import com.k9b9.model.Admin.Level;

/**
 * Admins model object will be used by consuming services, such as Web API
 */
public class Admins {

    public final static String ROOT_PKEY = "_ROOT_ADMIN";
    private AdminDao dao;

    public Admins(AdminDao dao) {
        this.dao = dao;
    }

    /**
     * Each call will overwrite the current Root User. There can be only one.
     */
    public AdminItem putRootAdmin(String name, String userId, String hashedPass) {
        AdminItem root = new AdminItem(Admins.ROOT_PKEY, name, userId, hashedPass, Level.ROOT);
        dao.putAdmin(root);
        return root;
    }

    public AdminItem getAdmin(String userId) {
        return dao.getAdmin(userId);
    }
}