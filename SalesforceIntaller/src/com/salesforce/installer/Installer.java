package com.salesforce.installer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Installer {

	static Map<String, Component> availableComponents = new HashMap<String, Component>();
	
	static Set<String> insatlledComponents = new HashSet<String>();
	
	static Set<String> installedDependencies = new HashSet<String>();

	private static final String ALREADY_INSTALLED = " is already installed.";
	private static final String STILL_NEEDED = " is still needed.";
	private static final String INSTALLING = "Installing ";
	private static final String REMOVING = "Removing ";

	public void addToAvailableDependencies(Component component, String dependency){
		component.addDependency(dependency);
		installedDependencies.add(dependency);
	}
	
	public void addToAvailableDependents(Component component, String dependent){
		component.addDependent(dependent);
	}
	
	/**
	 * This API will be called when you want to add any dependency
	 * @param componentName
	 * @return
	 */
	public static Component addDependency(String componentName){
		return getComponent(componentName);
	}
	
	/**
	 * Linking dependencies and dependents during DEPEND command
	 * @param componentName
	 * @return
	 */
	public static Component getComponent(String componentName){
		if(isComponentLinked(componentName)){
			return availableComponents.get(componentName);
		}
		else{
			Component component = new Component(componentName);
			availableComponents.put(componentName, component);
			return component;
		}
	}
	
	
	public static Component getAvailableComponent(String componentName){
			return availableComponents.get(componentName);
	}
	
	
	/**
	 * Use this API to while installing new component to a sustem
	 * @param componentName
	 */
	public static  void installComponent(String componentName) {
		//Linking component
		Component component = getComponent(componentName);
		if(!insatlledComponents.add(componentName)){
			System.out.println(componentName+ALREADY_INSTALLED);
			return;
		}
		
		//Installing required dependencies
		Set<String> requiredDependencies = component.getRequiredDependencies();
		for(String dependency: requiredDependencies){
			if(insatlledComponents.add(dependency)){
				System.out.println(INSTALLING+dependency);
			}
			
		}
		
		System.out.println(INSTALLING+componentName);

	}
	
	/**
	 * Use this API while removing a component (This will look for possible dependents in the component class before deleting it)
	 * @param component
	 */
	public static  void removeComponent(String componentName) {
		Component component = getAvailableComponent(componentName);
		Set<String> dependants = component.getDependents();
		if(dependants.size() > 0){
			System.out.println(componentName+STILL_NEEDED);
		}
		else{
			Set<String> dependencies = component.getRequiredDependencies();
			//Clearing dependencies 
			for(String dependency: dependencies){
				Component dependant = getAvailableComponent(dependency);
				removeDependency(dependant, component);
			}
			insatlledComponents.remove(componentName);
			System.out.println(REMOVING);
		}
	}
	
	/**
	 * Removing dependency from the component
	 * @param dependant
	 * @param component
	 */
	public static void removeDependency(Component dependant, Component component){
		Set<String> dependants = dependant.getDependents();
		dependants.remove(component.getComponentName());
	}
	
	
	/**
	 * This API will list all the installed components in a system
	 */
	public static void listInstalledComponents(){
		for(String component : insatlledComponents){
			System.out.println(component);
		}
	}
	
	public static boolean isComponentLinked(String componentName){
		return availableComponents.containsKey(componentName);
	}
	
	public static boolean isComponentInstalled(String componentName){
		return insatlledComponents.contains(componentName);
	}
}
