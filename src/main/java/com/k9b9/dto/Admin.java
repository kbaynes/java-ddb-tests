package com.k9b9.dto;

/**
 * Admin DTO
 * Goal of this class is to make serializatoin and deserialization easy.
 */
public class Admin {

    public String pkey;
    public String skey;
    public Admin.JsonValue jsonValue;

    public Admin(String pkey, String skey, String name, String userId, String hashedPass, int adminLevel) {
        this.pkey = pkey;
        this.skey = skey;
        this.jsonValue = new Admin.JsonValue(name, userId, hashedPass, adminLevel);
    }

    public Admin(String pkey, String skey, Admin.JsonValue value) {
        this.pkey = pkey;
        this.skey = skey;
        this.jsonValue = value;
    }

    @Override
    public String toString() {
        return DtoUtils.toItem(this).toString();
    }

    /**
     * @return the pkey
     */
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
    public String getSkey() {
        return skey;
    }

    /**
     * @param skey the skey to set
     */
    public void setSkey(String skey) {
        this.skey = skey;
    }

    /**
     * @return the jsonValue
     */
    public Admin.JsonValue getJsonValue() {
        return this.jsonValue;
    }

    /**
     * @param jsonValue the jsonValue to set
     */
    public void setJsonValue(Admin.JsonValue jsonValue) {
        this.jsonValue = jsonValue;
    }

    public static class JsonValue {

        public String name;
        public String userId;
        public String hashedPass;
        public int adminLevel;

        // default constructor used by JSON parsers
        public JsonValue() {}

        public JsonValue(String name, String userId, String hashedPass, int adminLevel) {
            this.name = name;
            this.userId = userId;
            this.hashedPass = hashedPass;
            this.adminLevel = adminLevel;
        }

        /**
         * @return the name
         */
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
         * @return the adminLevel as int
         */
        public int getAdminLevel() {
            return this.adminLevel;
        }

        /**
         * @param adminLevel the adminLevel to set
         */
        public void setAdminLevel(int adminLevel) {
            this.adminLevel = adminLevel;
        }
    }
}