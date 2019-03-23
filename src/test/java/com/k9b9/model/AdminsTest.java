package com.k9b9.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import com.k9b9.ddb.SingleTableDdb;
import com.k9b9.dto.Admin;

import org.junit.Before;
import org.junit.Test;

/**
 * AdminsTest
 */
public class AdminsTest {

    SingleTableDdb ddb;

    @Before
    public void beforeTest() {
        // this.myDb = new DynDb("YourKey", "YourSecretKey", "us-east-1", "SingleTableTest");
        this.ddb = new SingleTableDdb("SingleTableTest");
    }

    @Test
    public void testCreateRootAdmin() {

        // test valid put
        Admin admin = new Admin(Admins.ROOT_ADMIN_PKEY, Admins.ADMIN_SKEY, "adminName", "adminUid", "adminHashedPass", AdminLevel.ROOT.getValue());
        Admins admins = new Admins(this.ddb);
        assertTrue(admins.putRootAdmin(admin));
        System.out.println("CreateRootAdmin OK");

        // test bad pkey
        admin = new Admin("blah", Admins.ADMIN_SKEY, "adminName", "adminUid", "adminHashedPass", AdminLevel.ROOT.getValue());
        assertFalse(admins.putRootAdmin(admin));
        System.out.println("CreateRootAdmin bad pkey validated OK");

        // test bad skey
        admin = new Admin(Admins.ROOT_ADMIN_PKEY, "bad-skey", "adminName", "adminUid", "adminHashedPass", AdminLevel.ROOT.getValue());
        assertFalse(admins.putRootAdmin(admin));
        System.out.println("CreateRootAdmin bad skey validated OK");

        // test bad level
        admin = new Admin(Admins.ROOT_ADMIN_PKEY, Admins.ADMIN_SKEY, "adminName", "adminUid", "adminHashedPass", AdminLevel.ADMIN.getValue());
        assertFalse(admins.putRootAdmin(admin));
        System.out.println("CreateRootAdmin bad level validated OK");
    }

    @Test
    public void testCreateRandomAdmin() {
        
        Admins admins = new Admins(this.ddb);

        Admin admin = new Admin(java.util.UUID.randomUUID().toString(), Admins.ADMIN_SKEY, "adminName", "adminUid", "adminHashedPass", AdminLevel.ADMIN.getValue());
        assertTrue(admins.putAdmin(admin));
        System.out.println("CreateRandomAdmin OK");

        // confirm that must be admin sort
        admin = new Admin(java.util.UUID.randomUUID().toString(), "bad-skey", "adminName", "adminUid", "adminHashedPass", AdminLevel.ADMIN.getValue());
        assertFalse(admins.putAdmin(admin));
        System.out.println("CreateRandomAdmin bad skey validated OK");

        // confirm root level is disallowed
        admin = new Admin(java.util.UUID.randomUUID().toString(), Admins.ADMIN_SKEY, "adminName", "adminUid", "adminHashedPass", AdminLevel.ROOT.getValue());
        assertFalse(admins.putAdmin(admin));
        System.out.println("CreateRandomAdmin root level barred validated OK");

        // confirm root pkey is disallowed
        admin = new Admin(Admins.ROOT_ADMIN_PKEY, Admins.ADMIN_SKEY, "adminName", "adminUid", "adminHashedPass", AdminLevel.ADMIN.getValue());
        assertFalse(admins.putAdmin(admin));
        System.out.println("CreateRandomAdmin root pkey barred validated OK");
    }

    @Test
    public void testCreateRandomManager() {
        
        Admin admin = new Admin(java.util.UUID.randomUUID().toString(), Admins.ADMIN_SKEY, "managerName", "ManagerUID", "managerHashedPass", AdminLevel.MANAGER.getValue());
        Admins admins = new Admins(this.ddb);
        assertTrue(admins.putAdmin(admin));
        System.out.println("CreateRandomManager OK");
    }

    @Test
    public void testCreateRandomViewer() {
        
        Admins admins = new Admins(this.ddb);
        Admin admin = new Admin(java.util.UUID.randomUUID().toString(), Admins.ADMIN_SKEY, "viewerName", "viewerUID", "viewerHashedPass", AdminLevel.VIEWER.getValue());
        assertTrue(admins.putAdmin(admin));
        System.out.println("CreateRandomViewer OK");
    }

    @Test
    public void testGetAdmins() {
        Admins admins = new Admins(ddb);
        List<Admin> list = admins.getAdmins();
        for (Admin admin : list) {
            System.out.println(admin.toString());
        }
    }

    @Test
    public void testGetAdminsByLevel() {
        Admins admins = new Admins(ddb);
        List<Admin> list = admins.getAdminsByLevel(AdminLevel.ROOT);
        System.out.println("AdminsByLevel list size="+list.size());
        for (Admin admin : list) {
            System.out.println(admin.toString());
        }
    }

    /**
     * it is possible to add more than one user of the same UserId
     * This should be checked
     */
    @Test
    public void testGetAdminByUserId() {
        Admins admins = new Admins(this.ddb);
        String userId = "myAdminId";
        Admin admin = new Admin(java.util.UUID.randomUUID().toString(), Admins.ADMIN_SKEY, "My Admin Name", userId, "myAdminHashedPass", AdminLevel.VIEWER.getValue());
        assertTrue(admins.putAdmin(admin));
        List<Admin> list = admins.getAdminsByUserId(userId);
        assertTrue(list.size()>0);
    }
}