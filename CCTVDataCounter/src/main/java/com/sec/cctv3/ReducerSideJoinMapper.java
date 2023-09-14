package com.sec.cctv3;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ReducerSideJoinMapper extends Mapper<LongWritable, Text, Text, Text> {
    private Text outKey = new Text();
    private Text outValue = new Text();
    
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split("\",\"");

        if (fields.length == 14) {
            String agencyName = fields[1].replace("\"", "");
            String installationPurpose = fields[4].replace("\"", "");

            outKey.set(agencyName);
            outValue.set("CCTV\t" + installationPurpose);
            context.write(outKey, outValue);
            
        } else if (fields.length == 2) {
            String agencyName = fields[0].replace("\"", "");
            String bookName = fields[1].replace("\"", "");

            outKey.set(agencyName);
            outValue.set("BookName\t" + bookName);
            context.write(outKey, outValue);
        }
    }
}