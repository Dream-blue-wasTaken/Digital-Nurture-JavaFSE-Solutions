-- ============================================================
-- Exercise 1: Control Structures
-- Scenario: PL/SQL control structures for banking operations
-- ============================================================

-- Database Schema (provided in the document)
/*
CREATE TABLE Customers (
    customer_id NUMBER PRIMARY KEY,
    name VARCHAR2(100),
    dob DATE,
    balance NUMBER(10,2),
    last_modified DATE
);

CREATE TABLE Accounts (
    account_id NUMBER PRIMARY KEY,
    customer_id NUMBER REFERENCES Customers(customer_id),
    account_type VARCHAR2(20),
    balance NUMBER(10,2),
    last_modified DATE
);

CREATE TABLE Transactions (
    transaction_id NUMBER PRIMARY KEY,
    account_id NUMBER REFERENCES Accounts(account_id),
    transaction_date DATE,
    amount NUMBER(10,2),
    transaction_type VARCHAR2(10)
);

CREATE TABLE Loans (
    loan_id NUMBER PRIMARY KEY,
    customer_id NUMBER REFERENCES Customers(customer_id),
    loan_amount NUMBER(10,2),
    interest_rate NUMBER(5,2),
    start_date DATE,
    end_date DATE
);

CREATE TABLE Employees (
    employee_id NUMBER PRIMARY KEY,
    name VARCHAR2(100),
    position VARCHAR2(50),
    salary NUMBER(10,2),
    department VARCHAR2(50),
    hire_date DATE
);
*/

-- Exercise 1.1: Age-based loan interest discount
-- Give interest rate discount based on customer age
DECLARE
    v_customer_id NUMBER := 1;
    v_dob DATE;
    v_age NUMBER;
    v_interest_rate NUMBER := 10.00; -- Base interest rate
    v_discounted_rate NUMBER;
BEGIN
    -- Get customer date of birth
    SELECT dob INTO v_dob FROM Customers WHERE customer_id = v_customer_id;
    
    -- Calculate age
    v_age := TRUNC(MONTHS_BETWEEN(SYSDATE, v_dob) / 12);
    
    -- Apply discount based on age
    IF v_age < 25 THEN
        v_discounted_rate := v_interest_rate - 0.5; -- 0.5% discount for young customers
    ELSIF v_age BETWEEN 25 AND 60 THEN
        v_discounted_rate := v_interest_rate - 1.0; -- 1% discount for middle-aged
    ELSE
        v_discounted_rate := v_interest_rate - 1.5; -- 1.5% discount for senior citizens
    END IF;
    
    DBMS_OUTPUT.PUT_LINE('Customer Age: ' || v_age);
    DBMS_OUTPUT.PUT_LINE('Base Interest Rate: ' || v_interest_rate || '%');
    DBMS_OUTPUT.PUT_LINE('Discounted Rate: ' || v_discounted_rate || '%');
END;
/

-- Exercise 1.2: VIP status flagging based on balance
DECLARE
    CURSOR c_customers IS
        SELECT customer_id, name, balance FROM Customers;
    v_vip_limit NUMBER := 100000;
BEGIN
    FOR rec IN c_customers LOOP
        IF rec.balance > v_vip_limit THEN
            DBMS_OUTPUT.PUT_LINE('VIP Customer: ' || rec.name || 
                               ' (ID: ' || rec.customer_id || 
                               ', Balance: $' || rec.balance || ')');
        ELSIF rec.balance > 50000 THEN
            DBMS_OUTPUT.PUT_LINE('Premium Customer: ' || rec.name || 
                               ' (Balance: $' || rec.balance || ')');
        ELSE
            DBMS_OUTPUT.PUT_LINE('Regular Customer: ' || rec.name || 
                               ' (Balance: $' || rec.balance || ')');
        END IF;
    END LOOP;
END;
/

-- Exercise 1.3: Loan due date reminders
DECLARE
    v_days_until_due NUMBER;
    v_reminder_message VARCHAR2(200);
BEGIN
    FOR loan_rec IN (SELECT l.loan_id, l.customer_id, c.name, l.end_date, 
                            l.loan_amount, l.interest_rate
                     FROM Loans l
                     JOIN Customers c ON l.customer_id = c.customer_id) LOOP
        
        v_days_until_due := TRUNC(loan_rec.end_date - SYSDATE);
        
        IF v_days_until_due < 0 THEN
            v_reminder_message := 'OVERDUE! Loan #' || loan_rec.loan_id || 
                                ' was due ' || ABS(v_days_until_due) || ' days ago!';
        ELSIF v_days_until_due <= 30 THEN
            v_reminder_message := 'URGENT: Loan #' || loan_rec.loan_id || 
                                ' is due in ' || v_days_until_due || ' days';
        ELSIF v_days_until_due <= 90 THEN
            v_reminder_message := 'REMINDER: Loan #' || loan_rec.loan_id || 
                                ' is due in ' || v_days_until_due || ' days';
        ELSE
            v_reminder_message := 'INFO: Loan #' || loan_rec.loan_id || 
                                ' is due on ' || TO_CHAR(loan_rec.end_date, 'DD-MON-YYYY');
        END IF;
        
        DBMS_OUTPUT.PUT_LINE('Customer: ' || loan_rec.name);
        DBMS_OUTPUT.PUT_LINE(v_reminder_message);
        DBMS_OUTPUT.PUT_LINE('---');
    END LOOP;
END;
/

-- Sequence for transaction IDs (used by this exercise for demo purposes)
CREATE SEQUENCE seq_transaction_id
    START WITH 1000
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

-- Sample data insertion for testing
/*
INSERT INTO Customers VALUES (1, 'Alice Johnson', DATE '1990-05-15', 150000, SYSDATE);
INSERT INTO Customers VALUES (2, 'Bob Smith', DATE '1985-08-22', 85000, SYSDATE);
INSERT INTO Customers VALUES (3, 'Carol Davis', DATE '2000-03-10', 25000, SYSDATE);

INSERT INTO Accounts VALUES (101, 1, 'savings', 150000, SYSDATE);
INSERT INTO Accounts VALUES (102, 2, 'current', 85000, SYSDATE);
INSERT INTO Accounts VALUES (103, 3, 'savings', 25000, SYSDATE);

INSERT INTO Loans VALUES (1001, 1, 500000, 8.5, DATE '2024-01-01', DATE '2029-01-01');
INSERT INTO Loans VALUES (1002, 2, 300000, 9.0, DATE '2024-06-01', DATE '2027-06-01');
INSERT INTO Loans VALUES (1003, 3, 100000, 10.5, DATE '2025-01-01', DATE '2026-07-01');
*/
