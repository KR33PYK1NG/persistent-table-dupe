package rmc.mixins.persistent_table_dupe.actual;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import blusunrize.immersiveengineering.common.blocks.wooden.CraftingTableTileEntity;
import blusunrize.immersiveengineering.common.gui.CraftingTableContainer;
import blusunrize.immersiveengineering.common.gui.IEBaseContainer;
import net.minecraft.entity.player.PlayerEntity;
import rmc.mixins.persistent_table_dupe.PersistentTableDupe;
import rmc.mixins.persistent_table_dupe.extend.PersistentTileEntityEx;

/**
 * Developed by RMC Team, 2021
 */
@Mixin(value = CraftingTableContainer.class)
public abstract class IECraftingTableContainerMixin
extends IEBaseContainer<CraftingTableTileEntity> {

    @Shadow @Final private PlayerEntity player;

    @Inject(method = "Lblusunrize/immersiveengineering/common/gui/CraftingTableContainer;<init>(ILnet/minecraft/entity/player/PlayerInventory;Lblusunrize/immersiveengineering/common/blocks/wooden/CraftingTableTileEntity;)V",
            at = @At(value = "TAIL"))
    private void tryLockTable(CallbackInfo mixin) {
        PersistentTableDupe.tryLockTable((PersistentTileEntityEx) super.tile, this.player.getName().getString());
    }

    @Inject(method = "Lblusunrize/immersiveengineering/common/gui/CraftingTableContainer;onContainerClosed(Lnet/minecraft/entity/player/PlayerEntity;)V",
            at = @At(value = "TAIL"))
    private void tryUnlockTable(CallbackInfo mixin) {
        PersistentTableDupe.tryUnlockTable((PersistentTileEntityEx) super.tile, this.player.getName().getString());
    }

    private IECraftingTableContainerMixin() {
        super(null, null, 0);
    }

}