package sourcecoded.strikeachord;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.gen.feature.WorldGenWaterlily;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import org.lwjgl.input.Keyboard;
import sourcecoded.strikeachord.client.gui.MidiSetupGUI;
import sourcecoded.strikeachord.network.Pkt0x01Ping;
import sourcecoded.strikeachord.network.Pkt0x02PingReply;
import sourcecoded.strikeachord.network.SACPacketPipeline;
import sourcecoded.strikeachord.proxy.CommonProxy;

@Mod(modid = StrikeAChord.MODID, version = StrikeAChord.VERSION)
public class StrikeAChord {

    public static final String MODID = "strikeachord";
    public static final String VERSION = "1.7.10 - 0.1.0";

    public static volatile boolean canTakePackets = false;

    @SideOnly(Side.CLIENT)
    static KeyBinding menuKey;

    @SidedProxy(clientSide = "sourcecoded.strikeachord.proxy.ClientProxy", serverSide = "sourcecoded.strikeachord.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent ev) {
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent ev) {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);

        proxy.registerProxy();

        SACPacketPipeline.INSTANCE.init();

        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            menuKey = new KeyBinding("[Strike A Chord] Options Menu", Keyboard.KEY_C, "Strike A Chord");
            ClientRegistry.registerKeyBinding(menuKey);
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent ev) {

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void worldLoad(WorldEvent.Load ev) {
        if (ev.world.isRemote) {
            canTakePackets = false;
        }
    }

    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        SACPacketPipeline.INSTANCE.sendTo(new Pkt0x02PingReply(), (EntityPlayerMP) event.player);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent ev) {
        if (menuKey.getIsKeyPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new MidiSetupGUI());
        }
    }
}
