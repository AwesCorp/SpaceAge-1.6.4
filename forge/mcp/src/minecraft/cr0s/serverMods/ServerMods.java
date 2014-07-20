package cr0s.serverMods;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = "ServerMods", name = "ServerMods", version = "0.0.1")
@NetworkMod(clientSideRequired = false, serverSideRequired = true, connectionHandler = LoginHookClass.class)

/**
 * @author Cr0s, SkylordJoel
 */
public class ServerMods
{
    // The instance of your mod that Forge uses.
    @Instance("ServerMods")
    public static ServerMods instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        // Stub Method
    }

    @EventHandler
    public void load(FMLInitializationEvent event)
    {
        //
        //proxy.setupLoginHook();
        // 
        MinecraftForge.EVENT_BUS.register(new AntiFallDamage());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        // Stub Method
    }
}