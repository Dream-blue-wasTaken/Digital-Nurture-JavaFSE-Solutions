-- ============================================================
-- Exercise 3: Stored Procedures
-- Scenario: PL/SQL stored procedures for banking operations
-- ============================================================

-- Exercise 3.1: Monthly interest processing procedure
-- Process and add monthly interest to all savings accounts
CREATE OR REPLACE PROCEDURE process_monthly_interest(
    p_interest_rate IN NUMBER DEFAULT 0.04  -- 4% annual interest
) IS
    v_monthly_rate NUMBER := p_interest_rate / 12;
    v_interest_amount NUMBER;
    CURSOR c_savings_accounts IS
        SELECT account_id, customer_id, balance 
        FROM Accounts 
        WHERE account_type = 'savings'
        FOR UPDATE;
BEGIN
    DBMS_OUTPUT.PUT_LINE('Starting monthly interest processing...');
    DBMS_OUTPUT.PUT_LINE('Monthly rate: ' || ROUND(v_monthly_rate * 100, 4) || '%');
    
    FOR acc_rec IN c_savings_accounts LOOP
        v_interest_amount := acc_rec.balance * v_monthly_rate;
        
        UPDATE Accounts 
        SET balance = balance + v_interest_amount,
            last_modified = SYSDATE
        WHERE account_id = acc_rec.account_id;
        
        -- Log the transaction
        INSERT INTO Transactions (transaction_id, account_id, transaction_date, 
                                  amount, transaction_type)
        VALUES (seq_transaction_id.NEXTVAL, acc_rec.account_id, SYSDATE, 
                v_interest_amount, 'INTEREST');
        
        DBMS_OUTPUT.PUT_LINE('Account #' || acc_rec.account_id || 
                           ': Interest added = $' || ROUND(v_interest_amount, 2));
    END LOOP;
    
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Monthly interest processing completed.');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END process_monthly_interest;
/

-- Execute the procedure
BEGIN
    process_monthly_interest(0.04);
END;
/

-- Exercise 3.2: Employee performance bonus procedure
-- Calculate and distribute bonuses based on performance
CREATE OR REPLACE PROCEDURE calculate_employee_bonus(
    p_department IN VARCHAR2 DEFAULT NULL,
    p_bonus_percentage IN NUMBER DEFAULT 10
) IS
    v_bonus_amount NUMBER;
    CURSOR c_employees IS
        SELECT employee_id, name, salary, department
        FROM Employees
        WHERE (p_department IS NULL OR department = p_department)
        FOR UPDATE;
BEGIN
    DBMS_OUTPUT.PUT_LINE('Calculating bonuses...');
    
    FOR emp_rec IN c_employees LOOP
        v_bonus_amount := emp_rec.salary * (p_bonus_percentage / 100);
        
        -- Update salary with bonus
        UPDATE Employees 
        SET salary = salary + v_bonus_amount
        WHERE employee_id = emp_rec.employee_id;
        
        DBMS_OUTPUT.PUT_LINE('Employee: ' || emp_rec.name || 
                           ' (' || emp_rec.department || ')' || 
                           ' - Bonus: $' || ROUND(v_bonus_amount, 2));
    END LOOP;
    
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Bonus calculation completed.');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END calculate_employee_bonus;
/

-- Execute the procedure
BEGIN
    calculate_employee_bonus('IT', 15);
END;
/

-- Exercise 3.3: Inter-account fund transfer procedure
-- Transfer funds between two accounts
CREATE OR REPLACE PROCEDURE transfer_funds(
    p_from_account_id IN NUMBER,
    p_to_account_id IN NUMBER,
    p_amount IN NUMBER
) IS
    v_from_balance NUMBER;
    v_from_customer_id NUMBER;
    v_to_customer_id NUMBER;
    v_transaction_id NUMBER;
BEGIN
    -- Check for negative amount
    IF p_amount <= 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Transfer amount must be positive');
    END IF;
    
    -- Get source account details
    SELECT balance, customer_id INTO v_from_balance, v_from_customer_id
    FROM Accounts WHERE account_id = p_from_account_id
    FOR UPDATE;
    
    -- Get destination account details
    SELECT customer_id INTO v_to_customer_id
    FROM Accounts WHERE account_id = p_to_account_id
    FOR UPDATE;
    
    -- Check sufficient balance
    IF v_from_balance < p_amount THEN
        RAISE_APPLICATION_ERROR(-20002, 'Insufficient balance in source account');
    END IF;
    
    DBMS_OUTPUT.PUT_LINE('Processing transfer of $' || p_amount);
    DBMS_OUTPUT.PUT_LINE('From Account #' || p_from_account_id || 
                       ' (Balance: $' || v_from_balance || ')');
    DBMS_OUTPUT.PUT_LINE('To Account #' || p_to_account_id);
    
    -- Debit source account
    UPDATE Accounts 
    SET balance = balance - p_amount,
        last_modified = SYSDATE
    WHERE account_id = p_from_account_id;
    
    -- Credit destination account
    UPDATE Accounts 
    SET balance = balance + p_amount,
        last_modified = SYSDATE
    WHERE account_id = p_to_account_id;
    
    -- Record debit transaction
    INSERT INTO Transactions (transaction_id, account_id, transaction_date, 
                              amount, transaction_type)
    VALUES (seq_transaction_id.NEXTVAL, p_from_account_id, SYSDATE, 
            p_amount, 'DEBIT');
    
    -- Record credit transaction
    INSERT INTO Transactions (transaction_id, account_id, transaction_date, 
                              amount, transaction_type)
    VALUES (seq_transaction_id.NEXTVAL, p_to_account_id, SYSDATE, 
            p_amount, 'CREDIT');
    
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Transfer completed successfully!');
    
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Transfer failed: ' || SQLERRM);
        RAISE;
END transfer_funds;
/

-- Execute the procedure (example)
BEGIN
    transfer_funds(101, 102, 500);
END;
/

-- Sequence for transaction IDs
CREATE SEQUENCE seq_transaction_id
    START WITH 1000
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;
