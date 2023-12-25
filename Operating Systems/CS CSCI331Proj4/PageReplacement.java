import java.lang.reflect.Array;
import java.util.ArrayList;

public class PageReplacement {

    public static ArrayList<Integer> createRS(int sizeOfVM, int length,
                                              int sizeOfLocus, int rateOfMotion, double prob) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        int start = 0;
        int n;
        while (result.size() < length) {
            for (int i = 0; i < rateOfMotion; i++) {
                n = (int) (Math.random() * sizeOfLocus + start);
                result.add(n);
            }

            if (Math.random() < prob)
                start = (int) Math.random() * sizeOfVM;
            else
                start++;
        }

        return result;
    }

    public static int FIFOReplacement(ArrayList<Integer> rs, int numOfFrames) {
        int [] frames = new int[numOfFrames];
        int i, first = 0, count = 0;
        for( i = 0; i < numOfFrames; i++)
            frames[i] = i;
        for(i = 0; i < rs.size(); i++){
            if(isInArray(frames, rs.get(i)) == -1){
                frames[first] = rs.get(i);
                count++;
                first = (first + 1) % (frames.length);
            }
        }
        return count;
    }

    public static int LRUReplacement(ArrayList<Integer> rs, int numOfFrames){
        int [] frames = new int[numOfFrames];
        int i, j, first = 0, count = 0;
        for(i = 0; i < numOfFrames; i++)
            frames[i] = i;
        for(i = 0; i < rs.size(); i++) {
            int index = isInArray(frames, rs.get(i));
            int least;
            if(index == -1){
                least = rs.get(i);
                count++;
                index = 0;
            }
            else
                least = frames[index];
            for(j = index; j < frames.length -1; j++)
                frames[j] = frames[j + 1];
            frames[j] = least;
        }
        return  count;
    }

    private static int isInArray(int[] frames, int page){
        for(int i = 0; i < frames.length; i++)
            if(frames[i] == page) return i;
            return -1;
    }

    public static void main(String[] args) {
        ArrayList<Integer> rs = createRS(4096,1000, 10, 100, 0.1);
        System.out.println("The page fault using FIFO replacement algorithm: ");
        System.out.println(FIFOReplacement(rs, 10) + " times");
        System.out.println("The page fault using LRU replacement algorithm: ");
        System.out.println(LRUReplacement(rs, 10) + " times");
    }

}
