package mhfc.net.common.index;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;

/**
 * This class contains everything that needs to be refered to outside the code. This includes localizations, filenames,
 * config keys.<br>
 * If something does not need to be synched with resources, it should be put into the most specific registry class in
 * the .core.registry packet.
 *
 * @author WorldSEnder
 *
 */
public class ResourceInterface {
	public static final String main_config_file = "config/MHFC.cfg";
	public static final String main_config_mobCategory = "MHFC Mobs";
	public static final String main_config_mobs_summonTigrex_key = "SummonTigrex";
	public static final String main_modid = "mhfc";
	@Mod.Metadata(ResourceInterface.main_modid)
	private static ModMetadata main_metadata = null;

	public static ModMetadata getMetadata() {
		return main_metadata;
	}

	public static final String area_playfield_name = "mhfc.area.playfield.name";
	public static final String area_arena_name = "mhfc.area.arena.name";
	public static final String area_desert_name = "mhfc.area.desert.name";
	public static final String area_test_name = "mhfc.area.test.name";
	public static final String area_pokevillage_name = "mhfc.area.village_poke.name";
	
	// new 1.11
	
	public static final String area_sandy_name = "mhfc.area.sandy.name";
	public static final String area_greenvalley_name = "mhfc.area.greenvalley.name";

	public static final String armor_null_tex = "mhfc:textures/models/armor/null.png";
	public static final String armor_kirinS_boots_name = "c.mhf_boots";
	public static final String armor_kirinS_chest_name = "c.mhf_chest";
	public static final String armor_kirinS_helm_name = "c.mhf_helm";
	public static final String armor_kirinS_legs_name = "c.mhf_leg";
	public static final String armor_kirinS_tex = "mhfc:kirins";

	public static final String armor_kirin_boots_name = "b.mhf_boots";
	public static final String armor_kirin_buff_name = "kirinbuff";
	public static final String armor_kirin_chest_name = "b.mhf_chest";
	public static final String armor_kirin_helm_name = "b.mhf_helm";
	public static final String armor_kirin_legs_name = "b.mhf_legs";
	public static final String armor_kirin_tex = "mhfc:kirin";

	public static final String armor_tigrex_boots_name = "a.mhf_boots";
	public static final String armor_tigrex_chest_name = "a.mhf_chest";
	public static final String armor_tigrex_helm_name = "a.mhf_helm";
	public static final String armor_tigrex_legs_name = "a.mhf_leg";
	public static final String armor_tigrex_tex = "mhfc:tigrex";

	public static final String armor_yukumo_boots_name = "d.mhf_boots";
	public static final String armor_yukumo_chest_name = "d.mhf_chest";
	public static final String armor_yukumo_helm_name = "d.mhf_helm";
	public static final String armor_yukumo_legs_name = "d.mhf_leg";
	public static final String armor_yukumo_tex = "mhfc:yukumo";

	public static final String armor_rathalos_boots_name = "e.mhf_boots";
	public static final String armor_rathalos_chest_name = "e.mhf_chest";
	public static final String armor_rathalos_helm_name = "e.mhf_helm";
	public static final String armor_rathalos_legs_name = "e.mhf_leg";
	public static final String armor_rathalos_tex = "mhfc:rathalos";

	public static final String armor_dragoon_boots_name = "f.mhf_boots";
	public static final String armor_dragoon_chest_name = "f.mhf_chest";
	public static final String armor_dragoon_helm_name = "f.mhf_helm";
	public static final String armor_dragoon_legs_name = "f.mhf_leg";
	public static final String armor_dragoon_tex = "mhfc:dragoon";

	public static final String armor_velociprey_boots_name = "g.mhf_boots";
	public static final String armor_velociprey_chest_name = "g.mhf_chest";
	public static final String armor_velociprey_helm_name = "g.mhf_helm";
	public static final String armor_velociprey_legs_name = "g.mhf_leg";
	public static final String armor_velociprey_tex = "mhfc:velociprey";

	public static final String armor_tigrexb_boots_name = "h.mhf_boots";
	public static final String armor_tigrexb_chest_name = "h.mhf_chest";
	public static final String armor_tigrexb_helm_name = "h.mhf_helm";
	public static final String armor_tigrexb_legs_name = "h.mhf_leg";
	public static final String armor_tigrex_plus_tex = "mhfc:kishin";

	public static final String armor_nibelsnarf_boots_name = "i.mhf_boots";
	public static final String armor_nibelsnarf_chest_name = "i.mhf_chest";
	public static final String armor_nibelsnarf_helm_name = "i.mhf_helm";
	public static final String armor_nibelsnarf_legs_name = "i.mhf_leg";
	public static final String armor_nibelsnarf_tex = "mhfc:nibelsnarf";

	public static final String armor_bionic_helm_name = "j.mhf_helm";
	public static final String armor_bionic_chest_name = "j.mhf_chest";
	public static final String armor_bionic_legs_name = "j.mhf_leg";
	public static final String armor_bionic_boots_name = "j.mhf_boots";
	public static final String armor_bionic_tex = "mhfc:st_bionic";

	public static final String armor_deviljho_helm_name = "k.mhf_helm";
	public static final String armor_deviljho_chest_name = "k.mhf_chest";
	public static final String armor_deviljho_legs_name = "k.mhf_leg";
	public static final String armor_deviljho_boots_name = "k.mhf_boots";
	public static final String armor_deviljho_tex = "mhfc:deviljho";

	public static final String armor_greatjaggi_helm_name = "l.mhf_helm";
	public static final String armor_greatjaggi_chest_name = "l.mhf_chest";
	public static final String armor_greatjaggi_legs_name = "l.mhf_leg";
	public static final String armor_greatjaggi_boots_name = "l.mhf_boots";
	public static final String armor_greatjaggi_tex = "mhfc:greatjaggi";

	public static final String armor_barroth_helm_name = "m.mhf_helm";
	public static final String armor_barroth_chest_name = "m.mhf_chest";
	public static final String armor_barroth_legs_name = "m.mhf_leg";
	public static final String armor_barroth_boots_name = "m.mhf_boots";
	public static final String armor_barroth_tex = "mhfc:barroth";

	public static final String block_bbqspit_icon = "mhfc:armorstandblock";
	public static final String block_bbqspit_name = "bbqspit";
	public static final String block_blockingot0_tex = "mhfc:blockingot0";
	public static final String block_blockingot1_tex = "mhfc:blockingot1";
	public static final String block_blockingot2_tex = "mhfc:blockingot2";
	public static final String block_discstone_name = "discstone";
	public static final String block_discstone_tex = "mhfc:discstone";

	public static final String block_oreblock_basename = "wyverniaoreblocks";
	public static final String block_carbalite_name = "carbalite";
	public static final String block_carbalite_tex = "mhfc:carbalite";
	public static final String block_eltalite_name = "eltalite";
	public static final String block_eltalite_tex = "mhfc:eltalite";
	public static final String block_dragonite_name = "dragonite";
	public static final String block_dragonite_tex = "mhfc:dragonite";
	public static final String block_machalite_name = "machalite";
	public static final String block_machalite_tex = "mhfc:machalite";

	public static final String block_ores_basename = "wyverniaores";
	public static final String block_orearmorsphere_name = "orearmorsphere";
	public static final String block_orearmorsphereplus_name = "orearmorsphereplus";
	public static final String block_oremachalite_name = "oremachalite";
	public static final String block_orecarbalite_name = "orecarbalite";
	public static final String block_oredragonite_name = "oredragonite";
	public static final String block_oreeltalite_name = "oreeltalite";
	public static final String block_orefurukuraito_name = "orefurukuraito";

	public static final String block_wyverniaflower_basename = "wyverniaflower";
	public static final String block_carncase_name = "flowercarncase";
	public static final String block_felron_name = "flowerfelron";
	public static final String block_orctal_name = "flowerorctal";
	public static final String block_peno_name = "flowerpeno";
	public static final String block_shrine_name = "flowershrine";
	public static final String block_spindel_name = "flowerspindel";
	//1.11
	public static final String block_berpis_name = "flowerberpis";
	public static final String block_concave_name = "flowerconcave";
	public static final String block_delphi_name = "flowerdelphi";
	public static final String block_ember_name = "flowerember";
	public static final String block_gresha_name = "flowergresha";
	public static final String block_mowal_name = "flowermowal";
	public static final String block_neptia_name = "flowerneptia";
	public static final String block_roy_name = "flowerroy";
	public static final String block_sampa_name = "flowersampa";
	public static final String block_silon_name = "flowersilon";
	
	public static final String block_wyverniaplant_basename = "wyverniaplant";
	public static final String block_plantb1_name = "plant_b1";
	public static final String block_plantb2_name = "plant_b2";
	public static final String block_plantb3_name = "plant_b3";
	public static final String block_plantb4_name = "plant_b4";
	public static final String block_plantt1_name = "plant_t1";
	public static final String block_plantt2_name = "plant_t2";
	public static final String block_plantt3_name = "plant_t3";
	public static final String block_plantt4_name = "plant_t4";

	public static final String block_wyverniaplank_basename = "wyverniaplanks";
	public static final String block_calfer_name = "plankcalfer";
	public static final String block_direwood_name = "plankdirewood";
	public static final String block_grandifolia_name = "plankgrandifolia";
	public static final String block_maven_name = "plankmaven";
	public static final String block_negundo_name = "planknegundo";
	public static final String block_palmer_name = "plankpalmer";
	public static final String block_radel_name = "plankradel";
	public static final String block_sandy_name = "planksandy";
	public static final String block_tilia_name = "planktilia";

	public static final String block_wyverniarock_basename = "wyverniarock";
	public static final String block_auvel_name = "rockauvel";
	public static final String block_cradle_name = "rockcradle";
	public static final String block_tacren_name = "rocktacren";

	public static final String block_quicksand_name = "quicksand";

	public static final String block_wyvernialog_basename = "wyverniawood";
	public static final String block_log_calfer_name = "logcalfer";
	public static final String block_log_direwood_name = "logdirewood";
	public static final String block_log_grandifolia_name = "loggrandifolia";
	public static final String block_log_maven_name = "logmaven";
	public static final String block_log_negundo_name = "lognegundo";
	public static final String block_log_palmer_name = "logpalmer";
	public static final String block_log_radel_name = "logradel";
	public static final String block_log_sandy_name = "logsandy";
	public static final String block_log_tilia_name = "logtilia";

	public static final String block_hunterbench_name = "benchtable";
	public static final String block_icecrystal_name = "icecrystal";
	public static final String block_losgable_name = "losgable";
	public static final String block_stuntrap_name = "trapstun";
	public static final String block_wyverianclay_name = "wyverniaclayblock";
	public static final String block_wyveriandirt_name = "wyverniadirt";
	public static final String block_wyveriangrass_name = "wyverniagrass";
	public static final String block_wyveriansand_name = "wyverniasand";
	public static final String block_wyverianstone_name = "wyverniastone";
	public static final String block_questBoard_name = "questboard";
	public static final String block_respawn_name = "respawn";
	public static final String block_exploreArea_name = "exploreArea";

	public static final String entity_rathalosFireball_name = "Fireball";
	public static final String entity_rathalosfireball_tex = "mhfc:textures/projectile/rathalosfireball.png";
	public static final String entity_tigrexBlock_name = "TigrexBlock";
	public static final String entity_bullet_name = "Bullet";
	public static final String entity_flashbomb_name = "Flash bomb";
	public static final String gui_board_tex = "mhfc:textures/gui/mhfcbackground.png";
	public static final String gui_hunterbench_back_tex = "mhfc:textures/gui/mhfccraftstation.png";
	public static final String gui_hunterbench_burn_back_tex = "mhfc:textures/gui/ovenheat.png";
	public static final String gui_hunterbench_burn_front_tex = "mhfc:textures/gui/ovenborder.png";
	public static final String gui_hunterbench_burn_target_tex = "mhfc:textures/gui/heatindicator.png";
	public static final String gui_hunterbench_complete_tex = "mhfc:textures/gui/progarrow.png";
	public static final String gui_hunterbench_fuel_tex = "mhfc:textures/gui/progflame.png";
	public static final String gui_hunterbench_name = "Hunter's Bench";
	public static final String gui_hunterbench_tab_armor_name = "Armor Crafting";
	public static final String gui_hunterbench_tab_weapon_name = "Weapon Crafting";
	public static final String gui_status_inventory_tex = "textures/gui/demo_background.png";
	public static final String gui_status_onscreen_tex = "textures/gui/demo_background.png";
	public static final String gui_tab_texture = "mhfc:textures/gui/TabbedGui.png";
	public static final String gui_tab_name = "MHFC Tab";
	public static final String gui_list_tex = "mhfc:textures/gui/guilist.png";

	public static final String item_arrow0_name = "arrow0";

	public static final String item_armorsphere_basename = "armorsphere";
	public static final String item_armorsphere0_name = "normal";
	public static final String item_armorsphere1_name = "plus";

	public static final String item_base_basename = "base";
	public static final String item_base0_name = "anumium";
	public static final String item_base1_name = "meganum";

	public static final String item_bombmaterial_name = "bombmaterial";
	public static final String item_firestone_name = "firestone";
	public static final String item_gaguaegg_name = "gaguaegg";

	public static final String item_ingot_basename = "ingot";
	public static final String item_ingot0_name = "carbalite";
	public static final String item_ingot1_name = "dragonite";
	public static final String item_ingot2_name = "eltalite";
	public static final String item_ingot3_name = "machalite";

	public static final String item_remobra_basename = "remobra";
	public static final String item_remobra0_name = "skin";
	public static final String item_remobra1_name = "skull";
	public static final String item_remobra2_name = "wing";

	public static final String item_deviljho_basename = "deviljho";
	public static final String item_deviljho0_name = "scale";
	public static final String item_deviljho1_name = "fang";
	public static final String item_deviljho2_name = "hide";
	public static final String item_deviljho3_name = "talon";
	public static final String item_deviljho4_name = "scalp";
	public static final String item_deviljho5_name = "tail";

	public static final String item_kirin_basename = "kirin";
	public static final String item_kirin0_name = "mane";
	public static final String item_kirin1_name = "gem";
	public static final String item_kirin2_name = "tail";
	public static final String item_kirin3_name = "lightcrystal";
	public static final String item_kirin4_name = "purecrystal";
	public static final String item_kirin5_name = "mane";
	
	public static final String item_bug_basename = "bug";
	public static final String item_bug0_name = "insecthusk";
	public static final String item_bug1_name = "yambug";
	public static final String item_bug2_name = "bughopper";
	public static final String item_bug3_name = "snakebeelarva";
	public static final String item_bug4_name = "godbug";
	public static final String item_bug5_name = "bitterbug";
	public static final String item_bug6_name = "flashbug";
	public static final String item_bug7_name = "thunderbug";
	public static final String item_bug8_name = "carpenterbug";
	public static final String item_bug9_name = "kingscarab";
	public static final String item_bug10_name = "emperorcricket";
	public static final String item_bug11_name = "killerbeetle";
	public static final String item_bug12_name = "hercudrome";
	public static final String item_bug13_name = "rarescarab";
	public static final String item_bug14_name = "phantombutterfly";
	public static final String item_bug15_name = "rainbowinsect";
	public static final String item_bug16_name = "greathornfly";
	public static final String item_bug17_name = "greatladybug";
	public static final String item_bug18_name = "royalrhino";
	public static final String item_bug19_name = "divinerhino";
	public static final String item_bug20_name = "gluehopper";
	

	public static final String item_rathalos_basename = "rathalos";
	public static final String item_rathalos0_name = "shell";
	public static final String item_rathalos1_name = "webbing";
	public static final String item_rathalos2_name = "marrow";
	public static final String item_rathalos3_name = "wing";
	public static final String item_rathalos4_name = "plate";

	public static final String item_tigrex_basename = "tigrex";
	public static final String item_tigrex0_name = "scale";
	public static final String item_tigrex1_name = "shell";
	public static final String item_tigrex2_name = "fang";
	public static final String item_tigrex3_name = "claw";
	public static final String item_tigrex4_name = "tail";
	public static final String item_tigrex5_name = "skull";

	public static final String item_meat_basename = "meat";
	public static final String item_rawmeat_name = "raw";
	public static final String item_cookedmeat_name = "cooked";
	public static final String item_boostmeat_name = "boost";
	public static final String item_protectionmeat_name = "protection";
	public static final String item_poisonmeat_name = "poison";
	public static final String item_slowmeat_name = "slow";
	public static final String item_hungermeat_name = "hunger";
	public static final String item_firemeat_name = "fire";

	public static final String item_nutrients_basename = "nutrients";
	public static final String item_normalnutrients_name = "normal";
	public static final String item_meganutrient_name = "mega";

	public static final String item_bullet_basename = "bullet";
	public static final String item_bulletnormal_name = "normalS";
	public static final String item_bulletpierce_name = "pierceS";
	public static final String item_bulletcrag_name = "cragS";
	public static final String item_bulletflame_name = "flameS";

	public static final String item_lumberbar_name = "lumberbar";
	public static final String item_mhfcspawnegg_name = "mhfcegg";
	public static final String item_moldediron_name = "moldediron";

	public static final String item_sac_basename = "sac";
	public static final String item_sac0_name = "screamer";
	public static final String item_sac1_name = "poison";
	public static final String item_sac2_name = "toxin";
	public static final String item_sac3_name = "deadlypoison";
	public static final String item_sac4_name = "paralysis";
	public static final String item_sac5_name = "sleep";
	public static final String item_sac6_name = "coma";
	public static final String item_sac7_name = "flame";
	public static final String item_sac8_name = "inferno";
	public static final String item_sac9_name = "blazingfire";
	public static final String item_sac10_name = "electro";
	public static final String item_sac11_name = "thunder";
	public static final String item_sac12_name = "lightning";

	public static final String item_steelbar_name = "steelbar";

	public static final String item_traptool_name = "traptool";
	public static final String item_flashbomb_name = "flashbomb";
	public static final String item_woodrig_name = "woodrig";
	public static final String item_wyverncoin_name = "wyverncoin";
	public static final String item_wyverniaclay_name = "wyverniaclay";
	public static final String item_wyverniadust_name = "wyverniadust";

	// If something persist or crash and pointed at this line of codes. probably
	// you have a wrong input of directory @Heltrato

	// Resource General Location
	public static final String modelLoc = "mhfc:models/";
	public static final String textureLoc = "mhfc:textures/models/";

	// Tigrex
	public static final String mob_tigrex_name = "tigrex";
	public static final String mob_tigrex_dir = modelLoc + "Tigrex/";
	public static final String mob_tigrex_textureDir = textureLoc + "Tigrex/";
	public static final String mob_tigrex_model = mob_tigrex_dir + "Tigrex.mcmd";
	public static final String mob_tigrex_skeleton = mob_tigrex_dir + "Armature.mcskl";

	// Rathalos
	public static final String mob_rathalos_name = "rathalos";
	public static final String mob_rathalos_dir = modelLoc + "Rathalos/";
	public static final String mob_rathalos_textureDir = textureLoc + "Rathalos/";
	public static final String mob_rathalos_model = mob_rathalos_dir + "Rathalos.mcmd";
	public static final String mob_rathalos_skeleton = mob_rathalos_dir + "Armature.mcskl";

	// Great Jaggi
	public static final String mob_greatjaggi_name = "greatjaggi";
	public static final String mob_greatjaggi_dir = modelLoc + "GreatJaggi/";
	public static final String mob_greatjaggi_textureDir = textureLoc + "GreatJaggi/";
	public static final String mob_greatjaggi_model = mob_greatjaggi_dir + "GreatJaggi.mcmd";
	public static final String mob_greatjaggi_skeleton = mob_greatjaggi_dir + "Armature.mcskl";

	// Lagiacrus
	public static final String mob_lagiacrus_name = "lagiacrus";
	public static final String mob_lagiacrus_dir = modelLoc + "Lagiacrus/";
	public static final String mob_lagiacrus_textureDir = textureLoc + "Lagiacrus/";
	public static final String mob_lagiacrus_model = mob_lagiacrus_dir + "Lagiacrus.mcmd";
	public static final String mob_lagiacrus_skeleton = mob_lagiacrus_dir + "Armature.mcskl";

	// Delex
	public static final String mob_delex_name = "delex";
	public static final String mob_delex_dir = modelLoc + "Delex/";
	public static final String mob_delex_textureDir = textureLoc + "Delex/";
	public static final String mob_delex_model = mob_delex_dir + "PDelex.mcmd";
	public static final String mob_delex_skeleton = mob_delex_dir + "Armature.mcskl";

	// Nargacuga
	public static final String mob_nargacuga_name = "nargacuga";
	public static final String mob_nargacuga_dir = modelLoc + "Nargacuga/";
	public static final String mob_nargacuga_textureDir = textureLoc + "Nargacuga/";
	public static final String mob_nargacuga_model = mob_nargacuga_dir + "Nargacuga.mcmd";
	public static final String mob_nargacuga_skeleton = mob_nargacuga_dir + "Armature.mcskl";

	// Deviljho
	public static final String mob_deviljho_name = "deviljho";
	public static final String mob_deviljho_dir = modelLoc + "Deviljho/";
	public static final String mob_deviljho_textureDir = textureLoc + "Deviljho/";
	public static final String mob_deviljho_model = mob_deviljho_dir + "Deviljho.mcmd";
	public static final String mob_deviljho_skeleton = mob_deviljho_dir + "Armature.mcskl";

	// Kirin
	public static final String mob_kirin_name = "kirin";
	public static final String mob_kirin_dir = modelLoc + "Kirin/";
	public static final String mob_kirin_textureDir = textureLoc + "Kirin/";
	public static final String mob_kirin_model = mob_kirin_dir + "Kirin.mcmd";
	public static final String mob_kirin_skeleton = mob_kirin_dir + "Armature.mcskl";

	//Gagua
	public static final String mob_gagua_name = "gagua";
	public static final String mob_gagua_dir = modelLoc + "Gagua/";
	public static final String mob_gagua_textureDir = textureLoc + "Gagua/";
	public static final String mob_gagua_model = mob_gagua_dir + "Gagua.mcmd";
	public static final String mob_gagua_skeleton = mob_gagua_dir + "Armature.mcskl";

	// Barroth
	public static final String mob_barroth_name = "barroth";
	public static final String mob_barroth_dir = modelLoc + "Barroth/";
	public static final String mob_barroth_textureDir = textureLoc + "Barroth/";
	public static final String mob_barroth_model = mob_barroth_dir + "Barroth.mcmd";
	public static final String mob_barroth_skeleton = mob_barroth_dir + "Armature.mcskl";

	// Giaprey
	public static final String mob_giaprey_name = "giaprey";
	public static final String mob_giaprey_model = modelLoc + "Giaprey/Giaprey.mcmd";

	// Ukanlos
	public static final String mob_ukanlos_name = "ukanlos";
	public static final String mob_ukanlos_model = modelLoc + "Ukanlos/Ukanlos.mcmd";

	// WARNING: YOU HAVE TO GENERATE A NEW UUID FOR EVERY POTION
	// go to https://www.uuidgenerator.net/ to do so
	public static final String particle_kirinsfx_tex = "mhfc:textures/particle/kirinSFX.png";
	public static final String potion_attackup_name = "potion.attackuplow";
	public static final String potion_longsworddamageup_name = "potion.longswordattackup";
	public static final String potion_kirinbless_name = "potion.kirinbless";
	public static final String potion_kirinbless_tex = "mhfc:textures/potion/mhfcpotion.png";
	public static final String potion_paralyze_name = "potion.paralyze";
	public static final String potion_paralyze_tex = "mhfc:textures/potion/mhfcpotion.png";

	public static final String tile_armorstand_id = "Armor Stand";
	public static final String tile_armorstand_tex = "mhfc:textures/tile/armorstand.png";
	public static final String tile_armorstandbase_id = "Armor Stand Base";
	public static final String tile_armorstandbase_tex = "mhfc:textures/tile/armorstandbase.png";
	public static final String tile_bbqspit_id = "BBQ Spit";
	public static final String tile_bbqspit_tex = "mhfc:textures/tile/bbqspit.png";
	public static final String tile_huntersbench_id = "Hunter's Bench";
	public static final String tile_huntertable_tex = "mhfc:textures/tile/huntertable.png";
	public static final String tile_questboard_id = "Quest Board";
	public static final String tile_questboard_tex = "mhfc:textures/tile/questboard.png";
	public static final String tile_stuntrap_id = "Stun Trap";
	public static final String tile_stuntrap_tex = "mhfc:textures/tile/stuntrap.png";
	public static final String tile_exploreArea_id = "Explore Area";

	// Weapon names, see mhfc\net\common\core\registry\weapons.json

	public static final String weapon_bow_name = "type.weapon_bow";
	public static final String weapon_chargeaxe_name = "type.weapon_chargeaxe";
	public static final String weapon_dualsword_name = "type.weapon_double_sword";
	public static final String weapon_gunlance_name = "type.weapon_gunlance";
	public static final String weapon_lance_name = "type.weapon_lance";
	public static final String weapon_lightbowgun_name = "type.weapon_small_bowgun";
	public static final String weapon_heavybowgun_name = "type.weapon_big_bowgun";
	public static final String weapon_mediumbowgun_name = "type.weapon_medium_bowgun";
	public static final String weapon_dualbowgun_name = "type.weapon_dual_bowgun";
	public static final String weapon_greatsword_name = "type.weapon_great_sword";
	public static final String weapon_hammer_name = "type.weapon_hammer";
	public static final String weapon_huntinghorn_name = "type.weapon_hunting_horn";
	public static final String weapon_insectstaff_name = "type.weapon_insectstaff";
	public static final String weapon_longsword_name = "type.weapon_long_sword";
	public static final String weapon_swordshield_name = "type.weapon_sword_shield";

	public static final String projectile_wyverniaarrow_name = "Wyvernia Arrow";
	public static final String projectile_wyverniaarrow_tex = "mhfc:textures/projectile/wyverniaarrow.png";
	public static final String projectile_wyverniabullet_name = "Wyvernia Bullet";
	public static final String projectile_wyverniabullet_tex = "mhfc:textures/projectile/bullet0.png";
	public static final String projectile_breathe_name = "Breathe";
	public static final String projectile_breathe_tex = "mhfc:textures/projectile/invinsible.png";
	public static final String projectile_nargacugaspike_name = "Nargacuga Spike";
	public static final String projectile_nargacugaspike_tex = "mhfc:textures/projectile/bullet0.png";

	public static final String gui_questgiver_back_tex = "mhfc:textures/gui/questgiver.png";
	public static final String gui_huntinghorn_stave = "mhfc:textures/gui/notesheet.png";
	public static final String gui_huntinghorn_note = "mhfc:textures/gui/notes.png";
	public static final String gui_longsword_gauge = "mhfc:textures/gui/spiritgauge.png";

	public static final int potion_paralyze_iconindex = 1;
	public static final int potion_kirinbless_iconindex = 2;
	public static final int potion_attackuplow_iconindex = 3;

	public static final String mob_questGiver_name = "questGiver";
	public static final String unlocalized_tag_fee = "mhfc.quests.visual.tag.fee";
	public static final String unlocalized_tag_reward = "mhfc.quests.visual.tag.reward";
	public static final String unlocalized_tag_time = "mhfc.quests.visual.tag.time";
	public static final String unlocalized_tag_area = "mhfc.quests.visual.tag.area";
	public static final String unlocalized_tag_aims = "mhfc.quests.visual.tag.aims";
	public static final String unlocalized_tag_fails = "mhfc.quests.visual.tag.fails";
	public static final String unlocalized_tag_client = "mhfc.quests.visual.tag.client";
	public static final String unlocalized_tag_description = "mhfc.quests.visual.tag.description";
	public static final String unlocalized_tag_monsters = "mhfc.quests.visual.tag.monsters";
	public static final String unlocalized_tag_requisites = "mhfc.quests.visual.tag.requisites";
	public static final String unlocalized_tag_status_short = "mhfc.quests.visual.tag.statusshort";
	public static final String unlocalized_tag_status_long = "mhfc.quests.visual.tag.statuslong";

	public static final String questLocation = "mhfc:quests/quests.json";
	public static final String goalLocation = "mhfc:quests/goals.json";
	public static final String groupLocation = "mhfc:quests/groups.json";

	public static final String item_paintball_basename = "paintball";
	public static final String entity_paintball_name = "Paintball";
	public static final int max_duration_particle_emitter_in_ticks = 1200;
	public static final String mob_paint_emitter_name = "paintemitter";


}
