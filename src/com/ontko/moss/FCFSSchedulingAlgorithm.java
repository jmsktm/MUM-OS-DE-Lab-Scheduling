package com.ontko.moss;

/**
 * @author James Singh
 * Referenced base code at: http://www.ontko.com/moss
 */

import java.util.Vector;
import java.io.*;

import com.ontko.moss.algos.SchedulingAlgorithm;


public class FCFSSchedulingAlgorithm extends SchedulingAlgorithm {

	private static final String FILENAME = "Summary-Processes";
	private static final String SCHEDULING_TYPE = "Batch (Nonpreemptive)";
	private static final String SCHEDULING_NAME = "First-Come First-Served";

	private int execTotal = 0;
	private Vector<sProcess> processes;

	public FCFSSchedulingAlgorithm(int execTotal, Vector<sProcess> processes)
			throws FileNotFoundException {
		super(FILENAME, SCHEDULING_TYPE, SCHEDULING_NAME, processes);
		this.execTotal = execTotal;
		this.processes = processes;
	}

	public Results run() throws FileNotFoundException {
		Results result = new Results(SCHEDULING_TYPE, SCHEDULING_NAME, execTotal);
		
		int i = 0;
		int comptime = 0;
		int currentProcess = 0;
		int previousProcess = 0;
		int size = processes.size();
		int completed = 0;

		sProcess process = (sProcess) processes
				.elementAt(currentProcess);
		registered(currentProcess, process);
		while (comptime < execTotal) {
			if (process.cpudone == process.cputime) {
				completed++;
				completed(currentProcess, process);
				if (completed == size) {
					result.compuTime = comptime;
					return result;
				}
				for (i = size - 1; i >= 0; i--) {
					process = (sProcess) processes.elementAt(i);
					if (process.cpudone < process.cputime) {
						currentProcess = i;
					}
				}
				process = (sProcess) processes
						.elementAt(currentProcess);
				registered(currentProcess, process);
			}
			if (process.ioblocking == process.ionext) {
				blocked(currentProcess, process);
				process.numblocked++;
				process.ionext = 0;
				previousProcess = currentProcess;
				for (i = size - 1; i >= 0; i--) {
					process = (sProcess) processes.elementAt(i);
					if (process.cpudone < process.cputime
							&& previousProcess != i) {
						currentProcess = i;
					}
				}
				process = (sProcess) processes
						.elementAt(currentProcess);
				registered(currentProcess, process);
			}
			process.cpudone++;
			if (process.ioblocking > 0) {
				process.ionext++;
			}
			comptime++;
		}
			
		result.compuTime = comptime;
		return result;
	}
}
