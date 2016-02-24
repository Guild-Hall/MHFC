package mhfc.net.common.eventhandler;

import java.util.Objects;

import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;

public enum TickPhase {
	CLIENT_PRE,
	CLIENT_POST,
	PLAYER_PRE,
	PLAYER_POST,
	RENDER_PRE,
	RENDER_POST,
	SERVER_PRE,
	SERVER_POST,
	WORLD_PRE,
	WORLD_POST;

	public static TickPhase forEvent(TickEvent event) {
		Objects.requireNonNull(event);
		Objects.requireNonNull(event.phase);
		Objects.requireNonNull(event.type);

		switch (event.type) {
		case CLIENT:
			return event.phase == Phase.START ? CLIENT_PRE : CLIENT_POST;
		case PLAYER:
			return event.phase == Phase.START ? PLAYER_PRE : PLAYER_POST;
		case RENDER:
			return event.phase == Phase.START ? RENDER_PRE : RENDER_POST;
		case SERVER:
			return event.phase == Phase.START ? SERVER_PRE : SERVER_POST;
		case WORLD:
			return event.phase == Phase.START ? WORLD_PRE : WORLD_POST;
		}
		throw new IllegalStateException("Thought unreachable");
	}
}
