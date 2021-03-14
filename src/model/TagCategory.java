package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TagCategory {
	
	private String category;
	private List<Tag> taglist;
	
	public TagCategory() {
		
		this.taglist=new ArrayList<>();
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public List<Tag> getTaglist() {
		return taglist;
	}
	public void setTaglist(List<Tag> taglist) {
		this.taglist = taglist;
	}
	
	
	
	
	

}
