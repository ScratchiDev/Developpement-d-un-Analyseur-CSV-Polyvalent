import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';

    public static List<List<String>> parseCSV(String input) {
        List<List<String>> result = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        List<String> row = new ArrayList<>();
        boolean inQuotes = false;

        for (char ch : input.toCharArray()) {
            if (inQuotes) {
                if (ch == DEFAULT_QUOTE) {
                    inQuotes = !inQuotes;
                } else {
                    field.append(ch);
                }
            } else {
                switch (ch) {
                    case DEFAULT_QUOTE:
                        inQuotes = !inQuotes;
                        break;
                    case DEFAULT_SEPARATOR:
                        row.add(field.toString());
                        field = new StringBuilder();
                        break;
                    case '\n':
                        row.add(field.toString());
                        result.add(new ArrayList<>(row));
                        row.clear();
                        field = new StringBuilder();
                        break;
                    default:
                        field.append(ch);
                        break;
                }
            }
        }

        if (!field.toString().isEmpty()) {
            row.add(field.toString());
        }
        if (!row.isEmpty()) {
            result.add(row);
        }

        return result;
    }

    public static void printCSV(List<List<String>> data) {
        System.out.print("[");
        for (int i = 0; i < data.size(); i++) {
            List<String> row = data.get(i);
            System.out.print("[");
            for (int j = 0; j < row.size(); j++) {
                System.out.print("'" + row.get(j) + "'");
                if (j < row.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.print("]");
            if (i < data.size() - 1) {
                System.out.println(", ");
            }
        }
        System.out.println("]");
    }
    public static void printCSVFormatted(List<List<String>> data) {
        data.forEach(row -> {
            List<String> formattedRow = new ArrayList<>();
            row.forEach(field -> formattedRow.add(field.replace("\n", "\\n")));
            System.out.println(formattedRow);
        });
    }
}
