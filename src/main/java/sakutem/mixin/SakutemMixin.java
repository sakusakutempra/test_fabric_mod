package sakutem.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sakutem.SyazaiCallback;

//@Mixin(TitleScreen.class)
//@Mixin(SheepEntity.class)
//@Mixin(PlayerEntity.class)
@Mixin(ClientPlayerEntity.class)
//@Mixin(Entity.class)
public class SakutemMixin {

	@Shadow @Final protected MinecraftClient client;

	@Inject(
			at = @At(
					value = "HEAD"
//					value = "INVOKE",
//					target = "Lnet/minecraft/entity/passive/SheepEntity;dropItems()V",
//					target = "Lnet/minecraft/entity/player/PlayerEntity;dropItem()V"
//					target = "Lnet/minecraft/client/network/ClientPlayerEntity;sendChatMessage()V"
			),
			method = "sendChatMessage",
			cancellable = true
	)
	private void sendChatMessage(final String string, final CallbackInfo info) {
		ClientPlayerEntity client_player_entity = client.player;
		PlayerEntity player_entity = (PlayerEntity)client_player_entity;

		ActionResult result = SyazaiCallback.EVENT.invoker().interact(string, player_entity);

		System.out.println("mixin_sendChatMessage : " + string);

		if(result == ActionResult.SUCCESS){
			System.out.println("mixin_sendChatMessage : " + string + player_entity.getX());
			ItemStack stack = new ItemStack(Items.DIAMOND);
			ItemEntity itemEntity = new ItemEntity(
					player_entity.world,
					player_entity.getX() + 5.0,
					player_entity.getY() + 5.0,
					player_entity.getZ() + 5.0,
					stack
			);
			player_entity.world.spawnEntity(itemEntity);

			ItemEntity itemEntity2 = new ItemEntity(
					player_entity.world,
					player_entity.getX(),
					player_entity.getY(),
					player_entity.getZ(),
					stack
			);
			player_entity.world.spawnEntity(itemEntity2);

			System.out.println("mixin_sendChatMessage : TNT " + player_entity.world.toString() + " " + player_entity.getX() + " " + player_entity.getY() + " " + player_entity.getZ());
			TntEntity tnt_entity = EntityType.TNT.create(player_entity.world);
			tnt_entity.setFuse(0);
			tnt_entity.setPos(
					player_entity.getX(),
					player_entity.getY(),
					player_entity.getZ()
			);
			player_entity.world.spawnEntity(tnt_entity);
		}

		if(result == ActionResult.FAIL) {
			info.cancel();
		}
	}
}
