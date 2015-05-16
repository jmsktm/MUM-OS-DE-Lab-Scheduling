package edu.mum.os.lab1.algos;

import java.io.FileNotFoundException;
import java.util.Vector;

import edu.mum.os.lab1.beans.Context;
import edu.mum.os.lab1.beans.Process;


public class FCFSScheduler extends Scheduler {

	private static final String SCHEDULING_TYPE = "Batch (Nonpreemptive)";
	private static final String SCHEDULING_NAME = "First-Come First-Served";

	public FCFSScheduler() {
		super(SCHEDULING_TYPE, SCHEDULING_NAME);
	}

	public void execute() throws FileNotFoundException {		
		Context context = this.getContext();
		Vector<Process> processes = context.getProcesses();
		
		while (context.getTick() < context.getRuntime()) {
			for (Process process : processes) {
				if (process.cpudone < process.cputime) {
					this.schedule(process);
					break;
				}
			}
		}
		/*int execTotal = this.getContext().getRuntime();
		
		int i = 0;
		int comptime = 0;
		int currentProcess = 0;
		int previousProcess = 0;
		int size = processes.size();

		@SuppressWarnings("unused")
		Process process = (Process) processes.elementAt(currentProcess);
		registered(currentProcess, process);
		while (comptime < execTotal) {
			if (process.cpudone == process.cputime) {
				completed(currentProcess, process);
				for (i = size - 1; i >= 0; i--) {
					process = (Process) processes.elementAt(i);
					if (process.cpudone < process.cputime) {
						currentProcess = i;
					}
				}
				process = (Process) processes
						.elementAt(currentProcess);
				registered(currentProcess, process);
			}
			if (process.ioblocking == process.ionext) {
				blocked(currentProcess, process);
				process.numblocked++;
				process.ionext = 0;
				previousProcess = currentProcess;
				for (i = size - 1; i >= 0; i--) {
					process = (Process) processes.elementAt(i);
					if (process.cpudone < process.cputime
							&& previousProcess != i) {
						currentProcess = i;
					}
				}
				process = (Process) processes
						.elementAt(currentProcess);
				registered(currentProcess, process);
			}
			this.schedule(process);
			process.cpudone++;
			if (process.ioblocking > 0) {
				process.ionext++;
			}
			comptime++;
		}*/
	}
	
	public Process getNextProcess() {
		return null;
	}
}
