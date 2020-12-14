package com.example.utils;

public class ExamStudent {
	private int Type;
	private String IDCard;
	private String ExamCard;
	private String StudentName;
	private String Location;
	private int Grade;
	private int FlowId;
	public int getType() {
		return Type;
	}
	public void setType(int type) {
		Type = type;
	}
	public String getIDCard() {
		return IDCard;
	}
	public void setIDCard(String iDCard) {
		IDCard = iDCard;
	}
	public String getExamCard() {
		return ExamCard;
	}
	public void setExamCard(String examCard) {
		ExamCard = examCard;
	}
	public String getStudentName() {
		return StudentName;
	}
	public void setStudentName(String studentName) {
		StudentName = studentName;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public int getGrade() {
		return Grade;
	}
	public void setGrade(int grade) {
		Grade = grade;
	}
	public int getFlowId() {
		return FlowId;
	}
	public void setFlowId(int flowId) {
		FlowId = flowId;
	}
	@Override
	public String toString() {
		return "ExamStudent [Type=" + Type + ", IDCard=" + IDCard + ", ExamCard=" + ExamCard + ", StudentName="
				+ StudentName + ", Location=" + Location + ", Grade=" + Grade + ", FlowId=" + FlowId + "]";
	}
	public ExamStudent(int type, String iDCard, String examCard, String studentName, String location, int grade,
			int flowId) {
		super();
		Type = type;
		IDCard = iDCard;
		ExamCard = examCard;
		StudentName = studentName;
		Location = location;
		Grade = grade;
		FlowId = flowId;
	}
	public ExamStudent() {
		super();
	} 
	
	
	
	

}
