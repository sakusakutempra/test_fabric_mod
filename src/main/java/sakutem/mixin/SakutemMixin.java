package sakutem.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;
import java.util.UUID;

@Mixin(ClientPlayerEntity.class)
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

		String[] strKeyword = new String[KEYWORD_NUM];
		strKeyword[0] = "ごめんなさい";
		strKeyword[1] = "アカウント消";
		strKeyword[2] = "アカ消";
		strKeyword[3] = "もうマイクラしません";
		strKeyword[4] = "もうこの界隈離れます";
		strKeyword[5] = "無理です";
		strKeyword[6] = "くんさんにであえてよかった";
		strKeyword[7] = "gomi";
		strKeyword[8] = "50人クラフト辞め";
		strKeyword[9] = "今まですみませんでした";
		strKeyword[10] = "謝罪配信";
		strKeyword[11] = "スパチャもういい";

		int syazai_score = 0;

		for(int i = 0; i < KEYWORD_NUM; i++){
			if( string.contains(strKeyword[i]) ){
				syazai_score++;
				System.out.println("syazai_score " + syazai_score + " hit " + strKeyword[i]);
			}
		}

		if(syazai_score > 0){
			// サーバーWorldの取得.
			IntegratedServer integrated_server = client.getServer();
			PlayerManager player_manager = integrated_server.getPlayerManager();
			UUID player_uuid = player_entity.getUuid();
			ServerPlayerEntity server_player_entity = player_manager.getPlayer(player_uuid);

			ItemStack stack_diamond = new ItemStack(Items.DIAMOND, syazai_score);
			ItemStack stack_gold = new ItemStack(Items.GOLD_INGOT, syazai_score);
			Random random = new Random();
			float sinXZ, cosXZ, rate, dist = 3.0f;
			int treasure_num = syazai_score;
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
			}
		}
	}

	final int KEYWORD_NUM = 12;
}
