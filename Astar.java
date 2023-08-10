import java.util.ArrayList;

class Astar{
	private PrioQueueA queueA;
	private Board myBoard;
	
	public Astar(Board myBoard){
		this.myBoard = myBoard;
		this.queueA = new PrioQueueA(this.myBoard);
	}
	
	public PrioQueueA getQueue(){
		return queueA;
	}
	
	
	/*
		The parameter inState will be the initial State of the 
		input problem.
		
		The priority queue acts as the frontier from which we 
		select the cheapest move to execute in each iteration.
		
		The "counter" variable indicates the number of extensions
		the algorithm did in order to reach the final State.
	*/
	
	
	public void runAstar(State inState){
		
		int counter = 0;
		inState.findMoves();
		inState.calculateH();
		this.getQueue().enqueue(inState);
		
		System.out.println("Input state: ");
		System.out.println(inState);
		
		if(myBoard.isFinal(inState) == true){
			System.out.println("Total cost : 0");
			System.out.println("Ended");
			return;
		}	
		
		ArrayList<State> stateTree = new ArrayList<State>();
		
		
		
		while(true){
			
			Integer[] minCost = this.getQueue().dequeue();
			
			State minCostState = this.getQueue().getArray().get(minCost[4]);
			
			Integer[] selectedMove = new Integer[4];
			selectedMove[0] = minCost[0]; // Old x-coordinate
			selectedMove[1] = minCost[1]; // Old y-coordinate
			selectedMove[2] = minCost[2]; // New x-coordinate
			selectedMove[3] = minCost[3]; // New y-coordinate
			
			Cube cubeToMove = null;
			State newState = null;
			
			if(selectedMove[1] == 1){ // Old-y == 1
				cubeToMove = minCostState.getProblemState()[(selectedMove[0]-1)];
			}else{
				cubeToMove = minCostState.getProblemState()[(selectedMove[1]-1)*myBoard.getK() + (selectedMove[0]-1) + myBoard.getL() - myBoard.getK()];
			}

			if(cubeToMove != null){
				
				newState = myBoard.moveCube(minCostState,cubeToMove,selectedMove[2],selectedMove[3]);
				if(newState != null){
					
					if(myBoard.isFinal(newState) == true){ //Check if the move/State that was removed("was executed") 
														   //from the frontier was the final State.
						System.out.println("Finished");
						System.out.printf("Total cost : %f\n", newState.getCostFromRoot());
						System.out.println();
						
						stateTree.add(newState);
						
						State state = newState.getParentState();
						
						while(state.getParentState() != null){
							stateTree.add(state);
							state = state.getParentState();
						}
						
						System.out.println("Initial state: ");
						stateTree.add(inState);
						for(int i = stateTree.size() - 1; i >= 0; i--){
							System.out.println(stateTree.get(i));
							
							
							if(i == 1){
								System.out.println("Final state(Completed!): ");
							}else if(i != 0){
								System.out.println("Next state: ");
							}
							
							
						}  
						System.out.println("Number of expansions : " + counter);
						System.out.println();
						
						return;
						
					}
					
					newState.findMoves();
					newState.calculateH();
					
					counter += newState.getMoves().size();
					
					this.getQueue().enqueue(newState); // Add new State to the frontier.	
				}
			}	
		}	
	}	
}