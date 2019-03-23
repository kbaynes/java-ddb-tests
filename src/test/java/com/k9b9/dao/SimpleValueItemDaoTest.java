package com.k9b9.dao;

import java.util.Date;
import java.util.List;

import com.k9b9.dao.SimpleValueItemDao;
import com.k9b9.ddb.SingleTableDdb;
import com.k9b9.entity.SimpleValueItem;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This class tests with a direct DDB connection to a single DynamoDB table. 
 * By default it uses the credentials in ~/.aws/credentials
 * 
 * Many of the tests must be run directly
 * mvn test -Dtest=BaseItemDaoTest#testCreateRandomUser
 */
public class SimpleValueItemDaoTest {

    SimpleValueItemDao dao;

    @Before
    public void beforeTest() {
        // this.myDb = new DynDb("YourKey", "YourSecretKey", "us-east-1", "SingleTableTest");
        this.dao = new SimpleValueItemDao(new SingleTableDdb("SingleTableTest"));
    }

    @Test
    public void testCreateRandomUser() {
        String uuid = java.util.UUID.randomUUID().toString();
        SimpleValueItem item = this.dao.putBaseItem(uuid, "user", String.format("{'date':'%s'}",(new Date()).toString()));
        assertNotNull(item);
        assertTrue(item.skey.equals("user"));
        System.out.println("CreateRandomUser = "+item);
    }

    /**
     * This method will overwrite the existing user. So create and update are essentially the same.
     */
    @Test
    public void testPutTestUser() {
        SimpleValueItem item = this.dao.putBaseItem("test-user", "user", String.format("{'date':'%s'}",(new Date()).toString()));
        assertNotNull(item);
        System.out.println("PutTestUser = "+item);
    }

    @Test
    public void testGetTestUser() {
        SimpleValueItem item = this.dao.getBaseItem("test-user", "user");
        assertNotNull(item);
        System.out.println("Item retrieved:");
        System.out.println(item);
    }

    /**
     * JUnit won't guarantee that this test runs after the create test.
     * Run it manually to test.
     */
    @Ignore
    @Test
    public void testDeleteTestUser() {
        this.dao.deleteBaseItem("test-user", "user");
        System.out.println("Item deleted");
    }

    @Test
    public void testGetBaseItemsBySortKeyEquals() {
        List<SimpleValueItem> users = this.dao.getBaseItemsBySortKeyEquals("user");
        assertNotNull(users);
        for (SimpleValueItem item : users) {
            System.out.println("ItemBySortKey = "+item);
        }

    }

}