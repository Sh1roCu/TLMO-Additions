package com.mastermarisa.maidbeacon.entity;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.maidbeacon.MaidBeacon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ExtraRenderingEntity extends Entity {
    public static final EntityType<ExtraRenderingEntity> TYPE;

    private static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData
            .defineId(ExtraRenderingEntity.class, EntityDataSerializers.INT);

    public ExtraRenderingEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noCulling = true;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_OWNER_ID, -1);
    }

    public void setOwnerID(Integer id) {
        this.entityData.set(DATA_OWNER_ID, id);
    }

    public Integer getOwnerID() {
        return this.entityData.get(DATA_OWNER_ID);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            EntityMaid maid = getMaid();
            if (maid != null) {
                if (!this.isPassenger()) this.startRiding(maid);
            } else {
                this.discard();
            }
        }
    }

    public @Nullable EntityMaid getMaid() {
        return level().getEntity(getOwnerID()) instanceof EntityMaid maid ? maid : null;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("maid")) setOwnerID(tag.getInt("maid"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        int id = getOwnerID();
        if (id != -1) tag.putInt("maid", id);
    }

    static {
        TYPE = EntityType.Builder.of(ExtraRenderingEntity::new, MobCategory.MISC).
                sized(0.1F, 0.1F)
                .clientTrackingRange(256)
                .canSpawnFarFromPlayer()
                .noSave().noSummon().fireImmune()
                .build(MaidBeacon.resourceLocation("extra_rendering_entity").toString());
    }
}