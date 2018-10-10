package parball.arsserver.model.observer;

public interface ISubject {

	void registerObserver(IObserver o);

	void removeObserver(IObserver o);

}
