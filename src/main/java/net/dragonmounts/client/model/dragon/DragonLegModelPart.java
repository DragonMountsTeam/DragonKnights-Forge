package net.dragonmounts.client.model.dragon;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class DragonLegModelPart extends ModelRenderer {
    public final int index;
    public final boolean left;
    public final ModelRenderer shank;
    public final ModelRenderer foot;
    public final ModelRenderer toe;

    public DragonLegModelPart(Model model, boolean hind, boolean left, DragonLegConfig config) {
        super(model);
        this.index = hind ? 1 : 0;
        this.left = left;
        this.buildThigh(config);
        this.addChild(this.shank = this.createShank(model, config));
        this.shank.addChild(this.foot = this.createFoot(model, config));
        this.foot.addChild(this.toe = this.createToe(model, config));
    }

    abstract protected void buildThigh(DragonLegConfig config);

    abstract protected ModelRenderer createShank(Model model, DragonLegConfig config);

    abstract protected ModelRenderer createFoot(Model model, DragonLegConfig config);

    abstract protected ModelRenderer createToe(Model model, DragonLegConfig config);

    public static class Fore extends DragonLegModelPart {
        public Fore(Model model, boolean left, DragonLegConfig config) {
            super(model, false, left, config);
        }

        @Override
        protected void buildThigh(DragonLegConfig config) {
            final int width = config.width;
            this.setPos(-11, 18, 4);
            this.texOffs(112, 0);
            this.addBox(config.defaultOffset, config.defaultOffset, config.defaultOffset, width, config.getThighLength(false), width);
        }

        @Override
        protected ModelRenderer createShank(Model model, DragonLegConfig config) {
            final int length = config.getShankLength(false);
            final ModelRenderer renderer = new ModelRenderer(model);
            renderer.texOffs(148, 0);
            renderer.setPos(0, config.getThighLength(false) + config.defaultOffset, 0);
            return renderer.addBox(config.shankOffset, config.shankOffset, config.shankOffset, config.shankWidth, length, config.shankWidth);
        }

        @Override
        protected ModelRenderer createFoot(Model model, DragonLegConfig config) {
            final int length = config.getFootLength(false);
            final ModelRenderer renderer = new ModelRenderer(model);
            renderer.texOffs(210, 0);
            renderer.setPos(0, config.getShankLength(false) + config.shankOffset, 0);
            return renderer.addBox(config.defaultOffset, config.footOffset, length * -0.75F, config.width, config.footHeight, length);
        }

        @Override
        protected ModelRenderer createToe(Model model, DragonLegConfig config) {
            final int length = config.getToeLength(false);
            final ModelRenderer renderer = new ModelRenderer(model);
            renderer.setPos(0, 0, config.getFootLength(false) * -0.75F - config.footOffset / 2F);
            renderer.texOffs(176, 0);
            return renderer.addBox(config.defaultOffset, config.footOffset, -length, config.width, config.footHeight, length);
        }
    }

    public static class Hind extends DragonLegModelPart {
        public Hind(Model model, boolean left, DragonLegConfig config) {
            super(model, true, left, config);
        }

        @Override
        protected void buildThigh(DragonLegConfig config) {
            final int width = config.width + 1;
            this.setPos(-11, 13, 4);
            this.texOffs(112, 29);
            this.addBox(config.defaultOffset, config.defaultOffset, config.defaultOffset, width, config.getThighLength(true), width);
        }

        @Override
        protected ModelRenderer createShank(Model model, DragonLegConfig config) {
            final int length = config.getShankLength(true);
            final ModelRenderer renderer = new ModelRenderer(model);
            renderer.texOffs(152, 29);
            renderer.setPos(0, config.getThighLength(true) + config.defaultOffset, 0);
            return renderer.addBox(config.shankOffset, config.shankOffset, config.shankOffset, config.shankWidth, length, config.shankWidth);
        }

        @Override
        protected ModelRenderer createFoot(Model model, DragonLegConfig config) {
            final int length = config.getFootLength(true);
            final ModelRenderer renderer = new ModelRenderer(model);
            renderer.texOffs(180, 29);
            renderer.setPos(0, config.getShankLength(true) + config.shankOffset, 0);
            return renderer.addBox(config.defaultOffset, config.footOffset, length * -0.75F, config.width, config.footHeight, length);
        }

        @Override
        protected ModelRenderer createToe(Model model, DragonLegConfig config) {
            final int length = config.getToeLength(true);
            final ModelRenderer renderer = new ModelRenderer(model);
            renderer.setPos(0, 0, config.getFootLength(true) * -0.75F - config.footOffset / 2F);
            renderer.texOffs(215, 29);
            return renderer.addBox(config.defaultOffset, config.footOffset, -length, config.width, config.footHeight, length);
        }
    }
}
