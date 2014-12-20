package sound;

import util.Const;
import environment.character.mech.Mech.CharacterActionName;
import environment.character.mech.Nitro;

/**
 * Soundmanager for {@link Nitro}
 * 
 * @author Daniel
 *
 */
public class NitroSoundManager extends AudioManager<Nitro> {

	public NitroSoundManager(final Nitro target) {
		super(target);
		target.getCharacterAction(CharacterActionName.PRIMARY_ATTACK)
		.getListeners()
		.registerListener(new ActionSound(Const.SFX_PATH + "laser.wav"));
	}

}
