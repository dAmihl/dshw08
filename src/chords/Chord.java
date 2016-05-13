package chords;



public class Chord implements IChord {

	private Integer ID;
	
	
	public Chord(Integer id){
		this.ID = id;
	}
	
	public void connect(){
		
	}
	
	public Integer getID(){
		return this.ID;
	}
	
}
