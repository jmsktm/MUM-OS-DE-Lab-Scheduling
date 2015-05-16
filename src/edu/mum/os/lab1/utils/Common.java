package edu.mum.os.lab1.utils;

/**
 * @author James Singh
 * Referenced base code at: http://www.ontko.com/moss
 */

import java.io.File;

public class Common {
	
  static public String ALGO_PKG_TEMPLATE = "edu.mum.os.lab1.algos.%sScheduler";

  static public int s2i (String s) {
    int i = 0;

    try {
      i = Integer.parseInt(s.trim());
    } catch (NumberFormatException nfe) {
      System.out.println("NumberFormatException: " + nfe.getMessage());
    }
    return i;
  }

  static public double R1 () {
    java.util.Random generator = new java.util.Random(System.currentTimeMillis());
    double U = generator.nextDouble();
    while (U < 0 || U >= 1) {
      U = generator.nextDouble();
    }
    double V = generator.nextDouble();
    while (V < 0 || V >= 1) {
      V = generator.nextDouble();
    }
    double X =  Math.sqrt((8/Math.E)) * (V - 0.5)/U;
    if (!(R2(X,U))) { return -1; }
    if (!(R3(X,U))) { return -1; }
    if (!(R4(X,U))) { return -1; }
    return X;
  }

  static public boolean R2 (double X, double U) {
    if ((X * X) <= (5 - 4 * Math.exp(.25) * U)) {
      return true;
    } else {
      return false;
    }
  }

  static public boolean R3 (double X, double U) {
    if ((X * X) >= (4 * Math.exp(-1.35) / U + 1.4)) {
      return false;
    } else {
      return true;
    }
  }

  static public boolean R4 (double X, double U) {
    if ((X * X) < (-4 * Math.log(U))) {
      return true;
    } else {
      return false;
    }
  }
  
  static public void validateArgs(String args[]) throws Exception {
	if (args.length != 2) {
		throw new Exception("Usage: 'java Scheduling <Algorithm> <conf. filename>' Eg. 'java Scheduling FCFS scheduling.conf");
	}
	
	String algo = getSchedulerCanonicalName(args[0]);
	try {
		Class.forName(algo);
	} catch (ClassNotFoundException e) {
		throw new Exception(String.format("No algorithm by name '%s'", algo));
	}
	
	
	File f = new File(args[1]);
	if (!(f.exists())) {
		throw new Exception("Scheduling: error, file '" + f.getName()
				+ "' does not exist.");
	}
	if (!(f.canRead())) {
		throw new Exception("Scheduling: error, read of " + f.getName()
				+ " failed.");
	}
  }
  
  static public String getSchedulerCanonicalName(String shortName) {
	  return String.format(ALGO_PKG_TEMPLATE, shortName);
  }
}
