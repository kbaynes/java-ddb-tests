package com.k9b9.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.k9b9.ddb.DynDb;

/**
 * TablesDao
 */
public class TablesDao {

    private DynDb dynDb;

    public TablesDao(DynDb dynDb) {
        this.dynDb = dynDb;
    }

    public String getTableStatus(String tableName) throws InterruptedException {
        Table table = dynDb.dynamoDb.getTable(tableName);
        table.waitForActive();
        return table.getDescription().getTableStatus();
    }

    public List<String> listTables() {
        TableCollection<ListTablesResult> tables = dynDb.dynamoDb.listTables();
        Iterator<Table> iterator = tables.iterator();
        List<String> list = new ArrayList<String>();
        while (iterator.hasNext()) {
            Table table = iterator.next();
            list.add(table.getTableName());
        }
        return list;
    }
}