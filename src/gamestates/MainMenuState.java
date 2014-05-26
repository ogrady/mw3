package gamestates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import util.Const;

/**
 * This is the main menu the player sees when starting up the game (TODO: for
 * now, the game starts with the playing-state as going through the menu for
 * testing would be exhausting).<br>
 * Not very much to see here so far. Only a white on black string to test the
 * splitting into states.
 *
 * @author Daniel
 *
 */
public class MainMenuState extends BasicGameState {

	@Override
	public void init(final GameContainer gc, final StateBasedGame sbg)
			throws SlickException {

	}

	@Override
	public void render(final GameContainer gc, final StateBasedGame sbg,
			final Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.drawString("This is the menu. Press ESC to return to the game", 100,
				100);
	}

	@Override
	public void update(final GameContainer gc, final StateBasedGame sbg,
			final int delta) throws SlickException {
		if (gc.getInput().isKeyDown(Input.KEY_ESCAPE)) {
			sbg.enterState(Const.PLAYING_STATE_ID);
		}
	}

	@Override
	public int getID() {
		return Const.MAIN_MENU_STATE_ID;
	}

}
