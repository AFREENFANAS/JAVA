package com.hospitalmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Doctor {
   
	private Connection connection;
	
	public Doctor(Connection connection) {
		this.connection=connection;
	}
	
	public void viewDoctor() {
		String query="SELECT * from doctors";
		try {
			PreparedStatement ps=connection.prepareStatement(query);
			ResultSet r=ps.executeQuery();
			System.out.println("Doctor");
			System.out.println("-------------********------------");
			System.out.println("| doctor id | Name | dept ");
			while(r.next()) {
				int id= r.getInt("id");
				String name=r.getString("name");
				String department=r.getString("dept");
				System.out.printf("|%-10s|%-7s|%-14s|\n",id,name,department);//format method for print
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean getDoctorId(int id) {
		String query="SELECT * from doctors where id=?";
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
