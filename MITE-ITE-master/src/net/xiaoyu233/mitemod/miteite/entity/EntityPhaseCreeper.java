package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;

public class EntityPhaseCreeper extends EntityCreeper {
    int max_num_evasions;
    int num_evasions;

    public EntityPhaseCreeper(World par1World) {
        super(par1World);
        if (par1World != null && this.onServer()) {
            this.max_num_evasions = this.num_evasions = this.rand.nextInt(3) + 2;
        }
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setByte("max_num_evasions", (byte)this.max_num_evasions);
        par1NBTTagCompound.setByte("num_evasions", (byte)this.num_evasions);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.max_num_evasions = par1NBTTagCompound.getByte("max_num_evasions");
        this.num_evasions = par1NBTTagCompound.getByte("num_evasions");
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.onServer() && this.getHealth() > 0.0F) {
            int ticks_existed_with_offset = this.getTicksExistedWithOffset();
            if (this.num_evasions < this.max_num_evasions && ticks_existed_with_offset % 100 == 0) {
                ++this.num_evasions;
            }

            if (this.hasPath() && (this.getTarget() != null || this.fleeing) && ticks_existed_with_offset % 10 == 0 && this.rand.nextInt(3) == 0) {
                PathEntity path = this.getPathToEntity();
                if (!path.isFinished()) {
                    int n = path.getNumRemainingPathPoints();
                    if (n > 1) {
                        int path_index_advancement = MathHelper.clamp_int(this.rand.nextInt(n), 1, 4);
                        PathPoint path_point = path.getPathPointFromCurrentIndex(path_index_advancement);
                        if ((double)path_point.distanceSqTo(this) > (double)3.0F && this.tryTeleportTo((double)path_point.xCoord + (double)0.5F, (double)path_point.yCoord, (double)path_point.zCoord + (double)0.5F)) {
                            path.setCurrentPathIndex(path.getCurrentPathIndex() + path_index_advancement - 1);
                        }
                    }
                }
            }
        }

    }

    public boolean tryTeleportTo(double pos_x, double pos_y, double pos_z) {
        if (!this.isDead && !(this.getHealth() <= 0.0F)) {
            int x = MathHelper.floor_double(pos_x);
            int y = MathHelper.floor_double(pos_y);
            int z = MathHelper.floor_double(pos_z);
            if (y >= 1 && this.worldObj.blockExists(x, y, z)) {
                while(true) {
                    --y;
                    if (this.worldObj.isBlockSolid(x, y, z)) {
                        ++y;
                        if (!this.worldObj.isBlockSolid(x, y, z) && !this.worldObj.isLiquidBlock(x, y, z)) {
                            double delta_pos_x = pos_x - this.posX;
                            double delta_pos_y = pos_y - this.posY;
                            double delta_pos_z = pos_z - this.posZ;
                            AxisAlignedBB bb = this.boundingBox.translateCopy(delta_pos_x, delta_pos_y, delta_pos_z);
                            if (this.worldObj.getCollidingBoundingBoxes(this, bb).isEmpty() && !this.worldObj.isAnyLiquid(bb)) {
                                World var10000 = this.worldObj;
                                double distance = (double)World.getDistanceFromDeltas(delta_pos_x, delta_pos_y, delta_pos_z);
                                this.worldObj.blockFX(EnumBlockFX.particle_trail, x, y, z, (new SignalData()).setByte(EnumParticle.runegate.ordinal()).setShort((int)((double)16.0F * distance)).setApproxPosition((double)MathHelper.floor_double(this.posX), (double)MathHelper.floor_double(this.posY), (double)MathHelper.floor_double(this.posZ)));
                                this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "mob.endermen.portal", 1.0F, 1.0F);
                                this.setPosition(pos_x, pos_y, pos_z);
                                this.send_position_update_immediately = true;
                                return true;
                            }

                            return false;
                        }

                        return false;
                    }

                    if (y < 1) {
                        return false;
                    }

                    --pos_y;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean tryTeleportAwayFrom(Entity entity, double min_distance) {
        if (!this.isDead && !(this.getHealth() <= 0.0F)) {
            double min_distance_sq = min_distance * min_distance;
            int x = this.getBlockPosX();
            int y = this.getFootBlockPosY();
            int z = this.getBlockPosZ();
            double threat_pos_x = entity == null ? this.posX : entity.posX;
            double threat_pos_z = entity == null ? this.posZ : entity.posZ;

            for(int attempts = 0; attempts < 64; ++attempts) {
                int dx = this.rand.nextInt(11) - 5;
                int dy = this.rand.nextInt(9) - 4;
                int dz = this.rand.nextInt(11) - 5;
                if (Math.abs(dx) >= 3 || Math.abs(dz) >= 3) {
                    int try_x = x + dx;
                    int try_y = y + dy;
                    int try_z = z + dz;
                    double try_pos_x = (double)try_x + (double)0.5F;
                    double try_pos_z = (double)try_z + (double)0.5F;
                    World var10000 = this.worldObj;
                    if (!(World.getDistanceSqFromDeltas(try_pos_x - threat_pos_x, try_pos_z - threat_pos_z) < min_distance_sq) && try_y >= 1 && this.worldObj.blockExists(try_x, try_y, try_z)) {
                        do {
                            --try_y;
                        } while(!this.worldObj.isBlockSolid(try_x, try_y, try_z) && try_y >= 1);

                        if (try_y >= 1) {
                            ++try_y;
                            if (!this.worldObj.isBlockSolid(try_x, try_y, try_z) && !this.worldObj.isLiquidBlock(try_x, try_y, try_z) && this.tryTeleportTo(try_pos_x, (double)try_y, try_pos_z)) {
                                EntityPlayer target = this.findPlayerToAttack(Math.min(this.getMaxTargettingRange(), 24.0F));
                                if (target != null && target != this.getTarget()) {
                                    this.setTarget(target);
                                }

                                return true;
                            }
                        }
                    }
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public EntityDamageResult attackEntityFrom(Damage damage) {
        boolean can_evade = true;
        if (damage.isFallDamage() || damage.isFireDamage() || damage.isPoison()) {
            can_evade = false;
        }

        if (can_evade && this.num_evasions > 0) {
            --this.num_evasions;
            Entity entity = damage.getResponsibleEntityP();
            if (entity == null) {
                entity = damage.getResponsibleEntityP();
            }

            if (this.tryTeleportAwayFrom(entity, (double)3.0F)) {
                return null;
            }
        }

        return super.attackEntityFrom(damage);
    }
}
