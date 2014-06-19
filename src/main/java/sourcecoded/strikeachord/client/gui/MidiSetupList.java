package sourcecoded.strikeachord.client.gui;

import cpw.mods.fml.client.GuiScrollingList;
import net.minecraft.client.renderer.Tessellator;
import sourcecoded.strikeachord.midi.MidiUtils;

import javax.sound.midi.MidiDevice;

public class MidiSetupList extends GuiScrollingList {

    MidiSetupGUI parent;

    MidiDevice.Info[] infos;
    int selectedIndex;

    public MidiSetupList(MidiSetupGUI parent) {
        super(parent.mc, parent.width - 32, parent.height, 32, parent.height - 65 + 4, 10, 35);
        this.parent = parent;
        infos = MidiUtils.getInfoList();
    }

    public MidiDevice.Info getSelectedInfo() {
        return infos[selectedIndex];
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    @Override
    protected int getSize() {
        return infos.length;
    }

    @Override
    protected void elementClicked(int index, boolean doubleClick) {
        selectedIndex = index;
        MidiUtils.setInputDevice(infos[index]);
    }

    @Override
    protected boolean isSelected(int index) {
        return index == selectedIndex;
    }

    @Override
    protected void drawBackground() {
    }

    @Override
    protected void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5) {
        if (var3 > 32 && var3 < (parent.height - 69)) {
            this.parent.getFontRenderer().drawString(this.parent.getFontRenderer().trimStringToWidth(infos[var1].getName(), listWidth - 10), this.left + 3, var3, 0xFFFFFF);
            this.parent.getFontRenderer().drawString(this.parent.getFontRenderer().trimStringToWidth(infos[var1].getDescription(), listWidth - 10), this.left + 3, var3 + 10, 167115);
        }
    }
}

