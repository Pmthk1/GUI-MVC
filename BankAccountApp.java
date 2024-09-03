import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount {
    private String accountName;
    private String accountNumber;
    private double balance;

    public BankAccount(String accountName, String accountNumber, double initialBalance) {
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            JOptionPane.showMessageDialog(null, "Insufficient funds");
        }
    }
}

class BankAccountView extends JFrame {
    private JLabel accountNameLabel = new JLabel("Account Name:");
    private JTextField accountNameField = new JTextField(20);
    private JLabel accountNumberLabel = new JLabel("Account Number:");
    private JTextField accountNumberField = new JTextField(20);
    private JLabel balanceLabel = new JLabel("Balance:");
    private JTextField balanceField = new JTextField(20);
    private JLabel amountLabel = new JLabel("Amount:");
    private JTextField amountField = new JTextField(20);
    private JButton depositButton = new JButton("Deposit");
    private JButton withdrawButton = new JButton("Withdraw");

    public BankAccountView() {
        setTitle("Bank Account MVC");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 10));

        accountNameField.setEditable(false);
        accountNumberField.setEditable(false);
        balanceField.setEditable(false);

        panel.add(accountNameLabel);
        panel.add(accountNameField);
        panel.add(accountNumberLabel);
        panel.add(accountNumberField);
        panel.add(balanceLabel);
        panel.add(balanceField);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(depositButton);
        panel.add(withdrawButton);

        add(panel);
    }

    public void setAccountName(String accountName) {
        accountNameField.setText(accountName);
    }

    public void setAccountNumber(String accountNumber) {
        accountNumberField.setText(accountNumber);
    }

    public void setBalance(double balance) {
        balanceField.setText(String.valueOf(balance));
    }

    public String getAmount() {
        return amountField.getText();
    }

    public JButton getDepositButton() {
        return depositButton;
    }

    public JButton getWithdrawButton() {
        return withdrawButton;
    }

    public void addDepositListener(ActionListener listener) {
        depositButton.addActionListener(listener);
    }

    public void addWithdrawListener(ActionListener listener) {
        withdrawButton.addActionListener(listener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}

class BankAccountController {
    private BankAccount model;
    private BankAccountView view;

    public BankAccountController(BankAccount model, BankAccountView view) {
        this.model = model;
        this.view = view;
        view.setAccountName(model.getAccountName());
        view.setAccountNumber(model.getAccountNumber());
        view.setBalance(model.getBalance());
        view.addDepositListener(new DepositListener());
        view.addWithdrawListener(new WithdrawListener());
    }

    class DepositListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                double amount = Double.parseDouble(view.getAmount());
                model.deposit(amount);
                updateView();
                view.showMessage("Deposited: " + amount + " THB");
            } catch (NumberFormatException ex) {
                view.showMessage("Invalid input");
            }
        }
    }

    class WithdrawListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                double amount = Double.parseDouble(view.getAmount());
                model.withdraw(amount);
                updateView();
                view.showMessage("Withdrew: " + amount + " THB");
            } catch (NumberFormatException ex) {
                view.showMessage("Invalid input");
            }
        }
    }

    public void updateView() {
        view.setAccountName(model.getAccountName());
        view.setAccountNumber(model.getAccountNumber());
        view.setBalance(model.getBalance());
    }
}

public class BankAccountApp {
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        BankAccount account = new BankAccount("John", "123456789", 1000.00);
        BankAccountView view = new BankAccountView();
        var controller = new BankAccountController(account, view);

        view.setVisible(true);
    }
}






