package twitter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        Pattern mentionPattern = Pattern.compile("@(\\w+)");

        for (Tweet tweet : tweets) {
            String author = tweet.getAuthor().toLowerCase();
            Matcher matcher = mentionPattern.matcher(tweet.getText());
            
            while (matcher.find()) {
                String mentionedUser = matcher.group(1).toLowerCase();

                // Users can't follow themselves
                if (!mentionedUser.equals(author)) {
                    followsGraph.putIfAbsent(author, new HashSet<>());
                    followsGraph.get(author).add(mentionedUser);
                }
            }
        }

        return followsGraph;
    }

    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        Map<String, Integer> followerCount = new HashMap<>();

        // Count followers for each user
        for (Set<String> followers : followsGraph.values()) {
            for (String user : followers) {
                followerCount.put(user, followerCount.getOrDefault(user, 0) + 1);
            }
        }

        // Sort users by their follower count in descending order, excluding those with 0 followers
        return followerCount.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)  // Include only users with at least 1 follower
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()))
                .map(Map.Entry::getKey)
                .toList();
    }

}
