package renderer.slick;

/**
 * This interface references to the button assignment of the original game.<br>
 * There the SNES-gamepad was used to have 12 input-events in total so we're
 * having 12 inputs in total which can be remapped to other input-devices but
 * will have the same names (so the onStartButton-event can be caused by any
 * keyboard-key, mouse-button or whatever is mapped onto this event) and should
 * maintain the basic function as in the original game as described below<br>
 * Those actions will be sent to the renderer from the controller (which can of
 * course be anything besides an actual input-device, such as a network
 * connection or an AI).<br>
 * <br>
 * The d-pad, having four directions:
 * <ul>
 * <li><strong>Up</strong> determines the launch direction for Ballistic when
 * charged</li>
 * <li><strong>Down</strong> determines the launch direction for Ballistic when
 * charged</li>
 * <li><strong>Left</strong> makes the character go left</li>
 * <li><strong>Right</strong> makes the character go right</li>
 * </ul>
 * The four action-buttons on the right side:
 * <ul>
 * <li><strong>A</strong> secondary attack (like sword)</li>
 * <li><strong>B</strong> jump</li>
 * <li><strong>X</strong> special (like stationary shield)</li>
 * <li><strong>Y</strong> primary attack (shoot)</li>
 * </ul>
 * Two shoulder-buttons L(eft) and R(ight):
 * <ul>
 * <li><strong>L</strong> use the currently equipped item</li>
 * <li><strong>R</strong> block</li>
 * </ul>
 * and two additional buttons:
 * <ul>
 * <li><strong>Select</strong> enter or exit a mech</li>
 * <li><strong>Start</strong> pause or unpause the game</li>
 * </ul>
 * <img src=
 * "http://upload.wikimedia.org/wikipedia/commons/2/20/SNES_Controller.jpg"
 * width="400px" height="200px">
 * 
 * @author Daniel
 * 
 */
public interface IControllableRenderer {
	/**
	 * called when the controller receives LEFT keypress
	 */
	void onLeftButton();

	/**
	 * called when the controller receives RIGHT keypress
	 */
	void onRightButton();

	/**
	 * called when the controller receives UP keypress
	 */
	void onUpButton();

	/**
	 * called when the controller receives DOWN keypress
	 */
	void onDownButton();

	/**
	 * called when the controller receives L keypress (use equipped item)
	 */
	void onItemButton();

	/**
	 * called when the controller receives R keypress (block)
	 */
	void onBlockButton();

	/**
	 * called when the controller receives A keypress (secondary attack)
	 */
	void onSecondaryAttackButton();

	/**
	 * called when the controller receives B keypress (jump)
	 */
	void onJumpButton();

	/**
	 * called when the controller receives X keypress (special action)
	 */
	void onSpecialActionButton();

	/**
	 * called when the controller receives Y keypress (primary attack)
	 */
	void onPrimaryAttackButton();

	/**
	 * called when the controller receives SELECT keypress (eject pilot / enter
	 * mech)
	 */
	void onSelectButton();

	/**
	 * called when the controller receives START keypress
	 */
	void onStartButton();
}
