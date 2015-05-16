package edu.mum.os.lab1.algos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import edu.mum.os.lab1.beans.Context;
import edu.mum.os.lab1.beans.Process;

abstract public class Scheduler {

	private String schedulingType;
	private String schedulingName;
	private Context context;

	private PrintStream ss = null;
	private PrintStream rs = null;
	private PrintStream gc = null;

	private static final String OUTPURDIR	= "output";
	private static final String SUMMARYFILE = "Summary-Processes";
	private static final String RESULTFILE 	= "Summary-Results";
	private static final String GANTTCHART 	= "Gantt-Chart";
	
	private static final String REGISTERED 	= "PROCESS %-3s REGISTERED    %4s %4s %4s %4s";
	private static final String COMPLETED 	= "PROCESS %-3s COMPLETED     %4s %4s %4s %4s";
	private static final String BLOCKED 	= "PROCESS %-3s BLOCKED       %4s %4s %4s %4s";
	
	private static final String PROCESSNUM 	= "Process#";
	private static final String CPUTIME 	= "CPU Time (ms)";
	private static final String IOBLOCKING	= "IO Blocking (ms)";
	private static final String CPUCOMPLETED= "CPU Completed (ms)";
	private static final String CPUBLOCKED	= "CPU Blocked (ms)";
	
	private static final String RESULT = "%-10s %-15s %-17s %-19s %-17s";
	private static final String GANTT_RESULT = "PROCESS #%3s : %s";
	
	public Scheduler(String schedulingType,
			String schedulingName) {
		this.schedulingType = schedulingType;
		this.schedulingName = schedulingName;
	}
	
	public void setContext(Context context) {
		try {
			this.context = context;
			String folderName = OUTPURDIR + File.separator + schedulingName
					+ File.separator + schedulingType + File.separator
					+ "PROCESSCOUNT-" + context.getProcesses().size();
			File folder = new File(folderName);
			folder.mkdirs();		
		
			FileOutputStream sfos = new FileOutputStream(folder + File.separator + SUMMARYFILE);
			ss = new PrintStream(sfos);
			
			FileOutputStream rfos = new FileOutputStream(folder + File.separator + RESULTFILE);
			rs = new PrintStream(rfos);
			
			FileOutputStream gfos = new FileOutputStream(folder + File.separator + GANTTCHART);
			gc = new PrintStream(gfos);
		} catch (FileNotFoundException e) {
		}
	}

	abstract public void execute() throws FileNotFoundException;
	
	public void schedule(Process process) throws FileNotFoundException {
		while (context.getTick() < context.getRuntime()
				&& process.cpudone < process.cputime 
				&& process.ionext < process.ioblocking) {
			if (process.cpudone == 0) registered(process);
			context.tick();
			for (Process proc : this.getContext().getProcesses()) {
				proc.tick(proc == process);
			}
			if (process.cpudone == process.cputime) {
				completed(process);
			}
		}
		if (process.ionext == process.ioblocking) {
			process.ionext = 0;
			process.numblocked++;
			blocked(process);
		}
	}

	public void registered(Process process) throws FileNotFoundException {
		printSummary(process, REGISTERED);
	}

	public void completed(Process process) throws FileNotFoundException {
		printSummary(process, COMPLETED);
	}

	public void blocked(Process process) throws FileNotFoundException {
		printSummary(process, BLOCKED);
	}

	private void printSummary(Process process, String template)
			throws FileNotFoundException {
		String text = String.format(template, process.id, process.cputime,
				process.ioblocking, process.cpudone, process.cpudone);
		ss.println(text);
		System.out.println(text);
		ss.close();
	}
	
	public void printResult() {
		StringBuilder sb = new StringBuilder();
		Context context = this.getContext();
		sb.append("\nScheduling Name: " + this.schedulingName);
		sb.append("\nScheduling Type: " + this.schedulingType);
		sb.append("\nSimulation Run Time: " + context.getTick());
		sb.append("\nMean: " + context.getMeanDev());
		sb.append("\nStandard Deviation: " + context.getStandardDev() + "\n\n");
		
		sb.append(String.format(RESULT, PROCESSNUM, CPUTIME, IOBLOCKING, CPUCOMPLETED, CPUBLOCKED));
		
		for (Process process : context.getProcesses()) {
			sb.append("\n");
			sb.append(String.format(RESULT, process.id, process.cputime,
					process.ioblocking, process.cpudone, process.numblocked));
		}
		String text = sb.toString();
		System.out.println(text);
		rs.println(text);
		rs.close();
	}
	
	public void printGanttChart() {
		for (Process process : this.getContext().getProcesses()) {
			gc.println(String.format(GANTT_RESULT, process.id, process.getCpuUtil()));
		}
		gc.close();
	}
	
	public Context getContext() {
		return this.context;
	}
}
