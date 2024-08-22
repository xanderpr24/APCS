import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {
    private App app;
    
    public Utils(App app)
    { this.app = app; }

    
    public String[] getProperty(Function<Entry, Entry.Attribute> property) {
        return app.getData().stream()
                    .limit(app.getDataLimit())
                    .map(e -> property.apply(e).getLabel())
                    .collect(Collectors.toList())
                    .toArray(new String[1]);
    }

    public int[] getProperty(ToIntFunction<Entry> property) {
        return app.getData().stream()
                    .limit(app.getDataLimit())
                    .mapToInt(property)
                    .toArray();
    }

    public static String[] toStringArray(int[] arr) { 
        return IntStream.of(arr)
                        .mapToObj(i -> Integer.toString(i))
                        .collect(Collectors.toList())
                        .toArray(new String[1]);
    }

    public List<Entry> limit(int limit) {
        return app.getData().stream().limit(limit).collect(Collectors.toList());
    }
}
