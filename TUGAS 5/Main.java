import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Input Student
        System.out.print("Nama Student: ");
        String sName = input.nextLine();

        System.out.print("Alamat Student: ");
        String sAddress = input.nextLine();

        Student s = new Student(sName, sAddress);

        System.out.print("Jumlah Mata Kuliah: ");
        int jumlah = input.nextInt();
        input.nextLine();

        for (int i = 0; i < jumlah; i++) {
            System.out.print("Nama Course: ");
            String course = input.nextLine();

            System.out.print("Nilai: ");
            int grade = input.nextInt();
            input.nextLine();

            s.addCourseGrade(course, grade);
        }

        // Input Teacher
        System.out.print("\nNama Teacher: ");
        String tName = input.nextLine();

        System.out.print("Alamat Teacher: ");
        String tAddress = input.nextLine();

        Teacher t = new Teacher(tName, tAddress);

        t.addCourse("PBO");
        t.addCourse("Basis Data");

        // OUTPUT
        System.out.println("\n=== DATA STUDENT ===");
        System.out.println(s);
        s.printGrades();
        System.out.println("Rata-rata: " + s.getAverageGrade());

        System.out.println("\n=== DATA TEACHER ===");
        System.out.println(t);
        System.out.println("Course berhasil ditambahkan!");
    }
}