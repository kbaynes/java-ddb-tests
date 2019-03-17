package com.k9b9.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.k9b9.ddb.DynDb;
import com.k9b9.entity.AdminItem;

import org.junit.Before;
import org.junit.Test;

/**
 * This class tests with a direct DDB connection to a single DynamoDB table. 
 * By default it uses the credentials in ~/.aws/credentials
 * 
 * Many of the tests must be run directly
 * mvn test -Dtest=BaseItemDaoTest#testCreateRandomUser
 */
public class AdminDaoTest {

    AdminDao dao;

    @Before
    public void beforeTest() {
        // this.myDb = new DynDb("YourKey", "YourSecretKey", "us-east-1");
        this.dao = new AdminDao(new DynDb());
    }

    @Test
    public void testCreateRandomAdmin() {
        String uuid = java.util.UUID.randomUUID().toString();
        AdminItem admin = new AdminItem(uuid, "adminName", "adminUid", "adminHashedPass", AdminItem.Level.ADMIN);
        admin = this.dao.putAdmin(admin);
        assertNotNull(admin);
        assertTrue(admin.skey.equals("admin"));
        System.out.println("CreateRandomAdmin = "+admin);
    }

}