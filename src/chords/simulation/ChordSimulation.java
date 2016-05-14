package chords.simulation;

import java.util.HashMap;
import java.util.Map;

import chords.IChord;
import chords.SimulationChord;

public class ChordSimulation {

	private static int ADDRESS_BITS_M = 12;
	
	private static final int NUMBER_NODES_IN_SIM = 100;
	
	public static Map<Integer,IChord> SIMULATED_NETWORK;
	
	public void startSimulation(){
		System.out.println("Chord simulation starting..");
		simulateFirstNetwork();
	}
	
	private void simulateFirstNetwork(){
		ADDRESS_BITS_M = 5;
		SIMULATED_NETWORK = new HashMap<>();
		SimulationChord n1 = new SimulationChord(1, ADDRESS_BITS_M);
		SimulationChord n2 = new SimulationChord(3, ADDRESS_BITS_M);
		SimulationChord n3 = new SimulationChord(7, ADDRESS_BITS_M);
		SimulationChord n4 = new SimulationChord(8, ADDRESS_BITS_M);
		SimulationChord n5 = new SimulationChord(12, ADDRESS_BITS_M);
		SimulationChord n6 = new SimulationChord(15, ADDRESS_BITS_M);
		SimulationChord n7 = new SimulationChord(19, ADDRESS_BITS_M);
		SimulationChord n8 = new SimulationChord(25, ADDRESS_BITS_M);
		SimulationChord n9 = new SimulationChord(27, ADDRESS_BITS_M);
		/*SIMULATED_NETWORK.put(n1.getId(), n1);
		SIMULATED_NETWORK.put(n2.getId(), n2);
		SIMULATED_NETWORK.put(n3.getId(), n3);
		SIMULATED_NETWORK.put(n4.getId(), n4);
		SIMULATED_NETWORK.put(n5.getId(), n5);
		SIMULATED_NETWORK.put(n6.getId(), n6);
		SIMULATED_NETWORK.put(n7.getId(), n7);
		SIMULATED_NETWORK.put(n8.getId(), n8);
		SIMULATED_NETWORK.put(n9.getId(), n9);*/
		connectNode(n1, null);
		connectNode(n7, n1);
		connectNode(n3, n1);
		connectNode(n9, n1);
		connectNode(n2, n1);
		connectNode(n6, n1);
		connectNode(n4, n1);
		connectNode(n5, n1);
		connectNode(n8, n1);
		
		System.out.println("All nodes connected to network. Printing info..");
		for (IChord n: SIMULATED_NETWORK.values()){
			System.out.println(n.getInfoText());
			System.out.println("\n\n");
		}
		
		System.out.println("Sending message from 25 to 8");
		n8.sendMessage("Hello number 8", 8);
	}
	
	private void connectNode(IChord node, IChord knownNode){
		SIMULATED_NETWORK.put(node.getId(), node);
		node.connect(knownNode);
	}
	
	private void simulateSecondNetwork(){
		
	}
	
	public static IChord simulatePing(Integer toId){
		if (SIMULATED_NETWORK.containsKey(toId)){
			return SIMULATED_NETWORK.get(toId);
		}else{
			return null;
		}
	}
	
	public static void simulateBroadcastAnnouncement(IChord sender){
		System.out.println("Announcement broadcast sent.");
		for (IChord node : SIMULATED_NETWORK.values()){
			node.announceNewNode(sender);
		}
	}
	
}
