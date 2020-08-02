package sakutem;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.world.World;


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

			World world = player_entity.world;

			ItemStack stack = new ItemStack(Items.DIAMOND);
			ItemEntity itemEntity = new ItemEntity(
					player_entity.world,
					player_entity.getX(),
					player_entity.getY() + 5.0f,
					player_entity.getZ(),
					stack
			);
			player_entity.world.spawnEntity(itemEntity);

			ItemEntity itemEntity2 = new ItemEntity(
					world,
					100.0f,
					70.0f,
					105.0f,
					stack
			);
			world.spawnEntity(itemEntity2);
			TntEntity tnt_entity = EntityType.TNT.create(world);
			tnt_entity.setFuse(0);
			tnt_entity.setPos(
					player_entity.getX(),
					player_entity.getY(),
					player_entity.getZ()
			);
			world.spawnEntity(tnt_entity);

//			player_entity.setPos(100.0f, 100.0f, 100.0f);
//			player_entity.setAir(1);
			System.out.println("world " + world.toString());
			System.out.println("world dimention " + player_entity.world.getDimension());
			System.out.println("world dimention canPlayerSleep " + player_entity.world.getDimension().canPlayersSleep());
			System.out.println("world dimention nether " + player_entity.world.getDimension().isNether());
			System.out.println("world difficulty " + player_entity.world.getDifficulty().toString());
			System.out.println("world instanceof " + (player_entity.world instanceof World));

			return ActionResult.SUCCESS; // キャンセルすると、そこで止まる.キャンセルしないと、同一の条件のリスナも実行される（superの処理を阻害しない）.
		});

		System.out.println("Hello Fabric world!");
	}
}
