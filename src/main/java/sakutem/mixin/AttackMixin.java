package sakutem.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.container.PlayerContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class AttackMixin {
    @Inject(
            at = @At(
                    value = "HEAD"
            ),
            method = "attack",
            cancellable = true
    )
    private void onAttack(final Entity entity, final CallbackInfo info) {
        World world = entity.world;

        if(!world.isClient()) {
            ItemStack stack = new ItemStack(Items.DIAMOND);
            ItemEntity itemEntity = new ItemEntity(
                    entity.world,
                    entity.getX(),
                    entity.getY() + 5.0f,
                    entity.getZ(),
                    stack
            );
            entity.world.spawnEntity(itemEntity);

            System.out.println("AttackMixin world " + world.getDebugString());
        }
    }
}
