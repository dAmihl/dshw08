package chords;

public class FingerTable {

	private Integer[] table;
	private int size;
	
	private Integer successor;
	private Integer predecessor;
	
	public FingerTable(int m){
		this.size = m;
		this.table = new Integer[this.size];
		fillTable();
	}
	
	public Integer getEntry(int k){
		if (k >= this.size){
			return null;
		}else{
			return this.table[k];
		}
	}
	
	public Integer getSuccessor(){
		return this.successor;
	}
	
	public Integer getPredecessor(){
		return this.predecessor;
	}
	
	private void fillTable(){
		
	}
	
	public void sendMessage(String message, Integer toKey){
		
	}
	
}
