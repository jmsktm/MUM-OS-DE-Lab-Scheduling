package edu.mum.os.lab1;

import java.io.FileNotFoundException;

import edu.mum.os.lab1.algos.Scheduler;
import edu.mum.os.lab1.beans.Context;
import edu.mum.os.lab1.utils.Common;
import edu.mum.os.lab1.utils.Parser;

public class Execute {

	public static void main(String[] args) throws FileNotFoundException {
		try {
			Common.validateArgs(args);

			String algo = Common.getSchedulerCanonicalName(args[0]);
			String conf = args[1];
			Context context = Parser.getContext(conf);
			
			Scheduler algorithm = (Scheduler)(Class.forName(algo).newInstance());
			algorithm.setContext(context);
			
			algorithm.execute();
			algorithm.printResult();
			algorithm.printGanttChart();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
