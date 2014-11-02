package uedevkit.network;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import uedevkit.tile.TileElectricNetworked;
import universalelectricity.core.net.Network;

/** Energy network designed to allow several tiles to act as if they share the same energy
 * level */
public class EnergyDistributionNetwork extends Network<EnergyDistributionNetwork, TileElectricNetworked> {
    public long totalEnergy = 0;
    public long totalCapacity = 0;

    public EnergyDistributionNetwork() {
        super(TileElectricNetworked.class);
    }

    public void redistribute(TileElectricNetworked... exclusion) {
        int lowestY = 255, highestY = 0;

        totalEnergy = 0;
        totalCapacity = 0;

        for (TileElectricNetworked connector : this.getConnectors()) {
            totalEnergy += connector.getEnergyHandler().getEnergy();
            totalCapacity += connector.getEnergyHandler().getEnergyCapacity();

            lowestY = Math.min(connector.yCoord, lowestY);
            highestY = Math.max(connector.yCoord, highestY);

            connector.renderEnergyAmount = 0;
        }

        /** Apply render */
        long remainingRenderEnergy = totalEnergy;

        for (int y = lowestY; y <= highestY; y++) {
            Set<TileElectricNetworked> connectorsInlevel = new LinkedHashSet<TileElectricNetworked>();

            for (TileElectricNetworked connector : this.getConnectors()) {
                if (connector.yCoord == y) {
                    connectorsInlevel.add(connector);
                }
            }

            int levelSize = connectorsInlevel.size();
            long used = 0;

            for (TileElectricNetworked connector : connectorsInlevel) {
                long tryInject = Math.min(remainingRenderEnergy / levelSize, connector.getEnergyHandler().getEnergyCapacity());
                connector.renderEnergyAmount = tryInject;
                used += tryInject;
            }

            remainingRenderEnergy -= used;

            if (remainingRenderEnergy <= 0)
                break;
        }

        /** Apply energy loss. */
        double percentageLoss = 0;// Math.max(0, (1 - (getConnectors().size() * 6 / 100d)));
        long energyLoss = (long) (percentageLoss * 100);
        totalEnergy -= energyLoss;

        int amountOfNodes = this.getConnectors().size() - exclusion.length;

        if (totalEnergy > 0 && amountOfNodes > 0) {
            long remainingEnergy = totalEnergy;

            TileElectricNetworked firstNode = this.getFirstConnector();

            for (TileElectricNetworked node : this.getConnectors()) {
                if (node != firstNode && !Arrays.asList(exclusion).contains(node)) {
                    double percentage = ((double) node.getEnergyHandler().getEnergyCapacity() / (double) totalCapacity);
                    long energyForBattery = Math.max(Math.round(totalEnergy * percentage), 0);
                    node.getEnergyHandler().setEnergy(energyForBattery);
                    remainingEnergy -= energyForBattery;
                }
            }

            firstNode.getEnergyHandler().setEnergy(Math.max(remainingEnergy, 0));
        }
    }

    @Override
    protected void reconstructConnector(TileElectricNetworked node) {
        node.setNetwork(this);
    }

    @Override
    public EnergyDistributionNetwork newInstance() {
        return new EnergyDistributionNetwork();
    }
}