package spaceage.common.audio;

import spaceage.common.SpaceAgeCore;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class SpaceAgeAudio {
	
	@ForgeSubscribe
	public void onSound(SoundLoadEvent event) {
		//event.manager.addSound(SpaceAgeCore.modid + ":"/* + "hit.ogg"*/);
	}

}
