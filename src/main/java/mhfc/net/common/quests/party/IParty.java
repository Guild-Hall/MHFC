package mhfc.net.common.quests.party;

import java.util.Collection;

import net.minecraft.entity.player.EntityPlayer;

/**
 * An descriptive implementation for parties. This interface is used to
 * represent the party in an immutable way. It is exposed to provide information
 * about the party.
 *
 * @author WorldSEnder
 *
 */
public interface IParty {
	/**
	 * Gets the party leader
	 *
	 * @return the player that leads the party
	 */
	EntityPlayer getLeader();
	/**
	 * Gets all players that are in the party, including the leader
	 *
	 * @return a collection of all party members
	 */
	Collection<EntityPlayer> getPartyMembers();
}
