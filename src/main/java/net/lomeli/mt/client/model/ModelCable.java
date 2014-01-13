package net.lomeli.mt.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCable extends ModelBase {
    // fields
    public ModelRenderer Bottom;
    public ModelRenderer Top;
    public ModelRenderer Left;
    public ModelRenderer Front;
    public ModelRenderer Back;
    public ModelRenderer Center;
    public ModelRenderer Right;

    public ModelCable() {
        textureWidth = 64;
        textureHeight = 32;

        Bottom = new ModelRenderer(this, 0, 0);
        Bottom.addBox(0F, 0F, 0F, 4, 6, 4);
        Bottom.setRotationPoint(-2F, 18F, -2F);
        Bottom.setTextureSize(64, 32);
        Bottom.mirror = true;
        setRotation(Bottom, 0F, 0F, 0F);
        Top = new ModelRenderer(this, 0, 0);
        Top.addBox(0F, 0F, 0F, 4, 6, 4);
        Top.setRotationPoint(-2F, 8F, -2F);
        Top.setTextureSize(64, 32);
        Top.mirror = true;
        setRotation(Top, 0F, 0F, 0F);
        Left = new ModelRenderer(this, 0, 10);
        Left.addBox(0F, 0F, 0F, 6, 4, 4);
        Left.setRotationPoint(2F, 14F, -2F);
        Left.setTextureSize(64, 32);
        Left.mirror = true;
        setRotation(Left, 0F, 0F, 0F);
        Front = new ModelRenderer(this, 0, 18);
        Front.addBox(0F, 0F, 0F, 4, 4, 6);
        Front.setRotationPoint(-2F, 14F, -8F);
        Front.setTextureSize(64, 32);
        Front.mirror = true;
        setRotation(Front, 0F, 0F, 0F);
        Back = new ModelRenderer(this, 0, 18);
        Back.addBox(0F, 0F, 0F, 4, 4, 6);
        Back.setRotationPoint(-2F, 14F, 2F);
        Back.setTextureSize(64, 32);
        Back.mirror = true;
        setRotation(Back, 0F, 0F, 0F);
        Center = new ModelRenderer(this, 16, 0);
        Center.addBox(0F, 0F, 0F, 4, 4, 4);
        Center.setRotationPoint(-2F, 14F, -2F);
        Center.setTextureSize(64, 32);
        Center.mirror = true;
        setRotation(Center, 0F, 0F, 0F);
        Right = new ModelRenderer(this, 0, 10);
        Right.addBox(0F, 0F, 0F, 6, 4, 4);
        Right.setRotationPoint(-8F, 14F, -2F);
        Right.setTextureSize(64, 32);
        Right.mirror = true;
        setRotation(Right, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Bottom.render(f5);
        Top.render(f5);
        Left.render(f5);
        Front.render(f5);
        Back.render(f5);
        Center.render(f5);
        Right.render(f5);
    }

    public void render(float f5) {
        Bottom.render(f5);
        Top.render(f5);
        Left.render(f5);
        Front.render(f5);
        Back.render(f5);
        Center.render(f5);
        Right.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

}
