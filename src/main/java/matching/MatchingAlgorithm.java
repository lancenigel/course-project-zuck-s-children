package matching;

import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

import account_and_login.account_creation.Account;
import data_persistency.UserDatabase;
import profile.*;

import java.util.*;


public class MatchingAlgorithm {

    /**
     * Back-end matching algorithm that will work with the swiper to determine potential matches.
     * Potential matches are determined by a compatibility score
     *
     * @param user current user
     * @param otherUsers all other users
     */

    public static LinkedList<Account> MatchingAlgorithmMethod(Account user, ArrayList<Account> otherUsers) {
        /*
         * Construct an arraylist of potential matches, giving them a score compatibility
         */
        resetUserScores(otherUsers);
        HashMap<String, List<String>> preferences = user.getProfile().getStudyBuddyPreferences();

        for (Account oUser : otherUsers) {
            for (String key : preferences.keySet()) {
                assignScore(key, preferences, oUser.getProfile());
            }
        }

        Collections.sort(otherUsers);

        return createPotentialMatches(user, otherUsers);
    }

    private static LinkedList<Account> createPotentialMatches(Account user, ArrayList<Account> otherUsers) {
        LinkedList<Account> potentialMatches = new LinkedList<>();
        for (Account otherUser : otherUsers) {
            if (!user.getSeen().contains(otherUser)) {
                potentialMatches.add(otherUser);
            }
        }
        return potentialMatches;
    }

    private static void resetUserScores(ArrayList<Account> otherUsers) {
        for (Account otherUser : otherUsers) {
            otherUser.getProfile().setScore(0);
        }
    }

    public static void assignScore(String key, HashMap<String, List<String>> preferences, Profile oUser)  {

        /* Helper method that assigns score, for each matching preference and
        other user style, compatibility score is raised by 1
        * 3 keys: year, field, style
        * year preferences are sorted
         */

        if (Objects.equals(key, "year")) {
            oUser.setScore(assignYearScore(preferences, oUser, oUser.getScore()));
        }

        if (Objects.equals(key, "field")) {
            oUser.setScore(assignFieldScore(preferences, oUser, oUser.getScore()));
        }

        if (Objects.equals(key, "style")) {
            oUser.setScore(assignStyleScore(preferences, oUser, oUser.getScore()));
        }

    }

    private static int assignStyleScore(HashMap<String, List<String>> preferences, Profile oUser, int score) {
        if(!preferences.get("style").isEmpty()) {
            ArrayList<String> style = (ArrayList<String>) preferences.get("style");
            for (String s : style) {
                if (oUser.getStudyStyles().contains(s)) {
                    score += 1;
                }
            }
        }
        return score;
    }

    private static int assignFieldScore(HashMap<String, List<String>> preferences, Profile oUser, int score) {
        if(!preferences.get("field").isEmpty()) {
            ArrayList<String> field = (ArrayList<String>) preferences.get("field");
            for (String s : field) {
                if (s.equals(oUser.getFieldOfStudy())) {
                    score += 1;
                }
            }
        }
        return score;
    }

    private static int assignYearScore(HashMap<String, List<String>> preferences, Profile oUser, int score) {
        if(!preferences.get("year").isEmpty()) {
            ArrayList<String> years = (ArrayList<String>) preferences.get("year");
            for (String year : years) {
                if (Objects.equals(year, oUser.getYear())) {
                    score += 1;
                }
            }
        }
        return score;
    }

    public static ArrayList<Account> getOthers() {
        /*
         * method to get an arrayList of all other users excluding the current one
         */
        ArrayList<Account> otherUsers = new ArrayList<>();
        for (Account a : UserDatabase.getUserDatabase().getAccounts().values()) {
            if (a != UserDatabase.getUserDatabase().getCurrentUser()) {
                otherUsers.add(a);
            }
        }
        return otherUsers;
    }

    public static LinkedList<Account> finalMatches() {
        /*
         * method to get all finalMatches of the current user
         */
        return MatchingAlgorithmMethod(UserDatabase.getUserDatabase().getCurrentUser(),
                getOthers());
    }
}
