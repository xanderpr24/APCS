import java.util.function.Function;
import java.util.function.ToIntFunction;

public class Enums {

    public interface Field {
        public String getLabel();
    }

    public enum NumField implements Field {
        AGE("Age (years)", Entry::age),
        T_REST_BPS("Rest BP", Entry::trestBPS),
        CHOL("Chol", Entry::chol),
        MAX_RATE("Max Heart Rate (BPS)", Entry::maxRate),
        // OLDPEAK("Oldpeak", Entry::oldPeak),
        VESSELS("Major Vessels", Entry::majorVessels);

        private String label;
        private ToIntFunction<Entry> property;
        
        NumField(String name, ToIntFunction<Entry> property) {
            this.label = name;
            this.property = property;
        }

        public String getLabel() { return this.label; }
        public ToIntFunction<Entry> getProperty() { return this.property; }
    }

    public enum CatField implements Field {
        SEX("Sex", Entry::sex),
        REST_ECG("Rest ECG", Entry::restECG),
        SLOPE("ST slope", Entry::slope),
        THAL("Thalassemia", Entry::thal);

        private String label;
        private Function<Entry, Entry.Attribute> property;
        
        CatField(String label, Function<Entry, Entry.Attribute> property) {
            this.label = label;
            this.property = property;
        }

        public String getLabel() { return this.label; }
        public Function<Entry, Entry.Attribute> getProperty() { return this.property; }
    }

    public enum Graph {
        EMPTY,
        BAR,
        LINE,
        PIE,
        TABLE
    }

}
