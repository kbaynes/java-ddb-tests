package com.k9b9.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * DocumentValueItem
 */
@DynamoDBTable(tableName = "SingleTableTest")
public class DocumentValueItem {

    public String pkey;
    public String skey;
    public SimpleDoc simpleDoc;
    public BareDoc bareDoc;
    private CompoundDoc compoundDoc;

    public DocumentValueItem() {

    }

    public DocumentValueItem(String pkey, String skey, SimpleDoc simpleDoc) {
        this.pkey = pkey;
        this.skey = skey;
        this.simpleDoc = simpleDoc;
    }

    @Override
    public String toString() {
        return "Item [pkey=" + pkey + ", skey=" + skey + ", simpleDoc=" + simpleDoc + "]";
    }

    @DynamoDBHashKey(attributeName = "pkey")
    public String getPkey() {
        return this.pkey;
    }

    public void setPkey(String pkey) {
        this.pkey = pkey;
    }

    @DynamoDBRangeKey(attributeName = "skey")
    public String getSkey() {
        return this.skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    @DynamoDBAttribute(attributeName="SimpleDoc")  
    public SimpleDoc getSimpleDoc() { return this.simpleDoc; }
    public void setSimpleDoc(SimpleDoc simpleDoc) { this.simpleDoc = simpleDoc; }

    @DynamoDBAttribute(attributeName="BareDoc")  
    public BareDoc getBareDoc() { return this.bareDoc; }
    public void setBareDoc(BareDoc bareDoc) { this.bareDoc = bareDoc; }

    @DynamoDBAttribute(attributeName="CompoundDoc")  
    public CompoundDoc getCompoundDoc() { return this.compoundDoc; }
    public void setCompoundDoc(CompoundDoc compoundDoc) { this.compoundDoc = compoundDoc; }

    /**
     * Class with scalar values
     */
    @DynamoDBDocument
    public static class SimpleDoc {

        public String strVal;
        public int intVal;
        public float floatVal;

        public SimpleDoc(String strVal, int intVal, float floatVal) {
            this.strVal = strVal;
            this.intVal = intVal;
            this.floatVal = floatVal;
        }

        @Override
        public String toString() {
            return String
                    .format(
                        "SimpleDoc [strVal=%s, intVal=%s, floatVal=%s]",
                        this.strVal,this.intVal,this.floatVal);
        }

        /**
         * @return the strVal
         */
        @DynamoDBAttribute(attributeName = "strVal")
        public String getStrVal() {
            return strVal;
        }

        /**
         * @param strVal the strVal to set
         */
        public void setStrVal(String strVal) {
            this.strVal = strVal;
        }

        /**
         * @return the intVal
         */
        @DynamoDBAttribute(attributeName = "intVal")
        public int getIntVal() {
            return intVal;
        }

        /**
         * @param intVal the intVal to set
         */
        public void setIntVal(int intVal) {
            this.intVal = intVal;
        }

        /**
         * @return the floatVal
         */
        @DynamoDBAttribute(attributeName = "floatVal")
        public float getFloatVal() {
            return floatVal;
        }

        /**
         * @param floatVal the floatVal to set
         */
        public void setFloatVal(float floatVal) {
            this.floatVal = floatVal;
        }
    }

    /**
     * Testing class with only scalar values without accessor annotations
     * Fail: Test without accessors and annotations, must have annotations on accessors
     * Fail: Test with annotations on fields fails, must have annotations on accessors
     */
    @DynamoDBDocument
    public static class BareDoc {

        @DynamoDBAttribute(attributeName = "strVal")
        public String strVal;
        @DynamoDBAttribute(attributeName = "intVal")
        public int intVal;
        @DynamoDBAttribute(attributeName = "floatVal")
        public float floatVal;

        public BareDoc(String strVal, int intVal, float floatVal) {
            this.strVal = strVal;
            this.intVal = intVal;
            this.floatVal = floatVal;
        }

        @Override
        public String toString() {
            return String
                    .format(
                        "BareDoc [strVal=%s, intVal=%s, floatVal=%s]",
                        this.strVal,this.intVal,this.floatVal);
        }
    }

    /**
     * Testing a document with compound types
     * Fail: This class will not store
     */
    @DynamoDBDocument
    public static class CompoundDoc {

        public Map<String,String> map;
        public Set<String> set;
        public List<String> list;

        public CompoundDoc() {
            map = new HashMap<String,String>();
            map.put("hello", "map");
            set = new HashSet<String>();
            set.add("a");
            set.add("b");
            set.add("c");
            list = new ArrayList<String>();
            list.add("a");
            list.add("b");
            list.add("c");
        }

        @Override
        public String toString() {
            return String
                    .format(
                        "CompoundDoc [map=%s, set=%s, list=%s]",
                        this.map,this.set,this.list);
        }

        /**
         * @return the map
         */
        @DynamoDBAttribute
        public Map<String, String> getMap() {
            return map;
        }

        /**
         * @param map the map to set
         */
        public void setMap(Map<String, String> map) {
            this.map = map;
        }

        /**
         * @return the set
         */
        @DynamoDBAttribute
        public Set<String> getSet() {
            return set;
        }

        /**
         * @param set the set to set
         */
        public void setSet(Set<String> set) {
            this.set = set;
        }

        /**
         * @return the list
         */
        @DynamoDBAttribute
        public List<String> getList() {
            return list;
        }

        /**
         * @param list the list to set
         */
        public void setList(List<String> list) {
            this.list = list;
        }
    }
}