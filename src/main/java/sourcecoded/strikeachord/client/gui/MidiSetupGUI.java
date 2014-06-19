package sourcecoded.strikeachord.client.gui;

import cpw.mods.fml.client.GuiIngameModOptions;
import cpw.mods.fml.client.GuiModOptionList;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import sourcecoded.strikeachord.midi.MidiUtils;

public class MidiSetupGUI extends GuiScreen {

    MidiSetupList optionList;

    int BUTTON_DONE = 200;
    int BUTTON_START = 201;

    public FontRenderer getFontRenderer() {
        return this.fontRendererObj;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        this.optionList=new MidiSetupList(this);
        this.optionList.registerScrollButtons(this.buttonList, 7, 8);
        this.buttonList.add(new GuiButton(BUTTON_START, this.width / 2 - 100, this.height - 50, "Start Midi"));
        this.buttonList.add(new GuiButton(BUTTON_DONE, this.width / 2 - 100, this.height - 25, I18n.format("gui.done")));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button.id == BUTTON_DONE) {
                this.mc.displayGuiScreen(null);
            } else if (button.id == BUTTON_START) {
                MidiUtils.init();
                MidiUtils.startRecording();
                MidiUtils.doRefresh();
            }
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.optionList.drawScreen(par1, par2, par3);
        this.drawCenteredString(this.fontRendererObj, "Midi Options", this.width / 2, 15, 0xFFFFFF);
        super.drawScreen(par1, par2, par3);
    }

}
