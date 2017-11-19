package util;

import javafx.util.Pair;
import vo.StockInfo;
import vo.UserInterest;

import java.io.*;

public class FileHandleImpl implements FileHandler{
    public StockInfo[] getStockInfoFromFile(String filePath) {
        StockInfo[] dataStock = new StockInfo[61];

        try {

            BufferedReader myBuff = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"UTF-8"));
            String s = null;
            for(int a = 0;a<61;a++){
                dataStock[a] = new StockInfo();
            }
            int i = 0;
            while((s = myBuff.readLine())!= null){
                String tempS[] = s.split("\t");
                for(int d = 0; d<tempS.length;d++){
                    if(tempS[d].equals(" ")){
                        dataStock[i].setInformation(d,"nothing");
                    }else{
                        dataStock[i].setInformation(d,tempS[d]);
                    }
                    System.out.println(dataStock[i].getInformation(d)+"\t");
                }
                System.out.println();
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataStock;
    }

    public UserInterest[] getUserInterestFromFile(String filePath) {
        UserInterest[] UI = new UserInterest[500];
        try {
            FileReader myfile = new FileReader(new File(filePath));
            BufferedReader myBuff = new BufferedReader(myfile);
            String s = null;
            for(int a =0 ;a<500 ;a++){
                UI[a] = new UserInterest();
            }
            int count = 0;
            while(null!=(s=myBuff.readLine())){
                String temp[] = s.split("");
                for(int n = 0; n<60 ;n++){
                    int value = Integer.valueOf(temp[n]).intValue();
                    UI[count].setInterest(value,n);
                }
                count++;
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return UI;
    }

    public void setCloseMatrix2File(double[][] matrix) {
        byte[]buff = new byte []{};
        String t = "\t";
        String n = "\n";
        try {
            OutputStream os = new FileOutputStream("D:\\Close.txt");
            for(int a =0 ; a<60 ;a++){
                for(int b =0; b<60; b++){
                    buff = Double.toString(matrix[a][b]).getBytes();
                    os.write(buff,0,buff.length);
                    os.write(t.getBytes());
                }
                os.write(n.getBytes());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void setRecommend2File(double[][] recommend) {
        Pair<Integer,Double>[][]pairs = new Pair[500][60];
        for(int n = 0 ; n<500 ; n++){
            for(int m = 0 ;m <60 ; m++){
                pairs[n][m] = new Pair<Integer, Double>(m,recommend[n][m]);
            }
        }
        for(int a =0 ; a<500;a++ ){
            for(int b = 0; b<59 ; b++){
                for(int c = 0;c<59-b;c++){
                    if((pairs[a][c].getValue()<pairs[a][c+1].getValue())){
                        Pair<Integer,Double> temp = pairs[a][c];
                        pairs[a][c] = pairs[a][c+1];
                        pairs[a][c+1] = temp;
                    }
                }
            }
        }
        byte[] buff =new byte[]{};
        String t = "\t";
        String n = "\n";
        String sp = " ";
        try( OutputStream os = new FileOutputStream("D:\\recommender.txt")){
            for(int count = 0; count<500 ;count++){
                for(int m = 0 ;m <20; m++){
                    buff=pairs[count][m].getKey().toString().getBytes();
                    os.write(buff,0,buff.length);
                    os.write(sp.getBytes());
                    buff=pairs[count][m].getValue().toString().getBytes();
                    os.write(buff,0,buff.length);
                    os.write(t.getBytes());
                }
                os.write(n.getBytes());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
