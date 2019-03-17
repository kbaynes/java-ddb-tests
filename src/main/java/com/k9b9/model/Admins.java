package com.k9b9.model;

import com.k9b9.dao.AdminDao;
import com.k9b9.entity.AdminItem;
import com.k9b9.entity.AdminItem.Level;

/**
 * Admins model object will be used by consuming services, such as Web API
 */
public class Admins {

    public final String ROOT_PKEY = "_ROOT_ADMIN";
    public final String ROOT_NAME = "root";
    public final String ROOT_USER_ID = "root";
    private AdminDao dao;

    public Admins(AdminDao dao) {
        this.dao = dao;
    }

    public AdminItem createRootAdmin(String hashedPass) {

        AdminItem root = new AdminItem(ROOT_PKEY, ROOT_NAME, ROOT_USER_ID, hashedPass, Level.ROOT);
        dao.putAdmin(root);
        return root;
    }
}