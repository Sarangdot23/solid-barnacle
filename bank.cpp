#include <iostream>
#include <stdexcept> // For exception handling
using namespace std;

// Base class
class Account {
protected:
    string accNumber;
    string name;
    double balance;
public:
    Account(string accNum, string custName, double initialBalance) {
        if (initialBalance < 0) throw invalid_argument("Initial balance cannot be negative!");
        accNumber = accNum;
        name = custName;
        balance = initialBalance;
    }

    void deposit(double amount) {
        if (amount <= 0) throw invalid_argument("Deposit amount must be positive!");
        balance += amount;
        cout << "Deposited: $" << amount << endl;
    }

    virtual void withdraw(double amount) {
        if (amount <= 0) throw invalid_argument("Withdrawal amount must be positive!");
        if (amount > balance) throw runtime_error("Insufficient balance!");
        balance -= amount;
        cout << "Withdrawn: $" << amount << endl;
    }

    void display() {
        cout << "Account Number: " << accNumber << "\nName: " << name << "\nBalance: $" << balance << endl;
    }
};

// Derived class (inherits from Account)
class SavingsAccount : public Account {
public:
    SavingsAccount(string accNum, string custName, double initialBalance) 
        : Account(accNum, custName, initialBalance) {}

    void withdraw(double amount) override {
        if (balance - amount < 100) throw runtime_error("Minimum balance of $100 required!");
        Account::withdraw(amount);
    }
};

int main() {
    string accNum, name;
    double initialBalance;

    try {
        cout << "Enter Account Number: ";
        cin >> accNum;
        cout << "Enter Name: ";
        cin >> name;
        cout << "Enter Initial Balance: ";
        cin >> initialBalance;

        SavingsAccount account(accNum, name, initialBalance);

        int choice;
        do {
            cout << "\n1. Deposit\n2. Withdraw\n3. Display Account\n4. Exit\nEnter choice: ";
            cin >> choice;

            try {
                if (choice == 1) {
                    double amount;
                    cout << "Enter deposit amount: ";
                    cin >> amount;
                    account.deposit(amount);
                } else if (choice == 2) {
                    double amount;
                    cout << "Enter withdrawal amount: ";
                    cin >> amount;
                    account.withdraw(amount);
                } else if (choice == 3) {
                    account.display();
                }
            } catch (const exception &e) {
                cout << "Error: " << e.what() << endl;
            }
        } while (choice != 4);

    } catch (const exception &e) {
        cout << "Error: " << e.what() << endl;
    }

    return 0;
}
