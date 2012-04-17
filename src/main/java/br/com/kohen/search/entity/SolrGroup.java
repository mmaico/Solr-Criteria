package br.com.kohen.search.entity;

public class SolrGroup {

	private String groupName;
	
	private Long numFound;

	public SolrGroup(String groupName, Long numFound) {
		this.groupName = groupName;
		this.numFound = numFound;
	}
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Long getNumFound() {
		return numFound;
	}

	public void setNumFound(Long numFound) {
		this.numFound = numFound;
	}
	
	
}
