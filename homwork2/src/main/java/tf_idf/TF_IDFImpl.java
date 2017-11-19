package tf_idf;

import javafx.util.Pair;
import org.ansj.splitWord.analysis.ToAnalysis;
import vo.StockInfo;

import javax.xml.transform.Result;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TF_IDFImpl implements TF_IDF {
    public Pair<String, Double>[] getResult(List<String> words, StockInfo[] stockInfos) {
        Set<String> set = new HashSet();
        set.addAll(words);
        int count = set.size();
        int all = words.size();
        Pair<String,Double> pairs[] = new Pair[count];
        Iterator setIt = set.iterator();
        Iterator wordIt = words.iterator();
        int i = 0;
        while(setIt.hasNext()){
            String temp = setIt.next().toString();
            double elementCount = 1.0;
            while(wordIt.hasNext()){
                if(temp.equals(wordIt.next().toString())){
                    elementCount++;
                    System.out.println(temp);
                }
            }
            pairs[i] = new Pair<String, Double>(temp,elementCount/all);
            i++;
        }


        for(int a = 0; a<pairs.length - 1; a++){
            for(int b = 0;b<pairs.length-1-a;b++){
                if((pairs[b].getValue()<pairs[b+1].getValue())){
                    Pair<String,Double> temp = pairs[b];
                    pairs[b] = pairs[b+1];
                    pairs[b+1] = temp;
                }
            }
        }

        return pairs;
    }
}
