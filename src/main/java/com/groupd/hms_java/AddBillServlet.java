package com.groupd.hms_java;

import com.groupd.beans.Bill;
import com.groupd.beans.User;
import com.groupd.dao.BillDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "AddBillServlet", urlPatterns = {"/addBill"})
public class AddBillServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String patientId = request.getParameter("patientId");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String staffId = user.getId(); // Get staffId from User object

        BigDecimal amount = new BigDecimal(request.getParameter("amount"));
        Date billDate = Date.valueOf(request.getParameter("billDate"));
        String status = request.getParameter("status");

        BillDAO billDAO = new BillDAO();
        try {
            if (status.equals("Paid")) {
                // Fetch unpaid bills for the patient
                List<Bill> unpaidBills = billDAO.getPendingBillsByPatientId(patientId);

                // Calculate the total amount of unpaid bills
                BigDecimal totalUnpaid = unpaidBills.stream()
                        .map(Bill::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                // Determine remaining balance after payment
                BigDecimal remainingPayment = amount.subtract(totalUnpaid);
                BigDecimal totalPaid = BigDecimal.ZERO;

                for (Bill unpaidBill : unpaidBills) {
                    BigDecimal billAmount = unpaidBill.getAmount();
                    if (remainingPayment.compareTo(BigDecimal.ZERO) >= 0) {
                        unpaidBill.setStatus("Paid");
                        totalPaid = totalPaid.add(billAmount);
                        billDAO.updateBill(unpaidBill);
                    } else {
                        BigDecimal billBalance = billAmount.subtract(totalPaid);
                        if (amount.compareTo(billBalance) >= 0) {
                            unpaidBill.setStatus("Paid");
                            totalPaid = totalPaid.add(billBalance);
                            billDAO.updateBill(unpaidBill);
                        } else {
                            // Delete the original unpaid bill
                            billDAO.deleteBill(unpaidBill.getBillId());

                            // Insert a record for the amount paid
                            Bill paidBill = new Bill();
                            paidBill.setPatientId(patientId);
                            paidBill.setStaffId(staffId);
                            paidBill.setAmount(amount);
                            paidBill.setBillDate(billDate);
                            paidBill.setStatus("Paid");
                            billDAO.addBill(paidBill);

                            // Insert a record for the remaining unpaid amount
                            Bill remainingBill = new Bill();
                            remainingBill.setPatientId(patientId);
                            remainingBill.setStaffId(staffId);
                            remainingBill.setAmount(billBalance.subtract(amount));
                            remainingBill.setBillDate(billDate);
                            remainingBill.setStatus("Unpaid");
                            billDAO.addBill(remainingBill);

                            break;
                        }
                    }
                }

                // Set attributes for the JSP
                if (remainingPayment.compareTo(BigDecimal.ZERO) > 0) {
                    request.setAttribute("message", "Payment processed successfully! You have overpaid. Remaining balance to be returned: " + remainingPayment);
                    request.setAttribute("alertClass", "alert-info");
                } else if (remainingPayment.compareTo(BigDecimal.ZERO) < 0) {
                    BigDecimal balanceLeft = totalUnpaid.subtract(totalPaid);
                    request.setAttribute("message", "Payment processed successfully! Remaining balance to be paid: " + balanceLeft);
                    request.setAttribute("alertClass", "alert-warning");
                } else {
                    request.setAttribute("message", "Payment processed successfully!");
                    request.setAttribute("alertClass", "alert-success");
                }
                request.setAttribute("remainingPayment", remainingPayment);

            } else {
                // Add new bill if the status is not "Paid"
                Bill bill = new Bill();
                bill.setPatientId(patientId);
                bill.setStaffId(staffId);
                bill.setAmount(amount);
                bill.setBillDate(billDate);
                bill.setStatus(status);
                billDAO.addBill(bill);
                request.setAttribute("message", "Bill added successfully!");
                request.setAttribute("alertClass", "alert-success");
                request.setAttribute("remainingPayment", BigDecimal.ZERO);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Failed to add/update bill.");
            request.setAttribute("alertClass", "alert-danger");
            request.setAttribute("remainingPayment", BigDecimal.ZERO);
        }

        // Fetch pending bills
        List<Bill> pendingBills = null;
        if (patientId != null && !patientId.isEmpty()) {
            try {
                pendingBills = billDAO.getPendingBillsByPatientId(patientId);
                request.setAttribute("pendingBills", pendingBills);
                request.setAttribute("selectedPatientId", patientId); // Keep the patientId in the form
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("message", "Failed to retrieve pending bills.");
                request.setAttribute("alertClass", "alert-danger");
            }
        }

        // Forward to the JSP page
        request.getRequestDispatcher("staffs/staffManageBilling.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String patientId = request.getParameter("patientId");
        BillDAO billDAO = new BillDAO();
        List<Bill> pendingBills = null;

        if (patientId != null && !patientId.isEmpty()) {
            try {
                pendingBills = billDAO.getPendingBillsByPatientId(patientId);
                request.setAttribute("pendingBills", pendingBills);
                request.setAttribute("selectedPatientId", patientId); // Keep the patientId in the form
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("message", "Failed to retrieve pending bills.");
                request.setAttribute("alertClass", "alert-danger");
            }
        }

        // Forward to the JSP page
        request.getRequestDispatcher("staffs/staffManageBilling.jsp").forward(request, response);
    }
}
