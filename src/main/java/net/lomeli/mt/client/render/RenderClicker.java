package net.lomeli.mt.client.render;

import net.lomeli.lomlib.client.ResourceUtil;

import net.lomeli.mt.entity.EntityClicker;
import net.lomeli.mt.lib.Strings;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderClicker extends RenderBiped {
    private static final ResourceLocation clicker = ResourceUtil.getEntityTexture(Strings.MOD_ID.toLowerCase(), "ClickerrTLOU.png");

    private ModelBiped field_82434_o;
    protected ModelBiped field_82437_k;
    protected ModelBiped field_82435_l;
    protected ModelBiped field_82436_m;
    protected ModelBiped field_82433_n;
    @SuppressWarnings("unused")
    private int field_82431_q = 1;

    public RenderClicker() {
        super(new ModelZombie(), 0.5F, 1.0F);
        this.field_82434_o = this.modelBipedMain;
    }

    @Override
    protected void func_82421_b() {
        this.field_82423_g = new ModelZombie(1.0F, true);
        this.field_82425_h = new ModelZombie(0.5F, true);
        this.field_82437_k = this.field_82423_g;
        this.field_82435_l = this.field_82425_h;
    }

    protected int func_82429_a(EntityClicker par1EntityZombie, int par2, float par3) {
        this.func_82427_a(par1EntityZombie);
        return super.func_130006_a(par1EntityZombie, par2, par3);
    }

    public void func_82426_a(EntityClicker par1EntityZombie, double par2, double par4, double par6, float par8, float par9) {
        this.func_82427_a(par1EntityZombie);
        super.doRenderLiving(par1EntityZombie, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation func_110863_a(EntityClicker par1EntityZombie) {
        return par1EntityZombie instanceof EntityClicker ? clicker : clicker;
    }

    protected void func_82428_a(EntityClicker par1EntityZombie, float par2) {
        this.func_82427_a(par1EntityZombie);
        super.func_130005_c(par1EntityZombie, par2);
    }

    private void func_82427_a(EntityClicker par1EntityZombie) {
        this.mainModel = this.field_82434_o;
        this.field_82423_g = this.field_82437_k;
        this.field_82425_h = this.field_82435_l;

        this.modelBipedMain = (ModelBiped) this.mainModel;
    }

    protected void func_82430_a(EntityClicker par1EntityZombie, float par2, float par3, float par4) {
        if (par1EntityZombie.isConverting()) {
            par3 += (float) (Math.cos(par1EntityZombie.ticksExisted * 3.25D) * Math.PI * 0.25D);
        }

        super.rotateCorpse(par1EntityZombie, par2, par3, par4);
    }

    @Override
    protected void func_130005_c(EntityLiving par1EntityLiving, float par2) {
        this.func_82428_a((EntityClicker) par1EntityLiving, par2);
    }

    @Override
    protected ResourceLocation func_110856_a(EntityLiving par1EntityLiving) {
        return this.func_110863_a((EntityClicker) par1EntityLiving);
    }

    @Override
    public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.func_82426_a((EntityClicker) par1EntityLiving, par2, par4, par6, par8, par9);
    }

    @Override
    protected int func_130006_a(EntityLiving par1EntityLiving, int par2, float par3) {
        return this.func_82429_a((EntityClicker) par1EntityLiving, par2, par3);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
        return this.func_82429_a((EntityClicker) par1EntityLivingBase, par2, par3);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2) {
        this.func_82428_a((EntityClicker) par1EntityLivingBase, par2);
    }

    @Override
    protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4) {
        this.func_82430_a((EntityClicker) par1EntityLivingBase, par2, par3, par4);
    }

    @Override
    public void renderPlayer(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9) {
        this.func_82426_a((EntityClicker) par1EntityLivingBase, par2, par4, par6, par8, par9);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called
     * unless you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.func_110863_a((EntityClicker) par1Entity);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method,
     * always casting down its argument and then handing it off to a worker
     * function which does the actual work. In all probabilty, the class Render
     * is generic (Render<T extends Entity) and this method has signature public
     * void doRender(T entity, double d, double d1, double d2, float f, float
     * f1). But JAD is pre 1.5 so doesn't do that.
     */
    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.func_82426_a((EntityClicker) par1Entity, par2, par4, par6, par8, par9);
    }
}
