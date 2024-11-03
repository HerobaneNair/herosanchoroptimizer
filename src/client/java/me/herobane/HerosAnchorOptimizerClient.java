package me.herobane;

import me.herobane.block.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class HerosAnchorOptimizerClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Set block render layers for custom blocks
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FAKE_ANCHOR, RenderLayer.getTranslucent());

		// Register an event for right-clicking a respawn anchor
		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			if (world.isClient() && world.getBlockState(hitResult.getBlockPos()).isOf(Blocks.RESPAWN_ANCHOR)) {

				boolean isSingleplayer = MinecraftClient.getInstance().isInSingleplayer();
				int charge = world.getBlockState(hitResult.getBlockPos()).get(RespawnAnchorBlock.CHARGES);
				boolean holdingGlowstoneMainHand = player.getStackInHand(hand).isOf(Items.GLOWSTONE);
				boolean holdingGlowstoneOffHand = player.getOffHandStack().isOf(Items.GLOWSTONE);
				String dimension = world.getRegistryKey().getValue().toString();
				boolean wouldExplode = !dimension.contains("_nether");

				if (!player.isSneaking() && !holdingGlowstoneMainHand && !holdingGlowstoneOffHand && !isSingleplayer) {
					if ((charge >= 1 && charge <= 3 && wouldExplode) || (charge == 4 && wouldExplode)) {
						placeClientSideFakeAnchor(world, hitResult);
						return ActionResult.SUCCESS;
					}
				}
				return ActionResult.PASS;
			}
			return ActionResult.PASS;
		});
	}

	// Place a fake anchor client-side
	private void placeClientSideFakeAnchor(World world, BlockHitResult hitResult) {
		BlockPos pos = hitResult.getBlockPos();
		world.setBlockState(pos, ModBlocks.FAKE_ANCHOR.getDefaultState());
	}
}
