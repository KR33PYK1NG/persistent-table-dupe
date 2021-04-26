package rmc.mixins.persistent_table_dupe.actual;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import rmc.mixins.persistent_table_dupe.PersistentTableDupe;
import rmc.mixins.persistent_table_dupe.extend.PersistentTileEntityEx;
import wile.engineersdecor.blocks.EdCraftingTable.CraftingTableContainer;
import wile.engineersdecor.libmc.detail.Inventories.StorageInventory;

/**
 * Developed by RMC Team, 2021
 */
@Mixin(value = CraftingTableContainer.class)
public abstract class EDCraftingTableContainerMixin {

    @Shadow @Final private PlayerEntity player_;
    @Shadow @Final private IInventory inventory_;

    @Overwrite
    public boolean canInteractWith(PlayerEntity player) {
        PersistentTileEntityEx tileEx;
        if (this.inventory_ instanceof StorageInventory)
            tileEx = (PersistentTileEntityEx) ((StorageInventory) this.inventory_).getTileEntity();
        else
            tileEx = (PersistentTileEntityEx) this.inventory_;
        return this.inventory_.isUsableByPlayer(player)
            && PersistentTableDupe.checkTable(tileEx, player.getName().getString());
    }

    @Inject(method = "Lwile/engineersdecor/blocks/EdCraftingTable$CraftingTableContainer;<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/IInventory;Lnet/minecraft/util/IWorldPosCallable;)V",
            at = @At(value = "TAIL"))
    private void tryLockTable(CallbackInfo mixin) {
        PersistentTileEntityEx tileEx;
        if (this.inventory_ instanceof StorageInventory)
            tileEx = (PersistentTileEntityEx) ((StorageInventory) this.inventory_).getTileEntity();
        else
            tileEx = (PersistentTileEntityEx) this.inventory_;
        PersistentTableDupe.tryLockTable(tileEx, this.player_.getName().getString());
    }

    @Inject(method = "Lwile/engineersdecor/blocks/EdCraftingTable$CraftingTableContainer;onContainerClosed(Lnet/minecraft/entity/player/PlayerEntity;)V",
            at = @At(value = "TAIL"))
    private void tryUnlockTable(CallbackInfo mixin) {
        PersistentTileEntityEx tileEx;
        if (this.inventory_ instanceof StorageInventory)
            tileEx = (PersistentTileEntityEx) ((StorageInventory) this.inventory_).getTileEntity();
        else
            tileEx = (PersistentTileEntityEx) this.inventory_;
        PersistentTableDupe.tryUnlockTable(tileEx, this.player_.getName().getString());
    }

}