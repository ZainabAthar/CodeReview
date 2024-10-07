//TASK4
//package twitter;
//
//import java.time.Instant;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class Extract {
//
//    public static Timespan getTimespan(List<Tweet> tweets) {
//        if (tweets.isEmpty()) {
//            throw new IllegalArgumentException("The list of tweets cannot be empty");
//        }
//
//        Instant start = tweets.get(0).getTimestamp();
//        Instant end = start;
//
//        for (Tweet tweet : tweets) {
//            Instant timestamp = tweet.getTimestamp();
//            start = timestamp.isBefore(start) ? timestamp : start;
//            end = timestamp.isAfter(end) ? timestamp : end;
//        }
//
//        return new Timespan(start, end);
//    }
//
//    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
//        Set<String> mentionedUsers = new HashSet<>();
//        Pattern mentionPattern = Pattern.compile("(?<=\\s|^)@([A-Za-z0-9_]+)(?=[^A-Za-z0-9_]|$)");
//
//        for (Tweet tweet : tweets) {
//            Matcher matcher = mentionPattern.matcher(tweet.getText());
//
//            while (matcher.find()) {
//                mentionedUsers.add(matcher.group(1).toLowerCase());
//            }
//        }
//
//        return mentionedUsers;
//    }
//}

////VARIANT1// 
//package twitter;
//
//import java.time.Instant;import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class Extract {
//
//    // Define regex for valid mentions (start with '@', followed by alphanumeric or underscore, and no special chars around)
//    private static final Pattern mentionPattern = Pattern.compile("(?<=^|\\s)@([A-Za-z0-9_]+)(?=\\s|\\.|$)");
//
//    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
//        Set<String> mentionedUsers = new HashSet<>();
//        
//        for (Tweet tweet : tweets) {
//            Matcher matcher = mentionPattern.matcher(tweet.getText());
//            
//            while (matcher.find()) {
//                mentionedUsers.add(matcher.group(1).toLowerCase());  // Add mention in lowercase
//            }
//        }
//        
//        return mentionedUsers;
//    }
//    
//    public static Timespan getTimespan(List<Tweet> tweets) {
//        if (tweets.isEmpty()) {
//            throw new IllegalArgumentException("Tweet list cannot be empty");
//        }
//
//        Instant start = tweets.get(0).getTimestamp();
//        Instant end = start;
//
//        for (Tweet tweet : tweets) {
//            if (tweet.getTimestamp().isBefore(start)) {
//                start = tweet.getTimestamp();
//            }
//            if (tweet.getTimestamp().isAfter(end)) {
//                end = tweet.getTimestamp();
//            }
//        }
//
//        return new Timespan(start, end);
//    }
//}


////VARIANT2
//package twitter;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.time.Instant;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class Extract {
//
//    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
//        Set<String> mentionedUsers = new HashSet<>();
//        
//        for (Tweet tweet : tweets) {
//            String[] words = tweet.getText().split("\\s+");
//            
//            for (String word : words) {
//                if (word.startsWith("@")) {
//                    String mention = word.substring(1).toLowerCase();
//                    
//                    // Validate mention: ensure it contains only alphanumeric/underscore and no trailing invalid chars
//                    if (mention.matches("[A-Za-z0-9_]+")) {
//                        mentionedUsers.add(mention);
//                    }
//                }
//            }
//        }
//        
//        return mentionedUsers;
//    }
//    
//    public static Timespan getTimespan(List<Tweet> tweets) {
//        if (tweets.isEmpty()) {
//            throw new IllegalArgumentException("Tweet list cannot be empty");
//        }
//
//        Instant start = tweets.get(0).getTimestamp();
//        Instant end = start;
//
//        for (Tweet tweet : tweets) {
//            if (tweet.getTimestamp().isBefore(start)) {
//                start = tweet.getTimestamp();
//            }
//            if (tweet.getTimestamp().isAfter(end)) {
//                end = tweet.getTimestamp();
//            }
//        }
//
//        return new Timespan(start, end);
//    }
//}



//variant3
package twitter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extract {

    // Define regex to exclude invalid characters immediately after a mention
    private static final Pattern mentionPattern = Pattern.compile("(?<=^|\\s)@([A-Za-z0-9_]+)(?=\\s|$)");

    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentionedUsers = new HashSet<>();
        
        for (Tweet tweet : tweets) {
            Matcher matcher = mentionPattern.matcher(tweet.getText());
            
            while (matcher.find()) {
                mentionedUsers.add(matcher.group(1).toLowerCase());
            }
        }
        
        return mentionedUsers;
    }
    
    public static Timespan getTimespan(List<Tweet> tweets) {
        if (tweets.isEmpty()) {
            throw new IllegalArgumentException("Tweet list cannot be empty");
        }

        Instant start = tweets.get(0).getTimestamp();
        Instant end = start;

        for (Tweet tweet : tweets) {
            if (tweet.getTimestamp().isBefore(start)) {
                start = tweet.getTimestamp();
            }
            if (tweet.getTimestamp().isAfter(end)) {
                end = tweet.getTimestamp();
            }
        }

        return new Timespan(start, end);
    }
}
