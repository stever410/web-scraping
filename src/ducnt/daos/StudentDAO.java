package ducnt.daos;

import ducnt.db.MyConnection;
import ducnt.dtos.Student;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;

public class StudentDAO implements Serializable {
    public boolean insertStudents(List<Student> list) throws Exception {
        boolean check = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        HashMap<String, Double> scores;
        try {
            String sql = "insert into student(stu_id, name, dob, toan, ngu_van, vat_ly, hoa_hoc, sinh_hoc, khtn, tieng_anh, lich_su, dia_ly, gdcd, khxh)\n" +
                            "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)\n" +
                            "on duplicate key update toan=?, ngu_van=?, vat_ly=?, hoa_hoc=?, sinh_hoc=?, khtn=?, tieng_anh=?, lich_su=?, dia_ly=?, gdcd=?, khxh=?";
            connection = MyConnection.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            for (Student student : list) {
                scores = student.getScores();
                double toan = scores.get("Toán") == null ? -1 : scores.get("Toán");
                double nguVan = scores.get("Ngữ văn") == null ? -1 : scores.get("Ngữ văn");
                double vatLi = scores.get("Vật lí") == null ? -1 : scores.get("Vật lí");
                double hoaHoc = scores.get("Hóa học") == null ? -1 : scores.get("Hóa học");
                double sinhHoc = scores.get("Sinh học") == null ? -1 : scores.get("Sinh học");
                double khtn = scores.get("KHTN") == null ? -1 : scores.get("KHTN");
                double tiengAnh = scores.get("Tiếng anh") == null ? -1 : scores.get("Tiếng anh");
                double lichSu = scores.get("Lịch sử") == null ? -1 : scores.get("Lịch sử");
                double diaLi = scores.get("Địa lí") == null ? -1 : scores.get("Địa lí");
                double gdcd = scores.get("GDCD") == null ? -1 : scores.get("GDCD");
                double khxh = scores.get("KHXH") == null ? -1 : scores.get("KHXH");
                System.out.println(lichSu);
                preparedStatement.setString(1, student.getId());
                preparedStatement.setString(2, student.getName());
                preparedStatement.setDate(3, new Date(student.getDob().getTime()));
                preparedStatement.setDouble(4, toan);
                preparedStatement.setDouble(5, nguVan);
                preparedStatement.setDouble(6, vatLi);
                preparedStatement.setDouble(7, hoaHoc);
                preparedStatement.setDouble(8, sinhHoc);
                preparedStatement.setDouble(9, khtn);
                preparedStatement.setDouble(10, tiengAnh);
                preparedStatement.setDouble(11, lichSu);
                preparedStatement.setDouble(12, diaLi);
                preparedStatement.setDouble(13, gdcd);
                preparedStatement.setDouble(14, khxh);
                preparedStatement.setDouble(15, toan);
                preparedStatement.setDouble(16, nguVan);
                preparedStatement.setDouble(17, vatLi);
                preparedStatement.setDouble(18, hoaHoc);
                preparedStatement.setDouble(19, sinhHoc);
                preparedStatement.setDouble(20, khtn);
                preparedStatement.setDouble(21, tiengAnh);
                preparedStatement.setDouble(22, lichSu);
                preparedStatement.setDouble(23, diaLi);
                preparedStatement.setDouble(24, gdcd);
                preparedStatement.setDouble(25, khxh);
                preparedStatement.executeUpdate();
            }
            connection.commit();
            check = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
            if (preparedStatement != null)
                preparedStatement.close();
            if (connection != null)
                connection.close();
        }
        return check;
    }
}
