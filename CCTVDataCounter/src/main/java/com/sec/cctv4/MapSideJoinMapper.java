package com.sec.cctv4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class MapSideJoinMapper extends Mapper<LongWritable, Text, Text, Text> {
    private HashMap<String, String> bookData = new HashMap<>();
    private Text outKey = new Text();
    private Text outValue = new Text();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        // 캐시 파일 읽기
        BufferedReader reader = new BufferedReader(new FileReader("BookName.csv"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            if (fields.length == 2) {
                String agencyName = fields[0].replace("\"", "");
                String bookName = fields[1].replace("\"", "");
                bookData.put(agencyName, bookName);
            }
        }
        reader.close();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split("\",\"");

        if (fields.length == 14) {
            String agencyName = fields[1].replace("\"", "");
            String installationPurpose = fields[4].replace("\"", "");

            String bookName = bookData.get(agencyName);
            if (bookName != null) {
                outKey.set(agencyName);
                outValue.set(installationPurpose + "\t" + bookName);
                context.write(outKey, outValue);
            }
        }
    }
}