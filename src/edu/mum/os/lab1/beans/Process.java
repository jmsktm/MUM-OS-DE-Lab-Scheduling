package edu.mum.os.lab1.beans;

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
	public Context context;

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
	
	public void tick(boolean cpu) {
		if (!cpu) {
			cpuUtil.append(IDLE);
			return;
		}
		this.preExecute();
		this.execute();
		this.postExecute();
	}
	
	public void preExecute() {
		this.cpuUtil.append(EXECUTING);
		this.cpudone++;
		this.ionext++;
	}
	
	public void execute() {
		// process execution
	}
	
	public void postExecute() {
		
	}
	
	public String getCpuUtil() {
		return this.cpuUtil.toString();
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
}
