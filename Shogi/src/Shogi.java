//feel free to import whatever you need
public class Shogi {
	private byte [] position = new byte[81+14]; //81 board spaces and 14 counters for captured pieces
	// 0 = empty space
	// 1 = white l
	// 2 = white L
	// 3 = white n
	// 4 = white N
	// 5 = white s
	// 6 = white G
	// 7 = white g
	// 8 = white k
	// 9 = white r
	//10 = white R
	//11 = white b
	//12 = white B
	//13 = white p
	//14 = white T
	//15 = black l
	//16 = black L
	//17 = black n
	//18 = black N
	//19 = black s
	//20 = black G
	//21 = black g
	//22 = black k
	//23 = black r
	//24 = black R
	//25 = black b
	//26 = black B
	//27 = black p
	//28 = black T
	private char [] layout = new char[30*77]; //dimensions of the ASCII layout (you could change this to 2d array if you prefer)
	private String [] table; //dimensions will change as moves are made and width should remain at 22
	private int moves; //counter for the amount of moves made
	//feel free to add more variables if I missed any
	public Shogi() {
		//default constructor will initialize the settings for a new game
		//one way to do this in ASCII is to follow this layout:
		//              9     8     7     6     5     4     3     2     1
		// _______    _____________________________________________________
		//|       |  |  l  |  n  |  s  |  g  |  k  |  g  |  s  |  n  |  l  |
		//|       | 1|  v  |  v  |  v  |  v  |  v  |  v  |  v  |  v  |  v  |1
		//|_______|  |_____|_____|_____|_____|_____|_____|_____|_____|_____|
		//           |     |  r  |     |     |     |     |     |  b  |     |
		//          2|     |  v  |     |     |     |     |     |  v  |     |2
		//           |_____|_____|_____|_____|_____|_____|_____|_____|_____|
		//           |  p  |  p  |  p  |  p  |  p  |  p  |  p  |  p  |  p  |
		//          3|  v  |  v  |  v  |  v  |  v  |  v  |  v  |  v  |  v  |3
		//           |_____|_____|_____|_____|_____|_____|_____|_____|_____|
		//           |     |     |     |     |     |     |     |     |     |
		//          4|     |     |     |     |     |     |     |     |     |4
		//           |_____|_____|_____|_____|_____|_____|_____|_____|_____|
		//           |     |     |     |     |     |     |     |     |     |
		//          5|     |     |     |     |     |     |     |     |     |5
		//           |_____|_____|_____|_____|_____|_____|_____|_____|_____|
		//           |     |     |     |     |     |     |     |     |     |
		//          6|     |     |     |     |     |     |     |     |     |6
		//           |_____|_____|_____|_____|_____|_____|_____|_____|_____|
		//           |  ^  |  ^  |  ^  |  ^  |  ^  |  ^  |  ^  |  ^  |  ^  |
		//          7|  p  |  p  |  p  |  p  |  p  |  p  |  p  |  p  |  p  |7
		//           |_____|_____|_____|_____|_____|_____|_____|_____|_____|
		//           |     |  ^  |     |     |     |     |     |  ^  |     |
		//          8|     |  b  |     |     |     |     |     |  r  |     |8
		//           |_____|_____|_____|_____|_____|_____|_____|_____|_____|   _______
		//           |  ^  |  ^  |  ^  |  ^  |  ^  |  ^  |  ^  |  ^  |  ^  |  |       |
		//          9|  l  |  n  |  s  |  g  |  k  |  g  |  s  |  n  |  l  |9 |       |
		//           |_____|_____|_____|_____|_____|_____|_____|_____|_____|  |_______|
		//              9     8     7     6     5     4     3     2     1
		//the box to hold captured pieces can look like this later in the game:
		// _______ 
		//|lnsgrbp|
		//|1111111|
		//|_______|
		//feel free to improve the design if you want
		//when you promote a piece, capitalize the letter unless it is 's' or 'p' because they look similar to 'S' and 'P'
		//let 's' promote to 'G' and 'p' promote to 'T' because of the Japanese names and calligraphy
		//a promoted silver general is a 'G'eneral and behaves like 'g' was promoted to 'G'
		//a promoted pawn is called 'T'okin and one of the alternate forms in calligraphy looks like a 'T'

		//initialize array to 0;
		for(int i = 0; i < 97; i++){
			position[i] = 0;
		}

		//change the positions for pieces needed
		position[0] = 1; // white l
		position[1] = 3; // white n
		position[2] = 5; // white s
		position[3] = 7; // white g
		position[4] = 8; // white k 
		position[5] = 7; // white g
		position[6] = 5; // white s
		position[7] = 3; // white n
		position[8] = 1; // white l
		position[10] = 11; // white b
		position[16] = 9; // white r

		//white pawns
		for(int i = 54; i < 9; i++)
			position[i] = 13;

		position[72] = 15; //black l
		position[73] = 17; //black n
		position[74] = 19; //black s
		position[75] = 21; //black g
		position[76] = 22; //black k
		position[77] = 21; //black g
		position[78] = 19; //black s
		position[79] = 17; //black n
		position[80] = 15; //black l
		position[64] = 23; //black r
		position[70] = 25; //black b

		//black pawns
		for(int i = 18; i < 9; i++)
			position[i] = 27;
		

		
	}
	public void drawBoard() {
		//since makeMove shouldn't update the layout, you need to combine information from the layout and position arrays
	}
	public boolean makeMove(String move) {
		//current plan is to use the notation since it appears to be the easiest to translate into a programming language
		//we will use the western system
		//for example: move = "S72x83+"
		//this means the player with the current turn moves their silver general from column 7, row 2 to capture on column 8, row 3
		//lastly, the player chose to promote it
		//if the move is legal, then update the position array and return true
		//perpetual check is illegal but you don't need to check for it since it will be handled in the gameOver() method
		return true;
	}
	public void updateTable(String move, int time1, int time2) {
		//this is separate from makeMove because makeMove needs to be fast for AI
		//the main method will provide the arguments
	}
	public void drawTable() {
		//in the main method, let the player be able to call drawTable() through a command
		//the table can keep track of time and moves
		//here is an example:
		// ____________________
		//|     1:23:45 1:23:45|
		//|____________________|
		//|1.   P27-26  P83-84 |
		//|2.   P26-25  P84-85 |
		//|3.   G69-78  G41-32 |
		//|4.   P25-24  P23x24 |
		//|5.   R28x24  P*23   |
		//|____________________|
		//feel free to improve the design if you want
	}
	public boolean gameOver() {
		//returns true when the game is over
		return false;
	}
}