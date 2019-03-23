package com.k9b9.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.k9b9.ddb.SingleTableDdb;
import com.k9b9.dto.Admin;
import com.k9b9.dto.DtoUtils;

/**
 * Admins model object will be used by consuming services, such as Web API
 */
public class Admins {

    public final static String ROOT_ADMIN_PKEY = "_ROOT_ADMIN";
    public final static String ADMIN_SKEY = "admin";
    private SingleTableDdb ddb;

    public Admins(SingleTableDdb ddb) {
        this.ddb = ddb;
    }

    /**
     * Each call will overwrite the current Root User. There can be only one.
     * 
     * @throws JsonProcessingException
     */
    public boolean putRootAdmin(Admin admin) {
        // validate Admin
        if (!ROOT_ADMIN_PKEY.equals(admin.pkey)) return false;
        if (!ADMIN_SKEY.equals(admin.skey)) return false;
        if (admin.jsonValue.adminLevel!=AdminLevel.ROOT.getValue()) return false;
        // build Item
        Item item = DtoUtils.toItem(admin);
        this.ddb.putItem(item);
        // TODO add metrics for capacity used
        return true;
    }

    /**
     * Allow mulitiple admins with the same userID
     * 
     * @throws JsonProcessingException
     */
    public boolean putAdmin(Admin admin) {
        // validate Admin
        // do not add root via this method
        if (ROOT_ADMIN_PKEY.equals(admin.pkey)) return false;
        // must be in admin sort
        if (!ADMIN_SKEY.equals(admin.skey)) return false;
        // do not add root via this method
        if (admin.jsonValue.adminLevel==AdminLevel.ROOT.getValue()) return false;

        List<Admin> list = this.getAdminsByUserId(admin.jsonValue.userId);
        if (list.size()>=1) return false;

        // build Item
        Item item = DtoUtils.toItem(admin);
        this.ddb.putItem(item);
        // TODO add metrics for capacity used
        return true;
    }

    /**
     * Returns all admins
     */
    public List<Admin> getAdmins() {
        NameMap nameMap = new NameMap().with("#skey", "skey");
        ValueMap valueMap = new ValueMap().withString(":skey", Admins.ADMIN_SKEY);
        ItemCollection<ScanOutcome> items = this.ddb.table.scan("#skey = :skey", nameMap, valueMap);
        Iterator<Item> iter = items.iterator();
        List<Admin> list = new ArrayList<Admin>();
        while (iter.hasNext()) {
            Item item = iter.next();
            Admin admin = DtoUtils.toAdmin(item);
            list.add(admin);
        }
        return list;
    }

    public List<Admin> getAdminsByLevel(AdminLevel level) {
        NameMap nameMap = new NameMap()
            .with("#skey", "skey");
            // .with("#jsonvalue_adminLevel","jsonvalue.adminLevel");
        ValueMap valueMap = new ValueMap()
            .withString(":skey", Admins.ADMIN_SKEY)
            .withInt(":jsonvalue_adminLevel", level.getValue());
        ItemCollection<ScanOutcome> items = 
            this.ddb.table.scan("#skey = :skey AND jsonvalue.adminLevel = :jsonvalue_adminLevel", nameMap, valueMap);
        Iterator<Item> iter = items.iterator();
        List<Admin> list = new ArrayList<Admin>();
        while (iter.hasNext()) {
            Item item = iter.next();
            Admin admin = DtoUtils.toAdmin(item);
            list.add(admin);
        }
        return list;
    }

    public List<Admin> getAdminsByUserId(String userId) {
        NameMap nameMap = new NameMap()
            .with("#skey", "skey");
            // .with("#jsonvalue_adminLevel","jsonvalue.adminLevel");
        ValueMap valueMap = new ValueMap()
            .withString(":skey", Admins.ADMIN_SKEY)
            .withString(":jsonvalue_userId", userId);
        ItemCollection<ScanOutcome> items = 
            this.ddb.table.scan("#skey = :skey AND jsonvalue.userId = :jsonvalue_userId", nameMap, valueMap);
        Iterator<Item> iter = items.iterator();
        List<Admin> list = new ArrayList<Admin>();
        while (iter.hasNext()) {
            Item item = iter.next();
            Admin admin = DtoUtils.toAdmin(item);
            list.add(admin);
        }
        return list;
    }
    
}