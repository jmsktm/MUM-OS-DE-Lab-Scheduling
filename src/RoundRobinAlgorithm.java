/**
 * 
 * Round Robin Scheduling algorithm implementation
 * @author james.singh@hotmail.com
 * 
 */
import java.util.Vector;
import java.io.*;

public class RoundRobinAlgorithm {
	
	private static final String FILENAME = "Summary-Processes";
	private static final String SCHEDULING_TYPE = "Batch (Nonpreemptive)";
	private static final String SCHEDULING_NAME = "Round Robin";
	
	private static PrintStream out = null;
	
	public static PrintStream getStream() {
		FileOutputStream fos;
		try {
			if (out != null) {
				return out;
			}
			fos = new FileOutputStream(FILENAME);
			out = new PrintStream(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return out;
	}	

	public static Results Run(int runtime, 
			@SuppressWarnings("rawtypes") Vector processVector, 
			Results result) {		
		result.schedulingType = SCHEDULING_TYPE;
		result.schedulingName = SCHEDULING_NAME;
		
		int i = 0;
		int comptime = 0;
		int currentProcess = 0;
		int previousProcess = 0;
		int size = processVector.size();
		int completed = 0;
		
		sProcess process = (sProcess) processVector.elementAt(currentProcess);
		
		while (comptime < runtime) {
			process.cpudone++;
			if (process.cpudone == process.cputime) {
				completed++;
				print(currentProcess, process);
				if (completed == size) {
					result.compuTime = comptime;
					out.close();
					return result;
				}
				for (i = size - 1; i >= 0; i--) {
					process = (sProcess) processVector.elementAt(i);
					if (process.cpudone < process.cputime) {
						currentProcess = i;
						if (process.ioblocking == process.ionext) {
							print(currentProcess, process);
							process.numblocked++;
							process.ionext = 0;
							previousProcess = currentProcess;
							for (i = size - 1; i >= 0; i--) {
								process = (sProcess) processVector
										.elementAt(i);
								if (process.cpudone < process.cputime
										&& previousProcess != i) {
									currentProcess = i;
								}
							}
							process = (sProcess) processVector
									.elementAt(currentProcess);
							print(currentProcess, process);
						}
						process.cpudone++;
						if (process.ioblocking > 0) {
							process.ionext++;
						}
						comptime++;
					}
				}
				process = (sProcess) processVector
						.elementAt(currentProcess);
				print(currentProcess, process);
			}
		}
		out.close();
		result.compuTime = comptime;
		return result;
	}
	
	public static void print(int currentProcess, sProcess process) {
		getStream().println("Process: " + currentProcess
				+ " registered... (" + process.cputime + " "
				+ process.ioblocking + " " + process.cpudone + " "
				+ process.cpudone + ")");
	}
}
