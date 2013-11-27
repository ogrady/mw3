package renderer.slick;

import listener.IListenable;
import listener.ListenerSet;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class ObservableAnimation extends Animation implements IListenable<IAnimationListener>{
	private ListenerSet<IAnimationListener> listeners;
	public ObservableAnimation(SpriteSheet sheet, int i) {
		super(sheet, i);
		this.listeners = new ListenerSet<IAnimationListener>();
	}
	public void addListener(IAnimationListener listener){
		listeners.add(listener);
	}
	public void removeListener(IAnimationListener listener){
		listeners.remove(listener);
	}
	public void clearListeners(){
		listeners.clear();
	}
	@Override
	public ListenerSet<IAnimationListener> getListeners() {
		return listeners;
	}
	@Override
	public Image getCurrentFrame(){
		Image image = super.getCurrentFrame();
		if(getFrame()==getFrameCount()-1){
			for(IAnimationListener listener : listeners){
				listener.ended();
			}
		}
		return image;
	}
}
