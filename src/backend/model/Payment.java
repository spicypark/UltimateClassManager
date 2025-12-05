package backend.model;

import java.time.LocalDateTime;

/**
 * Represents a payment transaction when a student enrolls in a class.
 * Stores only primitive/String data to avoid circular GSON references.
 */
public class Payment {
    private double paymentAmount;
    private String className;
    private LocalDateTime classDateTime;
    private LocalDateTime transactionDateTime;
    private String studentName;

    public Payment(double amount, String className, LocalDateTime classDateTime, String studentName) {
        this.paymentAmount = amount;
        this.className = className;
        this.classDateTime = classDateTime;
        this.transactionDateTime = LocalDateTime.now();
        this.studentName = studentName;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public String getClassName() {
        return className;
    }

    public LocalDateTime getClassDateTime() {
        return classDateTime;
    }

    public LocalDateTime getTransactionDateTime() {
        return transactionDateTime;
    }

    public String getStudentName() {
        return studentName;
    }

    /**
     * Returns a CSV-formatted line for this payment.
     */
    public String toCSV() {
        return String.format("%.2f,%s,%s,%s,%s",
            paymentAmount,
            className,
            classDateTime.toString(),
            transactionDateTime.toString(),
            studentName);
    }

    /**
     * Returns the CSV header for payments.
     */
    public static String getCSVHeader() {
        return "Payment Amount,Class Name,Class Date/Time,Transaction Date/Time,Student Name";
    }
}
