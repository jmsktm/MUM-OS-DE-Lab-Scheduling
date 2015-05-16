package com.ontko.moss.algos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Vector;

import com.ontko.moss.Results;
import com.ontko.moss.sProcess;

abstract public class SchedulingAlgorithm {
	
	@SuppressWarnings("unused")
	private String fileName;
	@SuppressWarnings("unused")
	private String schedulingType;
	@SuppressWarnings("unused")
	private String schedulingName;
	private Vector<sProcess> processes;
	private Results result;

	public void setResults(Results result) {
		this.result = result;
	}

	private PrintStream ss = null;
	private PrintStream rs = null;

	private static final String OUTPURDIR	= "output";
	private static final String SUMMARYFILE = "Summary-Processes";
	private static final String RESULTFILE = "Summary-Results";
	
	private static final String REGISTERED 	= "Process %s registered    %4s %4s %4s %4s";
	private static final String COMPLETED 	= "Process %s completed     %4s %4s %4s %4s";
	private static final String BLOCKED 	= "Process %s registered    %4s %4s %4s %4s";
	
	public SchedulingAlgorithm(String schedulingType,
			String schedulingName, Vector<sProcess> processes)
			throws FileNotFoundException {
		this.schedulingType = schedulingType;
		this.schedulingName = schedulingName;

		String folderName = OUTPURDIR + File.separator + schedulingName
				+ File.separator + schedulingType + File.separator
				+ "PROCESSCOUNT-" + processes.size();
		File folder = new File(folderName);
		folder.mkdirs();
		
		FileOutputStream sfos = new FileOutputStream(folder + File.separator + SUMMARYFILE);
		ss = new PrintStream(sfos);
		
		FileOutputStream rfos = new FileOutputStream(folder + File.separator + RESULTFILE);
		rs = new PrintStream(rfos);
	}

	abstract public void execute() throws FileNotFoundException;

	public void registered(int currentProcess, sProcess process)
			throws FileNotFoundException {
		printSummary(currentProcess, process, REGISTERED);
	}

	public void completed(int currentProcess, sProcess process)
			throws FileNotFoundException {
		printSummary(currentProcess, process, COMPLETED);
	}

	public void blocked(int currentProcess, sProcess process)
			throws FileNotFoundException {
		printSummary(currentProcess, process, BLOCKED);
	}

	private void printSummary(int currentProcess, sProcess process, String template)
			throws FileNotFoundException {
		ss.println(String.format(template, currentProcess, process.cputime,
				process.ioblocking, process.cpudone, process.cpudone));
	}
	
	protected void printResult() {
		rs.println("Scheduling Type: " + result.schedulingType);
		rs.println("Scheduling Name: " + result.schedulingName);
		rs.println("Simulation Run Time: " + result.compuTime);
		rs.println("Mean: meanDev");
		rs.println("Standard Deviation: standardDev");
		rs.println("Process #\tCPU Time\tIO Blocking\tCPU Completed\tCPU Blocked");
		for (int i = 0; i < processes.size(); i++) {
			sProcess process = (sProcess) processes.elementAt(i);
			rs.print(Integer.toString(i));
			if (i < 100) {
				rs.print("\t\t");
			} else {
				rs.print("\t");
			}
			rs.print(Integer.toString(process.cputime));
			if (process.cputime < 100) {
				rs.print(" (ms)\t\t");
			} else {
				rs.print(" (ms)\t");
			}
			rs.print(Integer.toString(process.ioblocking));
			if (process.ioblocking < 100) {
				rs.print(" (ms)\t\t");
			} else {
				rs.print(" (ms)\t");
			}
			rs.print(Integer.toString(process.cpudone));
			if (process.cpudone < 100) {
				rs.print(" (ms)\t\t");
			} else {
				rs.print(" (ms)\t");
			}
			rs.println(process.numblocked + " times");
		}
		rs.close();
	}
}
