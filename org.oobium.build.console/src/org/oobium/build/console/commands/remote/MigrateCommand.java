/*******************************************************************************
 * Copyright (c) 2010 Oobium, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Jeremy Dowdall <jeremy@oobium.com> - initial API and implementation
 ******************************************************************************/
package org.oobium.build.console.commands.remote;

import static org.oobium.build.console.commands.RemoteCommand.getInstallations;
import static org.oobium.utils.coercion.TypeCoercer.coerce;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.oobium.build.console.BuilderCommand;
import org.oobium.build.exceptions.OobiumException;
import org.oobium.build.util.SSH;
import org.oobium.build.workspace.Application;
import org.oobium.build.workspace.Workspace;
import org.oobium.console.ConsolePrintStream;
import org.oobium.utils.Config;
import org.oobium.utils.Config.Mode;
import org.oobium.utils.FileUtils;
import org.oobium.utils.StringUtils;

public class MigrateCommand extends BuilderCommand {

	@Override
	public void configure() {
		applicationRequired = true;
	}

	private void deploy(Workspace ws, Application app, File exportDir) throws OobiumException, IOException {
		if(app.site == null || !app.site.isFile()) {
			console.err.println("site configuration file (site.js) cannot be found for " + app);
			return;
		}
		
		Config config = Config.loadConfiguration(app.site);
		
		String host = config.getString("host");
		String dir = config.getString("path");
		String username = config.getString("username");
		String password = config.getString("password");
		boolean sudo = coerce(config.get("sudo"), false);
		
		final SSH ssh = new SSH(host, username, password);
		ssh.setOut(new ConsolePrintStream(console.out));
		ssh.setErr(new ConsolePrintStream(console.err));
		
		String remote = config.getString("name", app.name);
		String data = "data";
		if(dir != null) {
			remote = dir + "/" + remote;
			data = dir + "/" + data;
		}

		String migrator = remote + "_migrator";
		String[] previous = getInstallations(ssh, remote);

		String current = (previous == null || previous.length == 0) ? null : previous[0];
		
		// deploy migrator
		if(flag('f')) {
			console.out.println("force install requested - performing full upload");
			ssh.copy(exportDir, migrator);
		} else {
			if(current == null) {
				console.out.println("no app installation found - performing full upload");
				ssh.copy(exportDir, migrator);
			} else {
				console.out.println("app installation found (" + current + ") - performing update");
				update(ssh, exportDir, current, migrator);
			}
		}
		
		// stop application
		if(current != null) {
			ssh.setSudo(sudo);
			ssh.exec("./stop.sh", current);
			ssh.setSudo(false);
		}
		
		// run migrator
		ssh.exec("java -jar bin/felix.jar", migrator);
		
		// start application
		if(current != null) {
			ssh.setSudo(sudo);
			ssh.exec("nohup ./start.sh", current);
			ssh.setSudo(false);
		}
		
		// output info
		ssh.exec("ps aux | grep felix");
		ssh.setSudo(sudo);
		ssh.exec("cat nohup.out", migrator);
		ssh.setSudo(false);

		// remove migrator and migrator database
		ssh.exec("rm -r " + migrator);
	}
	
	@Override
	public void run() {
		Workspace ws = getWorkspace();
		Application app = getApplication();

		Mode mode = hasParam("mode") ? Mode.parse(param("mode")) : Mode.PROD;

		if(flag('v')) {
			console.out.println("deploying in " + mode + " mode");
			console.capture();
		}
		
		try {
			long start = System.currentTimeMillis();

			ws.cleanExport(app);
			File exportDir = ws.exportMigrator(app, mode);
			
			String msg = "exported <a href=\"open file " + exportDir + "\">" + app.name() + "</a>";
			if(flag('v')) {
				console.out.println(msg + " in " + (System.currentTimeMillis() - start) + "ms");
			} else {
				console.out.println(msg);
			}

			deploy(ws, app, exportDir);
		} catch(Exception e) {
			console.err.print(e);
		} finally {
			if(flag('v')) {
				console.release();
			}
		}
	}
	
	private void update(SSH ssh, File exportDir, String previous, String current) throws IOException, OobiumException {
		Set<String> created = new HashSet<String>();
		ssh.exec("mkdir -p " + current);
		created.add(current);

		// copy over felix bin folder and file
		ssh.exec("cp -rp " + previous + "/bin " + current);

		// find all bundles in previous installation
		Set<String> inPrevious = new HashSet<String>();
		String[] sa = ssh.exec("ls -d " + previous + "/bundles/*").split("\\s+");
		for(String s : sa) {
			inPrevious.add(s.substring(s.lastIndexOf('/') + 1));
		}

		List<String> localCopy = new ArrayList<String>();
		Map<File, String> remoteCopy = new LinkedHashMap<File, String>();
		
		int len = exportDir.getAbsolutePath().length();
		File[] files = FileUtils.findAll(exportDir);
		for(File file : files) {
			String name = file.getName();
			if(!name.equals("felix.jar")) {
				// create directory if necessary
				String dpath = (current + file.getParent().substring(len)).replace('\\', '/');
				if(!created.contains(dpath)) {
					ssh.exec("mkdir -p " + dpath);
					created.add(dpath);
				}
				// copy file, either from local export directory or previous installation
				if(inPrevious.contains(name)) {
					localCopy.add(previous + "/bundles/" + name);
//					ssh.exec("cp -p " + previous + "/bundles/" + name + " " + current + "/bundles/");
				} else {
					String fpath = (current + file.getPath().substring(len)).replace('\\', '/');
					remoteCopy.put(file, fpath);
//					ssh.copy(file, fpath);
				}
			}
		}
		
		ssh.exec("cp -p " + StringUtils.join(localCopy, ' ') + " " + current + "/bundles/");
		
		for(Entry<File, String> entry : remoteCopy.entrySet()) {
			ssh.copy(entry.getKey(), entry.getValue());
		}
	}
	
}