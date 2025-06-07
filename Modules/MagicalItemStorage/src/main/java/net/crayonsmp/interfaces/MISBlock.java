package net.crayonsmp.interfaces;

import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import org.bukkit.block.Block;

public interface MISBlock extends Block {
    int getMISNetworkID();
    int getMISChennel();
    boolean isBlockOnline();
}
