package rmc.mixins.persistent_table_dupe.actual;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import blusunrize.immersiveengineering.common.gui.IEBaseContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import rmc.mixins.persistent_table_dupe.PersistentTableDupe;
import rmc.mixins.persistent_table_dupe.extend.PersistentTileEntityEx;

/**
 * Developed by RMC Team, 2021
 */
@Mixin(value = IEBaseContainer.class)
public abstract class IEBaseContainerMixin {

    @Shadow public TileEntity tile;
    @Shadow public IInventory inv;

    @Overwrite
    public boolean canInteractWith(PlayerEntity player) {
        boolean usable = this.inv != null && this.inv.isUsableByPlayer(player);
        if (!(this.tile instanceof PersistentTileEntityEx))
            return usable;
        return usable
            && PersistentTableDupe.checkTable((PersistentTileEntityEx) this.tile, player.getName().getString());
    }

}