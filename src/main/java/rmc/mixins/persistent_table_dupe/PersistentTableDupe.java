package rmc.mixins.persistent_table_dupe;

import rmc.mixins.persistent_table_dupe.extend.PersistentTileEntityEx;

/**
 * Developed by RMC Team, 2021
 */
public abstract class PersistentTableDupe {

    public static boolean checkTable(PersistentTileEntityEx tileEx, String username) {
        String oldname;
        return (oldname = tileEx.rmc$getCurrentUser()) != null && oldname.equals(username);
    }

    public static void tryLockTable(PersistentTileEntityEx tileEx, String username) {
        if (tileEx.rmc$getCurrentUser() == null)
            tileEx.rmc$setCurrentUser(username);
    }

    public static void tryUnlockTable(PersistentTileEntityEx tileEx, String username) {
        if (checkTable(tileEx, username))
            tileEx.rmc$setCurrentUser(null);
    }

}