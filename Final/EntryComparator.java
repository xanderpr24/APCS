import java.util.Comparator;
import java.util.function.ToIntFunction;

public class EntryComparator implements Comparator<Entry> {

    private ToIntFunction<Entry> mapper;

    public static EntryComparator comparing(ToIntFunction<Entry> mapper)
    { return new EntryComparator(mapper); }

    @Override
    public int compare(Entry e1, Entry e2)
    { return Integer.compare(mapper.applyAsInt(e1), mapper.applyAsInt(e2)); }

    private EntryComparator(ToIntFunction<Entry> mapper)
    { this.mapper = mapper; }

}
