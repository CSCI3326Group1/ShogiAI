// import org.lwjgl.*;
// import org.lwjgl.glfw.*;
// import org.lwjgl.opengl.*;
// import org.lwjgl.system.*;
// import java.nio.*;
// import static org.lwjgl.glfw.Callbacks.*;
// import static org.lwjgl.glfw.GLFW.*;
// import static org.lwjgl.opengl.GL11.*;
// import static org.lwjgl.system.MemoryStack.*;
// import static org.lwjgl.system.MemoryUtil.*;
import java.util.Stack;
import java.util.Scanner;
import org.apache.commons.lang3.*;
import org.apache.commons.lang3.time.StopWatch;

public class Game {

	Scanner s;
	Shogi shogi;
	Stack<String> moveList;
	// The window handle

	Game(){
		s = new Scanner(System.in);
		shogi = new Shogi();
	}

	private long window;

	public void run() {
		// init();
		 loop();
		// // Free the window callbacks and destroy the window
		// glfwFreeCallbacks(window);
		// glfwDestroyWindow(window);
		// // Terminate GLFW and free the error callback
		// glfwTerminate();
		// glfwSetErrorCallback(null).free();
	}
	private void init()	{
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		// GLFWErrorCallback.createPrint(System.err).set();
		// // Initialize GLFW. Most GLFW functions will not work before doing this.
		// if ( !glfwInit() )
		// 	throw new IllegalStateException("Unable to initialize GLFW");
		// // Configure GLFW
		// glfwDefaultWindowHints(); // optional, the current window hints are already the default
		// glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		// glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
		// // Create the window
		// window = glfwCreateWindow(300, 300, "Shogi", NULL, NULL);
		// if ( window == NULL )
		// 	throw new RuntimeException("Failed to create the GLFW window");
		// // Setup a key callback. It will be called every time a key is pressed, repeated or released.
		// glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
		// 	if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
		// 		glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		// });
		// // Get the thread stack and push a new frame
		// try ( MemoryStack stack = stackPush() ) {
		// 	IntBuffer pWidth = stack.mallocInt(1); // int*
		// 	IntBuffer pHeight = stack.mallocInt(1); // int*
		// 	// Get the window size passed to glfwCreateWindow
		// 	glfwGetWindowSize(window, pWidth, pHeight);
		// 	// Get the resolution of the primary monitor
		// 	GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		// 	// Center the window
		// 	glfwSetWindowPos(
		// 		window,
		// 		(vidmode.width() - pWidth.get(0)) / 2,
		// 		(vidmode.height() - pHeight.get(0)) / 2
		// 	);
		// } // the stack frame is popped automatically
		// // Make the OpenGL context current
		// glfwMakeContextCurrent(window);
		// // Enable v-sync
		// glfwSwapInterval(1);
		// // Make the window visible
		// glfwShowWindow(window);
	}
	private void loop() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		// GL.createCapabilities();
		// Set the clear color
		// glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		// while ( !glfwWindowShouldClose(window) ) {
			// glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			// glfwSwapBuffers(window); // swap the color buffers
			// Poll for window events. The key callback above will only be
			// invoked during this call.
			// glfwPollEvents();
		System.out.println("Welcome to Shogi!");

		//stopwatch
		StopWatch playerOne = new StopWatch();
		StopWatch playerTwo = new StopWatch();
		playerOne.start();
		//Str
		String currentMove;
		boolean running = true, b = true;

		while(running){

			shogi.drawBoard();

			System.out.print("Please enter your move (Enter 'Moves' to show list of moves): ");
			currentMove = s.nextLine();

			//let user enter command to show list of moves
			if(currentMove.equals("Moves") || currentMove.equals("moves")){
				shogi.drawTable(playerOne.toString().substring(0, 8), playerTwo.toString().substring(0, 8));
			}
			else{
				//add current move to the move stack, can be used to implement an undo function later on
				moveList.push(currentMove);
				if (shogi.moves % 2 == 0)
					b = true;
				else b = false;
				//Calls the makeMove method, if the move is not legal it let user know
				if(shogi.makeMove(shogi.convertMove(currentMove, b))){
					//setting times to 0 for now, not sure how to implement
					shogi.updateTable(currentMove);
					//once you update the table, stop playerOne's timer and start playerTwo and vice versa
					if(playerOne.isStarted() && playerTwo.isSuspended())
					{
						playerOne.suspend();
						playerTwo.resume();
					}
					else if(playerTwo.isStarted() && playerOne.isSuspended())
					{
						playerTwo.suspend();
						playerOne.resume();
					}
					else
					{
						playerOne.suspend();
						playerTwo.start();
					}
				}
				else{
					System.out.println("Sorry, that is not a legal move!");
				}
			}


			//check if game should continue running
			running = !shogi.gameOver();
		}
	}


	public static void main(String[] args) {
		//focus on ASCII mode first before using LWJGL3
		new Game().run();
	}
}