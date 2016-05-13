package application;

import chords.simulation.ChordSimulation;

public class Application {

	public static void main(String[] args) {
		System.out.println("Chord application started.");
		ChordSimulation sim = new ChordSimulation();
		sim.startSimulation();
	}
	
}
