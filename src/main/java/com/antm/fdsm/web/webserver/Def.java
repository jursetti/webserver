package com.antm.fdsm.web.webserver;

import java.util.Arrays;
import java.util.List;

import com.antm.fdsm.orcl.utils.Helpers;

public class Def {

	//public final static String SLACK_SERVER_WEBHOOKS = "hooks.slack.com";
	//public final static int SLACK_SERVER_PORT = 443;
	//public final static String SLACK_WEBHOOK_APP = "/services/TBNP5JXQT/BCCRW1EGM/km8nJrq6LqmOML8irXRKr8Ir";
	public final static String SLACK_WEBHOOK_APP = "/services/TBNP5JXQT/BC27FMF2T/O3Yycwp0lZc8cI5qau1h9RzS";

	public final static String HOME = Helpers.getHome();
	public static final String DIR_PROJECT = "dualutil";
	public static final String DIR_BKP = DIR_PROJECT + "/" +"bkp";
	public static final String DIR_TMP = DIR_PROJECT +"/" +"tmp";
	public static final String DIR_LOG = DIR_PROJECT + "/" + "log";
	public static final String DIR_DATA = DIR_PROJECT +"/" +"data";
	public static final List<String> DIRS = Arrays.asList(DIR_PROJECT,DIR_BKP,DIR_TMP,DIR_DATA,DIR_LOG);

	public static final String LINE = getDivider("-",100);
	public static final String DLINE = getDivider("=",100);
	public static final String SLINE = getDivider("*",100);
	
	private static String getDivider(String symbol, int length) {
		StringBuilder builder = new StringBuilder(symbol.length() * length);
		for (int i = 0; i < length; i++) {
		  builder.append(symbol);
		}
		return builder.toString();
	}

}
