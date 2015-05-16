package com.ontko.moss.beans;

public class Process {
	
	private static final String EXECUTING	= "#";
	private static final String IDLE		= ".";
	
	public int id;
	public int cputime;
	public int ioblocking;
	public int cpudone;
	public int ionext;
	public int numblocked;
	public StringBuffer cpuUtil;

	public Process(int id, int cputime, int ioblocking, int cpudone,
			int ionext, int numblocked) {
		this.id = id;
		this.cputime = cputime;
		this.ioblocking = ioblocking;
		this.cpudone = cpudone;
		this.ionext = ionext;
		this.numblocked = numblocked;
		this.cpuUtil = new StringBuffer();
	}
	
	public void execute() {
		cpuUtil.append(EXECUTING);
		System.out.print("");
	}
	
	public void idle() {
		cpuUtil.append(IDLE);
	}
	
	public String getCpuUtil() {
		return this.cpuUtil.toString();
	}
}
