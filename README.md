# Meet The Family

This application models the family tree of King Arthur. The application supports two operations,
1. Adding a child to any family through the mother
2. Get relations of a member

Supported relationships are,
- Son
- Daughter
- Siblings
- Brother-In-Law
- Sister-In-Law
- Maternal-Aunt
- Paternal-Aunt
- Maternal-Uncle
- Paternal-Uncle

## Input and Output
Input is a text file with a set of operations where each line contains a single operation.

Sample input content:

```text
ADD_CHILD Flora Minerva Female
GET_RELATIONSHIP Remus Maternal-Aunt
GET_RELATIONSHIP Minerva Siblings
ADD_CHILD Luna Lola Female
```

Path of the input file should be passes as the only argument to the application. The output will be printed to the standard output.

## Assumptions
- Only Female family members who have a spouse can have children. (There are no single mothers)
- In-laws include all spouses of their siblings. For example, Audrey's brother in laws are Bill, Charlie, Ronald and Harry

## Building and Running The Application
- Change directory to `geektrust-familytree`
- Run `mvn clean install`

After running the above command, a JAR file `geektrust.jar` will be generated in `geektrust-familytree/target` directory.
To run the application navigate to the `geektrust-familytree/target` and run the following command.

```text
java -jar geektrust.jar <input_file_path>
```