package Coding;
import java.util.PriorityQueue;

//import java.util.AbstractMap.SimpleEntry;
import java.util.Comparator;

// Common Code Signal question:
// Given a series of lamps on a straight line represented by ranges [x,y] and benches represented as points on the line, return how many lamps illuminate each bench 
// Lamps are inputted as a 2D array of shape: n x 2, and benches are inputted as a single dimension arrray 
public class Lamps {
    
    // The points are stored in the form [coordinate, type] where type can be 1,2,3
    // 1 corresponds to the start of the lamp range
    // 2 corresponds to the bench
    // 3 corresponds to the end of the lamp range
    static class PointComparator implements Comparator<int[]>{
        @Override
        public int compare(int[] arr1, int[] arr2) {

            if (arr1[0] != arr2[0]){
                // Primary comparison by coordinate
                return arr1[0]-arr2[0];
            } else {
                // Secondary comparison by type
                return arr1[1]-arr2[1];
            }
        }
    }

    // O(nlogn): n elements * log(n) per element for priority queue insertion
    public static int[] illuminatedBenches(int[][] lamps, int[] benches){
        PriorityQueue<int[]> pq = new PriorityQueue<>(new PointComparator());

        //Populating the priority queue with the lamps
        for (int i=0;i<lamps.length;i++){
            pq.add(new int[]{lamps[i][0],1});
            pq.add(new int[]{lamps[i][1],3});
        }

        //Populating the priority queue with the benches
        // The array has an extra parameter for the index in the benches array
        for( int i=0;i<benches.length;i++){
            pq.add(new int[]{benches[i],2,i});
        }

        // Array to store the number of lamps illuminating each bench
        int[] vals = new int[benches.length];

        // Variable to track how many lamps are currently on
        int lampsOn = 0;

        // Works by iterating through the priority queue and keeping track of how many lamps are currently on
        // When a bench is reached, the number of lamps on, is the number of lamps illuminating the bench
        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            if(curr[1]==1){
                lampsOn++;
            } else if (curr[1]==2){
                // Adds to vals using the associated index in the benches array
                vals[curr[2]] = lampsOn;
            } else {
                lampsOn--;
            }
        }

        return vals;
    }

    // Function to print an array
    public static String printArr(int[] arr){
        String res="";
        for(int i: arr){
            res+=(i+" ");
        }
        return res;
    }

    public static void main(String[] args){
        int[][] lamps = new int[][]{{0,6}, {5,12}, {13,14}, {11,18}};
        int[] benches = new int[]{5,7,12,8,16,21}; // 2 1 2 1 1 0

        int[] res = illuminatedBenches(lamps, benches);
        System.out.println(printArr(res));
        

    }


}
