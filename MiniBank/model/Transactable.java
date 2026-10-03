package MiniBank.model;

import MiniBank.exception.InsufficientFundsException;
import MiniBank.exception.InvalidAmountException;

public interface Transactable {

    void deposit(long amount) throws InvalidAmountException;

    boolean withdraw(long amount)
            throws InsufficientFundsException, InvalidAmountException;
}