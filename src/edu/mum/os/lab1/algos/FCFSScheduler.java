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
		
		int previous = -1;
		boolean complete = false;
		while (context.getTick() < context.getRuntime() || !complete) {
			for (int current = 0; current < processes.size(); current++) {
				if (context.getTick() == context.getRuntime()) break;
				if (previous == current) continue;
				Process process = processes.get(current);
				if (process.cpudone < process.cputime) {
					this.schedule(process);
					previous = current;
					break;
				} else if (previous == (processes.size() - 1)) {
					complete = true;
				}
			}
		}
	}
}
