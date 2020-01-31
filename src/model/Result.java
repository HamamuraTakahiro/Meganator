package model;

import java.io.Serializable;

public class Result implements Serializable {

	private int id;
	private String text;
	private int happy;
	private int mad;
	private int sad;
	private int joy;
	private String imageName;

	public Result() {}
	public Result(int id, String text, int happy, int mad, int sad, int joy,String imageName) {
		this.id = id;
		this.text = text;
		this.happy = happy;
		this.mad = mad;
		this.sad = sad;
		this.joy = joy;
		this.imageName = imageName;
	}


	public int getId() { return id;	}

	public void setId(int id) { this.id = id;}

	public String getText() { return text; }

	public void setText(String text) { this.text = text; }

	public int getHappy() { return happy; }

	public void setHappy(int happy) { this.happy = happy; }

	public int getMad() { return mad; }

	public void setMad(int mad) { this.mad = mad; }

	public int getSad() { return sad; }

	public void setSad(int sad) { this.sad = sad; }

	public int getJoy() { return joy; }

	public void setJoy(int joy) { this.joy = joy; }

	public String getImageName() { return imageName; }

	public void setImageName(String imageName) { this.imageName = imageName; }

}
