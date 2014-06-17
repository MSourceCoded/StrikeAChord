package sourcecoded.strikeachord.proxy;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerProxy() {
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return FMLClientHandler.instance().getClientPlayerEntity();
    }

}
