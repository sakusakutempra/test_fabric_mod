package sakutem;


import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import net.fabricmc.fabric.api.event.network.C2SPacketTypeCallback;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface SyazaiCallback {
    Event<SyazaiCallback> EVENT = EventFactory.createArrayBacked(SyazaiCallback.class,
            (listeners) -> (string, player_entity) -> {

                for (SyazaiCallback listener : listeners) {
                    ActionResult result = listener.interact(string, player_entity);

                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

        ActionResult interact(String string, PlayerEntity player_entity);
}