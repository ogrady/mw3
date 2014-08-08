package gamestates;

import gui.config.ScreenFactory;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import util.Const;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.nulldevice.NullSoundDevice;
import de.lessvoid.nifty.renderer.lwjgl.input.LwjglInputSystem;
import de.lessvoid.nifty.renderer.lwjgl.render.LwjglRenderDevice;
import de.lessvoid.nifty.screen.Screen;

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
	private Nifty _nifty;

	@Override
	public void init(final GameContainer container, final StateBasedGame game)
			throws SlickException {
		// TODO: get the Slick2dRenderDivice to work (contained in another jar
		// in the nifty-pack. Currently version
		// conflicts. Nifty expects a method with CharSequence as parameter, our
		// version of Slick2D provides the method with String as parameter)
		_nifty = new Nifty(new LwjglRenderDevice(), new NullSoundDevice(),
				new LwjglInputSystem(),
				new de.lessvoid.nifty.spi.time.TimeProvider() {

					@Override
					public long getMsTime() {
						return System.currentTimeMillis();
					}
				});

		_nifty.loadStyleFile("nifty-default-styles.xml");
		_nifty.loadControlFile("nifty-default-controls.xml");

		final Screen configScreen = ScreenFactory.buildConfigScreen(_nifty);

		_nifty.addScreen(configScreen.getScreenId(), configScreen);
		_nifty.gotoScreen(configScreen.getScreenId());
	}

	@Override
	public void render(final GameContainer gc, final StateBasedGame sbg,
			final Graphics g) throws SlickException {
		SlickCallable.enterSafeBlock();
		_nifty.render(false);
		SlickCallable.leaveSafeBlock();
	}

	@Override
	public void update(final GameContainer gc, final StateBasedGame sbg,
			final int delta) throws SlickException {
		if (gc.getInput().isKeyDown(Input.KEY_ESCAPE)) {
			sbg.enterState(Const.PLAYING_STATE_ID);
		}
		_nifty.update();
	}

	@Override
	public int getID() {
		return Const.MAIN_MENU_STATE_ID;
	}

}
