package com.mariusz.janus.DetectOutlierRules.domain;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestRestTemplate {

	private static final String FILE_PATH = "C:/Users/Mariusz Janus/Desktop/abc.xlsx";
	public static void main(String[]args)
	{
		/*
		RestTemplate rest = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization","Bearer "+"c5c3940b-5889-4406-b453-7dc88891d1f9");
		
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<Rule> response = rest.exchange(ServerProperty.SERVER_URL+ServerProperty.KNOWLEDGEBASE+"/5/rules/311", HttpMethod.GET, request,Rule.class);

		//List k = new ArrayList<>();
		Rule k= response.getBody();
		//String t=response.getBody();
		
		System.out.println(k.getAttributeValues());
		
		for(AttributeValues a:k.getAttributeValues())
		{
			//System.out.println(a.getAttributeOrder());
			if(a.getValue()==null)
				System.out.println("nic");
			else
			System.out.println(a.getValue().getName());
			
			//System.out.println(a.getAttribute().getName());
		}
		*/
		
		
		 
		TestRestTemplate t = new TestRestTemplate();
		t.writeStudentsListToExcel();
		      

		
	}
	
	public void writeStudentsListToExcel()
	{
		List<Student> studentList = new ArrayList<>();
		
        studentList.add(new Student("Magneto","90","100","80"));

        studentList.add(new Student("Wolverine","60","60","90"));

        studentList.add(new Student("ProfX","100","100","100"));
        
		Workbook workbook = new XSSFWorkbook();
		
		 
		        Sheet studentsSheet = workbook.createSheet("Students");
		
		        int rowIndex = 0;
		
		        for(Student student : studentList){
		
		            Row row = studentsSheet.createRow(rowIndex++);
		
		            int cellIndex = 0;
		
		            //first place in row is name
		
		            row.createCell(cellIndex++).setCellValue(student.a);
		
		 
		
		          //second place in row is marks in maths
		
		            row.createCell(cellIndex++).setCellValue(student.b);
		
		 
		
		            //third place in row is marks in Science
		
		            row.createCell(cellIndex++).setCellValue(student.c);
		
		 
		
		            //fourth place in row is marks in English
		
		            row.createCell(cellIndex++).setCellValue(student.d);
		
		 
		
		        }
		
		 
		
		        //write this workbook in excel file.
		
		        try {
		
		            FileOutputStream fos = new FileOutputStream(FILE_PATH);
		
		            workbook.write(fos);
		
		           fos.close();
		
	
		
		            System.out.println(FILE_PATH + " is successfully written");
		
		        } catch (FileNotFoundException e) {
		
		            e.printStackTrace();
		
		        } catch (IOException e) {
		
		            e.printStackTrace();
		
		        }
		
	
		    

	}
}

class Student
{
	public String a;
	public String b;
	public String c;
	public String d;
	
	public Student(String a, String b, String c,String d) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	
}
