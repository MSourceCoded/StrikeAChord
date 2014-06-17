package sourcecoded.strikeachord;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import sourcecoded.strikeachord.proxy.CommonProxy;

@Mod(modid = StrikeAChord.MODID, version = StrikeAChord.VERSION)
public class StrikeAChord {

    public static final String MODID = "strikeachord";
    public static final String VERSION = "1.7.2 - 0.0.1";

    @SidedProxy(clientSide = "sourcecoded.strikeachord.proxy.ClientProxy", serverSide = "sourcecoded.strikeachord.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent ev) {

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent ev) {
        proxy.registerProxy();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent ev) {

    }

}
