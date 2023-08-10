import java.util.ArrayList;

class PrioQueueA{ // For A-star.
	private ArrayList<State> queueA;
	private Board myBoard;
	
	public PrioQueueA(Board myBoard){
		this.myBoard = myBoard;
		this.queueA = new ArrayList<State>();
	}
	
	public ArrayList<State> getArray(){
		return queueA;
	} 
	
	/*
		It adds the given State to the priority queue.
	*/
	
	public void enqueue(State state){
		queueA.add(state);
	}
	
	
	/*
		The priority of the queue is based on the cost-value of each
		move that is currently in the queue.To be more specific the 
		queue contains a list of States and each State contains a list
		of moves that have been calculated earlier by the function "findMoves()".
		The cheapest move(in terms of it's cost from the root) will be selected.
		
		For every State in queue we check its list of moves(ArrayList moves) and 
		we keep the cheapest move.Then we remove the selected move and return the
		move and the index,in the queue, of the State that contains it.
		
		To calculate the cheapest move for the A-star algorithm we add the term
		"(total - tempState.getH())" which indicates how "close" we are to the final
		State. When it is equal to zero that means that we are at the final State.
	*/
	
	
	public Integer[] dequeue(){
		double tempCost = Double.MAX_VALUE;
		int selectedState = 0;
		int selectedMove = 0;
		int k = myBoard.getK();
		int total = 3*k;
		
		for(int i = 0; i < queueA.size(); i++){
			
			State tempState = queueA.get(i);
			
			for(int j = 0; j < tempState.getMoves().size(); j++){
				
				if(tempCost >  (tempState.getCostFromRoot() + myBoard.calculateCost(tempState.getMoves().get(j)[1],tempState.getMoves().get(j)[3]) + (total - tempState.getH()))){
					tempCost = (tempState.getCostFromRoot() + myBoard.calculateCost(tempState.getMoves().get(j)[1],tempState.getMoves().get(j)[3]) + (total - tempState.getH()));
					selectedMove = j;
					selectedState = i;
				}
				
			}	
		}
		
		Integer[] returnArray = new Integer[5];
		returnArray[0] = queueA.get(selectedState).getMoves().get(selectedMove)[0];
		returnArray[1] = queueA.get(selectedState).getMoves().get(selectedMove)[1];
		returnArray[2] = queueA.get(selectedState).getMoves().get(selectedMove)[2];
		returnArray[3] = queueA.get(selectedState).getMoves().get(selectedMove)[3];
		returnArray[4] = selectedState;
		
		queueA.get(selectedState).getMoves().remove(selectedMove);
		
		return returnArray;
		
	}	
}