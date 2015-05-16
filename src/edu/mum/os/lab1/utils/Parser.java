package edu.mum.os.lab1.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.StringTokenizer;

import edu.mum.os.lab1.beans.Context;
import edu.mum.os.lab1.beans.Process;

public class Parser {

	@SuppressWarnings("deprecation")
	public static Context getContext(String file) throws IOException {
		Context context = new Context();
		File f = new File(file);

		// Context attributes
		int numProcess = 0;
		int meanDev = 0;
		int stdDev = 0;
		int runtime = 0;
		
		// Process attributes
		int cputime = 0;
		int ioblocking = 0;
		
		// Transient variable
		double X = 0.0;

		DataInputStream in = new DataInputStream(new FileInputStream(f));
		String line;
		int count = 1;
		while ((line = in.readLine()) != null) {
			if (line.startsWith("numprocess")) {
				StringTokenizer st = new StringTokenizer(line);
				st.nextToken();
				numProcess = Common.s2i(st.nextToken());
			}
			if (line.startsWith("meandev")) {
				StringTokenizer st = new StringTokenizer(line);
				st.nextToken();
				meanDev = Common.s2i(st.nextToken());
			}
			if (line.startsWith("standdev")) {
				StringTokenizer st = new StringTokenizer(line);
				st.nextToken();
				stdDev = Common.s2i(st.nextToken());
			}
			if (line.startsWith("process")) {
				StringTokenizer st = new StringTokenizer(line);
				st.nextToken();
				ioblocking = Common.s2i(st.nextToken());
				X = Common.R1();
				while (X == -1.0) {
					X = Common.R1();
				}
				X = X * stdDev;
				cputime = (int) X + meanDev;
				Process process = new Process(count, cputime, ioblocking, 0, 0, 0);
				process.setContext(context);
				context.getProcesses().addElement(process);
				count++;
			}
			if (line.startsWith("runtime")) {
				StringTokenizer st = new StringTokenizer(line);
				st.nextToken();
				runtime = Common.s2i(st.nextToken());
			}
			context.setNumprocess(numProcess);
			context.setMeanDev(meanDev);
			context.setRuntime(runtime);
		}
		in.close();
		return context;
	}
}
