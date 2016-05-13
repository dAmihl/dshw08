package chords.simulation;

import chords.Chord;

public class ChordSimulation {

	public static final int ADDRESS_BITS_M = 12;
	
	private static final int NUMBER_NODES_IN_SIM = 100;
	
	public void startSimulation(){
		System.out.println("Chord simulation starting..");
		createNetwork();
	}
	
	private void createNetwork(){
		for (int i = 0; i < NUMBER_NODES_IN_SIM; i++){
			Chord tmpChord = new Chord(i, ADDRESS_BITS_M);
		}
	}
	
}
