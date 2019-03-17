package com.k9b9.entity;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * Admin entity
 */
@DynamoDBTable(tableName = "SingleTableTest")
public class AdminItem {

    public final String SKEY = "admin";

    public String pkey;
    public String skey = SKEY;
    public String name;
    public String userId;
    public String hashedPass;
    public Level level;

    public AdminItem(String pkey, String name, String userId, String hashedPass, Level level) {
        this.pkey = pkey;
        this.name = name;
        this.userId = userId;
        this.hashedPass = hashedPass;
        this.level = level;
    }

    public enum Level {

        ROOT(0),
        ADMIN(100),
        MANAGER(200),
        VIEWER(300);

        private int value;
        private static Map<Integer,Level> map = new HashMap<Integer,Level>();

        private Level(int value) {
            this.value = value;
        }

        static {
            for (Level level : Level.values()) {
                map.put(level.value, level);
            }
        }

        public static Level valueOf(int level) {
            return (Level) map.get(level);
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * @return the name
     */
    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the userId
     */
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the hashedPass
     */
    @DynamoDBAttribute(attributeName = "hashedPass")
    public String getHashedPass() {
        return hashedPass;
    }

    /**
     * @param hashedPass the hashedPass to set
     */
    public void setHashedPass(String hashedPass) {
        this.hashedPass = hashedPass;
    }

    /**
     * @return the level as int
     */
    @DynamoDBAttribute(attributeName = "level")
    public int getLevel() {
        return level.getValue();
    }

    /**
     * @return the level as enum
     */
    @DynamoDBIgnore
    public Level getLevelAsEnum() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = AdminItem.Level.valueOf(level);
    }

    /**
     * @return the pkey
     */
    @DynamoDBHashKey(attributeName = "pkey")
    public String getPkey() {
        return pkey;
    }

    /**
     * @param pkey the pkey to set
     */
    public void setPkey(String pkey) {
        this.pkey = pkey;
    }

    /**
     * @return the skey
     */
    @DynamoDBRangeKey(attributeName = "skey")
    public String getSkey() {
        return skey;
    }

    /**
     * @param skey the skey to set
     */
    public void setSkey(String skey) {
        this.skey = skey;
    }
}