package com.christofmeg.noblazeknockback;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.util.DamageSource;
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
            if (entity instanceof PlayerEntity) {
                DamageSource source = event.getSource();
                Entity sourceEntity = source.getEntity();
                if (sourceEntity != null) {
                    EntityType<?> sourceEntityType = sourceEntity.getType();
                    Entity directEntity = source.getDirectEntity();
                    if (sourceEntity instanceof BlazeEntity) {
                        if (directEntity instanceof SmallFireballEntity) {
                            hitByBlazeProjectile = true;
                        }
                    } else if (sourceEntityType.getDescriptionId().equals("entity.thermal.basalz")) {
                        if (directEntity != null) {
                            EntityType<?> directEntityType = directEntity.getType();
                            if (directEntityType.getDescriptionId().equals("entity.thermal.basalz_projectile")) {
                                hitByBlazeProjectile = true;
                            }
                        }
                    } else if (sourceEntityType.getDescriptionId().equals("entity.thermal.blitz")) {
                        if (directEntity != null) {
                            EntityType<?> directEntityType = directEntity.getType();
                            if (directEntityType.getDescriptionId().equals("entity.thermal.blitz_projectile")) {
                                hitByBlazeProjectile = true;
                            }
                        }
                    } else if (sourceEntityType.getDescriptionId().equals("entity.thermal.blizz")) {
                        if (directEntity != null) {
                            EntityType<?> directEntityType = directEntity.getType();
                            if (directEntityType.getDescriptionId().equals("entity.thermal.blizz_projectile")) {
                                hitByBlazeProjectile = true;
                            }
                        }
                    }

                }
            }
        });

        eventBus.addListener(EventPriority.LOWEST, false, LivingKnockBackEvent.class, (event) -> {
            Entity entity = event.getEntity();
            if (entity instanceof PlayerEntity) {
                DamageSource source = ((PlayerEntity) entity).getLastDamageSource();
                if (hitByBlazeProjectile || source != null && source.getMsgId().equals("basalz")) {
                    event.setStrength(0);
                    hitByBlazeProjectile = false;
                }
            }
        });

    }

}
