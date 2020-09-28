package data;

public class Loven {

    public static void main(String[] args) {

        calculateAndPrint("0123", "1234");

    }

    static void calculateAndPrint(String a, String b) {
        Step root = new Step("bg", calculateDistance(a, b), a, b, 0, 0);
        next(root);
        print(root, 0, "");
    }

    static void next(Step parent) {

        if (parent.distance == 0) {
            return;
        }

        parent.children = new Step[3];

        if (parent.resultA.length() > 1) {
            String a = parent.resultA.substring(1);
            parent.children[0] = new Step("mi", calculateDistance(a, parent.resultB), a, parent.resultB, 1, parent.total + 1);
            next(parent.children[0]);
        }

        if (parent.resultB.length() > 1) {
            String b = parent.resultB.substring(1);
            parent.children[1] = new Step("mj", calculateDistance(parent.resultA, b), parent.resultA, b, 2, parent.total + 1);
            next(parent.children[1]);
        }

        parent.children[2] = new Step("rd", 0, "", "", parent.distance, parent.total + parent.distance);
        next(parent.children[2]);


    }


    static int calculateDistance(String a, String b) {
        int wrong = Math.abs(b.length() - a.length());

        for (int i = 0; i < Math.min(b.length(), a.length()); ++i) {
            if (a.charAt(i) != b.charAt(i)) {
                wrong++;
            }
        }

        return wrong;
    }

    static void print(Step step, int deep, String operation) {

        deep++;

        System.out.println();

        operation += ">";
        operation += step.operation;

        System.out.print(String.format("%s %s %s %d %d", step.resultA, step.resultB, operation, step.total, step.distance));

        if (step.children != null) {
            for (Step step1 : step.children) {
                if (step1 != null) {
                    print(step1, deep, operation);
                }
            }
        }
    }

    static class Step {

        public Step(String operation, int distance, String resultA, String resultB, int cost, int total) {
            this.operation = operation;
            this.distance = distance;
            this.resultA = resultA;
            this.resultB = resultB;
            this.cost = cost;
            this.total = total;
        }

        String operation;
        int distance;
        String resultA;
        String resultB;
        int cost;
        int total;
        Step parent;
        Step[] children;
    }

}
