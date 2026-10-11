Yes — remember this question. It is extremely useful for your Wissen preparation.

This is not a standard "reverse an array" question. It is testing whether you can:

parse the input → understand units → convert to a common unit → sort → format the output.

For example, suppose the input is:

fileA.txt 2 KB
fileB.txt 500 B
fileC.txt 1 MB
fileD.txt 3 GB
fileE.txt 1 TB

The correct size ordering is:

fileB.txt   500 B
fileA.txt   2 KB
fileC.txt   1 MB
fileD.txt   3 GB
fileE.txt   1 TB
The key idea

Never compare the strings "2 KB" and "500 B" directly.

Convert everything to one common unit.

For example, use bytes:

B   = 1
KB  = 1024
MB  = 1024²
GB  = 1024³
TB  = 1024⁴

So:

500 B       → 500
2 KB        → 2048
1 MB        → 1048576
3 GB        → 3221225472
1 TB        → 1099511627776

Now sorting becomes a normal numeric sorting problem.

In Java, I'd structure it like this

Create an object:

class FileInfo {
String name;
double size;
String unit;


    FileInfo(String name, double size, String unit) {
        this.name = name;
        this.size = size;
        this.unit = unit;
    }
}

Then create a conversion method:

static double toBytes(double size, String unit) {


    switch (unit.toUpperCase()) {
        case "B":
            return size;


        case "KB":
            return size * 1024;


        case "MB":
            return size * 1024 * 1024;


        case "GB":
            return size * 1024 * 1024 * 1024;


        case "TB":
            return size * 1024 * 1024 * 1024 * 1024;


        default:
            throw new IllegalArgumentException("Invalid unit");
    }
}

Then sort based on the converted value:

files.sort(Comparator.comparingDouble(
file -> toBytes(file.size, file.unit)
));

The interviewer could then push you further:

What if the input is "movie.mp4 1.5 GB"?

Your parser needs to handle decimal values.

What if units are lowercase?

Use:

unit.toUpperCase()

What if there are duplicate sizes?

Normal sorting handles that.

What is the time complexity?

If there are n files:

Conversion: O(n)
Sorting:     O(n log n)


Overall:     O(n log n)
More importantly, this tells us something about Wissen

This question fits the pattern we've been seeing.

They may give you a real-world-looking problem rather than saying:

"Implement Merge Sort."

The file-size question is basically a custom sorting/comparator problem.

So add this category to your Wissen preparation:

Custom Sorting / Comparator

You should know how to solve:

Sort files by size
Sort employees by salary
Sort students by marks
Sort strings by length
Sort products by price
Sort objects by multiple fields

And especially:

Comparator.comparing(...)
Comparator.comparingInt(...)
Comparator.comparingDouble(...)
.thenComparing(...)

For example:

files.sort(
Comparator.comparingDouble(
f -> toBytes(f.size, f.unit)
)
);
I would add these to your DSA mock sheet

Custom sorting / comparator — MUST KNOW

Sort files by size with units
Sort employees by salary
Sort students by marks descending
Sort strings by length
Sort objects by two fields

This is much more relevant to your actual Wissen experience than blindly doing random LeetCode problems.

And I want you to tell me as much as you remember about that exact file-size question — especially what the input looked like and what output they expected. We can reconstruct the actual interview problem and I'll make you solve it in Java exactly the way you'd be expected to in the Wissen technical round.

So, in this, now I, like, remember this question, I, inside my mind, I tried using HashMap, where a name is in the key, key, key, and the size in the value.

Yes — and your HashMap idea was not stupid at all. You were actually identifying the important relationship:

file name → file size

So you were thinking:

HashMap<String, size>

For example:

"movie.mp4" → 2.5 GB
"photo.jpg" → 500 KB
"document.pdf" → 1 MB

That is a perfectly reasonable first data structure.

The problem is that HashMap is not the actual solution to the sorting part.

Why?

Suppose:

A.txt → 5 MB
B.txt → 2 KB
C.txt → 1 GB

You need:

B.txt → 2 KB
A.txt → 5 MB
C.txt → 1 GB

A HashMap is mainly for key → value lookup. It does not inherently maintain the files in size order.

So your thought process should become:

Step 1:
Store file information.


Step 2:
Convert every size into a common unit.


Step 3:
Sort according to converted size.


Step 4:
Print the file names in sorted order.
There is an even better way to think about it

Instead of:

HashMap<String, String>

I'd create an object:

class FileInfo {
String name;
double size;
String unit;
}

Then each file is:

FileInfo("A.txt", 5, "MB")
FileInfo("B.txt", 2, "KB")
FileInfo("C.txt", 1, "GB")

Now you can sort the objects using a Comparator.

That's an important interview concept:

When you have multiple attributes belonging to one thing, model it as an object.

But here's something important for your Wissen preparation

This question tells me that you should add "Collections + Comparator + Custom Sorting" to your DSA preparation.

Because the interviewer wasn't simply asking:

"Can you sort an array?"

They were testing whether you could take a real-world object, derive a comparable value, and sort the objects.

Your practice should include:

Sort employees by salary
Sort students by marks
Sort products by price
Sort files by size
Sort strings by length
Sort employees by salary, then name

And learn this properly:

Comparator.comparing(...)
Comparator.comparingInt(...)
Comparator.comparingDouble(...)
.thenComparing(...)
Your file problem essentially becomes
files.sort(
Comparator.comparingDouble(
file -> convertToBytes(file.size, file.unit)
)
);

The difficult part isn't the sorting algorithm.

The difficult part is:

"What value should I compare?"

Once you convert:

500 B
2 KB
5 MB
1 GB

into bytes, the sorting itself becomes straightforward.

And Swap, this is actually useful for your anxiety: you remembered a real question from your previous Wissen interview.

That means we can use your previous interview as a dataset.

So far we know they have tested you on:

DSA/problem solving + SQL + Java + custom real-world coding problems.

That is much more actionable than just preparing random LeetCode.

For this specific file-size problem, I would have you practice 3 versions:

Basic sorting using Comparator
Handling B/KB/MB/GB/TB
Handling decimal sizes like 1.5 GB

That will make this type of question very comfortable.

So can you give me one DSA sheet, like, for each pattern or a question I should, I must do it, pass the, like, at least pass the screening round properly. Please give me that. Also for the SQL part, and I want to practice SQL on, so can you like tell me what, wise, how much questions are, where the Wisen takes the questions.

Yes. I would make this screening-only, not a giant DSA roadmap.

I also checked the freshest Wissen reports I could find. A June 2026 Mumbai Software Engineer assessment was reported as 120 minutes, 5 MCQs + 1 SQL challenge + 2 DSA coding problems, with DSA worth 150 points total and SQL 25 points. Another July 2026 report says Wissen uses an assessment with DSA/SQL, followed by technical rounds that can again contain DSA.

So your immediate goal is:

Be able to solve 2 easy-to-medium DSA problems + 1 SQL problem under time pressure.

1. Wissen Screening — DSA Must-Know Sheet

I've deliberately kept this to the smallest useful set.

Priority A — MUST DO
Pattern	You must practice
Array basics	max/min, reverse, duplicates
HashMap	frequency, lookup
HashSet	duplicates, unique elements
Two Sum / Pair Sum	target sum
Two Pointers	pair in sorted array
Sliding Window	longest/maximum window
String frequency	first non-repeating character
Palindrome	normal + rearrangement
Prefix Sum	zero-sum subarray
Kadane	maximum subarray
Binary Search	standard search
Matrix	traversal, diagonal, transpose
Custom sorting	comparator/object sorting

These are not random choices. Wissen reports include longest substring without repeating characters, palindrome-by-rearrangement, non-repeating character, pivot index, and other array/string problems.

And your own previous Wissen question—sorting files by B/KB/MB/GB/TB—belongs squarely in custom sorting + comparator.

2. The exact DSA questions I want you to solve

Don't just read them. Code every one in Java.

Arrays — 5
Find largest and smallest element.
Reverse an array.
Move all zeros to the end.
Find missing number from 1..n.
Find duplicate elements.
HashMap / HashSet — 5
Count frequency of each element.
First non-repeating character.
Two Sum.
Count pairs with a given sum.
Check whether an array contains duplicates.
Strings — 4
Reverse a string.
Check palindrome.
Check whether a palindrome can be formed by rearranging characters.
Longest substring without repeating characters.

That last one has appeared in a recent Wissen Software Engineer report.

Sliding Window / Prefix Sum — 4
Maximum sum subarray of size K.
Longest substring without repeating characters.
Longest subarray with sum 0.
Pivot Index.

Pivot Index has also been reported in Wissen interview experiences.

Kadane / Binary Search — 3
Maximum subarray sum using Kadane's algorithm.
Standard binary search.
First/last occurrence in sorted array.
Matrix — 4
Print matrix row-wise.
Sum of main diagonal.
Sum of secondary diagonal.
Transpose a matrix.
Custom Sorting — 3
Sort employees by salary.
Sort students by marks descending.
Sort files by size where sizes can be B/KB/MB/GB/TB.

That last one is directly based on the question you remember from your previous Wissen technical interview.

3. What you DON'T need for the screening right now

Do not spend the next two days trying to master:

Dynamic Programming
Graphs
Advanced Trees
Trie
Segment Tree
Disjoint Set
Advanced Backtracking

There are reports of harder DSA later in Wissen's process, including trees/graphs, but that is different from your immediate screening objective.

For the screening, get A-level proficiency in the 28 problems above before touching advanced topics.

4. Your DSA pattern-recognition cheat sheet

This is more important than memorizing solutions.

See "frequency"?

→ HashMap

See "duplicate"?

→ HashSet

See "target sum / pair"?

→ HashMap or two pointers

See "sorted array + pair"?

→ Two pointers

See "longest substring"?

→ Sliding Window

See "subarray sum = 0"?

→ Prefix Sum + HashMap

See "maximum contiguous sum"?

→ Kadane

See "sorted array + search"?

→ Binary Search

See "matrix"?

→ Nested loops

See "objects need ordering"?

→ Comparator

That recognition should become automatic.

5. How much DSA should you actually mock?

Do three full coding mocks, not 50 random questions.

Mock 1 — 60 minutes
1. Two Sum
2. First Non-Repeating Character
3. Pivot Index
   Mock 2 — 60 minutes
1. Maximum Subarray
2. Longest Substring Without Repeating
3. Binary Search
   Mock 3 — 75 minutes
1. Longest Zero-Sum Subarray
2. Move Zeros
3. Matrix Diagonal Sum
4. File Size Sorting

For every question:

understand → code → dry run → complexity

Don't look at the solution during the timer.

6. SQL — this is your second major priority

This is especially important because you personally got stuck on SQL last time. Your previous notes say the unfamiliar movie/Marvel-style database question caused you to freeze because you didn't know the syntax.

The recent Mumbai assessment report says there was one SQL query challenge.

Older Wissen reports also describe SQL questions involving basic queries, GROUP BY, and SQL during technical rounds.

So I want you to practice 30 SQL questions, not 300.

7. Wissen SQL Sheet — 30 Questions
   Level 1 — Basic
   Select all employees.
   Select employees with salary > 50,000.
   Find employees from Mumbai.
   Sort employees by salary.
   Find distinct departments.
   Count total employees.
   Find maximum salary.
   Find minimum salary.
   Find average salary.
   Find employees whose name starts with A.
   Level 2 — GROUP BY
   Count employees per department.
   Find average salary per department.
   Find maximum salary per department.
   Departments having more than 5 employees.
   Departments with average salary > 60,000.
   Level 3 — JOIN

Assume:

employees
---------
id
name
dept_id
salary


departments
-----------
id
department_name
Show employee name + department name.
Show all employees including those without a department.
Find employees in the IT department.
Count employees per department using JOIN.
Find highest-paid employee with department name.
Level 4 — Subqueries
Find second-highest salary.
Find employees earning more than average salary.
Find employee with maximum salary.
Find departments whose average salary is above company average.
Find the third-highest salary.
Level 5 — Unfamiliar schema

This is the most important section for you.

Movies + actors → find actors appearing in a particular movie.
Customers + orders → total orders per customer.
Students + courses + enrollment → students taking a particular course.
Products + orders → total quantity sold per product.
Customers + transactions → total transaction amount per customer.

The point is not the domain.

It could say:

Marvel
Movies
Actors
Directors

or:

Students
Courses
Teachers

or:

Customers
Orders
Products

You must be able to identify the relationships and write:

SELECT ...
FROM ...
JOIN ...
WHERE ...
GROUP BY ...
HAVING ...
ORDER BY ...

That is exactly the skill you were missing last time.

8. Where should you practice SQL?

For the Wissen screening, I would use platforms where you have an actual schema and have to write queries, rather than just reading SQL theory.

Best choices

HackerRank SQL

Good for progressive SQL practice and close to the kind of assessment environment you may see.

LeetCode SQL 50

Very good for interview-style query patterns.

HackerEarth SQL problems

Relevant because the June 2026 Wissen assessment report specifically says the assessment was conducted on HackerEarth.

And HackerEarth's own SQL documentation confirms that its assessment system supports SQL challenges covering joins, aggregates, nested queries, window functions, string/date operations, and related SQL skills.

For you specifically

Don't start with advanced HackerRank SQL.

Do:

HackerRank SQL → Basic Select → Aggregation → Basic Join → Advanced Select/Subquery

Then move to:

LeetCode SQL 50 → first ~25 questions

That's enough for the immediate screening.

9. How many SQL questions does Wissen "take"?

There is no single fixed number across all Wissen drives.

The most recent Mumbai Software Engineer assessment report I found says:

5 MCQ + 1 SQL + 2 DSA.

An older Mumbai Wissen Infotech report says:

5 MCQ + 3 DSA + 1 SQL.

Another 2026 report says an OA had general coding, followed by a technical round with 2 DSA + 1 SQL.

So don't prepare for "exactly one SQL question."

Prepare for:

One serious SQL problem that could be unfamiliar.

That's the right mindset.

10. The screening score strategy

Because the recent Mumbai assessment had:

DSA = 150 points

SQL = 25 points

MCQs = relatively small points,

your priority is:

1️⃣ DSA — 50%
2️⃣ SQL — 30%
3️⃣ Java/CS MCQs — 20%

That doesn't mean ignore SQL. A 25-point SQL question can still be the difference between passing and failing.

11. What your Java MCQs should cover

Since you said there are 5–10 MCQs, don't study random aptitude.

Revise:

OOP
String
== vs equals
HashMap
HashSet
ArrayList
LinkedList
Exception handling
Java 8
Streams
Collections
Output prediction
Time complexity
Basic SQL/DBMS

A recent Wissen report specifically mentions HashMap internals, equals/hashCode, longest substring, and palindrome rearrangement in a Software Engineer interview.

Your final screening sheet

This is what I would literally keep beside you while preparing:

WISSEN SCREENING — MUST PASS


DSA
[ ] Arrays
[ ] HashMap
[ ] HashSet
[ ] Two Sum
[ ] Pair Sum
[ ] Frequency
[ ] First non-repeating character
[ ] Strings
[ ] Palindrome
[ ] Longest substring without repeating
[ ] Sliding Window
[ ] Prefix Sum
[ ] Zero-sum subarray
[ ] Pivot Index
[ ] Kadane
[ ] Binary Search
[ ] Matrix
[ ] Custom Comparator
[ ] File-size sorting


SQL
[ ] SELECT / WHERE
[ ] ORDER BY
[ ] DISTINCT
[ ] COUNT / SUM / AVG / MAX / MIN
[ ] GROUP BY
[ ] HAVING
[ ] INNER JOIN
[ ] LEFT JOIN
[ ] Subqueries
[ ] Second highest salary
[ ] 3-table JOIN
[ ] Aggregation + JOIN
[ ] Unfamiliar schema


JAVA MCQ
[ ] OOP
[ ] Collections
[ ] HashMap
[ ] HashSet
[ ] String
[ ] equals/hashCode
[ ] Java 8
[ ] Streams
[ ] Exceptions
[ ] Output questions
And one rule for your preparation:

Don't do 100 DSA problems badly.

Do these ~28 DSA problems repeatedly until you can recognize the pattern and code them in Java without looking at the solution.

For SQL, do the 30-question sheet twice, and especially practice unfamiliar schemas.

That is a much more realistic path to clearing the screening than trying to "finish DSA."

And the fact that the current Mumbai assessment was only 2 DSA + 1 SQL + 5 MCQs means we can prepare very deliberately for this gate.