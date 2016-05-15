package chords.simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import chords.IChord;
import chords.SimulationChord;

public class ChordSimulation {

	private static int ADDRESS_BITS_M = 12;
	
	private static final int NUMBER_NODES_IN_SIM = 100;
	
	public static Map<Integer,IChord> SIMULATED_NETWORK;
	
	public void startSimulation(){
		System.out.println("Chord simulation starting..");
		simulateFirstNetwork();
		simulateSecondNetwork();
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
		SIMULATED_NETWORK.clear();
		ADDRESS_BITS_M = 12;
		
		int numAddresses = (int) Math.pow(2, ADDRESS_BITS_M);
		
		IChord firstNodeInNetwork = new SimulationChord(0, ADDRESS_BITS_M);
		connectNode(firstNodeInNetwork, null);
		
		// Initiating network
		System.out.println("Starting randomly distributing nodes with m = "+ADDRESS_BITS_M+".");
		int nextAddress = 0;
		for (int i = 0; i < NUMBER_NODES_IN_SIM; i++){
			do {
				nextAddress = new Random().nextInt(numAddresses);
			}while (SIMULATED_NETWORK.containsKey(nextAddress));
			
			IChord newNode = new SimulationChord(nextAddress, ADDRESS_BITS_M);
			connectNode(newNode, firstNodeInNetwork);
			System.out.println("Node with Id "+newNode.getId()+" connected.");
		}
		
		System.out.println("Network initialized. Sending messages now..");
		
		// Sending messages
		
		double numMessages = 0;
		double allHops = 0; 
		double minHops = -1;
		double maxHops = -1;
		
		for(IChord c : SIMULATED_NETWORK.values()){
			SimulationChord msgFrom = (SimulationChord) c;
			for (IChord d: SIMULATED_NETWORK.values()){
				if (c == d) break;
				SimulationChord msgTo = (SimulationChord) d;
				int hops = msgFrom.sendMessageRecordHops("Couting hops", msgTo.getId());
				allHops += hops;
				numMessages++;
				
				if (minHops == -1){
					minHops = hops;
				}else if (hops < minHops){
					minHops = hops;
				}
				
				if (maxHops == -1){
					maxHops = hops;
				}else if (hops > maxHops){
					maxHops = hops;
				}	
			}
		}
		System.out.println("Messages sent:");
		System.out.println("MinHops: "+minHops);
		System.out.println("MaxHops: "+maxHops);
		double average = allHops/numMessages;
		System.out.println("Average Hops: "+average);
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
