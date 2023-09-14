package com.sec.cctv4;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MapSideJoinReducer extends Reducer<Text, Text, Text, Text> {
	
	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
	    // 중복을 제거하기 위한 Set
	    Set<Text> uniqueValues = new HashSet<>();
	    
	    // 중복을 제거하고 Set에 값 추가
	    for (Text value : values) {
	        uniqueValues.add(new Text(value));
	    }

	    // 중복을 제거한 값을 출력
	    for (Text uniqueValue : uniqueValues) {
	        context.write(key, uniqueValue);
	    }
	}

}
