package edu.mum.os.lab1;

import java.io.FileNotFoundException;

import edu.mum.os.lab1.algos.FCFSScheduler;
import edu.mum.os.lab1.algos.Scheduler;
import edu.mum.os.lab1.beans.Context;
import edu.mum.os.lab1.utils.Common;
import edu.mum.os.lab1.utils.Parser;

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
