package twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    // Test case 1: Empty List of Tweets
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Collections.emptyList());
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }

    // Test case 2: Tweets Without Mentions
    @Test
    public void testGuessFollowsGraphNoMentions() {
        List<Tweet> tweets = Arrays.asList(
                new Tweet(1, "user1", "Just a regular tweet", Instant.now())
        );
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }

    // Test case 3: Single Mention
    @Test
    public void testGuessFollowsGraphSingleMention() {
        List<Tweet> tweets = Arrays.asList(
                new Tweet(1, "user1", "Hello @user2", Instant.now())
        );
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        assertTrue("user1 should follow user2", followsGraph.containsKey("user1"));
        assertTrue("user1 should follow user2", followsGraph.get("user1").contains("user2"));
    }

    // Test case 4: Multiple Mentions
    @Test
    public void testGuessFollowsGraphMultipleMentions() {
        List<Tweet> tweets = Arrays.asList(
                new Tweet(1, "user1", "Hey @user2 @user3", Instant.now())
        );
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        assertTrue("user1 should follow user2 and user3", followsGraph.get("user1").containsAll(Arrays.asList("user2", "user3")));
    }

    // Test case 5: Multiple Tweets from One User
    @Test
    public void testGuessFollowsGraphMultipleTweets() {
        List<Tweet> tweets = Arrays.asList(
                new Tweet(1, "user1", "Hi @user2", Instant.now()),
                new Tweet(2, "user1", "Hello again @user3", Instant.now())
        );
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        assertTrue("user1 should follow user2 and user3", followsGraph.get("user1").containsAll(Arrays.asList("user2", "user3")));
    }

    // Test case 6: Empty Graph for influencers()
    @Test
    public void testInfluencersEmptyGraph() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertTrue("expected empty influencer list", influencers.isEmpty());
    }

    // Test case 7: Single User Without Followers
    @Test
    public void testInfluencersNoFollowers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("user1", new HashSet<>());
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertTrue("expected empty influencer list", influencers.isEmpty());
    }

    // Test case 8: Single Influencer
    @Test
    public void testSingleInfluencer() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("user1", new HashSet<>(Arrays.asList("user2")));
        followsGraph.put("user2", new HashSet<>());
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertEquals("user2 should be the top influencer", "user2", influencers.get(0));
    }

    // Test case 9: Multiple Influencers
    @Test
    public void testMultipleInfluencers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("user1", new HashSet<>(Arrays.asList("user2", "user3")));
        followsGraph.put("user4", new HashSet<>(Arrays.asList("user3")));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertEquals("user3 should be the top influencer", "user3", influencers.get(0));
        assertEquals("user2 should be second influencer", "user2", influencers.get(1));
    }

    // Test case 10: Tied Influence
    @Test
    public void testTiedInfluence() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("user1", new HashSet<>(Arrays.asList("user2")));
        followsGraph.put("user3", new HashSet<>(Arrays.asList("user2")));
        followsGraph.put("user4", new HashSet<>(Arrays.asList("user5")));
        followsGraph.put("user6", new HashSet<>(Arrays.asList("user5")));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertTrue("user2 and user5 should be tied for influence", influencers.subList(0, 2).containsAll(Arrays.asList("user2", "user5")));
    }
}
