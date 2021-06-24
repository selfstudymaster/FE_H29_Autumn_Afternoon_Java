package main;

// 基本情報 H29 秋季 午後 Java
// [プログラム 1]
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/*public */ class TableSorter {
    private Map<String, Comparator<String>> orderMap = new HashMap<String, Comparator<String>>(); // private Map</* (a) */> orderMap = new HashMap</* (a) */>();

    public void putSortOrder(String key, Comparator<String> order) {
        orderMap.put(key, order);
    }

    public void sort(String[][] table, final OrderBy... orderBys) {
        Arrays.sort(table, new Comparator<String[]>() {
            public int compare(String[] s1, String[] s2) {
                for (OrderBy orderBy : orderBys) {
                    int order = orderMap.get(orderBy.key).compare(s1[orderBy.col], s2[orderBy.col]); // int order = orderMap.get(orderBy.key).compare(s1/* (c) */, s2/* (c) */);
                    if (order != 0) {
                        return orderBy.isReversed ? -order : order;
                    }
                }
                return 0;
            }
        });
    }

    public static class OrderBy {
        final String key;
        final int col;
        final boolean isReversed;

        public OrderBy (String key, int col) {
            this(key, col, false);
        }

        public OrderBy(String key, int col, boolean isReversed) {
            this.key = key;
            this.col = col;
            this.isReversed = isReversed;
        }
    }
}



// [プログラム 2]
// import java.util.Comparator;

/*public*/ class TableSorterTester {
    public static void main(String... args) {
        String[][] data = new String[][] {
                {"apple", "3", "1,000"},
                {"cherry", "1", "1,000"},
                {"banana", "1", "300"},
                {"banana", "2", "2,000"},
                {"apple", "2", "300"},
        };
        TableSorter sorter = new TableSorter();
        sorter.putSortOrder("lex", new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        sorter.putSortOrder("num", new Comparator<String>() {
            public int compare(String o1, String o2) {
                return new Integer(o1).compareTo(new Integer(o2));
            }
        });
        // sorter.sort(data, new TableSorter.OrderBy("lex", 0), new TableSorter.OrderBy("num", 1, true)); // a
        // sorter.sort(data, new TableSorter.OrderBy("lex", 2), new TableSorter.OrderBy("lex", 0, true)); // 設問2
        sorter.putSortOrder("numC", new Comparator<String>() { // 設問3
            public int compare(String s1, String  s2) {
                return new Integer(s1.replace(",", "")).compareTo(new Integer(s2.replace(",", "")));
            }
        });
        sorter.sort(data, new TableSorter.OrderBy("numC", 2), new TableSorter.OrderBy("lex", 0)); // 設問3
        for (String[] row : data) {
            for (String col : row) {
                System.out.printf("%s ", col);
            }
            System.out.println();
        }
    }
}
// TableSorterTester 実行結果
// apple 3 1,000
// apple 2 300
// banana 2 2,000
// banana 1 300
// cherry 1 1,000


//public class Main {
//}
