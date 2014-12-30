package cr0s.WarpDrive.space;

import java.lang.reflect.Field;

import cpw.mods.fml.common.Loader;
import cr0s.WarpDrive.WarpDrive;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;

public class SpaceTpCommand extends CommandBase {
	
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandName() {
        return "space";
    }

    @Override
    public void processCommand(ICommandSender icommandsender, String[] astring) {
        EntityPlayerMP player = (EntityPlayerMP)icommandsender;
        MinecraftServer server = MinecraftServer.getServer();
        int targetDim = WarpDrive.instance.spaceDimID;

        if (astring.length >= 1) {
            if ("hyper".equals(astring[0])) {
                targetDim = WarpDrive.instance.hyperSpaceDimID;
            } else if (Loader.isModLoaded("SpaceAgePlanets")) {
            	try {
            		Class c = Class.forName("spaceage.planets.SpaceAgePlanets");
            		
            		Field vulcan = c.getField("vulcanID");
    				Field hades = c.getField("hadesID");
    				Field t0011 = c.getField("T0011ID");
    				Field eden = c.getField("edenID");
    				Field ontarine = c.getField("ontarineID");
    				
    				int vulcanID = Integer.parseInt(vulcan.toString());
    				int hadesID = Integer.parseInt(hades.toString());
    				int t0011ID = Integer.parseInt(t0011.toString());
    				int edenID = Integer.parseInt(eden.toString());
    				int ontarineID = Integer.parseInt(ontarine.toString());
    				
    				if ("vulcan".equals(astring[0])) {
    					targetDim = vulcanID;
    					System.out.println("TOTALLY HAD THE TARGET DIM AS VULCAN LOL");
    				} else if ("hades".equals(astring[0])) {
    					targetDim = hadesID;
    				} else if ("0011".equals(astring[0])) {
    					targetDim = t0011ID;
    				} else if ("eden".equals(astring[0])) {
    					targetDim = edenID;
    				} else if ("ontarine".equals(astring[0])) {
    					targetDim = ontarineID;
    				}
            	} catch(Exception e) {
            		e.printStackTrace();
            	}
            } else {
                notifyAdmins(icommandsender, "/space: teleporting player " + astring[0] + " to space", new Object[0]);
                player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(astring[0]);
            }
        }

        if (player == null) {
            return;
        }

        WorldServer to = server.worldServerForDimension(targetDim);
        SpaceTeleporter teleporter = new SpaceTeleporter(to, 0, MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ));
        server.getConfigurationManager().transferPlayerToDimension(player, targetDim, teleporter);
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender) {
        return "/space [hyper|<player>]";
    }
}