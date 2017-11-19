package Recommender;

import javafx.util.Pair;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import vo.StockInfo;
import vo.UserInterest;

import java.util.*;

public class RecommenderImpl implements Recommender {
    public double[][] calculateMatrix(StockInfo[] stocks) {
        List<String> inputList = new LinkedList<String>();
        Result result;
        for(int i=0;i<60;i++){
            result = ToAnalysis.parse(stocks[i+1].getInformation(5));
            List<Term> terms = result.getTerms();
            for(int j = 0; j<terms.size();j++){
                inputList.add(terms.get(j).getName());
            }
        }
        Set<String> set = new HashSet<String>();
        set.addAll(inputList);

        int count = set.size();
        int c = inputList.size();
        int i = 0;
        Pair<String,Double> pairs[] = new Pair[count];
        Iterator setIt = set.iterator();
        Iterator inputIt = inputList.iterator();
        while(setIt.hasNext()){
            String temp = setIt.next().toString();
            double elementCount = 1;
            while(inputIt.hasNext()){
                if(temp.equals(inputIt.next().toString())){
                    elementCount++;
                }
            }
            pairs[i] = new Pair<String, Double>(temp,elementCount);
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
        String keyWords[] = new String[20];
        for(int n = 0;n<20;n++){
            keyWords[n] = pairs[n].getKey();
        }

        int key[][] = new int[60][20];

        for(int n = 0; n<60;n++){

            Result resultList = ToAnalysis.parse(stocks[n+1].getInformation(5));
            List<Term> term = resultList.getTerms();
            for(int m = 0; m<20 ;m++){
                int elementCount = 0;
                for(int p = 0; p<term.size();p++){
                    if(keyWords[m].equals(term.get(p).getName())){
                        elementCount++;
                    }
                }
                key[n][m]=elementCount;
            }
        }
        double similarity[][] = new double[60][60];
        for(int n = 0; n<60;n++){
            for(int m = 0; m<60;m++){
                double denominator = 0;
                double numerator1 = 0;
                double numerator2 = 0;
                for(int p =0; p<20;p++){
                    denominator += key[n][p]*key[m][p];
                    numerator1 += key[n][p]*key[n][p];
                    numerator2 += key[m][p]*key[m][p];
                }
                if(numerator1==0||numerator2==0){
                    similarity[n][m]=0;
                }
                else{
                    similarity[n][m]=denominator/(Math.sqrt(numerator1)*Math.sqrt(numerator2));
                }

            }
        }

        return similarity;
    }

    public double[][] recommend(double[][] matrix, UserInterest[] userInterest) {
        double[][] recommend = new double[500][60];
        for(int n = 0 ; n<500 ; n++){
            for(int m = 0; m<60; m++){
                double rec = 0;
                for(int p = 0; p<60 ; p++){
                    rec += userInterest[n].getInterest(p)*matrix[m][p];
                }
                if(userInterest[n].getInterest(m)==1){
                    recommend[n][m] = 1;
                }
                else{
                    recommend[n][m] = rec;
                }
            }
        }
        return recommend;
    }
}
