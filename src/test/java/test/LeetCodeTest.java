package test;

import java.util.HashMap;

public class LeetCodeTest {

    public static void main(String[] args) {
        //0.(012)
        System.out.println(fractionToDecimal(4, 333));
    }

    public static String fractionToDecimal(int numerator, int denominator) {
        StringBuilder sb = new StringBuilder();
        HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
        char[] num = String.valueOf(numerator).toCharArray();
        int base = 0;
        int temp = num[0]-'0';

        temp = base/10;
        if(temp == 0) return sb.toString();
        sb.append(".");
        int count = 0;
        boolean flag = false;
        while(temp != 0||flag){
            if(temp / denominator != 0){
                if(map.containsKey(temp)){
                    char[] str = sb.toString().toCharArray();
                    for(int i = map.get(temp);i < str.length;i++){
                        sb.append("(");
                        sb.append(str[i]);
                        sb.append(")");
                        flag = true;
                        break;
                    }
                    if (flag) break;
                }
                sb.append(temp / denominator);
                map.put(temp,sb.length()-1);
                temp = temp % denominator;
                continue;

            }
            int dot = 0;
            temp = temp * 10;
            if (temp /denominator == 0) {
                if(map.containsKey(temp)){
                    char[] str = sb.toString().toCharArray();

                    for(int i = map.get(temp);i < str.length;i++){
                        if (str[i] == '.'){
                            dot = i;
                        }

                        sb.append(str[i]);
                        sb.append(")");
                        flag = true;
                        break;
                    }
                    if (flag) break;
                }
                sb.append(0);
                map.put(temp,sb.length()-1);
            }
        }
        return sb.toString();
    }
}
