package environment.character;

public class EmptyCharacterAction extends CharacterAction {

	public EmptyCharacterAction() {
		// null is okay, as we override perform(), where the actor would be
		// queried
		super(null, 0);
	}

	@Override
	public boolean perform() {
		return false;
	}

	@Override
	protected void execute() {
	}

}
