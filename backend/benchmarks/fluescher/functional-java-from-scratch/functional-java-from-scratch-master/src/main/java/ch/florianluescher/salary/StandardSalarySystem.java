package ch.florianluescher.salary;

import java.util.Random;

public class StandardSalarySystem {

    public Object paySalary(int employeeId) {

        Random rand = new Random();
		try {
            if (rand.nextBoolean()) return new Object();
            if (rand.nextBoolean()) return new Object();

            if (rand.nextBoolean()) {
                return new Object();
            } else {
                if (rand.nextBoolean()) return new Object();

                final int salary = rand.nextInt();

                return new Object();
            }
        } catch (Exception ex) {
            return new Object();
        }
    }

    public String toString() {
        return "StandardSalarySystem";
    }
}
