import java.util.Scanner;

public class ValIter {

	//Constraints from Formula
	private static int gamma = 1;
	private static final int MAX_ITERATIONS = 1000;
	private static double epsilon = 0.001;

	// Declaration of Matrices
	private static int U[][];
	private static int Udelta[][];
	private static int R[][];
	private static String Pi[][];

	// World Constraints
	private static int rows = 7;
	private static int cols = 7;
	private static int wind = 0;

	// Helper	
	private static int Rinitial = -1;
	public static Scanner reader = new Scanner(System.in); 
	
	public static void initializeUdelta() {
		int r;
		int c;
		Pi = new String[rows][cols];
		Udelta = new int[rows][cols];

		for (r=0; r<rows; r++) {
			for (c=0; c<cols; c++) {
				Udelta[r][c] = 0;
			}
		}
		U = new int[rows][cols];
		R = new int[rows][cols];
	}

	public static void initializeRewards() {
		int r;
		int c;		
		for (r=0; r<rows; r++) {
			for (c=0; c<cols; c++) {
				R[r][c] = Rinitial;
			}
		}
		R[3][6] = 0;		
	}

	public static void initialize() {
		initializeUdelta();
		initializeRewards();
	}

	public static void valueIteration(int wind) {
		int r;
		int c;
		int iterations = 0;
		double delta = 0;
		do {
			for (int i = 0; i < Udelta.length; i++) {
				for (int j = 0; j < Udelta[i].length; j++) {
					U[i][j] = Udelta[i][j];
				}
			}
			iterations++;
			delta = 0;
			for (r = 0; r < rows; r++) {
				for (c = 0; c < cols; c++) {
					findOptimalAction(r, c, wind);
					int diff = Math.abs(Udelta[r][c] - U[r][c]);
					if (diff > delta) {
						delta = diff;
					}
				}
			}
		} while (delta > epsilon && iterations < MAX_ITERATIONS);
		printWorld(iterations);
	}

	public static void findOptimalAction(int r, int c, int wind) {
		int action[] = new int[9];
		if ((r==3 && c==6)) {
			Udelta[r][c] = R[r][c];
		}
		else {
			action[0] = peekNorth(r,c, wind);
			action[1] = peekSouth(r,c, wind);
			action[2] = peekEast(r,c, wind);
			action[3] = peekWest(r,c, wind);
			action[4] = peekNorthEast(r,c, wind);
			action[5] = peekSouthEast(r,c, wind);
			action[6] = peekSouthWest(r,c, wind);
			action[7] = peekNorthWest(r,c, wind);
			action[8] = peekStay(r,c, wind);

			int best = bestAction(action);
			Udelta[r][c] = R[r][c] + gamma * action[best];
			if(best == 0) {
				Pi[r][c] = "N";
			}
			else if(best == 1) {
				Pi[r][c] = "S";
			}
			else if(best == 2) {
				Pi[r][c] = "E";
			}
			else if(best == 3) {
				Pi[r][c] = "W";
			}
			else if(best == 4) {
				Pi[r][c] = "NE";
			}
			else if(best == 5) {
				Pi[r][c] = "SE";
			}
			else if(best == 6) {
				Pi[r][c] = "SW";
			}
			else if(best == 7) {
				Pi[r][c] = "NW";
			}
			else if(best == 8) {
				Pi[r][c] = "STAY";
			}
		}
	}

	public static void printWorld(int n) {
		int r;
		int c;
		System.out.println("Number of Iterations: " + n);
		for (r=0; r<rows; r++) {
			for (c=0; c<cols; c++) {
				System.out.print(U[r][c] + "\t");
			}
		System.out.println();
		}
		Pi[3][6] = "*";
		System.out.println();
		System.out.println("Optimal:");
		System.out.println();
		
		for (r=0; r<rows; r++) {
			for (c=0; c<cols; c++) {
				System.out.print(Pi[r][c] + "\t");
			}
		System.out.println();
		}
	}

	public static int bestAction(int action[]) {
		int b = 0;
		for (int i = 1; i < action.length; i++) {
			b = (action[b] >= action[i]) ? b : i;
		}
		return b;
	}

	public static int peekNorth(int r, int c, int wind) {
		if (r==0) {
			return U[r][c];
		}
		// Case of Wind
		if(c > 2 && c < 6) {
			while(r-1 + wind < 0) {
				wind += 1;
			}
			return U[r - 1 + wind][c];
		}
		// No Wind
		return U[r-1][c];
	}

	public static int peekNorthEast(int r, int c, int wind) {
		if(c == cols-1) {
			return U[r][c];
		}
		if(r == 0) {
			return peekEast(r, c, 0);
		}
		// Case of Wind
		if(c > 2 && c < 6) {
			while(r-1 + wind < 0) {
				wind += 1;
			}
			return U[r - 1 + wind][c + 1];
		}
		// No Wind
		return U[r-1][c+1];
	}

	public static int peekEast(int r, int c, int wind) {
		if (c==cols-1) {
			return U[r][c];
		}
		//Case of Wind
		if(c > 2 && c < 6) {
			if(r == 0 &&  wind != 0) {
				return peekEast(r, c, 0);
			}
			else {
				while(r + wind < 0) {
					wind += 1;
				}
				return U[r + wind][c + 1];
			}
		}
		//No Wind
		return U[r][c+1];
	}


	public static int peekSouthEast(int r, int c, int wind) {
		if(c == cols-1) {
			return U[r][c];
		}
		if(r == rows-1) {
			return peekEast(r, c, 0);	
		}
		//Case of Wind
		if(c > 2 && c < 6) {
			while(r+1 + wind < 0) {
				wind += 1;
			}
			return U[r + 1 + wind][c + 1];
		}
		//No Wind
		return U[r+1][c+1];
	}

	public static int peekSouth(int r, int c, int wind) {
		if (r==rows-1) {
			return U[r][c];
		}
		// Case of Wind
		if(c > 2 && c < 6) {
			while(r + 1 + wind < 0) {
				wind += 1;
			}
			return U[r + 1 + wind][c];
		}
		// No Wind
		return U[r+1][c];
	}

	public static int peekSouthWest(int r, int c, int wind) {
		if(r == rows-1) {
			return peekWest(r,c, 0);
		}
		if(c == 0) {
			return U[r][c];
		}
		// Case of Wind
		if(c > 2 && c < 6) {
			while(r + 1 + wind < 0) {
				wind += 1;
			}
			return U[r + 1 + wind][c - 1];
		}
		// No Wind
		return U[r+1][c-1];
	}

	public static int peekWest(int r, int c, int wind) {
		if (c==0) {
			return U[r][c];
		}
		// Case of Wind
		if(c > 2 && c < 6) {
			if(r == 0 && wind != 0) {
				return peekWest(r, c, 0);
			}
			else {
				while(r + wind < 0) {
					wind += 1;
				}
				return U[r + wind][c - 1];
			}
		}
		// No Wind
		return U[r][c-1];
	}


	public static int peekNorthWest(int r, int c, int wind) {
		if(c == 0) {
			return U[r][c];
		}
		if(r == 0) {
			return peekWest(r,c, 0);				
		}
		// Case of Wind
		if(c > 2 && c < 6) {
			while(r-1 + wind < 0) {
				wind += 1;
			}
			return U[r - 1 + wind][c - 1];
		}
		// No Wind
		return U[r-1][c-1];
	}

	public static int peekStay(int r, int c, int wind) {
		// Case of Wind
		if(c > 2 && c < 6) {
			while(r + wind < 0) {
				wind += 1;
			}
			return U[r + wind][c];
		}
		// No Wind
		return U[r][c];
	}

	public static void main(String[] args) {
		while(true) {
			System.out.println();
			System.out.println("VALUE ITERATION");
			System.out.println("The given world for this Value Iteration is a 7x7 matrix.");
			 try {
  				Thread.sleep(500);
			} catch (Exception e) {
    			e.printStackTrace();
			}
			System.out.println("Please enter how strong you want the wind to be for this iteration. (Enter only '0', '-1' or '-2')");
			System.out.println("Type 'quit' to exit program.");
			System.out.println("Type 'clr' to make space in the console window.");
			System.out.println();
			System.out.print("Wind Strength (0/-1/-2): ");
			String input = reader.nextLine();
			
			if(input.equals("quit")) {
				reader.close();
				break;
			}
			else if(input.equals("clr")) {
				for(int i = 0; i <= 20; i++) {
					System.out.println();
				}
				continue;
			}
			System.out.println("Running value iteration with wind strength of " + input);
			wind = Integer.parseInt(input);
			initialize();
			valueIteration(wind);
			System.out.println("Done.");
		}
	}
}