package util;
import vo.StockInfo;
import vo.UserInterest;
public interface FileHandler {
    StockInfo[] getStockInfoFromFile(String filePath);
    UserInterest[] getUserInterestFromFile(String filePath);
    void setCloseMatrix2File(double[][] matrix);
    void setRecommend2File(double[][] recommend);
}
