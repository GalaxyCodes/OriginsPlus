package io.github.galaxy.originsplus;

import io.github.galaxy.originsplus.registry.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class OriginsPlusClient implements ClientModInitializer {
	public static final Identifier PacketID = new Identifier(OriginsPlus.MOD_ID, "spawn_packet");
	
	@Override
	@Environment(EnvType.CLIENT)
	public void onInitializeClient() {
		EntityRendererRegistry.INSTANCE.register(ModEntities.SMALL_DRAGON_FIREBALL,
                (context) -> new FlyingItemEntityRenderer(context));
        EntityRendererRegistry.INSTANCE.register(ModEntities.FIREBALL_AREA_EFFECT_CLOUD,
                (context) -> new EmptyEntityRenderer(context));
	}
	
}
