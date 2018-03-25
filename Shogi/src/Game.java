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

		//Str
		String currentMove;
		boolean running = true;

		//Keeps track of how many turns have gone by.
		int turns;
		while(running){

			shogi.drawBoard();

			System.out.print("Please enter your move (Enter 'Moves' to show list of moves): ");
			currentMove = s.nextLine();

			//let user enter command to show list of moves
			if(currentMove.equals("Moves") || currentMove.equals("moves")){
				shogi.drawTable();
			}
			else{
				//add current move to the move stack, can be used to implement an undo function later on
				moveList.push(currentMove);

				//Calls the makeMove method, if the move is not legal it let user know
				if(!shogi.makeMove(currentMove)){
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