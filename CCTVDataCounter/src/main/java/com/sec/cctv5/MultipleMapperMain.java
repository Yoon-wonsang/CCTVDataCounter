package com.sec.cctv5;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MultipleMapperMain {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Multiple Mapper Example");

        job.setJarByClass(MultipleMapperMain.class);

        // 첫 번째 매퍼 등록 (CCTV 데이터 처리)
        MultipleInputs.addInputPath(job, new Path(args[0]), TextInputFormat.class, MultipleMapper1.class);

        // 두 번째 매퍼 등록 (도서 정보 데이터 처리)
        MultipleInputs.addInputPath(job, new Path(args[1]), TextInputFormat.class, MultipleMapper2.class);

        job.setReducerClass(MultipleMapperReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileOutputFormat.setOutputPath(job, new Path(args[2])); // 출력 경로

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
