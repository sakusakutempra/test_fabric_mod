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
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.network.ServerPlayerEntity;
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

import java.util.Random;
import java.util.UUID;

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
	private void onChat(final String string, final CallbackInfo info) {
		ClientPlayerEntity client_player_entity = client.player;
		PlayerEntity player_entity = (PlayerEntity)client_player_entity;


		ActionResult result = SyazaiCallback.EVENT.invoker().interact(string, player_entity);




		if(string.equals("test1")) {
			System.out.println("test1 start");
			IntegratedServer integrated_server = client.getServer();
			System.out.println(integrated_server.toString());
			System.out.println("test1 end");
		}
		if(string.equals("test2")) {
			System.out.println("test2 start");
			IntegratedServer integrated_server = client.getServer();
			System.out.println(integrated_server.toString());
			PlayerManager player_manager = integrated_server.getPlayerManager();
			System.out.println(player_manager.toString());
			System.out.println("test2 end");
		}
		if(string.equals("test3")){
			System.out.println("test3 start");
			IntegratedServer integrated_server = client.getServer();
			System.out.println(integrated_server.toString());
			PlayerManager player_manager = integrated_server.getPlayerManager();
			System.out.println(player_manager.toString());
			UUID player_uuid = player_entity.getUuid();
			ServerPlayerEntity server_player = player_manager.getPlayer(player_uuid);
			System.out.println(server_player.getName());
			System.out.println("test3 end");
		}
		if(string.equals("test4")){
			System.out.println("test4 start");
			IntegratedServer integrated_server = client.getServer();
			System.out.println(integrated_server.toString());
			PlayerManager player_manager = integrated_server.getPlayerManager();
			System.out.println(player_manager.toString());
			UUID player_uuid = player_entity.getUuid();
			ServerPlayerEntity server_player_entity = player_manager.getPlayer(player_uuid);
			System.out.println(server_player_entity.getName());

			ItemStack stack = new ItemStack(Items.DIAMOND);
			ItemEntity itemEntity = new ItemEntity(
					server_player_entity.world,
					server_player_entity.getX() + 5.0,
					server_player_entity.getY() + 5.0,
					server_player_entity.getZ() + 5.0,
					stack
			);
			server_player_entity.world.spawnEntity(itemEntity);

			System.out.println("test4 end");
		}

		int syazai_score = 0;

		if( string.contains("ごめんなさい") ){
			syazai_score++;
			System.out.println("gomennasai");
		}

		if( string.contains("アカウント消します") ){
			syazai_score++;
			System.out.println("akauntokesimasu");
		}

		if( string.contains("病") ){
			syazai_score++;
			System.out.println("yamu");
		}

		if( string.contains("もう無理") ){
			syazai_score++;
			System.out.println("moumuri");
		}

		if( string.contains("アカ消しします") ){
			syazai_score++;
			System.out.println("akakesisimasu");
		}
		if( string.contains("suman") ){
			syazai_score++;
			System.out.println("suman");
		}

		if(syazai_score > 0){
			IntegratedServer integrated_server = client.getServer();
			PlayerManager player_manager = integrated_server.getPlayerManager();
			UUID player_uuid = player_entity.getUuid();
			ServerPlayerEntity server_player_entity = player_manager.getPlayer(player_uuid);
			System.out.println("get treasure:" + syazai_score + " who:" + server_player_entity.getName());


			ItemStack stack_diamond = new ItemStack(Items.DIAMOND, syazai_score);
			ItemStack stack_gold = new ItemStack(Items.GOLD_INGOT, syazai_score);
			Random random = new Random();
			float sinXZ, cosXZ, rate, dist = 3.0f;
			int treasure_num = (int)Math.pow(2.0, (double)syazai_score);
			treasure_num = syazai_score;
			for(int i = 0; i < treasure_num; i++ ){
				rate = random.nextFloat();
				sinXZ = (float)Math.sin( Math.PI * 2 * rate ) * dist;
				cosXZ = (float)Math.cos( Math.PI * 2 * rate ) * dist;

				ItemEntity itemEntity = new ItemEntity(
						server_player_entity.world,
						server_player_entity.getX() + sinXZ,
						server_player_entity.getY() + 5.0,
						server_player_entity.getZ() + cosXZ,
						(i%2 == 0) ? stack_diamond : stack_gold
				);
				server_player_entity.world.spawnEntity(itemEntity);

				System.out.println("spawn x " + sinXZ + " z " + cosXZ + " rate " + rate + " dist " + (sinXZ*sinXZ + cosXZ*cosXZ) );
			}
		}



		if(result == ActionResult.SUCCESS){
			ItemStack stack = new ItemStack(Items.DIAMOND);
/*			ItemEntity itemEntity = new ItemEntity(
					player_entity.world,
					player_entity.getX() + 5.0,
					player_entity.getY() + 5.0,
					player_entity.getZ() + 5.0,
					stack
			);
			player_entity.world.spawnEntity(itemEntity);*/

			ItemEntity itemEntity2 = new ItemEntity(
					player_entity.world,
					player_entity.getX(),
					player_entity.getY(),
					player_entity.getZ(),
					stack
			);
			player_entity.world.spawnEntity(itemEntity2);

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
