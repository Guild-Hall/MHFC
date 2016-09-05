package mhfc.net.common.core.registry;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.util.services.IServiceKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MHFCSoundRegistry {

	public static void staticInit() {}

	private static final IServiceKey<MHFCSoundRegistry> serviceAccess = RegistryWrapper
			.registerService("sound registry", MHFCSoundRegistry::new, MHFCMain.initPhase);

	public final SoundEvent barrothIdle;
	public final SoundEvent barrothDeath;
	public final SoundEvent barrothCharge;
	public final SoundEvent barrothRoar;
	public final SoundEvent barrothHeadsmash;
	public final SoundEvent barrothStomp;

	public final SoundEvent delexDeath;

	public final SoundEvent deviljhoIdle;
	public final SoundEvent deviljhoDeath;
	public final SoundEvent deviljhoRoar;
	public final SoundEvent deviljhoStep;
	public final SoundEvent deviljhoStomp;
	public final SoundEvent deviljhoBiteA;
	public final SoundEvent deviljhoBiteB;
	public final SoundEvent deviljhoDragonBreath;
	public final SoundEvent deviljhoLeap;
	public final SoundEvent deviljhoRockThrow;
	public final SoundEvent deviljhoTailWhip;

	public final SoundEvent greatJaggiDeath;
	public final SoundEvent greatJaggiRoar;
	public final SoundEvent greatJaggiBite;
	public final SoundEvent greatJaggiTailWhip;

	public final SoundEvent gargwaIdle;
	public final SoundEvent gargwaDeath;
	public final SoundEvent gargwaBite;

	public final SoundEvent kirinIdle;

	public final SoundEvent lagiacrusIdle;
	public final SoundEvent lagiacrusDeath;
	public final SoundEvent lagiacrusRoar;
	public final SoundEvent lagiacrusBeam;
	public final SoundEvent lagiacrusBite;
	public final SoundEvent lagiacrusSweep;

	public final SoundEvent nargacugaIdle;
	public final SoundEvent nargacugaDeath;
	public final SoundEvent nargacugaRoar;
	public final SoundEvent nargacugaBackOff;
	public final SoundEvent nargacugaCharge;
	public final SoundEvent nargacugaPounce;
	public final SoundEvent nargacugaTailSlam;

	public final SoundEvent rathalosIdle;
	public final SoundEvent rathalosDeath;
	public final SoundEvent rathalosCharge;
	public final SoundEvent rathalosBite;
	public final SoundEvent rathalosTailWhip;

	public final SoundEvent tigrexIdle;
	public final SoundEvent tigrexStep;
	public final SoundEvent tigrexDeath;
	public final SoundEvent tigrexCharge;
	public final SoundEvent tigrexRoar;
	public final SoundEvent tigrexHurt;
	public final SoundEvent tigrexBite;
	public final SoundEvent tigrexLeap;
	public final SoundEvent tigrexRockThrow;
	public final SoundEvent tigrexTailWhip;

	public final SoundEvent huntingHornPlayNote;

	public MHFCSoundRegistry() {
		barrothIdle = registerSoundEvent("mhfc:barroth.idle");
		barrothDeath = registerSoundEvent("mhfc:barroth.death");
		barrothCharge = registerSoundEvent("mhfc:barroth.charge");
		barrothRoar = registerSoundEvent("mhfc:barroth.roar");
		barrothHeadsmash = registerSoundEvent("mhfc:barroth.headsmash");
		barrothStomp = registerSoundEvent("mhfc:barroth.stomp");

		delexDeath = registerSoundEvent("mhfc:delex.death");

		deviljhoIdle = registerSoundEvent("mhfc:deviljho.idle");
		deviljhoDeath = registerSoundEvent("mhfc:deviljho.death");
		deviljhoRoar = registerSoundEvent("mhfc:deviljho.roar");
		deviljhoStep = registerSoundEvent("mhfc:deviljho.step");
		deviljhoStomp = registerSoundEvent("mhfc:deviljho.stomp");
		deviljhoBiteA = registerSoundEvent("mhfc:deviljho.bitea");
		deviljhoBiteB = registerSoundEvent("mhfc:deviljho.biteb");
		deviljhoDragonBreath = registerSoundEvent("mhfc:deviljho.dragonbreath");
		deviljhoLeap = registerSoundEvent("mhfc:deviljho.leap");
		deviljhoRockThrow = registerSoundEvent("mhfc:deviljho.rockthrow");
		deviljhoTailWhip = registerSoundEvent("mhfc:deviljho.tailwhip");

		greatJaggiDeath = registerSoundEvent("mhfc:greatjaggi.death");
		greatJaggiRoar = registerSoundEvent("mhfc:greatjaggi.roar");
		greatJaggiBite = registerSoundEvent("mhfc:greatjaggi.bite");
		greatJaggiTailWhip = registerSoundEvent("greatjaggi.tailwhip");

		gargwaIdle = registerSoundEvent("mhfc:gagua.idle");
		gargwaDeath = registerSoundEvent("mhfc:gagua.death");
		gargwaBite = registerSoundEvent("mhfc:gagua.bite");

		kirinIdle = registerSoundEvent("mhfc:kirin.idle");

		lagiacrusIdle = registerSoundEvent("mhfc:lagiacrus.idle");
		lagiacrusDeath = registerSoundEvent("mhfc:lagiacrus.death");
		lagiacrusRoar = registerSoundEvent("mhfc:lagiacrus.roar");
		lagiacrusBeam = registerSoundEvent("mhfc:lagiacrus.discharge");
		lagiacrusBite = registerSoundEvent("mhfc:lagiacrus.bite");
		lagiacrusSweep = registerSoundEvent("mhfc:lagiacrus.sweep");

		nargacugaIdle = registerSoundEvent("mhfc:narga.idle");
		nargacugaDeath = registerSoundEvent("mhfc:narga.death");
		nargacugaRoar = registerSoundEvent("mhfc:narga.roar");
		nargacugaBackOff = registerSoundEvent("mhfc:narga.leapback");
		nargacugaCharge = registerSoundEvent("mhfc:narga.charge");
		nargacugaPounce = registerSoundEvent("mhfc:narga.leapforward");
		nargacugaTailSlam = registerSoundEvent("mhfc:narga.tailjump");

		rathalosIdle = registerSoundEvent("mhfc:rathalos.idle");
		rathalosDeath = registerSoundEvent("mhfc:rathalos.death");
		rathalosCharge = registerSoundEvent("mhfc:rathalos.charge");
		rathalosBite = registerSoundEvent("mhfc:rathalos.bite");
		rathalosTailWhip = registerSoundEvent("mhfc:rathalos.tailwhip");

		tigrexIdle = registerSoundEvent("mhfc:tigrex.idle");
		tigrexStep = registerSoundEvent("mhfc:tigrex.step");
		tigrexDeath = registerSoundEvent("mhfc:tigrex.death");
		tigrexRoar = registerSoundEvent("mhfc:tigrex.roar");
		tigrexCharge = registerSoundEvent("mhfc:tigrex.charge");
		tigrexHurt = registerSoundEvent("mhfc:tigrex.hurt");
		tigrexBite = registerSoundEvent("mhfc:tigrex.bite");
		tigrexLeap = registerSoundEvent("mhfc:tigrex.leapforward");
		tigrexRockThrow = registerSoundEvent("mhfc:tigrex.rockthrow");
		tigrexTailWhip = registerSoundEvent("mhfc:tigrex.tailwhip");

		huntingHornPlayNote = registerSoundEvent(MHFCReference.weapon_hh_notesound);
	}

	private SoundEvent registerSoundEvent(String nameAndLocation) {
		return registerSoundEvent(nameAndLocation, nameAndLocation);
	}

	/**
	 * Registers a sound event. Note that the name should be consistent across versions of the mod, while the actual
	 * location may change at will.
	 *
	 * @param name
	 * @param soundLocation
	 * @return
	 */
	private SoundEvent registerSoundEvent(String name, String soundLocation) {
		return GameRegistry.register(new SoundEvent(new ResourceLocation(soundLocation)).setRegistryName(name));
	}

	public static MHFCSoundRegistry getRegistry() {
		return serviceAccess.getService();
	}
}
