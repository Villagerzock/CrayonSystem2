package net.crayonsmp.objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.crayonsmp.interfaces.MISBlock;
import net.crayonsmp.objects.blocks.MagicalCable;
import net.crayonsmp.objects.blocks.MagicalCore;
import net.crayonsmp.objects.blocks.MagicalDrive;
import net.crayonsmp.objects.blocks.io.MagicalExport;
import net.crayonsmp.objects.blocks.io.MagicalImport;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
public class MISNetwork {

    private final int networkID;

    private final int mexMISChennels;

    private boolean isNetworkOnline;

    private List<MagicalCore> cores;

    private List<MagicalCable> cables;

    private List<MagicalDrive> drives;

    private List<MagicalImport> imports;

    private List<MagicalExport> exports;

    private HashMap<Integer, MISBlock> MISChennels;

    private List<ItemStack> allStorredItems;
}
