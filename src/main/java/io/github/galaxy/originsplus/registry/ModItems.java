package io.github.galaxy.originsplus.registry;

import io.github.galaxy.originsplus.OriginsPlus;
import io.github.galaxy.originsplus.items.DragonFireballItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {
	public static final Item DRAGON_FIREBALL = new DragonFireballItem((new Item.Settings()).maxCount(16).group(ItemGroup.COMBAT));

    public static void register() {
        Registry.register(Registry.ITEM, new Identifier(OriginsPlus.MOD_ID, "dragon_fireball"), DRAGON_FIREBALL);
    }
}
