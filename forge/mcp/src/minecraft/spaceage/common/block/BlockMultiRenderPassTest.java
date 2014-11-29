package spaceage.common.block;

import spaceage.client.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockMultiRenderPassTest extends Block {

	public BlockMultiRenderPassTest(int id, Material material) {
		super(id, material);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return ClientProxy.blockMultiRenderType;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getRenderBlockPass() {
		return 1;
	}
	
	@Override
	public boolean canRenderInPass(int pass) {
		ClientProxy.renderPass = pass;
		return true;
	}
}
