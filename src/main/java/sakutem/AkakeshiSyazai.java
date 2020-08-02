package sakutem;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.client.network.ClientPlayerEntity;


public class AkakeshiSyazai implements ModInitializer {
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

/*
		// ヒツジの毛をダイアにするやつ.
		SyazaiCallback.EVENT.register((player, sheep) -> {
			sheep.setSheared(true);

			// create diamond item entity at sheep position
			ItemStack stack = new ItemStack(Items.DIAMOND);
			ItemEntity itemEntity = new ItemEntity(
					player.world,
					sheep.getX(),
					sheep.getY(),
					sheep.getZ(),
					stack
			);
			player.world.spawnEntity(itemEntity);

			return ActionResult.FAIL;
		});

 */
		SyazaiCallback.EVENT.register((string, player_entity) -> {
			System.out.println("log_output");
			System.out.println(string);

			ItemStack stack = new ItemStack(Items.DIAMOND);
			ItemEntity itemEntity = new ItemEntity(
					player_entity.world,
					player_entity.getX() + 5.0f,
					player_entity.getY() + 5.0f,
					player_entity.getZ() + 5.0f,
					stack
			);
			player_entity.world.spawnEntity(itemEntity);
			ItemEntity itemEntity2 = new ItemEntity(
					player_entity.world,
					100.0f,
					100.0f,
					105.0f,
					stack
			);
			player_entity.world.spawnEntity(itemEntity2);

			player_entity.setPos(100.0f, 100.0f, 100.0f);

			return ActionResult.FAIL; // キャンセルすると、そこで止まる.キャンセルしないと、同一の条件のリスナも実行される（superの処理を阻害しない）.
		});

		System.out.println("Hello Fabric world!");
	}
}
