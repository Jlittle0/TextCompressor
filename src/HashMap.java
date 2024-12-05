import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class HashMap {

    // Resize factor
    private final int RFACTOR;
    private KVPair[] map;
    private int currentElements;
    private int array_size;

    public HashMap() {
        RFACTOR = 2;
        // prime number that doesn't share every single factor possible with the radix
        array_size = 27457;
        map = new KVPair[array_size];
        currentElements = 0;
    }

    public void add(String key, int value) {
        // If the array is less than 50% full, add the element in a valid location
        // otherwise, resize array and then add element.
        if ((double)currentElements / array_size >= 0.5)
            resize();
        if (key == map[findIndex(map, key)].getKey()) {
            map[findIndex(map, key)] = new KVPair(key, map[findIndex(map, key)].getValue() + 1);
        }
        map[findIndex(map, key)] = new KVPair(key, value);
        currentElements++;
    }

    public int findElement(String key) {
        // Finds the corresponding value of a given key
        int index = hash(key);
        // Uses hashed key value to get initial position and then runs through the map in
        // a linear manner until it finds desired result (matching keys) or an empty space.
        while (map[index] != null && !map[index].getKey().equals(key)) {
            index = (index + 1) % array_size;
        }
//        return map[index] == null ? "INVALID KEY" : map[index].getValue();
        return map[index] == null ? null : map[index].getValue();
    }

    public void resize() {
        // Resizes the array whenever the previous size was 50% full so that linear shifting
        // to find an open space is faster.
        array_size *= RFACTOR;
        KVPair[] oldMap = map;
        map = new KVPair[array_size];
        // creates a new map and rehashes all the old elements and moves them to new map.
        for (KVPair k : oldMap)
            if (k != null) {
                add(k.getKey(), k.getValue());
            }
    }

    public int hash(String s) {
        // Multiplying by the radix (256) and adding char value then mod arrSize
        int numSTR = s.charAt(0);
        for (int i = 0; i < s.length(); i++) {
            numSTR = ((numSTR << 8) + s.charAt(i)) % array_size;
        }
        return numSTR;
    }

    public int findIndex(KVPair[] list, String key) {
        // Finds the next valid index by hashing the key and then moving to the right by
        // one until an empty space is found.
        int index = hash(key);
        while (list[index] != null && !list[index].getKey().equals(key)) {
            index = (index + 1) % array_size;
        }
        return index;
    }

    public KVPair[] sortAndPush(int num) {
        /*
         TODO:
          Take the current map, put it into an arraylist and remove all empty spaces, and then
          afterward sort the arraylist based on the value in each KVPair and send that in an
          array of KVPairs with size num that takes the first num elements from the arraylist.
          Or I could just return an arraylist that's of size num or rather however many elements
          there are if num < total # of elements in map.
         */

        ArrayList<KVPair> temp = new ArrayList<>();
        for (int i = 0; i < map.length; i++)
            if (map[i] != null)
                temp.add(map[i]);
        // Would work for a 2D array if I replaced getValue() with [0]
        temp.sort((a, b) -> Integer.compare(a.getValue(), b.getValue()));
        temp.sort(KVPair -> Comparator.comparing(o -> temp.get(o.getValue())));
        temp.sort(Comparator.comparing(o -> temp.get(o.getValue())));

        // TODO: Why is it so difficult to sort an arraylist of custom objects :(
        return null;
    }

}