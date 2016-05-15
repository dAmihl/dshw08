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
			//this.fingerTable.getSuccessor().announceNewNode(this);
			//this.fingerTable.getPredecessor().announceNewNode(this);
			startNewNodeAnnouncementBroadcast();
			fillFingerTable();
		}
		System.out.println("Connected. FingerTable: ");
		System.out.println(this.fingerTable);
		
	}
	
	private void fillFingerTable(){
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
		if (node == this) return;
		
		if (this.getId() < node.getId() && this.fingerTable.getSuccessor().getId() > node.getId() ||
				this.getSuccessor() == this){
			this.fingerTable.setSuccessor(node);
		}else if (this.getId() > node.getId() && node.getId() > this.fingerTable.getPredecessor().getId() ||
				this.getPredecessor() == this){
			this.fingerTable.setPredecessor(node);
		}
		fillFingerTable();
			
	}



	@Override
	public IChord getPredecessor() {
		return this.fingerTable.getPredecessor();
	}



	@Override
	public IChord getSuccessor() {
		return this.fingerTable.getSuccessor();
	}
	
	private void startNewNodeAnnouncementBroadcast(){
		ChordSimulation.simulateBroadcastAnnouncement(this);
	}



	@Override
	public String getInfoText() {
		return this.toString() + "\n" + this.getFingerTable()+"\n";
	}



	@Override
	public IChord sendMessageToChord(String msg, Integer key) {
		if (key == this.getId()){
			System.out.println(this.toString()+" Received Message: "+msg);
			return null;
		}else{
			System.out.println(this.toString()+" Received Message to other chord. Responding nearest chord in my fingertable.");
			IChord nearestChord = this.fingerTable.getNearestSmallerChord(key);
			return nearestChord;
		}	
	}
	
	@Override
	public void sendMessage(String msg, Integer key){
		IChord nearestChord = this.fingerTable.getNearestSmallerChord(key);
		IChord nextChord = null;
		int numHops = 1;
		while ((nextChord = nearestChord.sendMessageToChord(msg, key)) != null){
			nearestChord = nextChord;
			System.out.println("Message not sent yet. Got next chord: "+nearestChord);
			numHops++;
		}
		System.out.println(this.toString()+" successfully sent message. NumHops: "+numHops);
	}
	
	public Integer sendMessageRecordHops(String msg, Integer key){
		IChord nearestChord = this.fingerTable.getNearestSmallerChord(key);
		IChord nextChord = null;
		int numHops = 1;
		while ((nextChord = nearestChord.sendMessageToChord(msg, key)) != null){
			nearestChord = nextChord;
			System.out.println("Message not sent yet. Got next chord: "+nearestChord);
			numHops++;
		}
		System.out.println(this.toString()+" successfully sent message. NumHops: "+numHops);
		return numHops;
	}
	
}
