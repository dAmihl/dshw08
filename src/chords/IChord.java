package chords;
/**
 * Methods offered by a chord.
 * @author dAmihl
 *
 */
public interface IChord {
	public IChord getNextChordToId(Integer Id);
	public Integer getId();
}
