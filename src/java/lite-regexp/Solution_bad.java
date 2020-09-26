class Solution_bad {
  public boolean isMatch(String value , String regexp) {
    regexp = normalize(regexp);

    var parts = regexp.split("\\*");
    var valuableSize = 0;

    for (var part : parts) {
      valuableSize += part.length();
    }

    var rightSize = 0;

    var beginWithWildCart = regexp.startsWith("*");
    var endWithWildCart = regexp.endsWith("*");

    int index = -1;
    for (var part : parts) {

      index++;

      if (part.equals(""))
        continue;

      var allocation = partMatch(part, value, rightSize, value.length()-valuableSize + rightSize + part.length(), !beginWithWildCart && index == 0, !endWithWildCart && index == parts.length - 1);

      if (allocation == null) {
        return false;
      }

      rightSize = allocation.to;


    }

    return true;
  }


  private Allocation partMatch(String partRegexp, String value, int from, int to, boolean linkedToBegin, boolean linkedToEnd) {

    if (from >= to || value.length() < partRegexp.length()) {
      return null;
    }

    if (linkedToBegin && linkedToEnd) {
      if (matchExactly(value, partRegexp, 0)) {
        return new Allocation(0, value.length());
      }
    } else if (linkedToEnd) {
      var partValue = value.substring(value.length() - partRegexp.length());
      if (matchExactly(partValue, partRegexp, 0)) {
        return new Allocation(value.length() - partRegexp.length(), value.length());
      }
    } else if (linkedToBegin) {
      var partValue = value.substring(0, partRegexp.length());
      if (matchExactly(partValue, partRegexp, 0)) {
        return new Allocation(0, partRegexp.length());
      }
    } else {
      var partValue = value.substring(from, to);
      for (int offset = 0; offset <= partValue.length() - partRegexp.length(); offset++) {

        if (matchExactly(partValue.substring(offset, offset+partRegexp.length()), partRegexp, offset)) {
          return new Allocation(from + offset, from + offset + partRegexp.length());
        }
      }
    }

    return null;

  }

  /**
   * if all symbol are equals or '.'
   */
  private boolean matchExactly(String partValue, String partRegexp, int offset) {

    if (partRegexp.length() != partValue.length()) {
      return false;
    }

    for (int i = 0; i < partRegexp.length(); i++) {

      var oneCharValue = partValue.charAt(i + offset);
      var oneCharRegexp = partRegexp.charAt(i);

      if ('.' != oneCharRegexp && oneCharValue != oneCharRegexp) {
        return false;
      }

    }

    return true;
  }


  /**
   * repeated wildcard have no means
   */
  private String normalize(String regexp) {
    return regexp.replace("**", "*");
  }

  private static class Allocation {
    public int from;
    public int to;

    public Allocation(int from, int to) {
      this.from = from;
      this.to = to;
    }
  }

}
