package sourcecoded.strikeachord;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.world.WorldEvent;
import org.apache.logging.log4j.Level;
import sourcecoded.strikeachord.proxy.CommonProxy;
import sourcecoded.strikeachord.utils.WorldCache;

@Mod(modid = StrikeAChord.MODID, version = StrikeAChord.VERSION)
public class StrikeAChord {

    public static final String MODID = "strikeachord";
    public static final String VERSION = "1.7.2 - 0.0.1";

    @SidedProxy(clientSide = "sourcecoded.strikeachord.proxy.ClientProxy", serverSide = "sourcecoded.strikeachord.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent ev) {
        if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
            System.err.println("Strike A Chord is Client-Side Only!");
            return;
        }
    }

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void init(FMLInitializationEvent ev) {
        MinecraftForge.EVENT_BUS.register(this);
        proxy.registerProxy();
    }

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent ev) {

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void event(WorldEvent.Load ev) {
        //Store the world in here for playsound
        if (!ev.world.isRemote) {
            WorldCache.cachedWorld = ev.world;
        }
    }

    @SubscribeEvent
    public void event2(BonemealEvent ev) {
        WorldCache.cachedWorld.playSoundEffect((double)ev.x, (double)ev.y, (double)ev.z, "note.harp", 3.0F, 1.0F);
    }
}
