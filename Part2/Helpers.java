import java.util.Random;

public class Helpers { 
    // Returns a random string of a specified size
    public static String randomString(int size) {
        Random random = new Random();
        String chars = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm0123456789";
        StringBuilder newStr = new StringBuilder();
        for(int i = 0; i < size; i++) {
            int position = random.nextInt(chars.length());
            newStr.append(chars.charAt(position));
        }
        return newStr.toString();
    }
}
