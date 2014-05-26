package util;

import java.io.File;

import environment.character.StationaryShield;

/**
 * All constant values for configuring ingame-mechanics ought to go here.<br>
 * Values, that can be configured by the user (like button-bindings,
 * screen-resolution, sound-volume, ...) are supposed to be inside the
 * configuration-file!<br>
 * The values stored inside this class are for aspects of the game, the player
 * has no control over.
 *
 * @author Daniel
 *
 */
public class Const {
	private static final char SEP = File.separatorChar;
	public static final String DEBUGFLAGS = "debugflags";

	// names for the layers from the TMX-map
	public static final String MAP_LAYER_BACKGROUND = "background";
	public static final String MAP_LAYER_DESTRUCTABLE = "destructable";
	public static final String MAP_LAYER_SOLID = "solid";

	// distinct directories
	public static final String RSC_PATH = "rsc" + SEP;
	public static final String CONF_PATH = RSC_PATH + "conf.properties";
	public static final String NITRO_RSC = RSC_PATH + "nitro" + SEP;

	/**
	 * Factor by which sprites are scaled.
	 */
	public static final float SCALE_FACTOR = 2;
	/**
	 * Delay between two rotations of a Mechs arm.
	 */
	public static final long MECH_ARM_ROTATION_DELAY = 200;
	/**
	 * Time in ms to live until Nitros stationary shield disappears on its own.
	 */
	public static final long NITRO_SHIELD_TTL = 100000;
	/**
	 * Amount of damage a {@link StationaryShield} can take before being
	 * destroyed.
	 */
	public static final int NITRO_SHIELD_HP = 10;
	/**
	 * Time in ms until Nitro can spawn another stationary shield.
	 */
	public static final long NITRO_SHIELD_DELAY = 1000;
	/**
	 * Time in ms until Nitro can fire his SMG again.
	 */
	public static final long NITRO_SMG_DELAY = 200;
	/**
	 * Delay between two strikes of Nitros sword.
	 */
	public static final long NITRO_SWORD_DELAY = 1000;
	/**
	 * Max shields Nitro can have on the screen
	 */
	public static final int NITRO_MAX_SHIELD_COUNT = 1;
	/**
	 * Distance in pixels a bullet from Nitros SMG travels without hitting
	 * anything before despawning.
	 */
	public static final long NITRO_SMG_DISTANCE = 400;
	/**
	 * Base damage one bullet from Nitros SMG inflicts.
	 */
	public static final float NITRO_SMG_DMG = 3;
	/**
	 * Speed at which the bullets from Nitros SMG fly
	 */
	public static final float NITRO_SMG_SPEED = 7f;
	/**
	 * Base damage Nitros sword inflicts.
	 */
	public static final float NITRO_SWORD_DMG = 5;
	/**
	 * HP Nitro initially has.
	 */
	public static final int NITRO_HP = 100;

	private Const() {
	}
}
