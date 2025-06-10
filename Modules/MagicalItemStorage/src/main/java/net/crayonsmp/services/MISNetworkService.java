package net.crayonsmp.services;

import net.crayonsmp.interfaces.MISBlock;
import net.crayonsmp.objects.MISNetwork;

import java.util.HashMap;

public class MISNetworkService {
    public static HashMap<Integer, MISNetwork> MISNetworks;

    public static MISNetwork getMISNetworkByID(int MISNetworkID) {
        return MISNetworks.get(MISNetworkID);
    }

    public static MISBlock getMISBlockByChennelID(MISNetwork misNetwork, int MISChennel) {

    }

}
