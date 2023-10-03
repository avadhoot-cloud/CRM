package com.example.gymproj;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbConnect {


    // JDBC URL, username, and password
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/goldgym?useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Aarya@1971";

    // Method to establish and return a database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }
    private static final String INSERT_QUERY = "INSERT INTO Register (name, email, password) VALUES (?, ?, ?)";
    private static final String SELECT_QUERY = "SELECT * FROM Register WHERE email = ? and password = ?";

    public boolean insertRecord(String fullName, String email, String password) throws SQLException {

        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, USERNAME, PASSWORD);

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
            System.out.println("insertion fail");
             return  false;
        }



    }

    public boolean validate(String email, String password) throws SQLException {

        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, USERNAME, PASSWORD);

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
            return false;
        }

    // Add a new method to retrieve member names or IDs
    public static List<FeesController.MemberData> getMemberNamesAndIDs() {
        List<FeesController.MemberData> memberList = new ArrayList<>();

        // SQL query to retrieve member names or IDs (adjust the query as per your database schema)
        String query = "SELECT id,fname FROM member";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                // Replace "name_or_id_column" with the actual column name that contains member names or IDs
                int memberId = resultSet.getInt("id");
                String memberName = resultSet.getString("fname");
                // Create a MemberData object and add it to the list
                FeesController.MemberData memberData = new FeesController.MemberData(memberName, memberId);
                memberList.add(memberData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database-related exceptions here
        }

        return memberList;
    }
        public static void printSQLException(SQLException ex) {
            for (Throwable e: ex) {
                if (e instanceof SQLException) {
                    e.printStackTrace(System.err);
                    System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                    System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                    System.err.println("Message: " + e.getMessage());
                    Throwable t = ex.getCause();
                    while (t != null) {
                        System.out.println("Cause: " + t);
                        t = t.getCause();
                    }
        }}}}







