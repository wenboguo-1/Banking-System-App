package module;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class HistoryDateSorting {

    public static ArrayList<History>list(ArrayList<History> histories){
        ArrayList<History> list = histories;
        Collections.sort(list, new Comparator<History>() {
            @Override
            public int compare(History o1, History o2) {
                return (int) (o2.getDateTime() - o1.getDateTime());
            }
        });
        return list;
    }
}
