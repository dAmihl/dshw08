package chords;

public class FingerTable {

	private IChord[] table;
	private int size;
	private IChord chord;
	
	private IChord successor;
	private IChord predecessor;
	
	public FingerTable(int m, IChord node){
		this.size = m;
		this.table = new IChord[this.size];
		this.chord = node;
	}
	
	public IChord getEntry(int k){
		if (k >= this.size){
			return null;
		}else{
			return this.table[k];
		}
	}
	
	public IChord getSuccessor(){
		return this.successor;
	}
	
	public IChord getPredecessor(){
		return this.predecessor;
	}
	
	
	
	public void sendMessage(String message, Integer toKey){
		
	}
	
	/**
	 * Reference every entry to the node itself
	 */
	public void initEmptyRing(){
		this.successor = this.chord;
		this.predecessor = this.chord;
		for (int i = 0; i < this.size; i++){
			this.table[i] = this.chord;
		}
	}
	
	@Override
	public String toString() {
		String out = "";
		out += "| key | node |\n";
		for (int i = 0; i < this.size; i++){
			out += "| "+(i+1)+" | "+this.table[i]+" |\n";
		}
		return out;
	}
	
	public IChord getNextChordToId(Integer Id){
		for (int i = 0; i < this.size; i++){
			if (this.table[i].getId() > Id){
				return this.table[i];
			}
		}
		return this.chord;
	}
	
	public void setKey(Integer k, IChord node){
		this.table[k] = node;
	}
	
}
