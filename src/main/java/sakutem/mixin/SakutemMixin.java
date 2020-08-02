package sakutem.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
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

	/*
                            @Inject(at = @At("HEAD"), method = "init()V")

                            private void init(CallbackInfo info) {
                                System.out.println("This line is printed by an example mod mixin!");
                            }
                            */
	/*
	@Inject(
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/entity/passive/SheepEntity;dropItems()V"
			),
			method = "interactMob",
			cancellable = true
	)
	private void onShear(final PlayerEntity player, final Hand hand, final CallbackInfoReturnable<Boolean> info) {
		ActionResult result = SyazaiCallback.EVENT.invoker().interact(player, (SheepEntity) (Object) this);

		if(result == ActionResult.FAIL) {
			info.cancel();
		}
	}

	 */
/*
	@Shadow	@Final public World world;
	@Shadow	@Final private double x;
	@Shadow	@Final private double y;
	@Shadow	@Final private double z;
	@Shadow @Final private double lastX;
*/
	@Inject(
			at = @At(
					value = "HEAD"
//					value = "INVOKE",
//					target = "Lnet/minecraft/entity/passive/SheepEntity;dropItems()V",
//					target = "Lnet/minecraft/client/network/ClientPlayerEntity;sendChatMessage()V"
			),
			method = "sendChatMessage",
//			method = "sendMessage",
			cancellable = true
	)
	private void sendChatMessage(final String string, final CallbackInfo info) {
		ActionResult result = SyazaiCallback.EVENT.invoker().interact(string);
		ClientPlayerEntity entity_client = client.player;

		System.out.println("mixin_sendChatMessage : " + string);

		if(result == ActionResult.SUCCESS){
			System.out.println("mixin_sendChatMessage : " + string + entity_client.getX());
			ItemStack stack = new ItemStack(Items.DIAMOND);
			ItemEntity itemEntity = new ItemEntity(
					entity_client.world,
					entity_client.getX() + 5.0f,
					entity_client.getY() + 5.0f,
					entity_client.getZ() + 5.0f,
					stack
			);
			entity_client.world.spawnEntity(itemEntity);
		}

		if(result == ActionResult.FAIL) {
			info.cancel();
		}
	}
/*
	@Inject(
			at = @At(
					value = "HEAD"
//					value = "INVOKE",
//					target = "Lnet/minecraft/entity/passive/SheepEntity;dropItems()V",
//					target = "Lnet/minecraft/client/network/ClientPlayerEntity;sendChatMessage()V"
			),
//			method = "sendChatMessage",
			method = "sendMessage",
			cancellable = true
	)
	private void sendChatMessage(final String string, final CallbackInfo info) {
		ActionResult result = SyazaiCallback.EVENT.invoker().interact(string);


		if(result == ActionResult.SUCCESS){
			ItemStack stack = new ItemStack(Items.DIAMOND);
			ItemEntity itemEntity = new ItemEntity(
					this.world,
					this.x + 5.0f,
					5.0f,
					5.0f,
					stack
			);
			this.world.spawnEntity(itemEntity);
		}

		if(result == ActionResult.FAIL) {
			info.cancel();
		}
	}*/
}
