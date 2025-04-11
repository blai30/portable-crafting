package com.blai30;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;

public class CustomCraftingScreenHandler extends CraftingScreenHandler {
    public CustomCraftingScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(syncId, playerInventory, context);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        // Allow all players to use this container
        return true;
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
        // Custom logic for updating the crafting result slot
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        // Custom logic for handling item drops when the UI is closed
    }
}