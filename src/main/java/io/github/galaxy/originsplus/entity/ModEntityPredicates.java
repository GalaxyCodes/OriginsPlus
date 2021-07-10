package io.github.galaxy.originsplus.entity;

import java.util.function.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class ModEntityPredicates {
	public static final Predicate<Entity> EXCEPT_PLAYER = (entity) -> !(entity instanceof PlayerEntity);
}
