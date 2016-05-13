package chords;



public class Chord implements IChord {

	private Integer ID;
	private FingerTable fingerTable;
	
	public Chord(Integer id, Integer m){
		this.ID = id;
		this.fingerTable = new FingerTable(m);
	}
	

	
	public void connect(Chord knownChord){
		
	}
	
	public FingerTable getFingerTable(){
		return this.fingerTable;
	}
	
	public Integer getID(){
		return this.ID;
	}
	
}
