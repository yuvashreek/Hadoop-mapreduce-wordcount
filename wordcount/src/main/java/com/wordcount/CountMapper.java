package com.wordcount;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CountMapper extends Mapper<Object, Text, Text, IntWritable> {
	// used in v1
	// private final static IntWritable one = new IntWritable(1);
	private HashMap<String, IntWritable> hashmap;

	// setup method for version 3
	@Override
	protected void setup(Mapper<Object, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {

		hashmap = new HashMap<String, IntWritable>();


			// TODO Auto-generated method stub
		super.setup(context);
	}//end of setup method

	@Override
	public void map(Object key, Text value, Context output) throws IOException, InterruptedException {

		// version - 1

		// If more than one word is present, split using white space.
		/*String[] words = value.toString().split(" "); // Only the first word is the candidate name
		for (String string : words) {
			if (string.length() > 0) {
				output.write(new Text(string), one);
			}

		}*/

		// version - 2

		/*String[] words = value.toString().split(" ");
		IntWritable countWritable = new IntWritable(1);
		HashMap<String, IntWritable> hashmap = new HashMap<String, IntWritable>();
		for (String string : words) {
			if (string.length() > 0) {
				if (hashmap.containsKey(string)) {
					int count = hashmap.get(string).get();
					count++;
					// countWritable.set(count);
					hashmap.put(string, new IntWritable(count));
				} else {
					hashmap.put(string, countWritable);

				}
			}
		}
		for (Map.Entry<String, IntWritable> entry : hashmap.entrySet()) {
			String mystr = entry.getKey();
			IntWritable myint = entry.getValue();
			output.write(new Text(mystr), myint);
		}*/

		//version - 3
		String[] words = value.toString().split(" ");
		IntWritable countWritable = new IntWritable(1);
		for (String string : words) {
			if (string.length() > 0) {
				if (hashmap.containsKey(string)) {
					int count = hashmap.get(string).get();
					count++;
					// countWritable.set(count);
					hashmap.put(string, new IntWritable(count));
				} else {
					hashmap.put(string, countWritable);

				}
			}
		}

	}// end of map method 

	@Override
	protected void cleanup(Mapper<Object, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		for (Map.Entry<String, IntWritable> entry : hashmap.entrySet()) {
			String mystr = entry.getKey();
			IntWritable myint = entry.getValue();
			context.write(new Text(mystr), myint);
		}
		super.cleanup(context);
	}//end of cleanup method

}// end of mapper class