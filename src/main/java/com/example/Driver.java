/*
 * Driver.java
 * 
 * Copyright 2014 Luca Menichetti <meniluca@gmail.com>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 */

package com.example;

import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

public class Driver extends Configured implements Tool {

	private static Logger logger = Logger.getLogger(Driver.class);

	public static void main(String[] args) throws Exception {
		try {
			int res = ToolRunner.run(new Configuration(), (Tool) new Driver(), args);
			System.exit(res);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(255);
		}
	}

	public int run(String[] args) throws Exception {

		Configuration conf = new Configuration();
	    Job job = Job.getInstance(conf, "Your job name");
		
	    job.setJarByClass(Driver.class);
		
		logger.info("job " + job.getJobName() + " [" + job.getJar()
				+ "] started with the following arguments: "
				+ Arrays.toString(args));

		if (args.length < 2) {
			logger.warn("to run this jar are necessary at 2 parameters \""
					+ job.getJar()
					+ " input_files output_directory");
			return 1;
		}
		
		job.setMapperClass(WordcountMapper.class);
		logger.info("mapper class is " + job.getMapperClass());
		
		//job.setMapOutputKeyClass(Text.class);
		//job.setMapOutputValueClass(IntWritable.class);
		logger.info("mapper output key class is " + job.getMapOutputKeyClass());
		logger.info("mapper output value class is " + job.getMapOutputValueClass());

		job.setReducerClass(WordcountReducer.class);
		logger.info("reducer class is " + job.getReducerClass());
		job.setCombinerClass(WordcountReducer.class);
		logger.info("combiner class is " + job.getCombinerClass());
		//When you are not runnign any Reducer
		//OR 	job.setNumReduceTasks(0);
		//		logger.info("number of reduce task is " + job.getNumReduceTasks());

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		logger.info("output key class is " + job.getOutputKeyClass());
		logger.info("output value class is " + job.getOutputValueClass());

		job.setInputFormatClass(TextInputFormat.class);
		logger.info("input format class is " + job.getInputFormatClass());

		job.setOutputFormatClass(TextOutputFormat.class);
		logger.info("output format class is " + job.getOutputFormatClass());

		Path filePath = new Path(args[0]);
		logger.info("input path "+ filePath);
		FileInputFormat.setInputPaths(job, filePath);

		Path outputPath = new Path(args[1]);
		logger.info("output path "+ outputPath);
		FileOutputFormat.setOutputPath(job, outputPath);

		job.waitForCompletion(true);
		return 0;
	}
}
