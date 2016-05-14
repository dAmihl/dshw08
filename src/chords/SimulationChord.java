package chords;

import chords.simulation.ChordSimulation;

public class SimulationChord implements IChord {

	private Integer ID;
	private FingerTable fingerTable;
	private Integer BITS_M;
	
	public SimulationChord(Integer id, Integer m){
		this.ID = id;
		this.fingerTable = new FingerTable(m, this);
		this.BITS_M = m;
	}
	

	
	public void connect(IChord knownChord){
		System.out.println("Connecting node "+this+" to network using known chord "+knownChord);
		if (knownChord == null){
			this.fingerTable.initEmptyRing();
		}else{
			IChord nextChord = knownChord.getNextChordToId(this.getId());
			IChord oldPredecessor = nextChord.getPredecessor();
			this.fingerTable.setPredecessor(oldPredecessor);
			this.fingerTable.setSuccessor(nextChord);
			this.fingerTable.getSuccessor().announceNewNode(this);
			this.fingerTable.getPredecessor().announceNewNode(this);
			fillFingerTable(knownChord);
		}
		System.out.println("Connected. FingerTable: ");
		System.out.println(this.fingerTable);
		
	}
	
	private void fillFingerTable(IChord knownChord){
		IChord node;
		int n = this.getId();
		for (int i = 0; i < BITS_M; i++){
			int k = (int) ((n + Math.pow(2, i))%Math.pow(2,BITS_M));

			while ((node = ChordSimulation.simulatePing(k)) == null){
				k = (int) ((k+1)%Math.pow(2,BITS_M));
			}
			this.fingerTable.setKey(i, node);
		}
		
	}
	
	public FingerTable getFingerTable(){
		return this.fingerTable;
	}
	
	



	@Override
	public IChord getNextChordToId(Integer Id) {	
		return this.fingerTable.getNextChordToId(Id);
	}



	@Override
	public Integer getId() {
		return this.ID;
	}
	
	@Override
	public String toString() {
		return "Node("+this.ID+")";
	}



	@Override
	public void announceNewNode(IChord node) {
		if (this.getId() < node.getId() && this.fingerTable.getSuccessor().getId() > node.getId()){
			this.fingerTable.setSuccessor(node);
		}else if (this.getId() > node.getId() && node.getId() > this.fingerTable.getPredecessor().getId()){
			this.fingerTable.setPredecessor(node);
		}
			
	}



	@Override
	public IChord getPredecessor() {
		return this.fingerTable.getPredecessor();
	}



	@Override
	public IChord getSuccessor() {
		return this.fingerTable.getSuccessor();
	}
	
}
