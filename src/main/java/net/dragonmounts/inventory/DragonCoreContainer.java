package net.dragonmounts.inventory;

import net.dragonmounts.init.DMContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;

import static net.dragonmounts.util.BlockEntityUtil.getInventory;

/**
 * @see net.minecraft.inventory.container.ShulkerBoxContainer
 */
public class DragonCoreContainer extends Container {
    public final IInventory container;

    public DragonCoreContainer(int containerId, PlayerInventory playerInventory, PacketBuffer extraData) {
        this(containerId, playerInventory, getInventory(playerInventory.player, extraData, 1));
    }

    public DragonCoreContainer(int containerId, PlayerInventory playerInventory, IInventory container) {
        super(DMContainers.DRAGON_CORE.get(), containerId);
        (this.container = container).startOpen(playerInventory.player);
        this.addSlot(new LimitedSlot.Reject(container, 0, 80, 35));
        for (int i = 0; i < 3; ++i) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }
        }
        for (int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 142));
        }
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull PlayerEntity player, int index) {
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem(), copy = stack.copy();
            if (index == 0 && !this.moveItemStackTo(stack, 1, this.slots.size(), true)) return ItemStack.EMPTY;
            if (index <= 27 && !this.moveItemStackTo(stack, 28, this.slots.size(), false)) return ItemStack.EMPTY;
            if (!this.moveItemStackTo(stack, 1, 28, false)) return ItemStack.EMPTY;
            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            return copy;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void removed(@Nonnull PlayerEntity player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    @Override
    public boolean stillValid(@Nonnull PlayerEntity player) {
        return this.container.stillValid(player);
    }
}