package com.ontko.moss;
import java.io.*;
import java.util.*;

import com.ontko.moss.algos.SchedulingAlgorithm;

public class Sched {

	private static int processnum = 5;
	private static int meanDev = 1000;
	private static int standardDev = 100;
	private static int runtime = 1000;
	private static Vector<sProcess> processVector = new Vector<sProcess>();
	private static Results result = null;
	private static String resultsFile = "Summary-Results";

	@SuppressWarnings("deprecation")
	private static void Init(String file) {
		File f = new File(file);
		String line;
		int cputime = 0;
		int ioblocking = 0;
		double X = 0.0;

		try {
			DataInputStream in = new DataInputStream(new FileInputStream(f));
			while ((line = in.readLine()) != null) {
				if (line.startsWith("numprocess")) {
					StringTokenizer st = new StringTokenizer(line);
					st.nextToken();
					processnum = Common.s2i(st.nextToken());
				}
				if (line.startsWith("meandev")) {
					StringTokenizer st = new StringTokenizer(line);
					st.nextToken();
					meanDev = Common.s2i(st.nextToken());
				}
				if (line.startsWith("standdev")) {
					StringTokenizer st = new StringTokenizer(line);
					st.nextToken();
					standardDev = Common.s2i(st.nextToken());
				}
				if (line.startsWith("process")) {
					StringTokenizer st = new StringTokenizer(line);
					st.nextToken();
					ioblocking = Common.s2i(st.nextToken());
					X = Common.R1();
					while (X == -1.0) {
						X = Common.R1();
					}
					X = X * standardDev;
					cputime = (int) X + meanDev;
					processVector.addElement(new sProcess(cputime, ioblocking,
							0, 0, 0));
				}
				if (line.startsWith("runtime")) {
					StringTokenizer st = new StringTokenizer(line);
					st.nextToken();
					runtime = Common.s2i(st.nextToken());
				}
			}
			in.close();
		} catch (IOException e) {
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		int i = 0;

		if (args.length != 1) {
			System.out.println("Usage: 'java Scheduling <INIT FILE>'");
			System.exit(-1);
		}
		File f = new File(args[0]);
		if (!(f.exists())) {
			System.out.println("Scheduling: error, file '" + f.getName()
					+ "' does not exist.");
			System.exit(-1);
		}
		if (!(f.canRead())) {
			System.out.println("Scheduling: error, read of " + f.getName()
					+ " failed.");
			System.exit(-1);
		}
		System.out.println("Working...");
		Init(args[0]);
		if (processVector.size() < processnum) {
			i = 0;
			while (processVector.size() < processnum) {
				double X = Common.R1();
				while (X == -1.0) {
					X = Common.R1();
				}
				X = X * standardDev;
				int cputime = (int) X + meanDev;
				processVector
						.addElement(new sProcess(cputime, i * 100, 0, 0, 0));
				i++;
			}
		}
		
		SchedulingAlgorithm algorithm = new FCFSSchedulingAlgorithm(runtime, processVector);
		algorithm.execute();
		System.out.println("Completed.");
	}
}
