package com.k9b9.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.k9b9.dao.AdminDao;
import com.k9b9.ddb.DynDb;
import com.k9b9.entity.AdminItem;

import org.junit.Before;
import org.junit.Test;

/**
 * AdminsTest
 */
public class AdminsTest {

    Admins admins;

    @Before
    public void beforeTest() {
        // this.myDb = new DynDb("YourKey", "YourSecretKey", "us-east-1");
        this.admins = new Admins(new AdminDao(new DynDb()));
    }

    @Test
    public void testCreateRootAdmin() {
        AdminItem item = this.admins.putRootAdmin("rootName","rootUserId","hashedPass2");
        assertNotNull(item);
        assertTrue(item.skey.equals("admin"));
        System.out.println("CreateRootAdmin = "+item);
    }
}