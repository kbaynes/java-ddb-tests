package com.k9b9;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import com.k9b9.ddb.DynDb;
import com.k9b9.model.BaseItem;

import org.junit.Before;
import org.junit.Test;

/**
 * This class tests with a direct DDB connection to a single DynamoDB table. 
 * By default it uses the credentials in ~/.aws/credentials
 */
public class RemoteTest {

    DynDb myDb;
    // by default, table name on BaseItem @DynamoDBTable is used
    // can be overridden on save, see DynDb.putItem for example code
    String tableName = "SingleTableTest";
    String aws_accesskey = "YourKey";
    String aws_secretkey = "YourSecretKey";
    String aws_region = "us-east-1";

    @Before
    public void beforeTest() {
        // this.myDb = new DynDb(aws_accesskey, aws_secretkey, aws_region);
        this.myDb = new DynDb();
    }

    @Test
    public void testTableStatus() throws InterruptedException {
        String tableStatus = myDb.getTableStatus(this.tableName);
        assertTrue("Table status = ACTIVE", tableStatus.equals("ACTIVE"));
        System.out.println("Table status: " + tableStatus);
    }

    @Test
    public void listTableNames() {
        List<String> tableNames = myDb.listTables();
        assertNotNull(tableNames);
        assertTrue(tableName.length()>0);
        System.out.println(tableNames);
    }

    @Test
    public void testCreateRandomUser() {
        String uuid = java.util.UUID.randomUUID().toString();
        BaseItem item = myDb.putBaseItem(uuid, "user", String.format("{'date':'%s'}",(new Date()).toString()));
        System.out.println(item);
    }

    /**
     * This method will overwrite the existing user. So create and update are essentially the same.
     */
    @Test
    public void testPutTestUser() {
        BaseItem item = myDb.putBaseItem("test-user", "user", String.format("{'date':'%s'}",(new Date()).toString()));
        System.out.println(item);
    }

    @Test
    public void testGetTestUser() {
        BaseItem item = myDb.getBaseItem("test-user", "user");
        System.out.println("Item retrieved:");
        System.out.println(item);
    }

    @Test
    public void testDeleteTestUser() {
        myDb.deleteBaseItem("test-user", "user");
        System.out.println("Item deleted");
    }

    @Test
    public void testGetBaseItemsBySortKeyEquals() {
        List<BaseItem> users = myDb.getBaseItemsBySortKeyEquals("user");
        for (BaseItem item : users) {
            System.out.println(item);
        }
    }

}