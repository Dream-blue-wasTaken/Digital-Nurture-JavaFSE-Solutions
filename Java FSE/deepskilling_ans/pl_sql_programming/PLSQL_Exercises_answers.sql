-- Schema Setup and Sample Data
-- Run this schema script first before running individual exercises.

CREATE TABLE Customers (
    CustomerID NUMBER PRIMARY KEY,
    Name VARCHAR2(100),
    DOB DATE,
    Balance NUMBER,
    LastModified DATE,
    IsVIP CHAR(1) DEFAULT 'F'
);

CREATE TABLE Accounts (
    AccountID NUMBER PRIMARY KEY,
    CustomerID NUMBER,
    AccountType VARCHAR2(20),
    Balance NUMBER,
    LastModified DATE,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

CREATE TABLE Transactions (
    TransactionID NUMBER PRIMARY KEY,
    AccountID NUMBER,
    TransactionDate DATE,
    Amount NUMBER,
    TransactionType VARCHAR2(10),
    FOREIGN KEY (AccountID) REFERENCES Accounts(AccountID)
);

CREATE TABLE Loans (
    LoanID NUMBER PRIMARY KEY,
    CustomerID NUMBER,
    LoanAmount NUMBER,
    InterestRate NUMBER,
    StartDate DATE,
    EndDate DATE,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

CREATE TABLE Employees (
    EmployeeID NUMBER PRIMARY KEY,
    Name VARCHAR2(100),
    Position VARCHAR2(50),
    Salary NUMBER,
    Department VARCHAR2(50),
    HireDate DATE
);

CREATE TABLE AuditLog (
    LogID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    TransactionID NUMBER,
    AccountID NUMBER,
    Amount NUMBER,
    TransactionType VARCHAR2(10),
    LogDate DATE
);

-- Insert Sample Data
INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
VALUES (1, 'John Doe', TO_DATE('1955-05-15', 'YYYY-MM-DD'), 12000, SYSDATE);

INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
VALUES (2, 'Jane Smith', TO_DATE('1990-07-20', 'YYYY-MM-DD'), 1500, SYSDATE);

INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
VALUES (1, 1, 'Savings', 10000, SYSDATE);

INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
VALUES (2, 2, 'Checking', 1500, SYSDATE);

INSERT INTO Loans (LoanID, CustomerID, LoanAmount, InterestRate, StartDate, EndDate)
VALUES (1, 1, 5000, 5, SYSDATE, SYSDATE + 15);

INSERT INTO Loans (LoanID, CustomerID, LoanAmount, InterestRate, StartDate, EndDate)
VALUES (2, 2, 8000, 7, SYSDATE, SYSDATE + 45);

INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate)
VALUES (1, 'Alice Johnson', 'Manager', 70000, 'HR', TO_DATE('2015-06-15', 'YYYY-MM-DD'));

INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate)
VALUES (2, 'Bob Brown', 'Developer', 60000, 'IT', TO_DATE('2017-03-20', 'YYYY-MM-DD'));

COMMIT;


-- ============================================================================
-- Exercise 1: Control Structures
-- ============================================================================

-- Scenario 1: Apply 1% discount to loan interest rates for customers above 60 years old.
DECLARE
    v_age NUMBER;
BEGIN
    FOR c IN (SELECT CustomerID, DOB FROM Customers) LOOP
        v_age := TRUNC(MONTHS_BETWEEN(SYSDATE, c.DOB) / 12);
        IF v_age > 60 THEN
            UPDATE Loans
            SET InterestRate = InterestRate - 1
            WHERE CustomerID = c.CustomerID;
            DBMS_OUTPUT.PUT_LINE('1% Interest discount applied for Customer ID: ' || c.CustomerID);
        END IF;
    END LOOP;
    COMMIT;
END;
/

-- Scenario 2: Promote customer to VIP status based on balance > $10,000.
BEGIN
    FOR c IN (SELECT CustomerID, Balance FROM Customers) LOOP
        IF c.Balance > 10000 THEN
            UPDATE Customers
            SET IsVIP = 'T'
            WHERE CustomerID = c.CustomerID;
            DBMS_OUTPUT.PUT_LINE('Customer ID ' || c.CustomerID || ' promoted to VIP.');
        END IF;
    END LOOP;
    COMMIT;
END;
/

-- Scenario 3: Send reminders for loans due within the next 30 days.
BEGIN
    FOR l IN (
        SELECT l.LoanID, c.Name, l.EndDate
        FROM Loans l
        JOIN Customers c ON l.CustomerID = c.CustomerID
        WHERE l.EndDate BETWEEN SYSDATE AND SYSDATE + 30
    ) LOOP
        DBMS_OUTPUT.PUT_LINE('REMINDER: Loan ID ' || l.LoanID || ' for Customer ' || l.Name || ' is due on ' || TO_CHAR(l.EndDate, 'YYYY-MM-DD'));
    END LOOP;
END;
/


-- ============================================================================
-- Exercise 2: Error Handling
-- ============================================================================

-- Scenario 1: SafeTransferFunds with exception handling and rollback.
CREATE OR REPLACE PROCEDURE SafeTransferFunds (
    p_from_acc NUMBER,
    p_to_acc NUMBER,
    p_amount NUMBER
) IS
    v_balance NUMBER;
    e_insufficient_funds EXCEPTION;
BEGIN
    SELECT Balance INTO v_balance FROM Accounts WHERE AccountID = p_from_acc FOR UPDATE;
    IF v_balance < p_amount THEN
        RAISE e_insufficient_funds;
    END IF;

    UPDATE Accounts SET Balance = Balance - p_amount WHERE AccountID = p_from_acc;
    UPDATE Accounts SET Balance = Balance + p_amount WHERE AccountID = p_to_acc;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Transfer successful: $' || p_amount || ' transferred.');
EXCEPTION
    WHEN e_insufficient_funds THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERROR: Insufficient funds in Account ID ' || p_from_acc);
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERROR: Transfer failed due to -> ' || SQLERRM);
END;
/

-- Scenario 2: UpdateSalary procedure handling non-existing employee ID.
CREATE OR REPLACE PROCEDURE UpdateSalary (
    p_emp_id NUMBER,
    p_pct NUMBER
) IS
    e_emp_not_found EXCEPTION;
BEGIN
    UPDATE Employees
    SET Salary = Salary + (Salary * (p_pct / 100))
    WHERE EmployeeID = p_emp_id;

    IF SQL%NOTFOUND THEN
        RAISE e_emp_not_found;
    END IF;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Salary updated successfully for Employee ID: ' || p_emp_id);
EXCEPTION
    WHEN e_emp_not_found THEN
        DBMS_OUTPUT.PUT_LINE('ERROR: Employee ID ' || p_emp_id || ' does not exist.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('ERROR: Update failed -> ' || SQLERRM);
END;
/

-- Scenario 3: AddNewCustomer procedure preventing duplicate customer ID.
CREATE OR REPLACE PROCEDURE AddNewCustomer (
    p_cust_id NUMBER,
    p_name VARCHAR2,
    p_dob DATE,
    p_balance NUMBER
) IS
BEGIN
    INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
    VALUES (p_cust_id, p_name, p_dob, p_balance, SYSDATE);
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Customer added successfully: ' || p_name);
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('ERROR: Customer ID ' || p_cust_id || ' already exists.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('ERROR: Failed to add customer -> ' || SQLERRM);
END;
/


-- ============================================================================
-- Exercise 3: Stored Procedures
-- ============================================================================

-- Scenario 1: ProcessMonthlyInterest for savings accounts.
CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest IS
BEGIN
    UPDATE Accounts
    SET Balance = Balance + (Balance * 0.01)
    WHERE UPPER(AccountType) = 'SAVINGS';
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Monthly interest of 1% applied to all Savings Accounts.');
END;
/

-- Scenario 2: UpdateEmployeeBonus by department.
CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus (
    p_dept VARCHAR2,
    p_bonus_pct NUMBER
) IS
BEGIN
    UPDATE Employees
    SET Salary = Salary + (Salary * (p_bonus_pct / 100))
    WHERE UPPER(Department) = UPPER(p_dept);
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Bonus applied for department: ' || p_dept);
END;
/

-- Scenario 3: TransferFunds checking sufficient balance.
CREATE OR REPLACE PROCEDURE TransferFunds (
    p_src_acc NUMBER,
    p_dest_acc NUMBER,
    p_amount NUMBER
) IS
    v_balance NUMBER;
BEGIN
    SELECT Balance INTO v_balance FROM Accounts WHERE AccountID = p_src_acc;
    IF v_balance >= p_amount THEN
        UPDATE Accounts SET Balance = Balance - p_amount WHERE AccountID = p_src_acc;
        UPDATE Accounts SET Balance = Balance + p_amount WHERE AccountID = p_dest_acc;
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Funds transferred successfully.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Transfer aborted: Insufficient balance.');
    END IF;
END;
/


-- ============================================================================
-- Exercise 4: Functions
-- ============================================================================

-- Scenario 1: CalculateAge function.
CREATE OR REPLACE FUNCTION CalculateAge (
    p_dob DATE
) RETURN NUMBER IS
BEGIN
    RETURN TRUNC(MONTHS_BETWEEN(SYSDATE, p_dob) / 12);
END;
/

-- Scenario 2: CalculateMonthlyInstallment function.
CREATE OR REPLACE FUNCTION CalculateMonthlyInstallment (
    p_amount NUMBER,
    p_rate NUMBER,
    p_years NUMBER
) RETURN NUMBER IS
    v_r NUMBER;
    v_n NUMBER;
    v_emi NUMBER;
BEGIN
    v_r := p_rate / (12 * 100);
    v_n := p_years * 12;
    v_emi := (p_amount * v_r * POWER(1 + v_r, v_n)) / (POWER(1 + v_r, v_n) - 1);
    RETURN ROUND(v_emi, 2);
END;
/

-- Scenario 3: HasSufficientBalance function.
CREATE OR REPLACE FUNCTION HasSufficientBalance (
    p_acc_id NUMBER,
    p_amount NUMBER
) RETURN BOOLEAN IS
    v_balance NUMBER;
BEGIN
    SELECT Balance INTO v_balance FROM Accounts WHERE AccountID = p_acc_id;
    RETURN v_balance >= p_amount;
EXCEPTION
    WHEN OTHERS THEN
        RETURN FALSE;
END;
/


-- ============================================================================
-- Exercise 5: Triggers
-- ============================================================================

-- Scenario 1: UpdateCustomerLastModified trigger.
CREATE OR REPLACE TRIGGER UpdateCustomerLastModified
BEFORE UPDATE ON Customers
FOR EACH ROW
BEGIN
    :NEW.LastModified := SYSDATE;
END;
/

-- Scenario 2: LogTransaction trigger for AuditLog.
CREATE OR REPLACE TRIGGER LogTransaction
AFTER INSERT ON Transactions
FOR EACH ROW
BEGIN
    INSERT INTO AuditLog (TransactionID, AccountID, Amount, TransactionType, LogDate)
    VALUES (:NEW.TransactionID, :NEW.AccountID, :NEW.Amount, :NEW.TransactionType, SYSDATE);
END;
/

-- Scenario 3: CheckTransactionRules for deposits/withdrawals.
CREATE OR REPLACE TRIGGER CheckTransactionRules
BEFORE INSERT ON Transactions
FOR EACH ROW
DECLARE
    v_balance NUMBER;
BEGIN
    IF UPPER(:NEW.TransactionType) = 'WITHDRAWAL' THEN
        SELECT Balance INTO v_balance FROM Accounts WHERE AccountID = :NEW.AccountID;
        IF :NEW.Amount > v_balance THEN
            RAISE_APPLICATION_ERROR(-20001, 'Withdrawal amount exceeds current account balance.');
        END IF;
    ELSIF UPPER(:NEW.TransactionType) = 'DEPOSIT' THEN
        IF :NEW.Amount <= 0 THEN
            RAISE_APPLICATION_ERROR(-20002, 'Deposit amount must be positive.');
        END IF;
    END IF;
END;
/


-- ============================================================================
-- Exercise 6: Cursors
-- ============================================================================

-- Scenario 1: GenerateMonthlyStatements cursor.
DECLARE
    CURSOR c_monthly_stmt IS
        SELECT t.TransactionID, t.AccountID, t.Amount, t.TransactionType, t.TransactionDate, a.CustomerID
        FROM Transactions t
        JOIN Accounts a ON t.AccountID = a.AccountID
        WHERE TRUNC(t.TransactionDate, 'MM') = TRUNC(SYSDATE, 'MM');
BEGIN
    FOR r IN c_monthly_stmt LOOP
        DBMS_OUTPUT.PUT_LINE('Customer ID: ' || r.CustomerID || ' | Acc: ' || r.AccountID || ' | Trans ID: ' || r.TransactionID || ' | ' || r.TransactionType || ' $' || r.Amount);
    END LOOP;
END;
/

-- Scenario 2: ApplyAnnualFee cursor.
DECLARE
    v_fee CONSTANT NUMBER := 50;
    CURSOR c_accounts IS
        SELECT AccountID, Balance FROM Accounts FOR UPDATE;
BEGIN
    FOR acc IN c_accounts LOOP
        UPDATE Accounts
        SET Balance = Balance - v_fee,
            LastModified = SYSDATE
        WHERE CURRENT OF c_accounts;
    END LOOP;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Annual fee of $' || v_fee || ' applied to all accounts.');
END;
/

-- Scenario 3: UpdateLoanInterestRates cursor.
DECLARE
    CURSOR c_loans IS
        SELECT LoanID, InterestRate FROM Loans FOR UPDATE;
BEGIN
    FOR l IN c_loans LOOP
        UPDATE Loans
        SET InterestRate = InterestRate + 0.5
        WHERE CURRENT OF c_loans;
    END LOOP;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Updated loan interest rates for all active loans.');
END;
/


-- ============================================================================
-- Exercise 7: Packages
-- ============================================================================

-- Scenario 1: CustomerManagement Package
CREATE OR REPLACE PACKAGE CustomerManagement AS
    PROCEDURE AddCustomer(p_id NUMBER, p_name VARCHAR2, p_dob DATE, p_balance NUMBER);
    PROCEDURE UpdateCustomer(p_id NUMBER, p_name VARCHAR2, p_balance NUMBER);
    FUNCTION GetCustomerBalance(p_id NUMBER) RETURN NUMBER;
END CustomerManagement;
/

CREATE OR REPLACE PACKAGE BODY CustomerManagement AS
    PROCEDURE AddCustomer(p_id NUMBER, p_name VARCHAR2, p_dob DATE, p_balance NUMBER) IS
    BEGIN
        INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
        VALUES (p_id, p_name, p_dob, p_balance, SYSDATE);
        COMMIT;
    END AddCustomer;

    PROCEDURE UpdateCustomer(p_id NUMBER, p_name VARCHAR2, p_balance NUMBER) IS
    BEGIN
        UPDATE Customers
        SET Name = p_name, Balance = p_balance, LastModified = SYSDATE
        WHERE CustomerID = p_id;
        COMMIT;
    END UpdateCustomer;

    FUNCTION GetCustomerBalance(p_id NUMBER) RETURN NUMBER IS
        v_bal NUMBER;
    BEGIN
        SELECT Balance INTO v_bal FROM Customers WHERE CustomerID = p_id;
        RETURN v_bal;
    END GetCustomerBalance;
END CustomerManagement;
/

-- Scenario 2: EmployeeManagement Package
CREATE OR REPLACE PACKAGE EmployeeManagement AS
    PROCEDURE HireEmployee(p_id NUMBER, p_name VARCHAR2, p_pos VARCHAR2, p_sal NUMBER, p_dept VARCHAR2, p_hiredate DATE);
    PROCEDURE UpdateEmployee(p_id NUMBER, p_pos VARCHAR2, p_sal NUMBER);
    FUNCTION CalculateAnnualSalary(p_id NUMBER) RETURN NUMBER;
END EmployeeManagement;
/

CREATE OR REPLACE PACKAGE BODY EmployeeManagement AS
    PROCEDURE HireEmployee(p_id NUMBER, p_name VARCHAR2, p_pos VARCHAR2, p_sal NUMBER, p_dept VARCHAR2, p_hiredate DATE) IS
    BEGIN
        INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate)
        VALUES (p_id, p_name, p_pos, p_sal, p_dept, p_hiredate);
        COMMIT;
    END HireEmployee;

    PROCEDURE UpdateEmployee(p_id NUMBER, p_pos VARCHAR2, p_sal NUMBER) IS
    BEGIN
        UPDATE Employees SET Position = p_pos, Salary = p_sal WHERE EmployeeID = p_id;
        COMMIT;
    END UpdateEmployee;

    FUNCTION CalculateAnnualSalary(p_id NUMBER) RETURN NUMBER IS
        v_sal NUMBER;
    BEGIN
        SELECT Salary INTO v_sal FROM Employees WHERE EmployeeID = p_id;
        RETURN v_sal * 12;
    END CalculateAnnualSalary;
END EmployeeManagement;
/

-- Scenario 3: AccountOperations Package
CREATE OR REPLACE PACKAGE AccountOperations AS
    PROCEDURE OpenAccount(p_acc_id NUMBER, p_cust_id NUMBER, p_type VARCHAR2, p_balance NUMBER);
    PROCEDURE CloseAccount(p_acc_id NUMBER);
    FUNCTION GetTotalBalance(p_cust_id NUMBER) RETURN NUMBER;
END AccountOperations;
/

CREATE OR REPLACE PACKAGE BODY AccountOperations AS
    PROCEDURE OpenAccount(p_acc_id NUMBER, p_cust_id NUMBER, p_type VARCHAR2, p_balance NUMBER) IS
    BEGIN
        INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
        VALUES (p_acc_id, p_cust_id, p_type, p_balance, SYSDATE);
        COMMIT;
    END OpenAccount;

    PROCEDURE CloseAccount(p_acc_id NUMBER) IS
    BEGIN
        DELETE FROM Accounts WHERE AccountID = p_acc_id;
        COMMIT;
    END CloseAccount;

    FUNCTION GetTotalBalance(p_cust_id NUMBER) RETURN NUMBER IS
        v_total NUMBER;
    BEGIN
        SELECT SUM(Balance) INTO v_total FROM Accounts WHERE CustomerID = p_cust_id;
        RETURN NVL(v_total, 0);
    END GetTotalBalance;
END AccountOperations;
/
