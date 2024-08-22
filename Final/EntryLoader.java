import java.io.File;
import java.io.FileNotFoundException;
import java.util.stream.Collectors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class EntryLoader {

    private static Entry toEntry(String... line) {
        int age, trestBPS, chol, maxRate, majorVessels;
        float oldPeak;
        boolean fbs, exang, target;
        Entry.RestECG restECG;
        Entry.Slope slope;
        Entry.Thal thal;
        Entry.Sex sex;
        Entry.ChestPain chestPain;

        age = Integer.parseInt(line[0]);
        sex = Integer.parseInt(line[1]) == 0 ? Entry.Sex.FEMALE : Entry.Sex.MALE;
        chestPain = switch (line[2]) {
            case "0" -> Entry.ChestPain.ASYMPTOMATIC;
            case "1" -> Entry.ChestPain.ATYPICAL_ANGINA;
            case "2" -> Entry.ChestPain.NON_ANGINAL;
            default -> Entry.ChestPain.TYPICAL_ANGINA;
        };
        trestBPS = Integer.parseInt(line[3]);
        chol = Integer.parseInt(line[4]);
        fbs = line[5].equals("1");
        restECG = switch (line[6]) {
            case "0" -> Entry.RestECG.LEFT_VENTRICULAR_HYPERTROPHY;
            case "1" -> Entry.RestECG.NORMAL;
            default -> Entry.RestECG.ST_T_ABNORMALITY;
        };
        maxRate = Integer.parseInt(line[7]);
        exang = line[8].equals("1");
        oldPeak = Float.parseFloat(line[9]);
        slope = switch (line[10]) {
            case "0" -> Entry.Slope.DOWNSLOPING;
            case "1" -> Entry.Slope.FLAT;
            default -> Entry.Slope.UPSLOPING;
        };
        majorVessels = Integer.parseInt(line[11]);
        thal = switch (line[12]) {
            case "1" -> Entry.Thal.FIXED_DEFECT;
            case "2" -> Entry.Thal.NORMAL_FLOW;
            default -> Entry.Thal.REVERSIBLE_DEFECT;
        };
        target = line[13].equals("0");

        return new Entry(
            age, sex, chestPain, trestBPS,
            chol, fbs, restECG, maxRate, exang,
            oldPeak, slope, majorVessels, thal, target);
    }
    
    public static List<Entry> loadFromFile(File data) {
        try {
            BufferedReader b = new BufferedReader(new FileReader(data));
            List<Entry> entries = b.lines()
            .skip(1)
            .map(line -> line.split(","))
            .map(EntryLoader::toEntry)
            .collect(Collectors.toList());

            try { b.close(); }
            catch (IOException ex) { throw new RuntimeException(ex); }

            return entries;
        } catch (FileNotFoundException ex)
        { throw new RuntimeException(ex); }
    }

    public static String[] getHeaders(File data) {
        try {
            BufferedReader b = new BufferedReader(new FileReader(data));
            String line = b.readLine();
            b.close();
            return line.split(",");
        } catch (IOException ex)
        { throw new RuntimeException(ex); }
    }
}
