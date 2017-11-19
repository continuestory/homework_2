package vo;

public class UserInterest {
    private int Interest[];
    public UserInterest(){
        Interest = new int[60];
    }

    public int getInterest(int index) {
        return Interest[index];
    }
    public void setInterest(int value ,int index){
        Interest[index] = value;
    }

}
