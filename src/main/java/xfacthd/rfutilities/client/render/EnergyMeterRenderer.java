package xfacthd.rfutilities.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import xfacthd.rfutilities.RFUtilities;
import xfacthd.rfutilities.common.blockentity.EnergyMeterBlockEntity;

public class EnergyMeterRenderer implements BlockEntityRenderer<EnergyMeterBlockEntity>
{
    public static final Component LABEL_THROUGHPUT = Component.translatable("label.rfutilities.energy_meter.throughput");
    public static final Component LABEL_ACCUMULATED = Component.translatable("label.rfutilities.energy_meter.accumulated");
    private static final ResourceLocation FULLSPACE_FONT = new ResourceLocation(RFUtilities.MOD_ID, "full_space");
    private static final ResourceLocation FANCY_FONT = new ResourceLocation(RFUtilities.MOD_ID, "seven_segment");
    private static final Style FULLSPACE_FONT_STYLE = Style.EMPTY.withFont(FULLSPACE_FONT);
    private static final Style FANCY_FONT_STYLE = Style.EMPTY.withFont(FANCY_FONT);
    private static final Style DEFAULT_FONT_STYLE = Style.EMPTY.withFont(Style.DEFAULT_FONT);
    private static final Component SINGLE_FULLSPACE = Component.literal(" ").setStyle(FULLSPACE_FONT_STYLE);
    private static final Component DEFAULT_EMPTY = Component.literal("         ").setStyle(FULLSPACE_FONT_STYLE)
            .append(Component.literal("-").setStyle(DEFAULT_FONT_STYLE))
            .append(SINGLE_FULLSPACE)
            .append(Component.literal(" FE").setStyle(DEFAULT_FONT_STYLE));
    private static final Component FANCY_EMPTY = Component.literal("          ").setStyle(FANCY_FONT_STYLE)
            .append(SINGLE_FULLSPACE)
            .append(Component.literal(" FE").setStyle(DEFAULT_FONT_STYLE));
    private static final String DEFAULT_VALUE_FORMAT = "%10d";
    private static final String FANCY_VALUE_FORMAT = "%010d";
    private static final long MAX_VALUE = 9999999999L;
    private static final int TEXT_COLOR = 0xFFFFFF;
    private static final int TEXT_LIGHT = LightTexture.FULL_BRIGHT;
    private static final Component[] UNITS = new Component[] {
            Component.literal("k"),
            Component.literal("M"),
            Component.literal("G"),
            Component.literal("T"),
            Component.literal("P"),
            Component.literal("E")
    };

    private static boolean useFancyFont = false;
    private static Component dummyText = null;
    private final Font fontrenderer;

    public EnergyMeterRenderer(BlockEntityRendererProvider.Context ctx)
    {
        fontrenderer = ctx.getFont();
        onFancyFontConfigChanged(false);
    }

    @Override
    public void render(EnergyMeterBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay)
    {
        Minecraft.getInstance().getProfiler().push("rfutilities_energymeter");

        poseStack.pushPose();

        poseStack.translate(0.5D, 0.5D, 0.5D);
        float rot = -be.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot();
        poseStack.mulPose(Vector3f.YP.rotationDegrees(rot));
        poseStack.translate(-0.5D + 3.1/16, -0.5D + 9.9/16, 0.5001D);

        float scale = calculateScale();
        poseStack.scale(scale, -scale, scale);

        Matrix4f pose = poseStack.last().pose();

        fontrenderer.drawInBatch(LABEL_THROUGHPUT, 0, 0, TEXT_COLOR, false, pose, buffer, false, 0, TEXT_LIGHT);
        fontrenderer.drawInBatch(LABEL_ACCUMULATED, 0, 25, TEXT_COLOR, false, pose, buffer, false, 0, TEXT_LIGHT);

        Component text = formatScientific(be.getCurrentPower(), "FE/t");
        fontrenderer.drawInBatch(text, 0, 10, TEXT_COLOR, false, pose, buffer, false, 0, TEXT_LIGHT);

        if (be.isRecording())
        {
            text = formatScientific(be.getAccumulatedPower(), "FE");
            fontrenderer.drawInBatch(text, 0, 35, TEXT_COLOR, false, pose, buffer, false, 0, TEXT_LIGHT);
        }
        else
        {
            text = useFancyFont ? FANCY_EMPTY : DEFAULT_EMPTY;
            fontrenderer.drawInBatch(text, 0, 35, TEXT_COLOR, false, pose, buffer, false, 0, TEXT_LIGHT);
        }

        poseStack.popPose();

        Minecraft.getInstance().getProfiler().pop();
    }

    private float calculateScale()
    {
        float dummyWidth = fontrenderer.width(dummyText);
        return (1F / dummyWidth) * (9.8F / 16F);
    }

    private Component formatScientific(long value, String unit)
    {
        int prefixIdx = -1;
        while (value > MAX_VALUE && prefixIdx < (UNITS.length - 1))
        {
            value /= 1000;
            prefixIdx++;
        }
        Component unitPrefix = prefixIdx >= 0 ? UNITS[prefixIdx] : null;

        String valueFormat = useFancyFont ? FANCY_VALUE_FORMAT : DEFAULT_VALUE_FORMAT;
        String valueString = String.format(valueFormat, value).replace(' ', '_'); //TODO: find a way to get rid of the underscores
        MutableComponent valueText = Component.literal(valueString);
        MutableComponent unitText = Component.literal(" ")
                .append(unitPrefix != null ? unitPrefix : SINGLE_FULLSPACE)
                .append(Component.literal(unit).setStyle(DEFAULT_FONT_STYLE));

        if (useFancyFont)
        {
            valueText.setStyle(FANCY_FONT_STYLE);
            unitText.setStyle(DEFAULT_FONT_STYLE);
        }

        return valueText.append(unitText);
    }

    @Override
    public boolean shouldRender(EnergyMeterBlockEntity blockEntity, Vec3 cameraPos)
    {
        //TODO: only render when the front is visible
        return BlockEntityRenderer.super.shouldRender(blockEntity, cameraPos);
    }



    public static void onFancyFontConfigChanged(boolean fancyFont)
    {
        if (dummyText != null && useFancyFont == fancyFont) { return; }

        useFancyFont = fancyFont;

        MutableComponent numbers = Component.literal("0123456789");
        if (fancyFont)
        {
            numbers.setStyle(FANCY_FONT_STYLE);
        }
        MutableComponent unit = Component.literal(" _FE/t");
        if (fancyFont)
        {
            unit.setStyle(DEFAULT_FONT_STYLE);
        }
        dummyText = numbers.append(unit);
    }
}
