package com.sec.cctv5;

import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class MultipleMapper2 extends Mapper<LongWritable, Text, Text, Text> {
    private Text outKey = new Text();
    private Text outValue = new Text();
    
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 데이터 처리 및 키-값 쌍 생성
        String[] fields = value.toString().split(",");
        if (fields.length == 2) {
            String agencyName = fields[0].replace("\"", "");
            String bookName = fields[1].replace("\"", "");
            outKey.set(agencyName);
            outValue.set("BookName\t" + bookName);
            context.write(outKey, outValue);
        }
    }
}