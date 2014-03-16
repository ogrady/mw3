package util;

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
	// names for the layers from the TMX-map
	public static final String MAP_LAYER_BACKGROUND = "background";
	public static final String MAP_LAYER_DESTRUCTABLE = "destructable";
	public static final String MAP_LAYER_SOLID = "solid";

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
	public static final long NITRO_SMG_DELAY = 100;
	/**
	 * Distance in pixels a bullet from Nitros SMG travels without hitting
	 * anything before despawning.
	 */
	public static final long NITRO_SMG_DISTANCE = 200;
	/**
	 * Base damage one bullet from Nitros SMG inflicts.
	 */
	public static float NITRO_SMG_DMG = 1;

	private Const() {
	}
}
