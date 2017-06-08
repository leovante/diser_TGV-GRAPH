import com.Kruskal;
import com.Parsing;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Parsing pars = new Parsing();
        pars.mainOne();
        Kruskal krusk = new Kruskal();
        krusk.mainTwo();
//        com.HabrGraph.mainTree();

    }
}