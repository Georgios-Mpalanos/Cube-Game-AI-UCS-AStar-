class Cube{
	private int number;
	private int[] cords; // {x,y}
	private boolean inExtra = false; //True if the cube is in any "extra" space.
	private int k;
	
	public Cube(int number, int x, int y, int k){
		this.number = number;
		this.cords = new int[2];
		this.cords[0] = x+1;
		this.cords[1] = y+1;
		this.k = k;
		
		if(this.cords[0] > k){
			inExtra = true;
		}else{
			inExtra = false;
		}
		
	}
	
	//Setters-Getters:
	
	public void setInExtra(){
		if(this.cords[0] > k){
			inExtra = true;
		}else{
			inExtra = false;
		}
	}
	
	public boolean getInExtra(){
		return inExtra;
	}
	
	public int getNumber(){
		return this.number;
	}
	
	public int getX(){
		return this.cords[0];
	}
	
	public int getY(){
		return this.cords[1];
	}
	
	public void setX(int x){
		this.cords[0] = x;
	}
	
	public void setY(int y){
		this.cords[1] = y;
	}
	
	
	/* 
		Two cubes are considered equal if they
		have the same number and the same coordinates.
	*/
	public boolean isEqual(Cube tempCube){
		if(tempCube == null){
			return false;
		}
		
		if(tempCube.number != this.number){
			return false;
		}
		
		if(tempCube.getX() != this.getX()){
			return false;
		}
		
		if(tempCube.getY() != this.getY()){
			return false;
		}
		
		return true;
		
	}
	
	/*
		Calculates cube's index in the problemState 
		based on their coordinates(x,y).
		
		If the cube is located on the first row(table)
		its index is calculated based on the following 
		formula : cubeIndex = x - 1
		
		If the cube is located on the second or third row 
		its index is calculated based on the following 
		formula : cubeIndex = (y-1)*k + (x-1) + (4*k - k) , 4*k = L.
	
	*/
	
	public int getIndex(){
		if(getY() == 1){
			return (getX()-1);
		}
		
		return ((getY()-1)*k+(getX()-1)+(4*k - k));
	}
	
}