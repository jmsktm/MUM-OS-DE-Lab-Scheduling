package edu.mum.os.lab1.algos;

import java.io.FileNotFoundException;
import java.util.Vector;

import edu.mum.os.lab1.beans.Context;
import edu.mum.os.lab1.beans.Process;


public class RRScheduler extends Scheduler {

	private static final String SCHEDULING_TYPE = "Batch (Nonpreemptive)";
	private static final String SCHEDULING_NAME = "Round Robin";

	public RRScheduler() {
		super(SCHEDULING_TYPE, SCHEDULING_NAME);
	}

	public void execute() throws FileNotFoundException {		
		Context context = this.getContext();
		Vector<Process> processes = context.getProcesses();
		
		int count = 0;
		while ((context.getTick() < context.getRuntime())
				&& (count < processes.size())) {
			for (Process process : processes) {
				count++;
				if (process.cpudone < process.cputime) {
					this.schedule(process);
					count = 0;
				}
			}
		}
	}
}
