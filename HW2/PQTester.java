import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Set;
import org.junit.Test;

public class PQTester {

    // tests for single threaded
    @Test public void tae0() {
        PriorityQueue pq = new PriorityQueue(3);
        assertEquals(0,pq.add("hi9", 9));
        assertEquals(1,pq.add("hi0", 0));
        assertEquals(1,pq.add("hi8", 8));
        
        assertEquals(1,pq.search("hi8"));
        assertEquals(0,pq.search("hi9"));
        assertEquals(2,pq.search("hi0"));
        assertEquals(-1, pq.add("hi0",0));
//        pq.add("hi", 2);
        assertEquals("hi9", pq.getFirst());
        assertEquals("hi8", pq.getFirst());
        assertEquals(0, pq.add("hi5", 5));
        assertEquals(1, pq.add("hi2_5", 5));
        assertEquals("hi5", pq.getFirst());
        assertEquals("hi2_5", pq.getFirst());
        assertEquals("hi0", pq.getFirst());
        assertEquals(-1,pq.search("hiya"));
//        pq.getFirst();
    }
}
