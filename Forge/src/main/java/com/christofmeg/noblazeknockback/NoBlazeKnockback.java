package com.christofmeg.noblazeknockback;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unused")
@Mod("noblazeknockback")
public class NoBlazeKnockback {

    public static boolean hitByBlazeProjectile;

    public NoBlazeKnockback() {
        IEventBus eventBus = MinecraftForge.EVENT_BUS;
        cancelKnockbackEvent(eventBus);
    }

    public static void cancelKnockbackEvent(IEventBus eventBus) {
        eventBus.addListener(EventPriority.HIGHEST, false, LivingHurtEvent.class, (event) -> {
            Entity entity = event.getEntity();
            if (entity instanceof Player) {
                DamageSource source = event.getSource();
                Entity sourceEntity = source.getEntity();
                if (sourceEntity != null) {
                    EntityType<?> sourceEntityType = sourceEntity.getType();
                    Entity directEntity = source.getDirectEntity();
                    if (sourceEntity instanceof Blaze) {
                        if (directEntity instanceof SmallFireball) {
                            hitByBlazeProjectile = true;
                        }
                    } else if (sourceEntityType.toString().equals("entity.thermal.basalz")) {
                        if (directEntity != null) {
                            EntityType<?> directEntityType = directEntity.getType();
                            if (directEntityType.toString().equals("entity.thermal.basalz_projectile")) {
                                hitByBlazeProjectile = true;
                            }
                        }
                    } else if (sourceEntityType.toString().equals("entity.thermal.blitz")) {
                        if (directEntity != null) {
                            EntityType<?> directEntityType = directEntity.getType();
                            if (directEntityType.toString().equals("entity.thermal.blitz_projectile")) {
                                hitByBlazeProjectile = true;
                            }
                        }
                    } else if (sourceEntityType.toString().equals("entity.thermal.blizz")) {
                        if (directEntity != null) {
                            EntityType<?> directEntityType = directEntity.getType();
                            if (directEntityType.toString().equals("entity.thermal.blizz_projectile")) {
                                hitByBlazeProjectile = true;
                            }
                        }
                    }

                }
            }
        });

        eventBus.addListener(EventPriority.LOWEST, false, LivingKnockBackEvent.class, (event) -> {
            Entity entity = event.getEntity();
            if (entity instanceof Player) {
                DamageSource source = ((Player) entity).getLastDamageSource();
                if (hitByBlazeProjectile || source != null && source.getMsgId().equals("basalz_projectile")) {
                    event.setStrength(0);
                    hitByBlazeProjectile = false;
                }
            }
        });
    }

}
