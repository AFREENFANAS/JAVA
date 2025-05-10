
package com.hospitalmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Hospitalmanagement {

	private static final String url="jdbc:mysql://localhost:3306/hospitalm";
	private static final String username="root";
	private static final String password="root";
	
	public static void main(String args[]) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(Exception e) {
			
		}
		Scanner sc=new Scanner(System.in);
		try {
			Connection connection=DriverManager.getConnection(url,username,password);
			System.out.println("connected db");
			Patient patient=new Patient(connection,sc);
			Doctor doctor=new Doctor(connection);
			while(true) {
				System.out.println("Welcome to ABC Hospital Management");
				System.out.println("1,Add patient");
				System.out.println("2,View patient");
				System.out.println("3,View Doctors");
				System.out.println("4,book Appointment");
				System.out.println("5,Exit Patient");
				
				System.out.println("Enter your Choice");
				int choice=sc.nextInt();
				
				switch(choice) {
				case 1:
					patient.addPatient();
					break;
				case 2:
					patient.viewPatient();
					break;
				case 3:
					doctor.viewDoctor();
					break;
				case 4:
					bookAppointment(patient,doctor,connection,sc);
				    System.out.println();
					break;
				case 5:
					return;
				default:
					System.out.println("Invalid request");
					break;
					
				}
			}
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void bookAppointment(Patient patient,Doctor doctor,Connection connection,Scanner sc) {
		System.out.println("Enter the Patient Id: ");
		int patientId=sc.nextInt();
		
		System.out.println("Enter the Patient Name: ");
		String patientName=sc.next();
		
		System.out.println("Enter the Doctor Id: ");
		int doctorId=sc.nextInt();
		
		System.out.println("Enter the Appointment Date(YYYY-MM-DD) ");
		String appointmentDate=sc.next();
		
		if(patient.getPatientId(patientId)&&doctor.getDoctorId(doctorId)) {
			if(checkDoctorAvailability(doctorId,appointmentDate,connection)) {
				String appointmentquery="INSERT into appointments(patient_id,patient_name,doctor_id,appointment_date) values (?,?,?,?)";
				try {
					PreparedStatement ps=connection.prepareStatement(appointmentquery);
					ps.setInt(1,patientId);
					ps.setString(2,patientName);
					ps.setInt(3,doctorId);
					ps.setString(4,appointmentDate);
					int rowsaffected=ps.executeUpdate();
					if(rowsaffected>0) {
						System.out.println("Appointment Booked");
					}
					else {
						System.out.println("Failed To book appointment");
					}
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static boolean checkDoctorAvailability(int doctorId,String appointmentDate,Connection connection) {
		String query="select count(*) from appointments where doctor_id=? and appointment_date=?";
		try {
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setInt(1, doctorId);
			ps.setString(2, appointmentDate);
			ResultSet r= ps.executeQuery();
			if(r.next()) {
				int count=r.getInt(1);
				if(count==0)
					return true;
				else
					return false;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
