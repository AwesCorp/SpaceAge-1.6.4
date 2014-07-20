package cr0s.WarpDrive.client;

import cpw.mods.fml.client.FMLClientHandler;
import cr0s.WarpDrive.Vector3;
import cr0s.WarpDrive.WarpDriveConfig;
import cr0s.WarpDrive.common.CommonProxy;
import cr0s.WarpDrive.utils.FXBeam;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {
	
	public static String warpInterfaceGui;
	public static String guiRadar;
	public static String guiCloakingDeviceCore;
	public static String guiMiningLaser;
	public static String guiReactor;
	
    @Override
    public void registerRenderers() {
    	if(WarpDriveConfig.i.GUI_NORMAL_STYLE == false) {
    		warpInterfaceGui = "guiWarpInterface_alternate.png";
    		guiRadar = "guiRadar_alternate.png";
    		guiReactor = "guiReactor_alternate.png";
    		guiCloakingDeviceCore = "guiCloakingDevice_alternate.png";
    		guiMiningLaser = "guiMiningLaser_alternate.png";
    	} else {
    		warpInterfaceGui = "guiWarpInterface.png";
    		guiRadar = "guiRadar.png";
    		guiReactor = "guiReactor.png";
    		guiCloakingDeviceCore = "guiCloakingDevice.png";
    		guiMiningLaser = "guiMiningLaser.png";
    	}
    }

    @Override
    public void renderBeam(World world, Vector3 position, Vector3 target, float red, float green, float blue, int age, int energy) {
        //System.out.println("Rendering beam...");
        FMLClientHandler.instance().getClient().effectRenderer.addEffect(new FXBeam(world, position, target, red, green, blue, age, energy));
    }
}