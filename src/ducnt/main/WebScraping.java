package ducnt.main;

import ducnt.daos.StudentDAO;
import ducnt.dtos.Student;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebScraping {
    public static final int START = 2000001;
    public static final int END = 2074719;

    public static void main(String[] args) {
        String url = "http://diemthi.hcm.edu.vn/Home/Show?SoBaoDanh=";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Document doc;
        ArrayList<Student> students = new ArrayList<>();
        Pattern p = Pattern.compile("\\D+: \\d[.]\\d{2}");
        Matcher matcher;
        try {
            for (int i = START; i <= END; i++) {
                String studentID = "0" + i;
                String studentScoreRequestURL = url + studentID;
                doc = Jsoup.connect(studentScoreRequestURL).get();
                // get the student information from table
                Elements studentsInfo = doc.select("tbody > tr > td");
                if(!doc.html().contains("Không tìm thấy số báo danh này !")) {
                    // init student
                    Student student = new Student(studentID, studentsInfo.get(3).html(), dateFormat.parse(studentsInfo.get(4).html()));

                    // get score string
                    matcher = p.matcher(studentsInfo.get(5).html());
                    // Toan: 6.60
                    // push all score to hashmap
                    HashMap<String, Double> scores = new HashMap<>();
                    while (matcher.find()) {
                        String[] score = matcher.group().split(":");
                        scores.put(score[0].trim(), Double.parseDouble(score[1]));
                    }
                    student.setScores(scores);
                    students.add(student);
                }
            }
            if(students.size() > 0) {
                StudentDAO dao = new StudentDAO();
                dao.insertStudents(students);
            }
        } catch (Exception e) {
            System.out.println(students.size());
            e.printStackTrace();
        }
    }
}
