public class Data {
	byte move;
	//Convention is clockwise, least to greatest, followed by promote variations
	//        l,  n,  s,  g,  k,  r,   b,   p,   L,   N,   G,   R,   B,   T, drops for l,n,s,g,r,b,p
	//moves: 15,  4, 10,  6,  8, 64,  64,   2,   6,   6,   6,  36,  36,   6,   7
	//sums:   0, 15, 19, 29, 35, 43, 107, 171, 173, 179, 185, 191, 227, 263, 269
	//276 total moves
	//0 - l move 1 forward
	//1 - l move 2 forward
	//2 - l move 3 forward
	//3 - l move 4 forward
	//4 - l move 5 forward
	//5 - l move 6 forward
	//6 - l move 7 forward
	//7 - l move 8 forward and promote
	//8 - l move 1 forward and promote
	//9 - l move 2 forward and promote
	//10 - l move 3 forward and promote
	//11 - l move 4 forward and promote
	//12 - l move 5 forward and promote
	//13 - l move 6 forward and promote
	//14 - l move 7 forward and promote
	//15 - n move forward right
	//16 - n move forward left
	//17 - n move forward right and promote
	//18 - n move forward left and promote
	//19 - s move forward
	//20 - s move forward right
	//21 - s move backward right
	//22 - s move backward left
	//23 - s move forward left
	//24 - s move forward and promote
	//25 - s move forward right and promote
	//26 - s move backward right and promote
	//27 - s move backward left and promote
	//28 - s move forward left and promote
	//etc.
	boolean blocked;
	Data(byte x){
		move = x;
		blocked = false;
	}
	Data(byte x, boolean b){
		move = x;
		blocked = b;
	}
}