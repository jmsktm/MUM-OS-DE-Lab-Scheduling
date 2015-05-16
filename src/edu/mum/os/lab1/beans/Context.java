package edu.mum.os.lab1.beans;

import java.util.Vector;

public class Context {
	
	private Vector<Process> processes = new Vector<Process>();
	
	private int numprocess;
	private int meanDev;
	private int standardDev;
	private int runtime;
	
	private int tick;
	
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
	public void tick() {
		this.tick++;
	}
	public int getTick() {
		return this.tick;
	}
}
