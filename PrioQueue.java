import java.util.ArrayList;

class PrioQueue{ // For UCS.
	private ArrayList<State> queue;
	private Board myBoard;
	
	public PrioQueue(Board myBoard){
		this.myBoard = myBoard;
		this.queue = new ArrayList<State>();
	}
	
	
	public ArrayList<State> getArray(){
		return queue;
	} 
	
	/*
		It adds the given State to the priority queue.
	*/
	
	public void enqueue(State state){
		queue.add(state);
	}
	
	
	
	/*
		The priority of the queue is based on the cost-value of each
		move that is currently in the queue.To be more specific the 
		queue contains a list of States and each State contains a list
		of moves that have been calculated earlier by the function "findMoves()".
		The cheapest move(in terms of its cost from the root) will be selected.
		
		For every State in queue we check its list of moves(ArrayList moves) and 
		we keep the cheapest move.Then we remove the selected move and return the
		move and the index,in the queue, of the State that contains it.
		
	*/
	
	public Integer[] dequeue(){
		double tempCost = Double.MAX_VALUE;
		int selectedState = 0;
		int selectedMove = 0;
		
		for(int i = 0; i < queue.size(); i++){
			
			State tempState = queue.get(i);
			
			for(int j = 0; j < tempState.getMoves().size(); j++){
				
				if(tempCost > (tempState.getCostFromRoot() + myBoard.calculateCost(tempState.getMoves().get(j)[1],tempState.getMoves().get(j)[3]))){
					tempCost = (tempState.getCostFromRoot() + myBoard.calculateCost(tempState.getMoves().get(j)[1],tempState.getMoves().get(j)[3]));
					selectedMove = j;
					selectedState = i;
				}
				
			}	
		}
		
		Integer[] returnArray = new Integer[5];
		returnArray[0] = queue.get(selectedState).getMoves().get(selectedMove)[0];
		returnArray[1] = queue.get(selectedState).getMoves().get(selectedMove)[1];
		returnArray[2] = queue.get(selectedState).getMoves().get(selectedMove)[2];
		returnArray[3] = queue.get(selectedState).getMoves().get(selectedMove)[3];
		returnArray[4] = selectedState;
		
		queue.get(selectedState).getMoves().remove(selectedMove);
		
		
		return returnArray;
		
	}
}