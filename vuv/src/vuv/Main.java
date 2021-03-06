package vuv;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {

        Set<Integer> ints = new HashSet<>();

        ints.add(1);
        ints.add(2);
        ints.add(3);
        ints.add(4);
        System.out.println(perms(ints, 2));

        final StartData sd = Import.importStream(Main.class.getResourceAsStream("field.csv"));
        System.out.println(sd.courses);
        System.out.println(sd.students);
 /*
        List<Student> students = new ArrayList<>();
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("c1", 1));
        courses.add(new Course("c2", 2));
        courses.add(new Course("c3", 3));
        courses.add(new Course("c4", 4));
        courses.add(new Course("c5", 5));

        List<Course> coursesS1 = new ArrayList<>();
        List<Course> coursesS2 = new ArrayList<>();
        List<Course> coursesS3 = new ArrayList<>();

        System.out.println(sortStudents(sd));*/

        //final Map<Student, Course> assignments = solve(sd);
        final Map<Student, Course> assignments = solveAdvanced(sd);
        System.out.println(assignments);
        System.out.print("    ");
        for (final Course c : sd.courses) {
            System.out.print(String.format("%5s", c.name));
        }
        System.out.println();

        for (final Student s : sd.students) {
            System.out.print(String.format("%4s", s.name));
            for (final Course c : sd.courses) {
                if (assignments.get(s).equals(c)) {
                    System.out.print("    x");
                } else {
                    System.out.print("     ");
                }
            }
            System.out.println();
        }
    }

    // Part 1:

    public static List<Student> sortStudents(StartData d) {
        List<Student> students = d.students;
        List<Course> courses = d.courses;

        courses.sort(Comparator.comparing(Course::getCapacity));


        List<Student> result = new ArrayList<>();
        for (Course c : courses) {
            for (Student s : students) {
                if (s.courses.get(0).equals(c)) {
                    result.add(s);
                }
            }
        }
        return result;
    }

    public static Course getBestCourse(StartData d, Map<Student, Course> assignments, Student s) {
        List<Course> studentCourses = s.courses;
        for (int i = 0; i < studentCourses.size(); i++) {
            int counter = 0;
            for (Course course : d.courses) {
                if (studentCourses.get(i).equals(course)) {
                    int capacity = course.capacity;
                    for (Course cou : assignments.values()) {
                        if (cou.equals(course)) {
                            counter++;
                        }
                    }
                    if (counter < capacity) {
                        return course;
                    }
                }
            }
        }
        for (Course c : d.courses) {
            int counter = 0;
            int capacity = c.capacity;
            for (Course cou : assignments.values()) {
                if (cou.equals(c)) {
                    counter++;
                }
            }
            if (counter < capacity) {
                return c;
            }
        }
        return null;
    }

    public static Student getWorstAssignedStudent(Map<Student, Course> assignments) {
        Student result = null;
        int score = Integer.MAX_VALUE;
        Set<Student> students = new HashSet<>(assignments.keySet());
        for (Student s : students) {
            List<Course> courses = s.courses;
            int scoreStudent = 0;
            for (int i = 0; i < courses.size(); i++) {
                if (assignments.get(s).equals(courses.get(i))) {
                    if (i == 0) {
                        scoreStudent = 50;
                    } else if (i == 1) {
                        scoreStudent = 45;

                    } else if (i == 2) {
                        scoreStudent = 30;
                    }
                }
            }
            if (scoreStudent < score) {
                result = s;
                score = scoreStudent;
            }
        }
        return result;
    }

    public static List<Swap> getAllSwaps(Student s, Map<Student, Course> assignments) {
        List<Swap> result = new ArrayList<>();
        for (Student student : assignments.keySet()) {
            if (!student.equals(s)) {
                result.add(new Swap(s, student, assignments.get(s), assignments.get(student)));
            }
        }
        return result;
    }

    public static double getScore(Swap swap) {
        Student studA = swap.studentA;
        Student studB = swap.studentB;
        int scoreBefore = 0;
        for (int i = 0; i < studA.courses.size(); i++) {
            if (swap.courseA.equals(studA.courses.get(i))) {
                if (i == 0) {
                    scoreBefore += 50;
                } else if (i == 1) {
                    scoreBefore += 45;

                } else if (i == 2) {
                    scoreBefore += 30;
                }
            }
            if (swap.courseB.equals(studB.courses.get(i))) {
                if (i == 0) {
                    scoreBefore += 50;
                } else if (i == 1) {
                    scoreBefore += 45;

                } else if (i == 2) {
                    scoreBefore += 30;
                }
            }
        }
        int scoreAfter = 0;
        for (int i = 0; i < studA.courses.size(); i++) {
            if (swap.courseB.equals(studA.courses.get(i))) {
                if (i == 0) {
                    scoreAfter += 50;
                } else if (i == 1) {
                    scoreAfter += 45;

                } else if (i == 2) {
                    scoreAfter += 30;
                }
            }
            if (swap.courseA.equals(studB.courses.get(i))) {
                if (i == 0) {
                    scoreAfter += 50;
                } else if (i == 1) {
                    scoreAfter += 45;

                } else if (i == 2) {
                    scoreAfter += 30;
                }
            }
        }
        return scoreAfter - scoreBefore;
    }

    public static Swap getBestSwap(List<Swap> swaps) {
        Swap result = null;
        double min = Double.MIN_EXPONENT;
        for (Swap swap : swaps) {
            double score = getScore(swap);
            if (score > min) {
                result = swap;
                min = score;
            }
        }
        return result;
    }

    public static Map<Student, Course> solve(StartData d) {
        List<Student> sortStudents = sortStudents(d);
        Map<Student, Course> assignments = new HashMap<>();
        for (Student s : sortStudents) {
            Course c = getBestCourse(d, new HashMap<>(), s);
            assignments.put(s, c);
        }
        boolean bool = true;
        while (bool) {
            Student stud = getWorstAssignedStudent(assignments);
            List<Swap> swaps = getAllSwaps(stud, assignments);
            Swap swap = getBestSwap(swaps);
            if (getScore(swap) > 0) {
                assignments.put(swap.studentA, swap.courseB);
                assignments.put(swap.studentB, swap.courseA);
            } else bool = false;
        }
        return assignments;
    }

    // Part 2:


    private static <T> void helper(int start, ArrayList<T> nums, List<List<T>> result, int depthRemaining) {
        if (start == depthRemaining) {
            int counter = 0;
            ArrayList<T> list = new ArrayList<>();
            for (T num : nums) {
                if (counter < depthRemaining) {
                    list.add(num);
                    counter++;
                }
            }
            result.add(list);
            return;
        }

        for (int i = start; i <= depthRemaining + 1; i++) {
            swap(nums, i, start);
            helper(start + 1, nums, result, depthRemaining);
            swap(nums, i, start);

        }
    }

    private static <T> void swap(ArrayList<T> nums, int i, int j) {
        T temp = nums.get(i);
        nums.set(i, nums.get(j));
        nums.set(j, temp);
    }

    public static <T> List<List<T>> perms(Set<T> pool, int depthRemaining) {
        List<List<T>> result = new ArrayList<>();
        if (pool.isEmpty()) {
            List<List<T>> res = new ArrayList<>();
            res.add(new ArrayList<>(pool));
            return res;
        }
        if (pool.size() <= depthRemaining) {
            return new ArrayList<>(new ArrayList<>());
        }
        if (depthRemaining == 1) {
            for (T t : pool) {
                ArrayList<T> li = new ArrayList<>();
                li.add(t);
                result.add(li);
            }
            return result;
        }
        ArrayList<T> array = new ArrayList<>(pool);
        helper(0, array, result, depthRemaining);
        return result;

    }

    public static List<Swaps> getAllSwaps(int depth, Map<Student, Course> assignments) {
        List<Swaps> result = new ArrayList<>();
        //List<Student> students = new ArrayList<>(assignments.keySet());
        //List<Course> courses = new ArrayList<>(assignments.values());
        List<List<Student>> studList = perms(assignments.keySet(), depth);
        for (List<Student> studs : studList) {
            List<Course> courseList = new ArrayList<>();
            for (Student s : studs) {
                courseList.add(assignments.get(s));
            }
            result.add(new Swaps(studs, courseList));
        }
        return result;
    }


    public static double getScore(Swaps swaps) {
        ArrayList<Student> students = swaps.students;
        ArrayList<Course> courses = swaps.courses;
        int countBefore = 0;
        for (int i = 0; i < students.size(); i++) {
            for (int j = 0; j < students.get(i).courses.size(); j++) {
                if (students.get(i).courses.get(j).equals(courses.get(i))) {
                    if (j == 0) {
                        countBefore += 50;
                    } else if (j == 1) {
                        countBefore += 45;

                    } else if (j == 2) {
                        countBefore += 30;
                    }
                }
            }
        }
        int countAfter = 0;
        for (int i = 0; i < students.size(); i++) {
            for (int j = 0; j < students.get(i).courses.size(); j++) {
                int next = i + 1;
                if (i + 1 >= students.size()) {
                    next = (i + 1) % students.size();
                }
                if (students.get(i).courses.get(j).equals(courses.get(next))) {
                    if (j == 0) {
                        countAfter += 50;
                    } else if (j == 1) {
                        countAfter += 45;

                    } else if (j == 2) {
                        countAfter += 30;
                    }
                }
            }
        }
        return countAfter - countBefore;
    }

    public static Student getMostUrgentStudent(Set<Student> students, Map<Student, Course> assignments) {
        Student result = null;
        int freePlaces = Integer.MAX_VALUE;
        for (Student s : students) {
            Course firstChoice = s.courses.get(0);
            int courseCap = firstChoice.capacity;
            for (Student st : assignments.keySet()) {
                if (assignments.get(st).equals(firstChoice)) {
                    courseCap--;
                }
            }
            if (courseCap < freePlaces) {
                result = s;
                freePlaces = courseCap;
            }
        }
        return result;
    }

    public static Map<Student, Course> solveAdvanced(StartData d) {
        List<Student> students = d.students;
        Map<Student, Course> assignments = new HashMap<>();
        while (!students.isEmpty()) {
            Student studFirst = getMostUrgentStudent(new HashSet<>(d.students), assignments);
            students.remove(studFirst);
            Course c = getBestCourse(d, assignments, studFirst);
            assignments.put(studFirst, c);
            int number = 0;
            for (Course cou : assignments.values()) {
                if (cou.equals(c)) {
                    number++;
                }
            }
            if (number > 1) {
                List<Swap> swapList = getAllSwaps(studFirst, assignments);
                Swap targetSwaps = null;
                double score = Double.MIN_VALUE;
                for (Swap sw : swapList) {
                    double sc = getScore(sw);
                    if (sc > score) {
                        score = sc;
                        targetSwaps = sw;
                    }
                }
                if (targetSwaps != null) {
                    assignments.put(targetSwaps.studentA, targetSwaps.courseA);
                    assignments.put(targetSwaps.studentB, targetSwaps.courseB);
                }
            }
        }

        boolean bool = true;
        while (bool) {
            List<Swaps> swapsList = getAllSwaps(2, assignments);
            Swaps best = null;
            double bestScore = 0;
            for (Swaps sw : swapsList) {
                if (getScore(sw) > bestScore) {
                    best = sw;
                    bestScore = getScore(sw);
                }
            }
            if (bestScore > 0) {
                assignments.put(best.students.get(0), best.courses.get(0));
                assignments.put(best.students.get(1), best.courses.get(1));
                continue;

            }
            if (bestScore <= 0) {
                List<Swaps> swapsList1 = getAllSwaps(3, assignments);
                Swaps best1 = null;
                double bestScore1 = 0;
                for (Swaps sw1 : swapsList1) {
                    if (getScore(sw1) > bestScore1) {
                        best1 = sw1;
                        bestScore1 = getScore(sw1);
                    }
                }
                if (bestScore1 > 0) {
                    assignments.put(best1.students.get(0), best1.courses.get(0));
                    assignments.put(best1.students.get(1), best1.courses.get(1));
                } else if (bestScore1 <= 0) {
                    bool = false;
                }
            }
        }
        return assignments;
    }
}