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
	

	
	public void connect(SimulationChord knownChord){
		System.out.println("Connecting node to network using known chord "+knownChord);
		if (knownChord == null){
			this.fingerTable.initEmptyRing();
		}else{
			fillFingerTable(knownChord);
		}
		System.out.println("Connected. FingerTable: ");
		System.out.println(this.fingerTable);
		
	}
	
	private void fillFingerTable(SimulationChord knownChord){
		IChord node;
		int n = this.getId();
		for (int i = 0; i < BITS_M; i++){
			int k = (n + 2^(i))%(2^BITS_M);
			if ((node = ChordSimulation.simulatePing(k)) != null){
				this.fingerTable.setKey(k, node);
			}else{
				k = (k+1)%(2^BITS_M);
				while ((node = ChordSimulation.simulatePing(k)) == null){
					k = (k+1)%(2^BITS_M);
				}
			}
		}
	}
	
	public FingerTable getFingerTable(){
		return this.fingerTable;
	}
	
	



	@Override
	public SimulationChord getNextChordToId(Integer Id) {
		
		return this;
	}



	@Override
	public Integer getId() {
		return this.ID;
	}
	
}
