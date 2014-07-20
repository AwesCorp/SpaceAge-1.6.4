package spaceage.common.prefab.tile;

public interface FluidGuiHelper {
	
    /** A calculator used for scaling fluid, usually for drawing pixels in GUIs, but also to get percentages.  
     * 
     * @param capacityScaledTo
     * @return The fluid currently in the block in relation to the new capacity.
     * @author SkylordJoel
     */
    public int getFluidRemainingScaled(int capacityScaledTo);

}
