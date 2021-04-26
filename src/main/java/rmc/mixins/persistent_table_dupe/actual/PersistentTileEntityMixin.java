package rmc.mixins.persistent_table_dupe.actual;

import org.spongepowered.asm.mixin.Mixin;

import rmc.mixins.persistent_table_dupe.extend.PersistentTileEntityEx;

/**
 * Developed by RMC Team, 2021
 */
@Mixin(value = { wile.engineersdecor.blocks.EdCraftingTable.CraftingTableTileEntity.class,
                 blusunrize.immersiveengineering.common.blocks.wooden.CraftingTableTileEntity.class })
public abstract class PersistentTileEntityMixin
implements PersistentTileEntityEx {

    private String rmc$username;

    @Override
    public String rmc$getCurrentUser() {
        return this.rmc$username;
    }

    @Override
    public void rmc$setCurrentUser(String username) {
        this.rmc$username = username;
    }

}