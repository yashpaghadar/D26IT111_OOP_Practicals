package MiniBank.util;

import MiniBank.model.Account;

public class StatementFormatter {

    public static String buildStatement(Account account) {
        StringBuilder statement = new StringBuilder();
        statement.append("========== ACCOUNT STATEMENT ==========\n");
        statement.append("Account Number : ")
                 .append(account.getAccountNumber())
                 .append("\n");

        statement.append("Owner Name     : ")
                 .append(account.getOwnerName())
                 .append("\n");

        statement.append("Balance        : ")
                 .append(account.getBalance())
                 .append("\n");

        statement.append("Status         : ")
                 .append(account.isActive() ? "ACTIVE" : "INACTIVE")
                 .append("\n");

        statement.append("=======================================\n");

        return statement.toString();
    }
}