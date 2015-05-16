package com.ontko.moss.algos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import com.ontko.moss.beans.Context;
import com.ontko.moss.beans.Process;

abstract public class SchedulingAlgorithm {

	private String schedulingType;
	private String schedulingName;
	private Context context;

	private PrintStream ss = null;
	private PrintStream rs = null;

	private static final String OUTPURDIR	= "output";
	private static final String SUMMARYFILE = "Summary-Processes";
	private static final String RESULTFILE 	= "Summary-Results";
	
	private static final String REGISTERED 	= "PROCESS %-3s REGISTERED    %4s %4s %4s %4s";
	private static final String COMPLETED 	= "PROCESS %-3s COMPLETED     %4s %4s %4s %4s";
	private static final String BLOCKED 	= "PROCESS %-3s BLOCKED       %4s %4s %4s %4s";
	
	private static final String PROCESSNUM 	= "Process#";
	private static final String CPUTIME 	= "CPU Time (ms)";
	private static final String IOBLOCKING	= "IO Blocking (ms)";
	private static final String CPUCOMPLETED= "CPU Completed (ms)";
	private static final String CPUBLOCKED	= "CPU Blocked (ms)";
	
	private static final String RESULT = "%-10s %-15s %-17s %-19s %-17s";
	
	public SchedulingAlgorithm(String schedulingType,
			String schedulingName, Context context)
			throws FileNotFoundException {
		this.schedulingType = schedulingType;
		this.schedulingName = schedulingName;
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
	}

	abstract public void execute() throws FileNotFoundException;

	public void registered(int currentProcess, Process process)
			throws FileNotFoundException {
		printSummary(currentProcess, process, REGISTERED);
	}

	public void completed(int currentProcess, Process process)
			throws FileNotFoundException {
		printSummary(currentProcess, process, COMPLETED);
	}

	public void blocked(int currentProcess, Process process)
			throws FileNotFoundException {
		printSummary(currentProcess, process, BLOCKED);
	}

	private void printSummary(int currentProcess, Process process, String template)
			throws FileNotFoundException {
		String text = String.format(template, currentProcess, process.cputime,
				process.ioblocking, process.cpudone, process.cpudone);
		ss.println(text);
		System.out.println(text);
	}
	
	public void printResult() {
		StringBuilder sb = new StringBuilder();
		Context context = this.getContext();
		sb.append("Scheduling Name: " + this.schedulingName);
		sb.append("\nScheduling Type: " + this.schedulingType);
		sb.append("\nSimulation Run Time: " + context.getRuntime());
		sb.append("\nMean: " + context.getMeanDev());
		sb.append("\nStandard Deviation: " + context.getStandardDev() + "\n\n");
		
		sb.append(String.format(RESULT, PROCESSNUM, CPUTIME, IOBLOCKING, CPUCOMPLETED, CPUBLOCKED));
		
		for (Process process : context.getProcesses()) {
			sb.append("\n");
			sb.append(String.format(RESULT, process.id, process.cputime,
					process.ioblocking, process.cpudone, process.numblocked));
		}
		String text = sb.toString();
		rs.println(text);
		rs.close();
	}
	
	public Context getContext() {
		return this.context;
	}
}
