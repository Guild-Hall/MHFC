package mhfc.net.common.eventhandler.player;

import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.armor.ArmorBase;
import mhfc.net.common.util.Attributes;
import mhfc.net.common.weapon.melee.PerceptionHelper;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

@Mod.EventBusSubscriber(modid = ResourceInterface.main_modid)
public class WorldPlayerEvent {

	/**
	 * The reason why i replaced this as WorldPlayerEvent is i want to add every event that a player includes occurs.
	 **/

	public static final WorldPlayerEvent instance = new WorldPlayerEvent();

	@SubscribeEvent
	public static void onPlayerCreation(EntityConstructing e) {
		if (e.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e.getEntity();
			player.getAttributeMap().registerAttribute(Attributes.ATTACK_COOLDOWN);
			player.getAttributeMap().registerAttribute(Attributes.RELOAD_TIME);
			player.getAttributeMap().registerAttribute(Attributes.WEAPON_REACH);
		}
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent e) {
		if (!e.player.world.isRemote) {
			return;
		}
		if (e.phase != TickEvent.Phase.END) {
			return;
		}

		if (e.player == null || e.player.swingProgressInt != 1) {
			return;
		}

		IAttributeInstance attribute = e.player.getEntityAttribute(Attributes.WEAPON_REACH);
		double reach = attribute.getAttributeValue();
		RayTraceResult mov = PerceptionHelper.getMouseOver(0, (float) reach);

		if (mov != null && mov.entityHit != null && mov.entityHit != e.player && mov.entityHit.hurtResistantTime == 0) {
			FMLClientHandler.instance().getClient().playerController.attackEntity(e.player, mov.entityHit);
		}
	}

	@SubscribeEvent
	public static void onToolTipPoint(ItemTooltipEvent event) {
		if ((event.getItemStack().getItem() instanceof ArmorBase)) {
			for (int i = 0; i < event.getToolTip().size(); i++) {
				if (event.getToolTip().get(i).contains("Armor Toughness") && event.getToolTip().get(i).contains("+")) {
					event.getToolTip().remove(i);
					event.getToolTip().remove(i - 1);
				}
			}
			for (int i = 0; i < event.getToolTip().size(); i++) {
				if (event.getToolTip().get(i).contains("Armor") && event.getToolTip().get(i).contains("+")) {
					event.getToolTip().remove(i);
					event.getToolTip().remove(i - 1);
				}
			}
		}
	}

	/**
	 * TODO 1.5 WIP, if the player is inside the quest world, a partial ticks should occur, example temperature hotness
	 * etc etc.
	 **/
	@SubscribeEvent
	public static void onPlayerInsideQuestAreaEvent(PlayerTickEvent event) {

	}

	//TODO 1.5
	/** This should disable the game rules properly and will work only if the player is in the dimension. **/
	@SubscribeEvent
	public static void onPlayerEnterTheWorld(EntityJoinWorldEvent event) {
	}


}
