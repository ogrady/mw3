package listener;

/**
 * This is the base interface for every sort of listeners.<br>
 * When creating a new listener-listenable relation you should create a new
 * I...Listener-interface that extends this interface.<br>
 * That interface should hold methods that are called be the listenable and have
 * the form: onXyz() (e.g.: onSpawn, onDie, onDamage).<br>
 * 
 * @author Daniel
 * 
 */
public interface IListener {

}
