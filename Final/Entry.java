import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record Entry (
        int age,            Sex sex,            ChestPain chestPain,
        int trestBPS,       int chol,           boolean fbs,
        RestECG restECG,    int maxRate,        boolean exang,
        float oldPeak,      Slope slope,        int majorVessels,
        Thal thal,          boolean target) implements Comparable<Entry> {

    public String[] fields() {
        return new String[] {
            ""
        };
    }

    public static final Entry DUMMY = new Entry(0, Sex.MALE, ChestPain.ASYMPTOMATIC, 0, 0, false, RestECG.LEFT_VENTRICULAR_HYPERTROPHY, 0, false, 0, Slope.DOWNSLOPING, 0, Thal.FIXED_DEFECT, false);

    public interface Attribute
    { String getLabel(); }

    enum Sex implements Attribute {
        MALE("M"),
        FEMALE("F");

        private String label;

        Sex(String label)
        { this.label = label; }

        public String getLabel()
        { return this.label; }
    }
            
    enum ChestPain implements Attribute {
        ASYMPTOMATIC("ASYM"),
        ATYPICAL_ANGINA("AANG"),
        NON_ANGINAL("NANG"),
        TYPICAL_ANGINA("TANG");

        private String label;
        
        ChestPain(String label)
        { this.label = label; }

        public String getLabel()
        { return this.label; }
    }

    enum RestECG implements Attribute {
        LEFT_VENTRICULAR_HYPERTROPHY("LVH"),
        NORMAL("NRM"),
        ST_T_ABNORMALITY("STT");

        private String label;

        RestECG(String label)
        { this.label = label; }

        public String getLabel()
        { return this.label; }
    }

    enum Slope implements Attribute {
        DOWNSLOPING("DOWN"),
        FLAT("FLAT"),
        UPSLOPING("UP");

        private String label;

        Slope(String label)
        { this.label = label; }

        public String getLabel()
        { return this.label; }
    }

    enum Thal implements Attribute {
        NULL("NULL"),               // Previously dropped from dataset
        FIXED_DEFECT("FIXD"),       // No blood flow in some part of the heart
        NORMAL_FLOW("NRML"),
        REVERSIBLE_DEFECT("RVRS");   // A blood flow is observed but is not normal

        private String label;

        Thal(String label)
        { this.label = label; }

        public String getLabel()
        { return this.label; }
    }

    @Override
    public int compareTo(Entry other) {
        return EntryComparator.comparing(Entry::age).compare(this, other);
    }

    public String[] toArray() {
        return new String[] {
            Integer.toString(this.age),
            this.sex.toString(),
            this.chestPain.toString(),
            Integer.toString(this.trestBPS),
            Integer.toString(this.chol),
            this.fbs ? "> 120 mg>dl" : "<= 120 mg/dl",
            this.restECG.toString(),
            Integer.toString(this.maxRate),
            this.exang ? "Exercise induced angina" : "No angina",
            Float.toString(this.oldPeak),
            this.slope.toString(),
            Integer.toString(this.majorVessels),
            this.thal.toString(),
            this.target ? "No heart disease" : "Heart disease"
        };
    }

    public String[] toArray(int... toInclude) {
        Map<Integer, String> fields = new HashMap<>();
        fields.put(0, Integer.toString(this.age));
        fields.put(1, this.sex.toString());
        fields.put(2, this.chestPain.toString());
        fields.put(3, Integer.toString(this.trestBPS));
        fields.put(4, Integer.toString(this.chol));
        fields.put(5, this.fbs ? "> 120 mg>dl" : "<= 120 mg/dl");
        fields.put(6, this.restECG.toString());
        fields.put(7, Integer.toString(this.maxRate));
        fields.put(8, this.exang ? "Exercise induced angina" : "No angina");
        fields.put(9, Float.toString(this.oldPeak));
        fields.put(10, this.slope.toString());
        fields.put(11, Integer.toString(this.majorVessels));
        fields.put(12, this.thal.toString());
        fields.put(13, this.target ? "No heart disease" : "Heart disease");
        return IntStream.of(toInclude).mapToObj(fields::get).collect(Collectors.toList()).toArray(new String[1]);
    }

}