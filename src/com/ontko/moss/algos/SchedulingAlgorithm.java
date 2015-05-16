package com.ontko.moss.algos;

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
	@SuppressWarnings("unused")
	private Vector<sProcess> processes;

	private PrintStream out = null;
	public final String REGISTERED 	= "Process: %s registered    %4s %4s %4s %4s";
	public final String COMPLETED 	= "Process: %s completed     %4s %4s %4s %4s";
	public final String BLOCKED 	= "Process: %s registered    %4s %4s %4s %4s";
	
	public SchedulingAlgorithm(String fileName, String schedulingType,
			String schedulingName, Vector<sProcess> processes)
			throws FileNotFoundException {
		this.fileName = fileName;
		this.schedulingType = schedulingType;
		this.schedulingName = schedulingName;

		FileOutputStream fos = new FileOutputStream(fileName);
		out = new PrintStream(fos);
	}

	abstract public Results run() throws FileNotFoundException;

	public void registered(int currentProcess, sProcess process)
			throws FileNotFoundException {
		print(currentProcess, process, REGISTERED);
	}

	public void completed(int currentProcess, sProcess process)
			throws FileNotFoundException {
		print(currentProcess, process, COMPLETED);
	}

	public void blocked(int currentProcess, sProcess process)
			throws FileNotFoundException {
		print(currentProcess, process, BLOCKED);
	}

	private void print(int currentProcess, sProcess process, String template)
			throws FileNotFoundException {
		out.println(String.format(template, currentProcess, process.cputime,
				process.ioblocking, process.cpudone, process.cpudone));
	}
}
