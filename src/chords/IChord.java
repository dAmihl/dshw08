package chords;
/**
 * Methods offered by a chord.
 * @author dAmihl
 *
 */
public interface IChord {
	public IChord getNextChordToId(Integer Id);
	public Integer getId();
	public void connect(IChord knownChord);
	public void announceNewNode(IChord node);
	public IChord getPredecessor();
	public IChord getSuccessor();
}
