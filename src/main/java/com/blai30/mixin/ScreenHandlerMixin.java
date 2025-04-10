package com.blai30.mixin;

import com.blai30.CustomCraftingScreenHandlerFactory;
import net.minecraft.item.Items;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ScreenHandler.class)
public class ScreenHandlerMixin {
    @Inject(method = "onSlotClick", at = @At("HEAD"), cancellable = true)
    private void onSlotClick(int slotId, int button, SlotActionType actionType, net.minecraft.entity.player.PlayerEntity player, CallbackInfo ci) {
        ScreenHandler handler = (ScreenHandler) (Object) this;
        boolean isPlayerInventory = handler instanceof PlayerScreenHandler;
        boolean isSurvival = !player.getAbilities().creativeMode;

        if (isPlayerInventory && isSurvival && player instanceof ServerPlayerEntity && button == 1) {
            if (actionType == SlotActionType.PICKUP && player.currentScreenHandler.getSlot(slotId).hasStack()) {
                var stack = player.currentScreenHandler.getSlot(slotId).getStack();

                if (stack.getItem() == Items.CRAFTING_TABLE) {
                    ci.cancel();
                    Objects.requireNonNull(player.getServer()).execute(() -> {
                        player.openHandledScreen(new CustomCraftingScreenHandlerFactory(Text.translatable("container.crafting")));
                        player.incrementStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
                    });
                }
            }
        }
    }
}