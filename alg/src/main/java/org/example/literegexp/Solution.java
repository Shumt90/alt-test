package org.example.literegexp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Solution {

    public boolean isMatch(String value, String regexp) {

        if (value.equals("") && regexp.equals("")) {
            return true;
        }

        if (regexp.equals("")) {
            return false;
        }

        //System.out.println("===========" + value + " === " + regexp);

        var tokens = divide(regexp);

        Allocations[] allocations = new Allocations[tokens.size()];

        int i = -1;
        for (var token : tokens) {
            var allocation = new Allocations(token);
            allocation.combinations = getCombinations(value, token);
            if (allocation.combinations.isEmpty()) {
                return false;
            } else {
                allocations[++i] = allocation;
            }
        }

        //System.out.println(Arrays.toString(allocations));

        if (i == -1) {
            return false;
        }

        for (var allocation : allocations[0].combinations) {
            if (allocation.from != 0) {
                continue;
            }

            if (haveWay(allocations, 1, value.length(), allocation)) {
                return true;
            }
        }

        return false;

    }

    boolean haveWay(Allocations[] allocations, int offsetNext, int end, Allocation allocation) {

        if (offsetNext == allocations.length) {
            return allocation.to == end;
        }

        for (var allocationNext : allocations[offsetNext].combinations) {
            if (allocation.to == allocationNext.from && haveWay(allocations, offsetNext + 1, end, allocationNext)) {
                return true;
            }

        }

        return false;
    }

    List<Allocation> getCombinations(String value, Token token) {

        var allocations = new ArrayList<Allocation>();

        if (!token.wild) {

            int maxOffset = value.length() - token.nextCost - token.prevCost - token.value.length();

            for (int offset = 0; offset <= maxOffset; offset++) {

                if (equalsIgnoreDot(value.substring(offset + token.prevCost, offset + token.prevCost + token.value.length()), token.value)) {
                    int cur = offset + token.prevCost;
                    allocations.add(new Allocation(cur, token.value.length() + cur));
                }

            }
        } else {

            //maxWordSize - light of word for patternMatch
            //wordOffset - offset of word
            //wordInx - character index for compare

            int maxWordSize = value.length() - token.nextCost - token.prevCost;
            for (int curWordSize = 1; curWordSize <= maxWordSize; curWordSize++) {
                two:
                for (int wordOffset = 0; wordOffset <= maxWordSize - curWordSize; wordOffset++) {
                    for (int wordInx = 0; wordInx < curWordSize; wordInx++) {

                        if (!equalsIgnoreDot(token.value.charAt(0), value.charAt(token.prevCost + wordOffset + wordInx))) {
                            wordOffset = token.prevCost + wordOffset + wordInx;
                            continue two;
                        }

                    }

                    allocations.add(new Allocation(token.prevCost + wordOffset, token.prevCost + wordOffset + curWordSize));

                }
                allocations.add(new Allocation(token.prevCost + curWordSize, token.prevCost + curWordSize));
            }
            allocations.add(new Allocation(maxWordSize, maxWordSize));
            allocations.add(new Allocation(token.prevCost, token.prevCost));
        }

        return allocations;
    }

    private boolean equalsIgnoreDot(char a, char b) {
        if (a == '.' || b == '.') {
            return true;
        }
        return a == b;
    }

    private boolean equalsIgnoreDot(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }

        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != '.' && b.charAt(i) != '.' && a.charAt(i) != b.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    private LinkedList<Token> divide(String regexp) {

        var tokens = new LinkedList<Token>();

        int beg = 0;
        int end = 0;
        for (int i = 0; i < regexp.length(); i++) {
            if (regexp.charAt(i) == '*') {
                if (i - 1 - beg > 0) {
                    var token = regexp.substring(beg, i - 1);
                    tokens.add(new Token(token.length(), token, false));
                }

                tokens.add(new Token(0, regexp.substring(i - 1, i), true));

                beg = i + 1;
            }
            end = i;

        }

        if (beg <= end) {
            var token = regexp.substring(beg, end + 1);
            tokens.add(new Token(token.length(), token, false));
        }

        int allCost = 0;
        for (var token : tokens) {
            allCost += token.minCost;
        }

        int prevCost = 0;
        for (var token : tokens) {
            allCost -= token.minCost;

            token.nextCost = allCost;
            token.prevCost = prevCost;

            prevCost += token.minCost;
        }

        return tokens;
    }

    private static class Token {

        int minCost;
        String value;
        boolean wild;
        int prevCost;
        int nextCost;

        public Token(int minCost, String value, boolean wild) {
            this.minCost = minCost;
            this.value = value;
            this.wild = wild;
        }

        @Override
        public String toString() {
            return "Token{" +
                    "minCost=" + minCost +
                    ", value='" + value + '\'' +
                    ", wild=" + wild +
                    ", prevCost=" + prevCost +
                    ", nextCost=" + nextCost +
                    '}';
        }
    }

    private static class Allocations {

        Token token;
        List<Allocation> combinations = new ArrayList<>();

        public Allocations(Token token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return "Allocations{" +
                    "token=" + token +
                    ", \ncombinations=" + combinations +
                    '}';
        }
    }

    private static class Allocation {

        int from;
        int to;

        public Allocation(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "\nAllocation{" +
                    "from=" + from +
                    ", to=" + to +
                    "}";
        }
    }
}
