package com.abc;

import com.abc.accounts.Account;
import com.abc.accounts.CheckingAccount;
import com.abc.accounts.SavingsAccount;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTransferBetweenAccounts(){
        SavingsAccount savingsAccount = new SavingsAccount();
        CheckingAccount checkingAccount = new CheckingAccount();

        Customer zuzana = new Customer("Zuzana")
                .openAccount(savingsAccount);
        zuzana.openAccount(checkingAccount);

        savingsAccount.deposit(5000);
        zuzana.transferMoneyTo(savingsAccount, checkingAccount, 2000);

        assertEquals(2000.0, checkingAccount.getTotalAmount(), DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferBetweenAccountsThrowsException(){
        SavingsAccount savingsAccount = new SavingsAccount();
        CheckingAccount checkingAccount = new CheckingAccount();

        Customer zuzana = new Customer("Zuzana")
                .openAccount(savingsAccount);

        Customer peter = new Customer("Peter").openAccount(checkingAccount);

        savingsAccount.deposit(5000);
        zuzana.transferMoneyTo(savingsAccount, checkingAccount, 2000);
    }
}
