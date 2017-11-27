package com.salesforce.cli;

import java.util.Scanner;

import com.salesforce.installer.Component;
import com.salesforce.installer.Installer;

/**
 * Use this command line installer to call the following installation commands.
 * DEPEND to add dependencies for any component
 * INSTALL to install a new component
 * REMOVE to remove any exist component 
 * LIST to list all the installed component
 * END to terminate the process
 * @author sdillibabu
 *
 */
public class CommandLineInstaller {

	public static void main(String[] args) {
		inputInstallerCommand();

	}
	
	/**
	 * Reading the command line input and parse it.
	 */
	private static void inputInstallerCommand() {
		Scanner scanner = new Scanner(System.in);
		String commandLine = scanner.nextLine();
		//Case-sensitive ans should be always Upper case
		if (!commandLine.equals("END")) {
			callIstallerCommand(commandLine);
			inputInstallerCommand();
		}
	}

	/**
	 * Parsing installer command .
	 * Call the intended behavior by creating installer instance
	 */
	private static void callIstallerCommand(String commandLine) {
		String[] commandParts = commandLine.split(" ");
		Installer installer = new Installer();
		
		//getting component name from command argument
		String command = commandParts[0];
		Component component = null;
		String componentName = null;
		
		switch (command) {
		case "DEPEND":
			componentName = commandParts[1];
			component = installer.addDependency(componentName);
			for(int i = 2; i < commandParts.length; i++){
				//Parsing dependencies to component instance
				String dependencyComponent = commandParts[i];
				Component dependency = installer.addDependency(dependencyComponent);
				installer.addToAvailableDependencies(component, dependencyComponent);
				installer.addToAvailableDependents(dependency, componentName);
			}
			
			break;

		case "INSTALL":
			componentName = commandParts[1];
			installer.installComponent(componentName);
			break;

		case "REMOVE":
			componentName = commandParts[1];
			installer.removeComponent(componentName);
			break;

		case "LIST":
			installer.listInstalledComponents();
			break;

		default:
			System.out.println("Invalid command " + componentName);
		}
	}

	public Installer createInstaller(){
		return new Installer();
	}

}
