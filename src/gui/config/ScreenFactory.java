package gui.config;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.screen.DefaultScreenController;
import de.lessvoid.nifty.screen.Screen;

/**
 * Factory for building the screens that consist solely of a GUI. Usable for the
 * config-menu and maybe the settings when creating a new game. TODO: load the
 * GUIs from an xml-file instead of building them manually.
 *
 * @author Daniel
 *
 */
public class ScreenFactory {
	private static final String CONFIG_ID = "configScreen";

	/**
	 * No instantiation for factory classes
	 */
	private ScreenFactory() {
	}

	public static final Screen buildConfigScreen(final Nifty nifty) {
		final Screen configScreen = new ScreenBuilder(CONFIG_ID) {
			{
				controller(new DefaultScreenController());
				layer(new LayerBuilder("layer") {
					{
						backgroundColor("#003f");
						childLayoutCenter();
						panel(new PanelBuilder() {
							{
								id("panel");
								childLayoutCenter();
								/*height(percentage(25));
								width(percentage(80));*/
								control(new LabelBuilder("mylabel") {
									{
										text("This is the menu. Press ESC to return to the game");
									}
								});
							}
						});
					}
				});
			}
		}.build(nifty);
		return configScreen;
	}

}
