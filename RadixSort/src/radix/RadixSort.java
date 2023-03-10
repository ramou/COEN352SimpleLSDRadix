package radix;

import java.util.Arrays;
import java.util.Random;

public class RadixSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final int SIZE = 10000000;
		
		long[] input = new long[SIZE];
		long seed = System.currentTimeMillis();
		System.out.println(seed);
		Random r = new Random(seed);
		for(int i = 0; i < input.length; ++i) {
			input[i] = r.nextLong(0, Long.MAX_VALUE);
		}
		long[] control = input.clone();
		try {

			
			
			
			long start = System.currentTimeMillis();
			Arrays.sort(control);
			long end = System.currentTimeMillis();
			System.out.println("Arrays.sort took: " + (end-start));
			
			start = System.currentTimeMillis();
			rsort(input);
			end = System.currentTimeMillis();
			System.out.println("Radix Sort took: " + (end-start));
			
			for(int i = 0; i < input.length; ++i) {
				if(input[i] != control[i]) throw new RuntimeException("It's borked!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(input.length < 20) {
				System.out.println(Arrays.toString(input));
				System.out.println(Arrays.toString(control));
			}
		}

		
	}


    public static void insertionSort(long array[]) {  
        int n = array.length;  
        for (int j = 1; j < n; j++) {  
            long key = array[j];  
            int i = j-1;  
            while ( (i > -1) && ( array [i] > key ) ) {  
                array [i+1] = array [i];  
                i--;  
            }  
            array[i+1] = key;  
        }  
    }  
	
	public static void zeroCounters(int[] buckets) {
		for(int i = 0; i < 257; ++i) buckets[i] = 0;
	}
	
	//This is a Least Significant Digit Radix Sort
	public static void rsort(long[] arr) {
		long[] buffer = new long[arr.length];
		int[] buckets = new int[257];
		
		zeroCounters(buckets);
		
		for(int digit = 0; digit < 8; ++digit) {
			//Count the digits
			for(int i = 0; i < arr.length; ++i) buckets[(int)(1+((arr[i] >> digit*8) & 255))]++;		
			//System.out.println(Arrays.toString(buckets));
			//Convert the digits to bucket offsets
			for(int i = 1; i < 257; ++i) buckets[i] = buckets[i] + buckets[i-1];
			//System.out.println(Arrays.toString(buckets));
			
			//Copy into the corresponding buffer
			for(int i = 0; i < arr.length; ++i) {
				//System.out.println(buckets[(arr[i] >> (digit*8)) & 255]);
				buffer[buckets[(int)((arr[i] >> (digit*8)) & 255)]] = arr[i];
				buckets[(int)((arr[i] >> (digit*8)) & 255)]++;
			}
			
			//swap arrays and zero buckets...
			long[] temp = buffer;
			buffer = arr;
			arr = temp;
			zeroCounters(buckets);
		}
		
	}
	
	public static void divrsort(long[] arr) {
		long[] buffer = new long[arr.length];
		int[] buckets = new int[257];
		
		zeroCounters(buckets);
		
		for(int digit = 4; digit < 8; ++digit) {
			//Count the digits
			for(int i = 0; i < arr.length; ++i) buckets[(int)(1+((arr[i] >> digit*8) & 255))]++;		
			//System.out.println(Arrays.toString(buckets));
			//Convert the digits to bucket offsets
			for(int i = 1; i < 257; ++i) buckets[i] = buckets[i] + buckets[i-1];
			//System.out.println(Arrays.toString(buckets));
			
			//Copy into the corresponding buffer
			for(int i = 0; i < arr.length; ++i) {
				//System.out.println(buckets[(arr[i] >> (digit*8)) & 255]);
				buffer[buckets[(int)((arr[i] >> (digit*8)) & 255)]] = arr[i];
				buckets[(int)((arr[i] >> (digit*8)) & 255)]++;
			}
			
			//swap arrays and zero buckets...
			long[] temp = buffer;
			buffer = arr;
			arr = temp;
			zeroCounters(buckets);
		}
		insertionSort(arr);
		
	}
}
