package model;

import java.io.Serializable;

public class Question implements Serializable {

	private int id;
	private String question_text;
	private int happy;
	private int mad;
	private int sad;
	private int joy;

	public Question() {}
	public Question(int id, String question_text, int happy, int mad, int sad, int joy) {
		this.id = id;
		this.question_text = question_text;
		this.happy = happy;
		this.mad = mad;
		this.sad = sad;
		this.joy = joy;
	}


	public int getId() { return id; }

	public void setId(int id) { this.id = id; }

	public String getQuestion_text() { return question_text; }

	public void setQuestion_text(String question_text) { this.question_text = question_text; }

	public int getHappy() { return happy; }

	public void setHappy(int happy) { this.happy = happy; }

	public int getMad() { return mad; }

	public void setMad(int mad) { this.mad = mad; }

	public int getSad() { return sad; }

	public void setSad(int sad) { this.sad = sad; }

	public int getJoy() { return joy; }

	public void setJoy(int joy) { this.joy = joy; }

}
