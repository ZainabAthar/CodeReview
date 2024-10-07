
package twitter;

import java.util.List;
import java.util.stream.Collectors;

public class Filter {


    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        // Bug: Incorrectly checking if username is not equal instead of equal
        return tweets.stream()
                .filter(tweet -> !tweet.getAuthor().equalsIgnoreCase(username))  // Incorrect logic
                .collect(Collectors.toList());
    }

    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
        // Bug: Checking if timestamp is strictly after end and strictly before start (inverted logic)
        return tweets.stream()
                .filter(tweet -> tweet.getTimestamp().isAfter(timespan.getEnd()) || 
                                 tweet.getTimestamp().isBefore(timespan.getStart()))  // Incorrect logic
                .collect(Collectors.toList());
    }

    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
        // Bug: Return all tweets instead of filtering based on words
        return tweets.stream()  // Incorrect logic
                .collect(Collectors.toList());
    }

}


//TASK 5

//VARIANT 1
//package twitter;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class Filter {
//
//  /**
//   * Find tweets written by a particular user.
//   *
//   * @param tweets list of tweets with distinct ids
//   * @param username Twitter username (case-insensitive)
//   * @return all and only the tweets in the list whose author is username,
//   *         in the same order as in the input list
//   */
//  public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
//      return tweets.stream()
//              .filter(tweet -> tweet.getAuthor().equalsIgnoreCase(username))  // Correct logic
//              .collect(Collectors.toList());
//  }
//
//  /**
//   * Find tweets that were sent during a specific time range.
//   *
//   * @param tweets list of tweets with distinct ids
//   * @param timespan timespan to filter by
//   * @return all and only the tweets in the list that were sent during
//   *         the timespan, in the same order as in the input list
//   */
//  public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
//      return tweets.stream()
//              .filter(tweet -> !tweet.getTimestamp().isBefore(timespan.getStart()) &&
//                               !tweet.getTimestamp().isAfter(timespan.getEnd()))  // Correct logic
//              .collect(Collectors.toList());
//  }
//
//  /**
//   * Find tweets that contain any of a set of words.
//   *
//   * @param tweets list of tweets with distinct ids
//   * @param words list of words to search for in the tweets
//   * @return all and only the tweets in the list such that the tweet text
//   *         contains at least one of the words, in the same order as in the input list
//   */
//  public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
//      return tweets.stream()
//              .filter(tweet -> words.stream()
//                      .anyMatch(word -> tweet.getText().toLowerCase().contains(word.toLowerCase())))  // Correct logic
//              .collect(Collectors.toList());
//  }
//}

//VARIANT 2
//package twitter;
//
//import java.util.List;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//
//public class Filter {
//
//  public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
//      return tweets.stream()
//              .filter(tweet -> tweet.getAuthor().equalsIgnoreCase(username))
//              .collect(Collectors.toList());
//  }
//
// public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
//      return tweets.stream()
//              .filter(tweet -> !tweet.getTimestamp().isBefore(timespan.getStart()) &&
//                               !tweet.getTimestamp().isAfter(timespan.getEnd()))
//              .collect(Collectors.toList());
//  }
//
//  public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
//      if (words.isEmpty()) {
//          return List.of();
//      }
//      // Build regular expression from the list of words
//      String regex = words.stream()
//              .map(Pattern::quote)  // Quote to escape special characters
//              .collect(Collectors.joining("|", "(?i)\\b(", ")\\b"));
//
//      Pattern pattern = Pattern.compile(regex);
//
//      return tweets.stream()
//              .filter(tweet -> pattern.matcher(tweet.getText()).find())
//              .collect(Collectors.toList());
//  }
//}


//VARIANT 3
//package twitter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Filter {
//
//  public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
//      List<Tweet> result = new ArrayList<>();
//      for (Tweet tweet : tweets) {
//          if (tweet.getAuthor().equalsIgnoreCase(username)) {
//              result.add(tweet);
//          }
//      }
//      return result;
//  }
//
//  public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
//      List<Tweet> result = new ArrayList<>();
//      for (Tweet tweet : tweets) {
//          if (!tweet.getTimestamp().isBefore(timespan.getStart()) &&
//              !tweet.getTimestamp().isAfter(timespan.getEnd())) {
//              result.add(tweet);
//          }
//      }
//      return result;
//  }
//
//  public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
//      List<Tweet> result = new ArrayList<>();
//      for (Tweet tweet : tweets) {
//          String text = tweet.getText().toLowerCase();
//          for (String word : words) {
//              if (text.contains(word.toLowerCase())) {
//                  result.add(tweet);
//                  break;
//              }
//          }
//      }
//      return result;
//  }
//}
