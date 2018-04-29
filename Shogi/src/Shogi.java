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
	private byte [] trans = {-9, -18, -27, -36, -45, -54, -63, -72, -9, -18, -27, -36, -45, -54, -63, -10, -8, -10, -8, -9, -10, 8, 10, -8, 
			                 -9, -10, 8, 10, -8, -9, -10, -1, 9, 1, -8, -9, -10, -1, 8, 9, 10, 1, -8, -9, -18, -27, -36, -45, -54, -63, 
			                 -72, -1, -2, -3, -4, -5, -6, -7, -8, 9, 18, 27, 36, 45, 54, 63, 72, 1, 2, 3, 4, 5, 6, 7, 8, -9, -18, -27, -36, 
			                 -45, -54, -63, -72, -1, -2, -3, -4, -5, -6, -7, -8, 9, 18, 27, 36, 45, 54, 63, 72, 1, 2, 3, 4, 5, 6, 7, 8, 
			                 -10, -20, -30, -40, -50, -60, -70, -80, 8, 16, 24, 32, 40, 48, 56, 64, 10, 20, 30, 40, 50, 60, 70, 80, -8, 
			                 -16, -24, -32, -40, -48, -56, -64, -10, -20, -30, -40, -50, -60, -70, -80, 8, 16, 24, 32, 40, 48, 56, 64, 10, 
			                 20, 30, 40, 50, 60, 70, 80, -8, -16, -24, -32, -40, -48, -56, -64, -9, -9, -9, -10, -1, 9, 1, -8, -9, -10, -1, 
			                 9, 1, -8, -9, -10, -1, 9, 1, -8, -9, -18, -27, -36, -45, -54, -63, -72, -10, -1, -2, -3, -4, -5, -6, -7, -8, 
			                 8, 9, 18, 27, 36, 45, 54, 63, 72, 10, 1, 2, 3, 4, 5, 6, 7, 8, -8, -9, -10, -20, -30, -40, -50, -60, -70, -80, 
			                 -1, 8, 16, 24, 32, 40, 48, 56, 64, 9, 10, 20, 30, 40, 50, 60, 70, 80, 1, -8, -16, -24, -32, -40, -48, -56, 
			                 -64, -9, -10, -1, 9, 1, -8};
	//this array is used to locate a move's destination square
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
		for (int i = 0; i < 97; i++)
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
		for (int i = 54; i < 54 + 9; i++) {
			squares[i].piece = 27;
			squares[i].bo.add(new Data((byte) 171));
			squares[i - 9].bi.add(new Data((byte) 171));
		}		
		squares[95].piece = 4; //square of white king
		squares[96].piece = 76; //square of black king
		//Initialize layout array
		try {
			boardFile = new File("board.txt");
			boardReader = new Scanner(boardFile);
			for(int i = 0; i < 30; i++) {
				//Step through lines of char array and assign them to layout array
				String currentLine = boardReader.nextLine();
				for(int j = 0; j < 77; j++)
					//i * 77 + j is the number of rows time 77 characters in each column + number of chars in current line
					layout[i *77 + j] = currentLine.charAt(j);
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
			for (int j = 0; j < 9; j++) {
				if (squares[9 * i + 8 - j].piece < 15 && squares[9 * i + 8 - j].piece != 0) {
					board[77 * (2 + 3 * i) + 14 + 6 * j] = pieces[(squares[9 * i + 8 - j].piece - 1) % 14];
					board[77 * (3 + 3 * i) + 14 + 6 * j] = 118;
				}
				else if (squares[9 * i + 8 - j].piece != 0) {
					board[77 * (3 + 3 * i) + 14 + 6 * j] = pieces[(squares[9 * i + 8 - j].piece - 1) % 14];
					board[77 * (2 + 3 * i) + 14 + 6 * j] = 94;
				}
			}
		for (int i = 1; i < 8; i++) {
			if (squares[i + 80].piece > 0) {
				board[77 * 2 + i] = pieces[2 * (i - 1)];
				board[77 * 3 + i] = (char) (48 + squares[i + 80].piece);
			}
			if (squares[i + 87].piece > 0) {
				board[77 * 26 + 68 + i] = pieces[2 * (i - 1)];
				board[77 * 27 + 68 + i] = (char) (48 + squares[i + 87].piece);
			}
		}
		for (int i = 0; i < 30; i++) {
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
		//move = square index * 276 + square move
		//if the move is legal, then update the squares array and return true
		//perpetual check is illegal but you don't need to check for it since it will be handled in the gameOver() method
		//1,  3,  5,  7,  8,  9,  11,  13,   2,   4,   6,  10,  12,  14,           1,3,5,7,9,11,13
		//l,  n,  s,  g,  k,  r,   b,   p,   L,   N,   G,   R,   B,   T, drops for l,n,s,g,r,b,p
		//0, 15, 19, 29, 35, 43, 107, 171, 173, 179, 185, 191, 227, 263, 269
		byte index = (byte) (move / 276);
		if (numberOfMoves % 2 == 0) {
			byte m = (byte) (move % 276);	
			if (m < 269) {
				if (squares[index].bo.isEmpty() || squares[index].piece < 15)
					return false;		
				int k = index + trans[m];
				boolean b = squares[index].bo.removeIf((Data item)->item.move == m && (item.blocked == false || (squares[k].piece < 15 && squares[k].piece != 0)));
				if (!b)
					return false;
				byte temp = squares[index].piece;
				squares[index].piece = 0;
				//remove the incoming moves on affected squares
				for (int i = 0; i < squares[index].bo.size(); i++) {
					Data x = squares[index].bo.get(i);
					int l = index + trans[x.move];
					for (int j = 0; j < squares[l].bi.size(); j++)
						if (squares[l].bi.get(j).move == x.move) {
							squares[l].bi.remove(j);
							break;
						}
				}
				for (int j = 0; j < squares[k].bi.size(); j++)
					if (squares[k].bi.get(j).move == m) {
						squares[k].bi.remove(j);
						break;
					}
				squares[index].bo.clear();
				//checking if there was a capture
				byte temp2 = 0;
				if (squares[k].piece != 0) {
					temp2 = squares[k].piece;
					b = false;
					for (int i = 0; i < squares[k].wo.size(); i++) {
						Data x = squares[k].wo.get(i);
						int l = k - trans[x.move];
						for (int j = 0; j < squares[l].wi.size(); j++)
							if (squares[l].wi.get(j).move == x.move) {
								squares[l].wi.remove(j);
								break;
							}
					}
					squares[k].wo.clear();
				}
				//making the move
				if ((m > 6 && m < 15) || m == 17 || m == 18 || (m > 23 && m < 29) || (m > 74 && m < 107) || (m > 138 && m < 171) || m == 172)
					squares[k].piece = (byte) (temp + 1);
				else {
					squares[k].piece = temp;
					if (m > 34 && m < 43)
						squares[96].piece = (byte) k;
				}
				//blocking the destination square
				for (int i = 0; i < squares[k].bi.size(); i++)
					squares[k].bi.get(i).blocked = true;
				for (int i = 0; i < squares[k].wi.size(); i++)
					squares[k].wi.get(i).blocked = true;
				//moves no longer blocked on original square
				for (int i = 0; i < squares[index].bi.size(); i++)
					squares[index].bi.get(i).blocked = false;
				for (int i = 0; i < squares[index].wi.size(); i++)
					squares[index].wi.get(i).blocked = false;
				//incoming captured piece
				if (!b) {
					boolean c = true;
					if (squares[81 + (temp2 - 1) / 2].piece++ == 0) {
						if (temp2 > 12)
							for (int i = 9; i < 81; i++) {
								if (squares[i - 9].piece == 8) {
									for (int j = 0; j < squares[i].wi.size(); j++)
										if (squares[i].wi.get(j).move < 269) {
											c = false;
											break;
										}
									if (!c || squares[i - 9].wo.size() != 0) {
										if (squares[i].piece != 0)
											squares[i].bi.add(new Data((byte) 275, true));
										else squares[i].bi.add(new Data((byte) 275));
									}
								}
								else if (squares[i].piece != 0)
									squares[i].bi.add(new Data((byte) 275, true));
								else squares[i].bi.add(new Data((byte) 275));
							}
						else if (temp2 == 3 || temp2 == 4)
							for (int i = 18; i < 81; i++) {
								if (squares[i].piece != 0)
									squares[i].bi.add(new Data((byte) 270, true));
								else squares[i].bi.add(new Data((byte) 270));
							}
						else if (temp2 == 1 || temp2 == 2)
							for (int i = 9; i < 81; i++) {
								if (squares[i].piece != 0)
									squares[i].bi.add(new Data((byte) 269, true));
								else squares[i].bi.add(new Data((byte) 269));
							}
						else {
							int n = 269 + (temp2 - 1) / 2;
							for (int i = 0; i < 81; i++) {
								if (squares[i].piece != 0)
									squares[i].bi.add(new Data((byte) n, true));
								else squares[i].bi.add(new Data((byte) n));
							}
						}
					}
				}
			}
			else {
				if (squares[index].bi.isEmpty())
					return false;
				boolean c = squares[index].bi.removeIf((Data item)->item.move == m && item.blocked == false);
				if (!c)
					return false;
				if (--squares[m - 188].piece == 0)
					for (int i = 0; i < 81; i++)
						for (int j = 0; j < squares[i].bi.size(); j++)
							if (squares[i].bi.get(j).move == m) {
								squares[i].bi.remove(j);
								break;
							}
			}
			//adding all legal moves
			int k = index;
			if (m < 269)
				k += trans[m];
			if (m < 7) {
				for (int i = 0; i < 7; i++) {
					int l = k - 9 * i - 9;
					if (l < 0)
						break;
					else if (l < 9) {
						if (squares[l].piece != 0) {
							squares[l].bi.add(new Data((byte) (i + 8), true));
							if (squares[l].piece < 15)
								squares[k].bo.add(new Data((byte) (i + 8), true));
						}	
						else {
							squares[l].bi.add(new Data((byte) (i + 8)));
							squares[k].bo.add(new Data((byte) (i + 8)));
						}
						break;
					}
					else if (l < 27) {
						if (squares[l].piece != 0) {
							squares[l].bi.add(new Data((byte) i, true));
							squares[l].bi.add(new Data((byte) (i + 8), true));
							if (squares[l].piece < 15) {
								squares[k].bo.add(new Data((byte) i, true));
								squares[k].bo.add(new Data((byte) (i + 8), true));
							}
							break;
						}
						else {
							squares[l].bi.add(new Data((byte) i));
							squares[l].bi.add(new Data((byte) (i + 8)));
							squares[k].bo.add(new Data((byte) i));
							squares[k].bo.add(new Data((byte) (i + 8)));
						}
					}
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
					}
				}
			}
			else if (m < 15 || (m > 172 && m < 179)) {
				for (int i = 173; i < 179; i++) {
					int l = k + trans[i];
					if (l < 0 || l > 80 || ((i == 174 || i == 175) && l % 9 == 0) || ((i == 177 || i == 178) && l % 9 == 8)) {}
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
					}
				}
			}
			else if (m < 17) {
				for (int i = 15; i < 17; i++) {
					int l = k + trans[i];
					if (l < 0 || l > 80 || (i == 15 && l % 9 == 0) || (i == 16 && l % 9 == 8)) {}
					else if (l < 18) {
						if (squares[l].piece != 0) {
							squares[l].bi.add(new Data((byte) (i + 2), true));
							if (squares[l].piece < 15)
								squares[k].bo.add(new Data((byte) (i + 2), true));
						}	
						else {
							squares[l].bi.add(new Data((byte) (i + 2)));
							squares[k].bo.add(new Data((byte) (i + 2)));
						}
					}
					else if (l < 27) {
						if (squares[l].piece != 0) {
							squares[l].bi.add(new Data((byte) i, true));
							squares[l].bi.add(new Data((byte) (i + 2), true));
							if (squares[l].piece < 15) {
								squares[k].bo.add(new Data((byte) i, true));
								squares[k].bo.add(new Data((byte) (i + 2), true));
							}
						}
						else {
							squares[l].bi.add(new Data((byte) i));
							squares[l].bi.add(new Data((byte) (i + 2)));
							squares[k].bo.add(new Data((byte) i));
							squares[k].bo.add(new Data((byte) (i + 2)));
						}
					}
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
					}
				}
			}
			else if (m < 19 || (m > 178 && m < 185)) {
				for (int i = 179; i < 185; i++) {
					int l = k + trans[i];
					if (l < 0 || l > 80 || ((i == 180 || i == 181) && l % 9 == 0) || ((i == 183 || i == 184) && l % 9 == 8)) {}
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
					}
				}
			}
			else if (m < 24) {
				for (int i = 19; i < 24; i++) {
					int l = k + trans[i];
					if (l < 0 || l > 80 || ((i == 20 || i == 21) && l % 9 == 0) || ((i == 22 || i == 23) && l % 9 == 8)) {}
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (k < 27 || l < 27) {
							squares[l].bi.add(new Data((byte) (i + 5), true));
							if (squares[l].piece < 15) {
								squares[k].bo.add(new Data((byte) i, true));
								squares[k].bo.add(new Data((byte) (i + 5), true));
							}		
						}
						else if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
						if (k < 27 || l < 27) {
							squares[l].bi.add(new Data((byte) (i + 5)));
							squares[k].bo.add(new Data((byte) (i + 5)));
						}
					}
				}
			}
			else if (m < 29 || (m > 184 && m < 191)) {
				for (int i = 185; i < 191; i++) {
					int l = k + trans[i];
					if (l < 0 || l > 80 || ((i == 186 || i == 187) && l % 9 == 0) || ((i == 189 || i == 190) && l % 9 == 8)) {}
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
					}
				}
			}
			else if (m < 35) {
				for (int i = 29; i < 35; i++) {
					int l = k + trans[i];
					if (l < 0 || l > 80 || ((i == 30 || i == 31) && l % 9 == 0) || ((i == 33 || i == 34) && l % 9 == 8)) {}
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
					}
				}
			}
			else if (m < 43) {
				for (int i = 35; i < 43; i++) {
					int l = k + trans[i];
					boolean c = false;
					for (int j = 0; j < squares[l].wi.size(); j++)
						if (squares[l].wi.get(j).move < 269)
							c = true;
					if (l < 0 || l > 80 || c || ((i > 35 && i < 39) && l % 9 == 0) || ((i > 39 && i < 43) && l % 9 == 8)) {}
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
					}
				}
			}
			else if (m < 75) {
				for (int i = 43; i < 51; i++) {
					int l = k - 9 * (i - 42);
					if (l < 0)
						break;
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (k < 27 || l < 27) {
							squares[l].bi.add(new Data((byte) (i + 32), true));
							if (squares[l].piece < 15) {
								squares[k].bo.add(new Data((byte) (i + 32), true));
								squares[k].bo.add(new Data((byte) i, true));
							}
						}
						else if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
						if (k < 27 || l < 27) {
							squares[l].bi.add(new Data((byte) (i + 32)));
							squares[k].bo.add(new Data((byte) (i + 32)));
						}
					}
				}
				for (int i = 51; i < 59; i++) {
					int l = k - i + 50;
					if (l < 0 || l / 9 != k / 9)
						break;
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (k < 27) {
							squares[l].bi.add(new Data((byte) (i + 32), true));
							if (squares[l].piece < 15) {
								squares[k].bo.add(new Data((byte) (i + 32), true));
								squares[k].bo.add(new Data((byte) i, true));
							}
						}
						else if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
						if (k < 27) {
							squares[l].bi.add(new Data((byte) (i + 32)));
							squares[k].bo.add(new Data((byte) (i + 32)));
						}
					}
				}
				for (int i = 59; i < 67; i++) {
					int l = k + 9 * (i - 58);
					if (l > 80)
						break;
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (l < 27 || k < 27) {
							squares[l].bi.add(new Data((byte) (i + 32), true));
							if (squares[l].piece < 15) {
								squares[k].bo.add(new Data((byte) (i + 32), true));
								squares[k].bo.add(new Data((byte) i, true));
							}
						}
						else if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
						if (l < 27 || k < 27) {
							squares[l].bi.add(new Data((byte) (i + 32)));
							squares[k].bo.add(new Data((byte) (i + 32)));
						}
					}
				}
				for (int i = 67; i < 75; i++) {
					int l = k + i - 66;
					if (l / 9 != k / 9)
						break;
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (k < 27) {
							squares[l].bi.add(new Data((byte) (i + 32), true));
							if (squares[l].piece < 15) {
								squares[k].bo.add(new Data((byte) (i + 32), true));
								squares[k].bo.add(new Data((byte) i, true));
							}
						}
						else if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
						if (k < 27) {
							squares[l].bi.add(new Data((byte) (i + 32)));
							squares[k].bo.add(new Data((byte) (i + 32)));
						}
					}
				}
			}
			else if (m < 107 || (m > 190 && m < 227)) {
				for (int i = 191; i < 199; i++) {
					int l = k - 9 * (i - 190);
					if (l < 0)
						break;
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
					}
				}
				if (k % 9 == 0) {}
				else if (squares[k - 10].piece != 0) {
					squares[k - 10].bi.add(new Data((byte) 199, true));
					if (squares[k - 10].piece < 15)
						squares[k].bo.add(new Data((byte) 199, true));
				}
				else {
					squares[k - 10].bi.add(new Data((byte) 199));
					squares[k].bo.add(new Data((byte) 199));
				}
				for (int i = 200; i < 208; i++) {
					int l = k - i + 199;
					if (l < 0 || l / 9 != k / 9)
						break;
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
					}
				}
				if (k % 9 == 0) {}
				else if (squares[k + 8].piece != 0) {
					squares[k + 8].bi.add(new Data((byte) 208, true));
					if (squares[k + 8].piece < 15)
						squares[k].bo.add(new Data((byte) 208, true));
				}
				else {
					squares[k + 8].bi.add(new Data((byte) 208));
					squares[k].bo.add(new Data((byte) 208));
				}
				for (int i = 209; i < 217; i++) {
					int l = k + 9 * (i - 208);
					if (l > 80)
						break;
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
					}
				}
				if (k % 9 == 8) {}
				else if (squares[k + 10].piece != 0) {
					squares[k + 10].bi.add(new Data((byte) 217, true));
					if (squares[k + 10].piece < 15)
						squares[k].bo.add(new Data((byte) 217, true));
				}
				else {
					squares[k + 10].bi.add(new Data((byte) 217));
					squares[k].bo.add(new Data((byte) 217));
				}
				for (int i = 218; i < 226; i++) {
					int l = k + i - 217;
					if (l / 9 != k / 9)
						break;
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
					}
				}
				if (k % 9 == 8) {}
				else if (squares[k - 8].piece != 0) {
					squares[k - 8].bi.add(new Data((byte) 226, true));
					if (squares[k - 8].piece < 15)
						squares[k].bo.add(new Data((byte) 226, true));
				}
				else {
					squares[k - 8].bi.add(new Data((byte) 226));
					squares[k].bo.add(new Data((byte) 226));
				}
			}
			else if (m < 139) {
				for (int i = 107; i < 115; i++) {
					int l = k - 10 * (i - 106);
					if (l < 0 || k % 9 <= i - 107)
						break;
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (k < 27 || l < 27) {
							squares[l].bi.add(new Data((byte) (i + 32), true));
							if (squares[l].piece < 15) {
								squares[k].bo.add(new Data((byte) (i + 32), true));
								squares[k].bo.add(new Data((byte) i, true));
							}
						}
						else if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
						if (k < 27 || l < 27) {
							squares[l].bi.add(new Data((byte) (i + 32)));
							squares[k].bo.add(new Data((byte) (i + 32)));
						}
					}
				}
				for (int i = 115; i < 123; i++) {
					int l = k + 8 * (i - 114);
					if (l > 80 || k % 9 <= i - 115)
						break;
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (k < 27 || l < 27) {
							squares[l].bi.add(new Data((byte) (i + 32), true));
							if (squares[l].piece < 15) {
								squares[k].bo.add(new Data((byte) (i + 32), true));
								squares[k].bo.add(new Data((byte) i, true));
							}
						}
						else if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
						if (k < 27 || l < 27) {
							squares[l].bi.add(new Data((byte) (i + 32)));
							squares[k].bo.add(new Data((byte) (i + 32)));
						}
					}
				}
				for (int i = 123; i < 131; i++) {
					int l = k + 10 * (i - 122);
					if (l > 80 || k % 9 >= 8 - (i - 123))
						break;
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (k < 27 || l < 27) {
							squares[l].bi.add(new Data((byte) (i + 32), true));
							if (squares[l].piece < 15) {
								squares[k].bo.add(new Data((byte) (i + 32), true));
								squares[k].bo.add(new Data((byte) i, true));
							}
						}
						else if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
						if (k < 27 || l < 27) {
							squares[l].bi.add(new Data((byte) (i + 32)));
							squares[k].bo.add(new Data((byte) (i + 32)));
						}
					}
				}
				for (int i = 131; i < 139; i++) {
					int l = k - 8 * (i - 130);
					if (l < 0 || k % 9 >= 8 - (i - 131))
						break;
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (k < 27 || l < 27) {
							squares[l].bi.add(new Data((byte) (i + 32), true));
							if (squares[l].piece < 15) {
								squares[k].bo.add(new Data((byte) (i + 32), true));
								squares[k].bo.add(new Data((byte) i, true));
							}
						}
						else if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
						if (k < 27 || l < 27) {
							squares[l].bi.add(new Data((byte) (i + 32)));
							squares[k].bo.add(new Data((byte) (i + 32)));
						}
					}
				}
			}
			else if (m < 171 || (m > 226 && m < 263)) {
				if (k - 9 < 0) {}
				else if (squares[k - 9].piece != 0) {
					squares[k - 9].bi.add(new Data((byte) 227, true));
					if (squares[k - 9].piece < 15)
						squares[k].bo.add(new Data((byte) 227, true));
				}
				else {
					squares[k - 9].bi.add(new Data((byte) 227));
					squares[k].bo.add(new Data((byte) 227));
				}
				for (int i = 228; i < 236; i++) {
					int l = k - 10 * (i - 227);
					if (l < 0 || k % 9 <= i - 228)
						break;
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
					}
				}
				if (k % 9 == 0) {}
				else if (squares[k - 1].piece != 0) {
					squares[k - 1].bi.add(new Data((byte) 236, true));
					if (squares[k - 1].piece < 15)
						squares[k].bo.add(new Data((byte) 236, true));
				}
				else {
					squares[k - 1].bi.add(new Data((byte) 236));
					squares[k].bo.add(new Data((byte) 236));
				}
				for (int i = 237; i < 245; i++) {
					int l = k + 8 * (i - 236);
					if (l > 80 || k % 9 <= i - 237)
						break;
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
					}
				}
				if (k + 9 > 80) {}
				else if (squares[k + 9].piece != 0) {
					squares[k + 9].bi.add(new Data((byte) 245, true));
					if (squares[k + 9].piece < 15)
						squares[k].bo.add(new Data((byte) 245, true));
				}
				else {
					squares[k + 9].bi.add(new Data((byte) 245));
					squares[k].bo.add(new Data((byte) 245));
				}
				for (int i = 246; i < 254; i++) {
					int l = k + 10 * (i - 245);
					if (l > 80 || k % 9 >= 8 - (i - 246))
						break;
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
					}
				}
				if (k % 9 == 8) {}
				else if (squares[k + 1].piece != 0) {
					squares[k + 1].bi.add(new Data((byte) 254, true));
					if (squares[k + 1].piece < 15)
						squares[k].bo.add(new Data((byte) 254, true));
				}
				else {
					squares[k + 1].bi.add(new Data((byte) 254));
					squares[k].bo.add(new Data((byte) 254));
				}
				for (int i = 255; i < 263; i++) {
					int l = k - 8 * (i - 254);
					if (l < 0 || k % 9 >= 8 - (i - 255))
						break;
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
					}
				}
			}
			else if (m == 171) {
				if (k < 18) {
					if (squares[k - 9].piece != 0) {
						squares[k - 9].bi.add(new Data((byte) 172, true));
						if (squares[k - 9].piece < 15)
							squares[k].bo.add(new Data((byte) 172, true));
					}
					else {
						squares[k - 9].bi.add(new Data((byte) 172));
						squares[k].bo.add(new Data((byte) 172));
					}
				}
				else if (k < 36) {
					if (squares[k - 9].piece != 0) {
						squares[k - 9].bi.add(new Data((byte) 171, true));
						squares[k - 9].bi.add(new Data((byte) 172, true));
						if (squares[k - 9].piece < 15) {
							squares[k].bo.add(new Data((byte) 171, true));
							squares[k].bo.add(new Data((byte) 172, true));
						}
					}
					else {
						squares[k - 9].bi.add(new Data((byte) 171));
						squares[k].bo.add(new Data((byte) 171));
						squares[k - 9].bi.add(new Data((byte) 172));
						squares[k].bo.add(new Data((byte) 172));
					}
				}
				else if (squares[k - 9].piece != 0) {
					squares[k - 9].bi.add(new Data((byte) 171, true));
					if (squares[k - 9].piece < 15)
						squares[k].bo.add(new Data((byte) 171, true));
				}
				else {
					squares[k - 9].bi.add(new Data((byte) 171));
					squares[k].bo.add(new Data((byte) 171));
				}
			}
			else {
				for (int i = 263; i < 269; i++) {
					int l = k + trans[i];
					if (l < 0 || l > 80 || ((i == 264 || i == 265) && l % 9 == 0) || ((i == 267 || i == 268) && l % 9 == 8)) {}
					else if (squares[l].piece != 0) {
						squares[l].bi.add(new Data((byte) i, true));
						if (squares[l].piece < 15)
							squares[k].bo.add(new Data((byte) i, true));
					}
					else {
						squares[l].bi.add(new Data((byte) i));
						squares[k].bo.add(new Data((byte) i));
					}
				}
			}
		}
		else {
			//white's move
			byte m = (byte) (move % 276);	
			if (m < 269) {
				if (squares[index].wo.isEmpty() || squares[index].piece > 14 || squares[index].piece == 0)
					return false;		
				int k = index - trans[m];
				boolean b = squares[index].wo.removeIf((Data item)->item.move == m && (item.blocked == false || squares[k].piece > 14));
				if (!b)
					return false;
				byte temp = squares[index].piece;
				squares[index].piece = 0;
				//remove the incoming moves on affected squares
				for (int i = 0; i < squares[index].wo.size(); i++) {
					Data x = squares[index].wo.get(i);
					int l = index - trans[x.move];
					for (int j = 0; j < squares[l].wi.size(); j++)
						if (squares[l].wi.get(j).move == x.move) {
							squares[l].wi.remove(j);
							break;
						}
				}
				for (int j = 0; j < squares[k].wi.size(); j++)
					if (squares[k].wi.get(j).move == m) {
						squares[k].wi.remove(j);
						break;
					}
				squares[index].wo.clear();
				//checking if there was a capture
				byte temp2 = 0;
				if (squares[k].piece != 0) {
					temp2 = squares[k].piece;
					b = false;
					for (int i = 0; i < squares[k].bo.size(); i++) {
						Data x = squares[k].bo.get(i);
						int l = k + trans[x.move];
						for (int j = 0; j < squares[l].bi.size(); j++)
							if (squares[l].bi.get(j).move == x.move) {
								squares[l].bi.remove(j);
								break;
							}
					}
					squares[k].bo.clear();
				}
				//making the move
				if ((m > 6 && m < 15) || m == 17 || m == 18 || (m > 23 && m < 29) || (m > 74 && m < 107) || (m > 138 && m < 171) || m == 172)
					squares[k].piece = (byte) (temp + 1);
				else {
					squares[k].piece = temp;
					if (m > 34 && m < 43)
						squares[95].piece = (byte) k;
				}
				//blocking the destination square
				for (int i = 0; i < squares[k].bi.size(); i++)
					squares[k].bi.get(i).blocked = true;
				for (int i = 0; i < squares[k].wi.size(); i++)
					squares[k].wi.get(i).blocked = true;
				//moves no longer blocked on original square
				for (int i = 0; i < squares[index].bi.size(); i++)
					squares[index].bi.get(i).blocked = false;
				for (int i = 0; i < squares[index].wi.size(); i++)
					squares[index].wi.get(i).blocked = false;
				//incoming captured piece
				if (!b) {
					boolean c = true;
					if (squares[81 + (temp2 - 1) / 2].piece++ == 0) {
						if (temp2 > 26)
							for (int i = 0; i < 72; i++) {
								if (squares[i + 9].piece == 8) {
									for (int j = 0; j < squares[i].bi.size(); j++)
										if (squares[i].bi.get(j).move < 269) {
											c = false;
											break;
										}
									if (!c || squares[i + 9].bo.size() != 0) {
										if (squares[i].piece != 0)
											squares[i].wi.add(new Data((byte) 275, true));
										else squares[i].wi.add(new Data((byte) 275));
									}
								}
								else if (squares[i].piece != 0)
									squares[i].wi.add(new Data((byte) 275, true));
								else squares[i].wi.add(new Data((byte) 275));
							}
						else if (temp2 == 17 || temp2 == 18)
							for (int i = 0; i < 63; i++) {
								if (squares[i].piece != 0)
									squares[i].wi.add(new Data((byte) 270, true));
								else squares[i].wi.add(new Data((byte) 270));
							}
						else if (temp2 == 15 || temp2 == 16)
							for (int i = 0; i < 72; i++) {
								if (squares[i].piece != 0)
									squares[i].wi.add(new Data((byte) 269, true));
								else squares[i].wi.add(new Data((byte) 269));
							}
						else {
							int n = 269 + (temp2 - 15) / 2;
							for (int i = 0; i < 81; i++) {
								if (squares[i].piece != 0)
									squares[i].wi.add(new Data((byte) n, true));
								else squares[i].wi.add(new Data((byte) n));
							}
						}
					}
				}
			}
			else {
				if (squares[index].wi.isEmpty())
					return false;
				boolean c = squares[index].wi.removeIf((Data item)->item.move == m && item.blocked == false);
				if (!c)
					return false;
				if (--squares[m - 181].piece == 0)
					for (int i = 0; i < 81; i++)
						for (int j = 0; j < squares[i].wi.size(); j++)
							if (squares[i].wi.get(j).move == m) {
								squares[i].wi.remove(j);
								break;
							}
			}
			//adding all legal moves
			int k = index;
			if (m < 269)
				k -= trans[m];
			if (m < 7) {
				for (int i = 0; i < 7; i++) {
					int l = k + 9 * i + 9;
					if (l > 80)
						break;
					else if (l > 71) {
						if (squares[l].piece != 0) {
							squares[l].wi.add(new Data((byte) (i + 8), true));
							if (squares[l].piece > 14)
								squares[k].wo.add(new Data((byte) (i + 8), true));
						}	
						else {
							squares[l].wi.add(new Data((byte) (i + 8)));
							squares[k].wo.add(new Data((byte) (i + 8)));
						}
						break;
					}
					else if (l > 53) {
						if (squares[l].piece != 0) {
							squares[l].wi.add(new Data((byte) i, true));
							squares[l].wi.add(new Data((byte) (i + 8), true));
							if (squares[l].piece > 14) {
								squares[k].wo.add(new Data((byte) i, true));
								squares[k].wo.add(new Data((byte) (i + 8), true));
							}
							break;
						}
						else {
							squares[l].wi.add(new Data((byte) i));
							squares[l].wi.add(new Data((byte) (i + 8)));
							squares[k].wo.add(new Data((byte) i));
							squares[k].wo.add(new Data((byte) (i + 8)));
						}
					}
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
					}
				}
			}
			else if (m < 15 || (m > 172 && m < 179)) {
				for (int i = 173; i < 179; i++) {
					int l = k - trans[i];
					if (l < 0 || l > 80 || ((i == 174 || i == 175) && l % 9 == 8) || ((i == 177 || i == 178) && l % 9 == 0)) {}
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
					}
				}
			}
			else if (m < 17) {
				for (int i = 15; i < 17; i++) {
					int l = k - trans[i];
					if (l < 0 || l > 80 || (i == 15 && l % 9 == 8) || (i == 16 && l % 9 == 0)) {}
					else if (l > 62) {
						if (squares[l].piece != 0) {
							squares[l].wi.add(new Data((byte) (i + 2), true));
							if (squares[l].piece > 14)
								squares[k].wo.add(new Data((byte) (i + 2), true));
						}	
						else {
							squares[l].wi.add(new Data((byte) (i + 2)));
							squares[k].wo.add(new Data((byte) (i + 2)));
						}
					}
					else if (l > 53) {
						if (squares[l].piece != 0) {
							squares[l].wi.add(new Data((byte) i, true));
							squares[l].wi.add(new Data((byte) (i + 2), true));
							if (squares[l].piece > 14) {
								squares[k].wo.add(new Data((byte) i, true));
								squares[k].wo.add(new Data((byte) (i + 2), true));
							}
						}
						else {
							squares[l].wi.add(new Data((byte) i));
							squares[l].wi.add(new Data((byte) (i + 2)));
							squares[k].wo.add(new Data((byte) i));
							squares[k].wo.add(new Data((byte) (i + 2)));
						}
					}
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
					}
				}
			}
			else if (m < 19 || (m > 178 && m < 185)) {
				for (int i = 179; i < 185; i++) {
					int l = k - trans[i];
					if (l < 0 || l > 80 || ((i == 180 || i == 181) && l % 9 == 8) || ((i == 183 || i == 184) && l % 9 == 0)) {}
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
					}
				}
			}
			else if (m < 24) {
				for (int i = 19; i < 24; i++) {
					int l = k - trans[i];
					if (l < 0 || l > 80 || ((i == 20 || i == 21) && l % 9 == 8) || ((i == 22 || i == 23) && l % 9 == 0)) {}
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (k > 53 || l > 53) {
							squares[l].wi.add(new Data((byte) (i + 5), true));
							if (squares[l].piece > 14) {
								squares[k].wo.add(new Data((byte) i, true));
								squares[k].wo.add(new Data((byte) (i + 5), true));
							}		
						}
						else if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
						if (k > 53 || l > 53) {
							squares[l].wi.add(new Data((byte) (i + 5)));
							squares[k].wo.add(new Data((byte) (i + 5)));
						}
					}
				}
			}
			else if (m < 29 || (m > 184 && m < 191)) {
				for (int i = 185; i < 191; i++) {
					int l = k - trans[i];
					if (l < 0 || l > 80 || ((i == 186 || i == 187) && l % 9 == 8) || ((i == 189 || i == 190) && l % 9 == 0)) {}
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
					}
				}
			}
			else if (m < 35) {
				for (int i = 29; i < 35; i++) {
					int l = k - trans[i];
					if (l < 0 || l > 80 || ((i == 30 || i == 31) && l % 9 == 8) || ((i == 33 || i == 34) && l % 9 == 0)) {}
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
					}
				}
			}
			else if (m < 43) {
				for (int i = 35; i < 43; i++) {
					int l = k - trans[i];
					boolean c = false;
					for (int j = 0; j < squares[l].bi.size(); j++)
						if (squares[l].bi.get(j).move < 269)
							c = true;
					if (l < 0 || l > 80 || c || ((i > 35 && i < 39) && l % 9 == 8) || ((i > 39 && i < 43) && l % 9 == 0)) {}
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
					}
				}
			}
			else if (m < 75) {
				for (int i = 43; i < 51; i++) {
					int l = k + 9 * (i - 42);
					if (l > 80)
						break;
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (k > 53 || l > 53) {
							squares[l].wi.add(new Data((byte) (i + 32), true));
							if (squares[l].piece > 14) {
								squares[k].wo.add(new Data((byte) (i + 32), true));
								squares[k].wo.add(new Data((byte) i, true));
							}
						}
						else if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
						if (k > 53 || l > 53) {
							squares[l].wi.add(new Data((byte) (i + 32)));
							squares[k].wo.add(new Data((byte) (i + 32)));
						}
					}
				}
				for (int i = 51; i < 59; i++) {
					int l = k + i - 50;
					if (l / 9 != k / 9)
						break;
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (k > 53) {
							squares[l].wi.add(new Data((byte) (i + 32), true));
							if (squares[l].piece > 14) {
								squares[k].wo.add(new Data((byte) (i + 32), true));
								squares[k].wo.add(new Data((byte) i, true));
							}
						}
						else if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
						if (k > 53) {
							squares[l].wi.add(new Data((byte) (i + 32)));
							squares[k].wo.add(new Data((byte) (i + 32)));
						}
					}
				}
				for (int i = 59; i < 67; i++) {
					int l = k - 9 * (i - 58);
					if (l < 0)
						break;
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (l > 53 || k > 53) {
							squares[l].wi.add(new Data((byte) (i + 32), true));
							if (squares[l].piece > 14) {
								squares[k].wo.add(new Data((byte) (i + 32), true));
								squares[k].wo.add(new Data((byte) i, true));
							}
						}
						else if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
						if (l > 53 || k > 53) {
							squares[l].wi.add(new Data((byte) (i + 32)));
							squares[k].wo.add(new Data((byte) (i + 32)));
						}
					}
				}
				for (int i = 67; i < 75; i++) {
					int l = k - i + 66;
					if (l < 0 || l / 9 != k / 9)
						break;
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (k > 53) {
							squares[l].wi.add(new Data((byte) (i + 32), true));
							if (squares[l].piece > 14) {
								squares[k].wo.add(new Data((byte) (i + 32), true));
								squares[k].wo.add(new Data((byte) i, true));
							}
						}
						else if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
						if (k > 53) {
							squares[l].wi.add(new Data((byte) (i + 32)));
							squares[k].wo.add(new Data((byte) (i + 32)));
						}
					}
				}
			}
			else if (m < 107 || (m > 190 && m < 227)) {
				for (int i = 191; i < 199; i++) {
					int l = k + 9 * (i - 190);
					if (l > 80)
						break;
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
					}
				}
				if (k % 9 == 8) {}
				else if (squares[k + 10].piece != 0) {
					squares[k + 10].wi.add(new Data((byte) 199, true));
					if (squares[k + 10].piece > 14)
						squares[k].wo.add(new Data((byte) 199, true));
				}
				else {
					squares[k + 10].wi.add(new Data((byte) 199));
					squares[k].wo.add(new Data((byte) 199));
				}
				for (int i = 200; i < 208; i++) {
					int l = k + i - 199;
					if (l / 9 != k / 9)
						break;
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
					}
				}
				if (k % 9 == 8) {}
				else if (squares[k - 8].piece != 0) {
					squares[k - 8].wi.add(new Data((byte) 208, true));
					if (squares[k - 8].piece > 14)
						squares[k].wo.add(new Data((byte) 208, true));
				}
				else {
					squares[k - 8].wi.add(new Data((byte) 208));
					squares[k].wo.add(new Data((byte) 208));
				}
				for (int i = 209; i < 217; i++) {
					int l = k - 9 * (i - 208);
					if (l < 0)
						break;
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
					}
				}
				if (k % 9 == 0) {}
				else if (squares[k - 10].piece != 0) {
					squares[k - 10].wi.add(new Data((byte) 217, true));
					if (squares[k - 10].piece > 14)
						squares[k].wo.add(new Data((byte) 217, true));
				}
				else {
					squares[k - 10].wi.add(new Data((byte) 217));
					squares[k].wo.add(new Data((byte) 217));
				}
				for (int i = 218; i < 226; i++) {
					int l = k - i + 217;
					if (l < 0 || l / 9 != k / 9)
						break;
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
					}
				}
				if (k % 9 == 0) {}
				else if (squares[k + 8].piece != 0) {
					squares[k + 8].wi.add(new Data((byte) 226, true));
					if (squares[k + 8].piece > 14)
						squares[k].wo.add(new Data((byte) 226, true));
				}
				else {
					squares[k + 8].wi.add(new Data((byte) 226));
					squares[k].wo.add(new Data((byte) 226));
				}
			}
			else if (m < 139) {
				for (int i = 107; i < 115; i++) {
					int l = k + 10 * (i - 106);
					if (l > 80 || k % 9 >= 8 - (i - 107))
						break;
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (k > 53 || l > 53) {
							squares[l].wi.add(new Data((byte) (i + 32), true));
							if (squares[l].piece > 14) {
								squares[k].wo.add(new Data((byte) (i + 32), true));
								squares[k].wo.add(new Data((byte) i, true));
							}
						}
						else if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
						if (k > 53 || l > 53) {
							squares[l].wi.add(new Data((byte) (i + 32)));
							squares[k].wo.add(new Data((byte) (i + 32)));
						}
					}
				}
				for (int i = 115; i < 123; i++) {
					int l = k - 8 * (i - 114);
					if (l < 0 || k % 9 >= 8 - (i - 115))
						break;
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (k > 53 || l > 53) {
							squares[l].wi.add(new Data((byte) (i + 32), true));
							if (squares[l].piece > 14) {
								squares[k].wo.add(new Data((byte) (i + 32), true));
								squares[k].wo.add(new Data((byte) i, true));
							}
						}
						else if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
						if (k > 53 || l > 53) {
							squares[l].wi.add(new Data((byte) (i + 32)));
							squares[k].wo.add(new Data((byte) (i + 32)));
						}
					}
				}
				for (int i = 123; i < 131; i++) {
					int l = k - 10 * (i - 122);
					if (l < 0 || k % 9 <= i - 123)
						break;
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (k > 53 || l > 53) {
							squares[l].wi.add(new Data((byte) (i + 32), true));
							if (squares[l].piece > 14) {
								squares[k].wo.add(new Data((byte) (i + 32), true));
								squares[k].wo.add(new Data((byte) i, true));
							}
						}
						else if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
						if (k > 53 || l > 53) {
							squares[l].wi.add(new Data((byte) (i + 32)));
							squares[k].wo.add(new Data((byte) (i + 32)));
						}
					}
				}
				for (int i = 131; i < 139; i++) {
					int l = k + 8 * (i - 130);
					if (l > 80 || k % 9 <= i - 131)
						break;
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (k > 53 || l > 53) {
							squares[l].wi.add(new Data((byte) (i + 32), true));
							if (squares[l].piece > 14) {
								squares[k].wo.add(new Data((byte) (i + 32), true));
								squares[k].wo.add(new Data((byte) i, true));
							}
						}
						else if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
						if (k > 53 || l > 53) {
							squares[l].wi.add(new Data((byte) (i + 32)));
							squares[k].wo.add(new Data((byte) (i + 32)));
						}
					}
				}
			}
			else if (m < 171 || (m > 226 && m < 263)) {
				if (k + 9 > 80) {}
				else if (squares[k + 9].piece != 0) {
					squares[k + 9].wi.add(new Data((byte) 227, true));
					if (squares[k + 9].piece > 14)
						squares[k].wo.add(new Data((byte) 227, true));
				}
				else {
					squares[k + 9].wi.add(new Data((byte) 227));
					squares[k].wo.add(new Data((byte) 227));
				}
				for (int i = 228; i < 236; i++) {
					int l = k + 10 * (i - 227);
					if (l > 80 || k % 9 >= 8 - (i - 228))
						break;
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
					}
				}
				if (k % 9 == 8) {}
				else if (squares[k + 1].piece != 0) {
					squares[k + 1].wi.add(new Data((byte) 236, true));
					if (squares[k + 1].piece > 14)
						squares[k].wo.add(new Data((byte) 236, true));
				}
				else {
					squares[k + 1].wi.add(new Data((byte) 236));
					squares[k].wo.add(new Data((byte) 236));
				}
				for (int i = 237; i < 245; i++) {
					int l = k - 8 * (i - 236);
					if (l < 0 || k % 9 >= 8 - (i - 237))
						break;
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
					}
				}
				if (k - 9 < 0) {}
				else if (squares[k - 9].piece != 0) {
					squares[k - 9].wi.add(new Data((byte) 245, true));
					if (squares[k - 9].piece > 14)
						squares[k].wo.add(new Data((byte) 245, true));
				}
				else {
					squares[k - 9].wi.add(new Data((byte) 245));
					squares[k].wo.add(new Data((byte) 245));
				}
				for (int i = 246; i < 254; i++) {
					int l = k - 10 * (i - 245);
					if (l < 0 || k % 9 <= i - 246)
						break;
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
					}
				}
				if (k % 9 == 0) {}
				else if (squares[k - 1].piece != 0) {
					squares[k - 1].wi.add(new Data((byte) 254, true));
					if (squares[k - 1].piece > 14)
						squares[k].wo.add(new Data((byte) 254, true));
				}
				else {
					squares[k - 1].wi.add(new Data((byte) 254));
					squares[k].wo.add(new Data((byte) 254));
				}
				for (int i = 255; i < 263; i++) {
					int l = k + 8 * (i - 254);
					if (l > 80 || k % 9 <= i - 255)
						break;
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
						break;
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
					}
				}
			}
			else if (m == 171) {
				if (k > 62) {
					if (squares[k + 9].piece != 0) {
						squares[k + 9].wi.add(new Data((byte) 172, true));
						if (squares[k + 9].piece > 14)
							squares[k].wo.add(new Data((byte) 172, true));
					}
					else {
						squares[k + 9].wi.add(new Data((byte) 172));
						squares[k].wo.add(new Data((byte) 172));
					}
				}
				else if (k > 44) {
					if (squares[k + 9].piece != 0) {
						squares[k + 9].wi.add(new Data((byte) 171, true));
						squares[k + 9].wi.add(new Data((byte) 172, true));
						if (squares[k + 9].piece > 14) {
							squares[k].wo.add(new Data((byte) 171, true));
							squares[k].wo.add(new Data((byte) 172, true));
						}
					}
					else {
						squares[k + 9].wi.add(new Data((byte) 171));
						squares[k].wo.add(new Data((byte) 171));
						squares[k + 9].wi.add(new Data((byte) 172));
						squares[k].wo.add(new Data((byte) 172));
					}
				}
				else if (squares[k + 9].piece != 0) {
					squares[k + 9].wi.add(new Data((byte) 171, true));
					if (squares[k + 9].piece > 14)
						squares[k].wo.add(new Data((byte) 171, true));
				}
				else {
					squares[k + 9].wi.add(new Data((byte) 171));
					squares[k].wo.add(new Data((byte) 171));
				}
			}
			else {
				for (int i = 263; i < 269; i++) {
					int l = k - trans[i];
					if (l < 0 || l > 80 || ((i == 264 || i == 265) && l % 9 == 8) || ((i == 267 || i == 268) && l % 9 == 0)) {}
					else if (squares[l].piece != 0) {
						squares[l].wi.add(new Data((byte) i, true));
						if (squares[l].piece > 14)
							squares[k].wo.add(new Data((byte) i, true));
					}
					else {
						squares[l].wi.add(new Data((byte) i));
						squares[k].wo.add(new Data((byte) i));
					}
				}
			}
		}
		//need to redo moves for relevant rooks, bishops, and lancers
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