import java.io.File;
import java.util.Scanner;
//feel free to import whatever you need
public class Shogi {
	private Square [] squares = new Square[81+14+2]; //81 board spaces, 14 counters for captured pieces, and 2 king locations
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
	public int numberOfMoves; //counter for the amount of moves made
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
		
		numberOfMoves = 0;
		//Create Squares
		for(int i = 0; i < 97; i++)
			squares[i] = new Square();
		//file containing the board layout
		File boardFile;
		//Scanner to read the lines from boardFile
		Scanner boardReader;
		//Initialize string array
		table = new String[30];

		//change the squares for pieces needed
		squares[0].piece = 1; // white l
		squares[0].wo.add(new Data((byte) 0));
		squares[9].wi.add(new Data((byte) 0));
		squares[18].wi.add(new Data((byte) 1, true));
		squares[1].piece = 3; // white n
		squares[18].wi.add(new Data((byte) 16, true));
		squares[20].wi.add(new Data((byte) 15, true));
		squares[2].piece = 5; // white s
		squares[2].wo.add(new Data((byte) 19));
		squares[2].wo.add(new Data((byte) 20));
		squares[10].wi.add(new Data((byte) 23, true));
		squares[11].wi.add(new Data((byte) 19));
		squares[12].wi.add(new Data((byte) 20));
		squares[3].piece = 7; // white g
		squares[3].wo.add(new Data((byte) 29));
		squares[3].wo.add(new Data((byte) 30));
		squares[3].wo.add(new Data((byte) 34));
		squares[11].wi.add(new Data((byte) 34));
		squares[12].wi.add(new Data((byte) 29));
		squares[13].wi.add(new Data((byte) 30));
		squares[2].wi.add(new Data((byte) 33, true));
		squares[4].wi.add(new Data((byte) 31, true));
		squares[4].piece = 8; // white k
		squares[4].wo.add(new Data((byte) 35));
		squares[4].wo.add(new Data((byte) 36));
		squares[4].wo.add(new Data((byte) 42));
		squares[12].wi.add(new Data((byte) 42));
		squares[13].wi.add(new Data((byte) 35));
		squares[14].wi.add(new Data((byte) 36));
		squares[3].wi.add(new Data((byte) 41, true));
		squares[5].wi.add(new Data((byte) 37, true));
		squares[5].piece = 7; // white g
		squares[5].wo.add(new Data((byte) 29));
		squares[5].wo.add(new Data((byte) 30));
		squares[5].wo.add(new Data((byte) 34));
		squares[13].wi.add(new Data((byte) 34));
		squares[14].wi.add(new Data((byte) 29));
		squares[15].wi.add(new Data((byte) 30));
		squares[4].wi.add(new Data((byte) 33, true));
		squares[6].wi.add(new Data((byte) 31, true));
		squares[6].piece = 5; // white s
		squares[6].wo.add(new Data((byte) 19));
		squares[6].wo.add(new Data((byte) 23));
		squares[16].wi.add(new Data((byte) 20, true));
		squares[15].wi.add(new Data((byte) 19));
		squares[14].wi.add(new Data((byte) 23));
		squares[7].piece = 3; // white n
		squares[24].wi.add(new Data((byte) 16, true));
		squares[26].wi.add(new Data((byte) 15, true));
		squares[8].piece = 1; // white l
		squares[8].wo.add(new Data((byte) 0));
		squares[17].wi.add(new Data((byte) 0));
		squares[26].wi.add(new Data((byte) 1, true));
		squares[10].piece = 11; // white b
		squares[0].wi.add(new Data((byte) 123, true));
		squares[2].wi.add(new Data((byte) 115, true));
		squares[18].wi.add(new Data((byte) 131, true));
		squares[20].wi.add(new Data((byte) 107, true));
		squares[16].piece = 9; // white r
		squares[16].wo.add(new Data((byte) 71));
		squares[16].wo.add(new Data((byte) 70));
		squares[16].wo.add(new Data((byte) 69));
		squares[16].wo.add(new Data((byte) 68));
		squares[16].wo.add(new Data((byte) 67));
		squares[16].wo.add(new Data((byte) 51));
		squares[11].wi.add(new Data((byte) 71));
		squares[12].wi.add(new Data((byte) 70));
		squares[13].wi.add(new Data((byte) 69));
		squares[14].wi.add(new Data((byte) 68));
		squares[15].wi.add(new Data((byte) 67));
		squares[17].wi.add(new Data((byte) 51));
		squares[25].wi.add(new Data((byte) 43, true));
		squares[10].wi.add(new Data((byte) 72, true));
		squares[7].wi.add(new Data((byte) 59, true));

		//white pawns
		for(int i = 18; i < 18 + 9; i++) {
			squares[i].piece = 13;
			squares[i].wo.add(new Data((byte) 171));
			squares[i + 9].wi.add(new Data((byte) 171));
		}

		squares[72].piece = 15; //black l
		squares[72].bo.add(new Data((byte) 0));
		squares[63].bi.add(new Data((byte) 0));
		squares[54].bi.add(new Data((byte) 1, true));
		squares[73].piece = 17; //black n
		squares[65].bi.add(new Data((byte) 16, true));
		squares[63].bi.add(new Data((byte) 15, true));
		squares[74].piece = 19; //black s
		squares[74].bo.add(new Data((byte) 19));
		squares[74].bo.add(new Data((byte) 23));
		squares[64].bi.add(new Data((byte) 20, true));
		squares[65].bi.add(new Data((byte) 19));
		squares[66].bi.add(new Data((byte) 23));
		squares[75].piece = 21; //black g
		squares[75].bo.add(new Data((byte) 29));
		squares[75].bo.add(new Data((byte) 30));
		squares[75].bo.add(new Data((byte) 34));
		squares[67].bi.add(new Data((byte) 34));
		squares[66].bi.add(new Data((byte) 29));
		squares[65].bi.add(new Data((byte) 30));
		squares[76].bi.add(new Data((byte) 33, true));
		squares[74].bi.add(new Data((byte) 31, true));
		squares[76].piece = 22; //black k
		squares[76].bo.add(new Data((byte) 35));
		squares[76].bo.add(new Data((byte) 36));
		squares[76].bo.add(new Data((byte) 42));
		squares[68].bi.add(new Data((byte) 42));
		squares[67].bi.add(new Data((byte) 35));
		squares[66].bi.add(new Data((byte) 36));
		squares[77].bi.add(new Data((byte) 41, true));
		squares[75].bi.add(new Data((byte) 37, true));
		squares[77].piece = 21; //black g
		squares[77].bo.add(new Data((byte) 29));
		squares[77].bo.add(new Data((byte) 30));
		squares[77].bo.add(new Data((byte) 34));
		squares[69].bi.add(new Data((byte) 34));
		squares[68].bi.add(new Data((byte) 29));
		squares[67].bi.add(new Data((byte) 30));
		squares[78].bi.add(new Data((byte) 33, true));
		squares[76].bi.add(new Data((byte) 31, true));
		squares[78].piece = 19; //black s
		squares[78].bo.add(new Data((byte) 19));
		squares[78].bo.add(new Data((byte) 20));
		squares[70].bi.add(new Data((byte) 23, true));
		squares[69].bi.add(new Data((byte) 19));
		squares[68].bi.add(new Data((byte) 20));
		squares[79].piece = 17; //black n
		squares[71].bi.add(new Data((byte) 16, true));
		squares[69].bi.add(new Data((byte) 15, true));
		squares[80].piece = 15; //black l
		squares[80].bo.add(new Data((byte) 0));
		squares[71].bi.add(new Data((byte) 0));
		squares[62].bi.add(new Data((byte) 1, true));
		squares[64].piece = 23; //black r
		squares[64].bo.add(new Data((byte) 71));
		squares[64].bo.add(new Data((byte) 70));
		squares[64].bo.add(new Data((byte) 69));
		squares[64].bo.add(new Data((byte) 68));
		squares[64].bo.add(new Data((byte) 67));
		squares[64].bo.add(new Data((byte) 51));
		squares[69].bi.add(new Data((byte) 71));
		squares[68].bi.add(new Data((byte) 70));
		squares[67].bi.add(new Data((byte) 69));
		squares[66].bi.add(new Data((byte) 68));
		squares[65].bi.add(new Data((byte) 67));
		squares[63].bi.add(new Data((byte) 51));
		squares[55].bi.add(new Data((byte) 43, true));
		squares[70].bi.add(new Data((byte) 72, true));
		squares[73].bi.add(new Data((byte) 59, true));
		squares[70].piece = 25; //black b
		squares[80].bi.add(new Data((byte) 123, true));
		squares[78].bi.add(new Data((byte) 115, true));
		squares[62].bi.add(new Data((byte) 131, true));
		squares[60].bi.add(new Data((byte) 107, true));

		//black pawns
		for(int i = 54; i < 54 + 9; i++) {
			squares[i].piece = 27;
			squares[i].bo.add(new Data((byte) 171));
			squares[i - 9].bi.add(new Data((byte) 171));
		}
		
		squares[95].piece = 4; //square of white king
		squares[96].piece = 76; //square of black king
		//Initialize layout array
		try{
			boardFile = new File("board.txt");
			boardReader = new Scanner(boardFile);
			for(int i = 0; i < 30; i++){
				//Step through lines of char array and assign them to layout array
				String currentLine = boardReader.nextLine();
				for(int j = 0; j < 77; j++){
					//i * 77 + j is the number of rows time 77 characters in each column + number of chars in current line
					layout[i *77 + j] = currentLine.charAt(j);
				}
			}
			boardReader.close();
		}
		catch(Exception e){
			System.out.println("boardfile.txt not found");
		}
	}
	public void drawBoard() {
		//since makeMove shouldn't update the layout, you need to combine information from the layout and squares arrays
		char [] pieces = {108, 76, 110, 78, 115, 71, 103, 107, 114, 82, 98, 66, 112, 84};
		char[] board = new char[30*77];
		for (int i = 0; i < 30; i++)
			for (int j = 0; j < 77; j++)
				board[77 * i + j] = layout[77 * i + j];
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++){
				if (squares[9 * i + j].piece < 15 && squares[9 * i + j].piece != 0){
					board[77 * (2 + 3 * i) + 14 + 6 * j] = pieces[(squares[9 * i + 8 - j].piece - 1) % 14];
					board[77 * (3 + 3 * i) + 14 + 6 * j] = 118;
				}
				else if (squares[9 * i + j].piece != 0){
					board[77 * (3 + 3 * i) + 14 + 6 * j] = pieces[(squares[9 * i + 8 - j].piece - 1) % 14];
					board[77 * (2 + 3 * i) + 14 + 6 * j] = 94;
				}
			}
		for (int i = 1; i < 8; i++){
			if (squares[i + 80].piece > 0){
				board[77 * 2 + i] = pieces[2 * (i - 1)];
				board[77 * 3 + i] = (char) (48 + squares[i + 80].piece);
			}
			if (squares[i + 87].piece > 0){
				board[77 * 26 + 68 + i] = pieces[2 * (i - 1)];
				board[77 * 27 + 68 + i] = (char) (48 + squares[i + 87].piece);
			}
		}
		for (int i = 0; i < 30; i++){
			for (int j = 0; j < 77; j++)
				System.out.print(board[77 * i + j]);
			System.out.println();
		}
	}
	public short convertMove(String move) {
		//convertMove is only called when a valid String is inputed
		//current plan is to use the Kitao-Kawasaki notation since it appears to be the easiest to translate into a programming language
		//we will use the western system
		//for example: move = "S72x83+"
		//this means the player with the current turn moves their silver general from column 7, row 2 to capture on column 8, row 3
		//lastly, the player chose to promote it
		return 0;
	}
	public boolean makeMove(short move) {
		//move = square index * square move
		//if the move is legal, then update the squares array and return true
		//perpetual check is illegal but you don't need to check for it since it will be handled in the gameOver() method
		return true;
	}
	public void updateTable(String move) {
		//this is separate from makeMove because makeMove needs to be fast for AI
		//the main method will provide the arguments
		
		//should check if table is big enough, if not, then call resizeTable
		if(numberOfMoves > table.length)
			resizeTable(table);
		else
			table[numberOfMoves] = move;
		
	}
	public void resizeTable(String[] table)
	{
		//this method resizes the table array if the amount of moves exceeds the predefined table size
		//create a temp array with 25+ moves for each player
		String[] temp = new String[table.length + 50];
		//copy all contents of table to the temp array
		for(int i = 0; i < table.length; i++)
			temp[i] = table[i];
		//once all contents have been copied, make temp the new table array
		table = temp;
		
	}
	public void drawTable(String time1, String time2) {
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
		int blackMove = 0, whiteMove = 1;
		System.out.println(" ________________________");
		System.out.println("|\t" + time1 + " " + time2 + "|");
		System.out.println("|________________________|");
		for(int i = 0; i < numberOfMoves/2; i++)
		{
			System.out.println("|" + (i+1)+ ".\t" + table[blackMove] + "\t " + table[whiteMove] + "\t |");
			blackMove += 2;
			whiteMove += 2;
		}
		System.out.println("|________________________|");
	}
	public boolean gameOver() {
		//returns true when the game is over
		return false;
	}
}