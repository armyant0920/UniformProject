package model;

import java.util.Random;

public class TagExam2 {

	public static void main(String[] args) {
		TagCategory tc1 = new TagCategory();
		TagCategory tc2 = new TagCategory();
		tc1.setCategory("category1");
		tc2.setCategory("category2");
		tc1.getTaglist().add(new Tag("Hello"));
		tc1.getTaglist().add(new Tag("Hi"));
		tc1.getTaglist().add(new Tag("Greeting"));
		tc2.getTaglist().add(new Tag("EMMA"));
		tc2.getTaglist().add(new Tag("Kevin"));
		tc2.getTaglist().add(new Tag("Julia"));
		String result = "";
		Tag tag1 = tc1.getTaglist().get((new Random()).nextInt(tc1.getTaglist().size()));
		Tag tag2 = tc2.getTaglist().get((new Random()).nextInt(tc1.getTaglist().size()));
		System.out.println(String.valueOf(tag1.getItem()) + tag2.getItem());
	}
}
