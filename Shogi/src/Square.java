import java.util.LinkedList;
public class Square {
	byte piece;
	LinkedList<Data> bo, bi, wo, wi;
	public Square() {
		piece = 0;
		bo = new LinkedList<Data>(); //black outgoing moves
		bi = new LinkedList<Data>(); //black incoming moves
		wo = new LinkedList<Data>(); //white outgoing moves
		wi = new LinkedList<Data>(); //white incoming moves
	}
}