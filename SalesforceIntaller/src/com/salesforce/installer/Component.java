package com.salesforce.installer;

import java.util.HashSet;
import java.util.Set;


/**
 * Component or dependency that is installed in the syste.
 * @author sdillibabu
 *
 */
public class Component {
	private Set<String> requiredDependencies;
	private Set<String> dependents;
	private String componentName;
	
	public Component(String componentName){
		requiredDependencies = new HashSet<String>();
		dependents = new HashSet<String>();
		this.componentName = componentName; 
		
	}
	

	public void addDependency(String dependency){
		requiredDependencies.add(dependency);
	}
	
	public void addDependent(String dependent){
		dependents.add(dependent);
	}


	public Set<String> getRequiredDependencies() {
		return requiredDependencies;
	}


	public void setRequiredDependencies(Set<String> requiredDependencies) {
		this.requiredDependencies = requiredDependencies;
	}


	public Set<String> getDependents() {
		return dependents;
	}


	public void setDependents(Set<String> dependents) {
		this.dependents = dependents;
	}


	public String getComponentName() {
		return componentName;
	}


	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	
	
}
