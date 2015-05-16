package com.ontko.moss;

import java.io.FileNotFoundException;

import com.ontko.moss.algos.FCFSSchedulingAlgorithm;
import com.ontko.moss.algos.SchedulingAlgorithm;
import com.ontko.moss.beans.Context;
import com.ontko.moss.utils.Common;
import com.ontko.moss.utils.Parser;

public class Test {

	public static void main(String[] args) throws FileNotFoundException {
		try {
			Common.validateArgs(args);

			String conf = args[0];
			Context context = Parser.getContext(conf);
			SchedulingAlgorithm algorithm = new FCFSSchedulingAlgorithm(context);
			algorithm.execute();
			algorithm.printResult();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
