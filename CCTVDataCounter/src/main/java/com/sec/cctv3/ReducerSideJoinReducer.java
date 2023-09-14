package com.sec.cctv3;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class ReducerSideJoinReducer extends Reducer<Text, Text, Text, Text> {
	private Text outputValue = new Text();

	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
//        String installationPurpose = null;
		String bookName = null;

		// Installation purpose를 저장할 Set
		Set<String> installationPurposeSet = new HashSet<>();

		for (Text value : values) {
			String[] fields = value.toString().split("\t");

			if (fields[0].equals("CCTV")) {
//                installationPurpose = fields[1];
				installationPurposeSet.add(fields[1]);
			} else if (fields[0].equals("BookName")) {
				bookName = fields[1];
			}
		}

		for (String purpose : installationPurposeSet) {
			if (bookName != null) {
				outputValue.set(purpose + "\t" + bookName);
				context.write(key, outputValue);
			}
		}
	}
}