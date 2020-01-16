package test;

public class Test6 {
    public static void main(String[] args) {
        String[] s = {"q","w","e","r","t","y"};
        String ss = "";
        for(int i =0;i<s.length;i++){
            ss += s[i]+"/";
        }
        System.out.println(ss);
    }
}
