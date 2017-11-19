package Recommender;
import vo.StockInfo;
import vo.UserInterest;
public interface Recommender {
    double[][] calculateMatrix(StockInfo[] stocks);
    double[][] recommend(double[][] matrix, UserInterest[] userInterest);
}
