import java.util.HashMap;

class Board{
	
	private HashMap<Integer,Cube> myMap; /* It maps the finalState. Each key represents the position(extra spaces are not included
											and each value represents the Cube object that has to be in that position. */

	private State finalState;	// State object that contains the final state.
	private int k;
	private int l;
	private int n;
	
	
	public Board(int k){
		this.k = k;
		this.l = 4*k;
		this.n = 3*k;
		Cube[] finalBoard = new Cube[2*k+l]; // FinalBoard is an array that has all the cubes placed at their final position.
		
		
		myMap = new HashMap<Integer,Cube>();
		
		/*
			The following code creates the final arrangement of the cubes,
			(the cube arrangement is stored in the finalState and in the HashMap myMap).
		*/
		
		int pos = 0;
		int pos2 = 0;
		for(int i = 0; i < 3; i++){
			if(i == 0){
				for(int j = 0; j < k; j++){
					Cube tempCube = new Cube(pos+1,j,i,k);
					finalBoard[pos2] = tempCube;
					myMap.put(pos+1,tempCube);
					pos++;
					pos2++;
				}
				for(int j = 0; j < l-k; j++){
					
					finalBoard[pos2] = null;
					
					pos2++;
				}
			}else{
				for(int j = 0; j < k; j++){
					Cube tempCube = new Cube(pos+1,j,i,k);
					finalBoard[pos2] = tempCube;
					myMap.put(pos+1,tempCube);
					pos++;
					pos2++;
				}
			}
		}
		
		this.finalState = new State(null,null,finalBoard,0);
	}
	
	
	//Setters-Getters.
	public HashMap<Integer,Cube> getMyMap(){
		return myMap;
	}
	
	public State getFinalState(){
		return finalState;
	}
	
	public int getK(){
		return this.k;
	}
	
	public int getL(){
		return this.l;
	}
	
	/*
		Function checkUp checks if the given cube has any cubes above it.
		
		Base case: The cube is located on the third row(top).
		
		If the given cube is located on the first row(table) or the second
		row we check if the position above it on the array "problemState" 
		has "null" value.
		
	*/
	
	public boolean checkUp(Cube tempCube,State state){
		Cube[] problemState = state.getProblemState();
		boolean returnVal = false;
		
		if(tempCube.getY() == 1){
			if(tempCube.getX() > k){
				return true;
			}

			if(problemState[(tempCube.getIndex() + l)] == null){
				returnVal = true;
			}else{
				returnVal = false;
			}
		}
		
		if(tempCube.getY() == 2){
			if(problemState[(tempCube.getIndex() + k)] == null){
				returnVal = true;
			}else{
				returnVal = false;
			}
			
		}
		
		if(tempCube.getY() == 3){
			returnVal = true;
		}
		
		return returnVal;
		
	}
	
	/*
		Function checkDown checks if the given cube has any cubes below it.
		
		Base case: The cube is located on the first row(table).
		
		If the given cube is located on the second row or the third
		row we check if the position below it on the array "problemState" 
		has Cube object(not "null") as a value.
		
	*/
	
	public boolean checkDown(Cube tempCube,State state){
		Cube[] problemState = state.getProblemState();
		boolean returnVal = false;
		
		if(tempCube.getY() == 1){
			returnVal = true;
		}
		
		if(tempCube.getY() == 2){
			if(problemState[(tempCube.getIndex() - l)] == null){
				returnVal = false;
			}else{
				returnVal = true;
			}
		}
		
		if(tempCube.getY() == 3){
			if(problemState[(tempCube.getIndex() - k)] == null){
				returnVal = false;
			}else{
				returnVal = true;
			}
		}
		
		return returnVal;
		
	}
	
	
	/*
		To validate a given State firstly we check if 
		1<=y<=3. Then we check if 1<=x<=l (for y==1)
		and 1<=x<=k (for (y == 2 or y == 3)).
		
		After that, we check for each cube that is not 
		located on the first row(table) if it has another
		cube below it.
		
	*/
	
	
	public boolean isValid(State state){
		Cube[] problemState = state.getProblemState();
		
		//Check Y: 
		for(Cube cube: problemState){
			if(cube != null){
				if(cube.getY() < 1 || cube.getY() > 3){
					return false;
				}
			}
		}
		
		//Check X: 
		for(Cube cube: problemState){
			if(cube != null){
				if(cube.getY() == 1){
					if(cube.getX() < 1 || cube.getX() > l){
						return false;
					}
				}else{
					if(cube.getX() < 1 || cube.getX() > k){
						return false;
					}
				}
			}
		}
		
		//Check down: 
		for(Cube cube: problemState){
			if(cube != null){
				if(cube.getY() != 1){
					if(checkDown(cube,state) == false){
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	
	/*
		To conclude if a given State is final we use the 
		function "isEqual" ,of class State, to check if it 
		is equal with "finalState" (that we initialized earlier).
	*/
	
	public boolean isFinal(State state){
		return state.isEqual(finalState);
	}
	
	
	/*
		A cube is free if it doesn't have any cubes above it.
	*/
	
	public boolean isFree(Cube myCube,State state){
		return checkUp(myCube,state);		
	}
	
	/*
		"calculateCost" calculates the cost of a movement
		based on the old y-coordinate and the new y-coordinate.
	*/
	
	public double calculateCost(int y, int newY){
		double cost = 0;
		
		if(newY > y){
			cost = newY - y;
		}else if(newY < y){
			cost = 0.5*(y-newY);
		}else{
			cost = 0.75;
		}
		
		return cost;
	}
	
	
	/*
		"isEmptySpace" returns True if the given position's value is "null",
		otherwise it returns False.
	*/
	
	public boolean isEmptySpace(State state,int x, int y){
		Cube[] problemState = state.getProblemState();
		boolean returnVal = true;
		
		if(y == 1){
			if(problemState[x-1] != null){
				returnVal = false;
			}
		}else{
			if(problemState[(y-1)*k + (x-1) + (l-k)] != null){
				returnVal = false;
			}
		}
		
		return returnVal;
	}
	
	
	/*
		"moveCube" receives a set of coordinates that indicates
		the movement of the cube "myCube" to this location. 
		
		First, it checks if "myCube" is free to move and then it 
		creates a new State "returnState" that includes the moved
		cube.
		
		When the "returnState" is created we check that it is 
		valid and that it didn't appear as a state earlier.
		
		If everything is ok we return the created State.
		
		To check if the "returnState" has been already created we
		check if it is equal with its ancestors.(Code at lines 350-357).
	*/
	
	public State moveCube(State state,Cube myCube, int x, int y){
		Cube[] problemState = state.getProblemState();
		Cube[] tempProblemState = new Cube[problemState.length];
		double moveCost = 0;
		
		for(int i = 0; i < problemState.length; i++){
			tempProblemState[i] = problemState[i];
		}
		
		Cube newCube = new Cube(myCube.getNumber(),x-1,y-1,k);
		moveCost = calculateCost(myCube.getY(),y);

		
		if(isFree(myCube,state) == false || isEmptySpace(state,x,y) == false){
			return null;
		}
		
		int oldIndex = myCube.getIndex();
		int newIndex = newCube.getIndex();
		
		
		tempProblemState[newIndex] = newCube;
		tempProblemState[oldIndex] = null;
		
		State returnState = new State(this,state,tempProblemState,moveCost);
		
		
		if(isValid(returnState) == false){
			return null;
		}
		

		while(state.getParentState() != null){
			if(returnState.isEqual(state)){
				//System.out.println("State already visited!");
				return null;
			}
			
			state = state.getParentState();	
		}
		
		return returnState;	
	}	
}