package videotheque;


public interface Réduction {
	public default float reduc() {
		return 0;		
	}
	
	public default void ObtenirReduc() {
		System.out.println("");
	}
}
