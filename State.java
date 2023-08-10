import java.util.ArrayList;
import java.util.HashMap;

class State{
	private Board myBoard;
	private State parentState;
	private Cube[] problemState;
	private double costFromRoot;
	
	private int h;
	
	private ArrayList<Integer[]> moves = new ArrayList<Integer[]>();
	
	public State(Board myBoard,State parentState,Cube[] problemState,double moveCost){
		this.myBoard = myBoard;
		this.parentState = parentState;
		this.problemState = problemState;
		
		if(this.parentState == null){
			costFromRoot = moveCost;
		}else{
			costFromRoot = parentState.getCostFromRoot() + moveCost;
		}
		
	}
	
	//Getters.
	public Cube[] getProblemState(){
		return problemState;
	}
	
	public double getCostFromRoot(){
		return costFromRoot;
	}
	
	public State getParentState(){
		return parentState;
	}
	
	public ArrayList<Integer[]> getMoves(){
		return moves;
	}
	
	public int getH(){
		return h;
	}
	
	
	/*
		Two states are equal if their problemStates are equal.
	*/
	
	public boolean isEqual(State other){
		Cube[] otherProblem = other.getProblemState();
		for(int i = 0; i < problemState.length; i++){
			if((otherProblem[i] == null && problemState[i] != null) || (otherProblem[i] != null && problemState[i] == null)){
				return false;
			}else if(otherProblem[i] == null && problemState[i] == null){
				continue;
			}else if(problemState[i].isEqual(otherProblem[i]) == false){
				return false;
			}
		}
		
		return true;
		
	}
	
	public String toString(){
		String returnString = "";
		for(Cube cube: problemState){
			if(cube != null){
				returnString += "(x:"+ cube.getX() + ",y:" + cube.getY() + ") " + cube.getNumber() + "\n";
			}else{
				returnString += "null" + "\n";
			}
		}
		
		return	returnString;
	}
	
	/*
		Heuristic function used for A-star:
		
		The value of the heuristic function is based on the 
		number of cubes which are placed correctly.
		
		It gives more "points" for any cube that is placed correctly
		and it is located at the first row(table), because otherwise
		it would require at least 3 moves to place the current cube on
		any of the extra spaces and then move it again to its correct
		position.If the cube is located on the second or the third row,
		if it is placed correctly and if the cube below it, is also placed 
		correctly it increments the points by one.
		
		(Especially for the thrid row, if the current cube is placed 
		correctly and the cubes below ,are also placed correctly the
		heuristic function gives more "points" because there won't be any
		extra moves needed to "fix" the cubes on the first and second 
		row).
	
	*/
	
	public void calculateH(){
		int correctCubes = 0;
		State finalState = myBoard.getFinalState();
		Cube[] finalProblem = finalState.getProblemState();
		
		for(int y = 0; y < 3; y++){
			for(int x = 0; x < myBoard.getK(); x++){
				if(y == 0){
					if(problemState[x] != null && problemState[x].getNumber() == finalProblem[x].getNumber()){
						correctCubes += 4;
					}
				}else if(y == 1){
					if(problemState[y*myBoard.getK() + x + myBoard.getL() - myBoard.getK()] != null 
					&& problemState[y*myBoard.getK() + x + myBoard.getL() - myBoard.getK()].getNumber() == finalProblem[y*myBoard.getK() + x + myBoard.getL() - myBoard.getK()].getNumber()){
						if(problemState[x].getNumber() == finalProblem[x].getNumber()){
							correctCubes ++;
						}
					}
				}else{
					if(problemState[y*myBoard.getK() + x + myBoard.getL() - myBoard.getK()] != null 
					&& problemState[y*myBoard.getK() + x + myBoard.getL() - myBoard.getK()].getNumber() == finalProblem[y*myBoard.getK() + x + myBoard.getL() - myBoard.getK()].getNumber()){
						if(problemState[x].getNumber() == finalProblem[x].getNumber() && problemState[(y-1)*myBoard.getK() + x + myBoard.getL() - myBoard.getK()].getNumber() == finalProblem[(y-1)*myBoard.getK() + x + myBoard.getL() - myBoard.getK()].getNumber()){
							correctCubes += 4;
						}else{
							correctCubes ++;
						}
					}
				}
			}
		}
		
		this.h += correctCubes;
		
	} 
	
	
	/*
		In order to find all the possible moves for a certain
		State we use the "findMoves()" function. Children States
		are referred as "moves".
		
		For every cube ,we check if it is free and then depending on 
		its y-coordinate(and later on ,its x-coordinate) we "calculate" 
		the locations that could be placed.These locations have to 
		be empty. If a cube is placed on any of the extra spaces then "moves" 
		that place the cube on another extra space are not counted 
		because they wouldn't benefit the result neither the execution of the
		program as they are considered the same.
		
		Finally every move that is valid and beneficial it is added to the 
		ArrayList "moves" for later use.
		
	*/
	
	public void findMoves(){
		for(int i = 0; i < problemState.length; i ++){
			if(problemState[i] != null){
				
				if(myBoard.isFree(problemState[i],this) == true){
					for(int y = 0; y < 3; y++){
						if(y == 0){
							
							if(problemState[i].getInExtra() == true){
								
								for(int x = 0; x < myBoard.getK(); x++){
									Integer[] temp = new Integer[4];
									if(myBoard.isEmptySpace(this,x+1,y+1) == true){
										if(problemState[i].getX() == x+1 && problemState[i].getY() == y){continue;}
										temp[0] = problemState[i].getX(); // Old x-coordinate
										temp[1] = problemState[i].getY(); // Old y-coordinate
										temp[2] = x+1; // New x-coordinate
										temp[3] = y+1; // New y-coordinate
										moves.add(temp);
									}
			
								}
								
							}else{
								
								for(int x = 0; x < myBoard.getL(); x++){
									Integer[] temp = new Integer[4];
									if(myBoard.isEmptySpace(this,x+1,y+1) == true){
										if(problemState[i].getX() == x+1 && problemState[i].getY() == y){continue;}
										temp[0] = problemState[i].getX(); // Old x-coordinate
										temp[1] = problemState[i].getY(); // Old y-coordinate
										temp[2] = x+1; // New x-coordinate
										temp[3] = y+1; // New y-coordinate
										moves.add(temp);
									}	
									
								}
							}	
							
							
						}else{
							
							for(int x = 0; x < myBoard.getK(); x++){
								Integer[] temp = new Integer[4];
								if(myBoard.isEmptySpace(this,x+1,y+1) == true && myBoard.isEmptySpace(this,x+1,y) == false){
									if(problemState[i].getX() == x+1 && problemState[i].getY() == y){continue;}
									temp[0] = problemState[i].getX();
									temp[1] = problemState[i].getY();
									temp[2] = x+1;
									temp[3] = y+1;
									moves.add(temp);
								}
								
							}
						}
					}	
				}
			}
		}	
	}
}