package net.crayonsmp.interfaces;

import lombok.Getter;
import lombok.Setter;
import net.crayonsmp.enums.MISBlockType;
import org.bukkit.block.Block;


public interface MISBlock extends Block {
    int MISNetworkID();
    int MISChennel();
    MISBlockType MISBlockType();
    boolean BlockOnline();
}
