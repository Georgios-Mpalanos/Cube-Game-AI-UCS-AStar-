import java.util.Scanner;
import java.util.ArrayList;

class CubeGame{
	
	public static void main(String[] args){
		
		Scanner input = new Scanner(System.in);
		
		
		int k = 1;
		System.out.print("Please enter the value for variable k: ");
		k = input.nextInt();
		
		while(k <= 0){
			System.out.print("Please enter a valid value for variable k (k>=0): ");
			k = input.nextInt();
		}
		
		
		Board myBoard = new Board(k);
		
		int inputStateSize = 6*k;
		Cube[] initialState = new Cube[inputStateSize];
		State inputState = null;
		
		
		boolean okState = true;
		
		/*
			Initializing state based on the user's input.
		*/
		
		do{
			
			for(int i = 0; i < 3*k; i++){
				System.out.println("Please enter cube's number and cube's position {x,y}.");
				int tempNum = input.nextInt();
				int tempX = input.nextInt() - 1;
				int tempY = input.nextInt() - 1;
				
				Cube tempCube = new Cube(tempNum,tempX,tempY,k);
				int tempIdx = tempCube.getIndex();
				
				initialState[tempIdx] = tempCube;
				
			}
			
			inputState = new State(myBoard,null,initialState,0);
			
			if(!myBoard.isValid(inputState)){
				okState = false;
				System.out.println("Invalid initialState,please try again.");
				
				for(int j = 0; j < 6*k; j++){
					initialState[j] = null;	
				}
				
				inputState = null;
				
			}else{
				okState = true;
			}
			
		}while(okState == false);
		
		
		int methodToRun = 0;
		System.out.println("Enter '1' to run UCS.");
		System.out.println("Enter '2' to run A-star.");
		System.out.println("Enter '3' to run both(UCS and A-star).");
		methodToRun = input.nextInt();
		
		if(methodToRun == 1){
			System.out.println();
			System.out.println("Running UCS");
			//UCS:
			UCS ucsGame = new UCS(myBoard);
			ucsGame.runUCS(inputState);
		}else if(methodToRun == 2){
			System.out.println();
			System.out.println("Running Astar");
			//Astar:
			Astar astarGame = new Astar(myBoard);
			astarGame.runAstar(inputState);
		}else if(methodToRun == 3){
			System.out.println();
			System.out.println("Running UCS");
			//UCS:
			UCS ucsGame = new UCS(myBoard);
			ucsGame.runUCS(inputState);
			
			System.out.println();
			System.out.println();
			System.out.println();
			
			System.out.println("Running Astar");
			//Astar:
			Astar astarGame = new Astar(myBoard);
			astarGame.runAstar(inputState);
		}	
	}
}