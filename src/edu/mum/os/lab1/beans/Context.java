package edu.mum.os.lab1.beans;

/**
 * @author James Singh
 * Referenced base code at: http://www.ontko.com/moss
 */

import java.util.Vector;

public class Context {
	
	private int numprocess;
	private int meanDev;
	private int standardDev;
	private int runtime;
	
	private Vector<Process> processes = new Vector<Process>();
	
	public int getNumprocess() {
		return numprocess;
	}
	public void setNumprocess(int numprocess) {
		this.numprocess = numprocess;
	}
	public int getMeanDev() {
		return meanDev;
	}
	public void setMeanDev(int meanDev) {
		this.meanDev = meanDev;
	}
	public int getStandardDev() {
		return standardDev;
	}
	public void setStandardDev(int standardDev) {
		this.standardDev = standardDev;
	}
	public int getRuntime() {
		return runtime;
	}
	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}
	public Vector<Process> getProcesses() {
		return processes;
	}
	public void addProcess(Process process) {
		this.processes.add(process);
	}
}
