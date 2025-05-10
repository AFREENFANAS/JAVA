package com.hospitalmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
public class Patient {

	private Connection connection;
	private Scanner sc;
	
	public Patient(Connection connection,Scanner sc) {
		this.connection=connection;
		this.sc=sc;
	}
	
	public void addPatient() {
		System.out.println("Enter the Patient Details : ");
		String name=sc.next();
		System.out.println("Enter Patient Age : ");
		int age=sc.nextInt();
		System.out.println("Enter Patient Gender : ");
		String gender=sc.next();
		
		try {
			String query="INSERT INTO patients(name,age,gender) VALUES(?,?,?)";
			PreparedStatement ps=connection.prepareStatement(query);//statement set the value and execute the statement
			ps.setString(1, name);//1 index name
			ps.setInt(2, age);//2 index age
			ps.setString(3, gender);//3 index gender
			
			int affectedRows=ps.executeUpdate();//for insert  the value so use executeupdate method or select statemt use execute query
			
			if(affectedRows>0) {
				System.out.println("Patient Added");
			}
			else {
				System.out.println("Failed");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void viewPatient() {
		String query="SELECT * from patients";
		try {
			PreparedStatement ps=connection.prepareStatement(query);
			ResultSet r=ps.executeQuery();
			System.out.println("Patients");
			System.out.println("-------------********------------");
			System.out.println("|patient id | Name    | Age | Gender |");
			while(r.next()) {
				int id= r.getInt("id");
				String name=r.getString("name");
				int age=r.getInt("age");
				String gender=r.getString("gender");
				System.out.printf("|%-11s|%-8s|%-8s|%-10s|\n",id,name,age,gender);//format method for print
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean getPatientId(int id) {
		
		String query="SELECT * from patients where id=?";
		try {
		PreparedStatement ps=connection.prepareStatement(query);
		ps.setInt(1, id);
		ResultSet r=ps.executeQuery();
		
		if(r.next())
			return true;
		else
			return false;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
