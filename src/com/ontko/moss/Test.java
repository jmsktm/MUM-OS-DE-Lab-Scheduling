package com.ontko.moss;

import java.io.FileNotFoundException;

import com.ontko.moss.algos.FCFSScheduler;
import com.ontko.moss.algos.Scheduler;
import com.ontko.moss.beans.Context;
import com.ontko.moss.utils.Common;
import com.ontko.moss.utils.Parser;

public class Test {

	public static void main(String[] args) throws FileNotFoundException {
		try {
			Common.validateArgs(args);

			String conf = args[0];
			Context context = Parser.getContext(conf);
			Scheduler algorithm = new FCFSScheduler(context);
			algorithm.execute();
			algorithm.printResult();
			algorithm.printGanttChart();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
