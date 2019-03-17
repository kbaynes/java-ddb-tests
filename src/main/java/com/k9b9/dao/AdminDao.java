package com.k9b9.dao;

import com.k9b9.ddb.DynDb;
import com.k9b9.entity.AdminItem;

/**
 * AdminDao
 */
public class AdminDao {

    private DynDb dynDb;

    public AdminDao(DynDb dynDb) {
        this.dynDb = dynDb;
    }

    /**
     * Create and Update are same operation
     */
    public AdminItem putAdmin(AdminItem admin) {
        this.dynDb.dynamoDbMapper.save(admin);
        return admin;
    }
}