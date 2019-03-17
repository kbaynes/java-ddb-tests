package com.k9b9.dao;

import com.k9b9.dao.TablesDao;
import com.k9b9.ddb.DynDb;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * TablesDaoTest
 */
public class TablesDaoTest {

   TablesDao dao;
    // by default, table name on BaseItem @DynamoDBTable is used
    // can be overridden on save, see DynDb.putItem for example code
    String tableName = "SingleTableTest";

    @Before
    public void beforeTest() {
        // new DynDb(aws_accesskey, aws_secretkey, aws_region);
        dao = new TablesDao(new DynDb());
    }

    @Test
    public void testTableStatus() throws InterruptedException {
        String tableStatus = dao.getTableStatus(this.tableName);
        assertTrue("Table status = ACTIVE", tableStatus.equals("ACTIVE"));
        System.out.println("Table status: " + tableStatus);
    }

    @Test
    public void listTableNames() {
        List<String> tableNames = dao.listTables();
        assertNotNull(tableNames);
        assertTrue(tableName.length()>0);
        System.out.println(tableNames);
    }
}