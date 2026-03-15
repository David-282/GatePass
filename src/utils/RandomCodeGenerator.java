package utils;

import java.util.Random;

public class RandomCodeGenerator {


    public static String codeGenerator (){

    String digits = "0123456789";
    String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
    String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    String pool = digits + lowerCaseLetters + upperCaseLetters;

    Random random = new Random();
    StringBuilder code = new StringBuilder();


    for(int count = 0; count< 10;count ++) {
        code.append(pool.charAt(random.nextInt(pool.length())));
    }
        return code.toString();
}


}
