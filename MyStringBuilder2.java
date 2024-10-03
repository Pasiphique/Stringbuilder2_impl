/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// CS 0445 Fall 2021
// Read this class and its comments very carefully to make sure you implement
// the class properly.  The data and public methods in this class are identical
// to those in MyStringBuilder, with the exception of the two additional methods
// shown at the end.  You cannot change the data or add any instance
// variables.  However, you may (and will need to) add some private methods.
// No iteration (i.e. no loops) is allowed in this implementation. 
// For more details on the general functionality of most of these methods, 
// see the specifications of the method in the StringBuilder class from Assignment 2.  
public class MyStringBuilder2 {
    // These are the only two instance variables you are allowed to have.
    // See details of CNode class below.  In other words, you MAY NOT add
    // any additional instance variables to this class.  However, you may
    // use any method variables that you need within individual methods.
    // But remember that you may NOT use any variables of any other
    // linked list class or of the predefined StringBuilder or 
    // or StringBuffer class in any place in your code.  You may only use the
    // String class where it is an argument or return type in a method.

    private CNode firstC;	// reference to front of list.  This reference is necessary
    // to keep track of the list
    private int length;  	// number of characters in the list

    // You may also add any additional private methods that you need to
    // help with your implementation of the public methods.
    // Create a new MyStringBuilder2 initialized with the chars in String s.
    // This method is provided to you -- see the Assignment 3 document.
    // Constructor to make a new MyStringBuilder2 from a String. The constructor
// itself is NOT recursive – however, it calls a recursive method to do the
// actual set up work. This should be your general approach for all of the
// methods, since the recursive methods typically need extra parameters that
// are not given in the specification.
    public MyStringBuilder2(String s) {
        if (s != null && s.length() > 0) {
            makeBuilder(s, 0);
        } else {// no String so initialize empty MyStringBuilder2
            firstC = null;
            length = 0;
        }
    }
// Recursive method to set up a new MyStringBuilder2 from a String

    private void makeBuilder(String s, int pos) {
        // Recursive case – we have not finished going through the String
        if (pos < s.length() - 1) {
            // Note how this is done – we make the recursive call FIRST, then
// add the node before it. In this way EVERY node we add is
// the front node, and it enables us to avoid having to make a
// special test for the front node. However, many of your
// methods will proceed in the normal front to back way.

            makeBuilder(s, pos + 1);
            CNode temp = new CNode(s.charAt(pos));
            CNode back = firstC.prev;
            temp.prev = back;
            back.next = temp;
            temp.next = firstC;
            firstC.prev = temp;
            firstC = temp;
            length++;
        } else if (pos == s.length() - 1) {
            // Special case for last char in String
            // This is a base case and initializes
            firstC = new CNode(s.charAt(pos));
            firstC.next = firstC;
            firstC.prev = firstC;
            length = 1;

        } else {

            // This case should never be reached, due to the way the
// constructor is set up. However, I included it as a
            // safeguard (in case some other method calls this one)
            length = 0;
            firstC = null;
        }

    }
    // Create a new MyStringBuilder2 initialized with the chars in array s

    public MyStringBuilder2(char[] s) {
        if (s != null && s.length > 0) {
            charBuilder(s, 0);
        } else {// no String so initialize empty MyStringBuilder2
            firstC = null;
            length = 0;
        }
    }

    private void charBuilder(char[] s, int pos) {
        // Recursive case – we have not finished going through the String
        if (pos < s.length - 1) {
            // Note how this is done – we make the recursive call FIRST, then
// add the node before it. In this way EVERY node we add is
// the front node, and it enables us to avoid having to make a
// special test for the front node. However, many of your
// methods will proceed in the normal front to back way.

            charBuilder(s, pos + 1);
            CNode temp = new CNode(s[pos]);
            CNode back = firstC.prev;
            temp.prev = back;
            back.next = temp;
            temp.next = firstC;
            firstC.prev = temp;
            firstC = temp;
            length++;

        } else if (pos == s.length - 1) {
            // Special case for last char in String
            // This is a base case and initializes
            firstC = new CNode(s[pos]);
            firstC.next = firstC;
            firstC.prev = firstC;
            length = 1;

        } else {
            // This case should never be reached, due to the way the
// constructor is set up. However, I included it as a
            // safeguard (in case some other method calls this one)
            length = 0;
            firstC = null;
        }
    }

    // Copy constructor -- make a new MyStringBuilder2 from an old one.  Be sure
    // that you make new nodes for the copy.
    public MyStringBuilder2(MyStringBuilder2 old) {
        if (old != null && old.length > 0) {
            CNode oldNode = old.firstC;
            firstC = new CNode(oldNode.data);
            CNode currentNode = firstC;
            length++;
            copyBuilder(oldNode, 1, old, currentNode);
        } else {// no String so initialize empty MyStringBuilder2
            firstC = null;
            length = 0;
        }
    }

    private void copyBuilder(CNode oldNode, int pos, MyStringBuilder2 old, CNode currentNode) {
        // Recursive case– we have not finished going through the String
        if (old.length() == 1) {
            // Special case for last char in String
            // This is a base case and initializes

            firstC = new CNode(oldNode.data);
            firstC.next = firstC;
            firstC.prev = firstC;
            length = 1;

        }
        if (pos < old.length()) {

            CNode temp = new CNode(oldNode.next.data);
            currentNode.next = temp;
            temp.prev = currentNode;

            temp.next = firstC;
            firstC.prev = temp;

            currentNode = temp;
            length++;
            copyBuilder(oldNode.next, pos + 1, old, currentNode);

        }

    }

    // Create a new empty MyStringBuilder2
    public MyStringBuilder2() {
        firstC = null;
        length = 0;
    }

    // Return the entire contents of the current MyStringBuilder2 as a String.
    // This method is provided to you -- see Assignment 3 document.
    // Again note that the specified method is not actually recursive – rather it
// calls a recursive method to do the work. Note that in this case we also
// create a char array before the recursive call, then pass it as a
// parameter, then construct and return a new string from the char array.
// Carefully think about the parameters you will be passing to your recursive
// methods. Through them you must be able to move through the list and
// reduce the "problem size" with each call.
    public String toString() {
        char[] c = new char[length];
        getString(c, 0, firstC);
        return (new String(c));
    }
// Here we need the char array to store the characters, the pos value to
// indicate the current index in the array and the curr node to access
// the data in the actual MyStringBuilder2. Note that these rec methods
// are private – the user of the class should not be able to call them.

    private void getString(char[] c, int pos, CNode curr) {
        if (pos < length) // Not at end of the list
        {
            c[pos] = curr.data; // put next char into array
            getString(c, pos + 1, curr.next); // recurse to next node and
        } // next pos in array
    }

    // Append MyStringBuilder2 b to the end of the current MyStringBuilder2, and
    // return the current MyStringBuilder2.  Be careful for special cases!
    public MyStringBuilder2 append(MyStringBuilder2 b) {
        if (b != null && b.length > 0) {
            CNode currentNode = firstC.prev;
            CNode b2 = b.firstC;
            appendBuilder(currentNode, 0, b, b2);
        } else {// no String so initialize empty MyStringBuilder2
            firstC = null;
            length = 0;
        }
        return this;
    }

    // appends a stringBuilder to the end of the current MyStringBuilder2
    private void appendBuilder(CNode currentNode, int pos, MyStringBuilder2 b, CNode b2) {
        if (b.length() == 1) { // adding one element to my stringBuilder

            CNode temp = new CNode(b.firstC.data);
            currentNode.next = temp;
            temp.prev = currentNode;

            temp.next = firstC;
            firstC.prev = temp;

            currentNode = temp;

        } else if (b.length > 1 && pos < b.length) {

            CNode temp = new CNode(b2.data);
            currentNode.next = temp;
            temp.prev = currentNode;

            temp.next = firstC;
            firstC.prev = temp;

            currentNode = temp;
            length++;
            appendBuilder(currentNode, pos + 1, b, b2.next); // recursive case, not done with append

        }
    }

    // Append String s to the end of the current MyStringBuilder2, and return
    // the current MyStringBuilder2.  Be careful for special cases!
    public MyStringBuilder2 append(String s) {
        if (length == 0) { // adding one string to end of current MyStringBuilder2
            firstC = new CNode(s.charAt(0));
            length++;
            CNode currentNode = firstC;
            appendString(currentNode, s, 1);
            // return this;
        } else if (s != null && s.length() > 0) {
            CNode currentNode = firstC.prev;    // setting currentNode = to lastNode

            appendString(currentNode, s, 0); // recursive case not done with appending string
        } else {
            firstC = null;
            length = 0;
        }
        return this;
    }

    private void appendString(CNode currentNode, String s, int pos) { // recursive case for appending string
        if (s.length() == 1) { // adding one element to the end of the stringbuilder
            CNode temp = new CNode(s.charAt(pos));
            currentNode.next = temp;
            temp.prev = currentNode;
            temp.next = firstC;
            firstC.prev = temp;
            length++;

        } else if (pos < s.length()) {

            CNode temp = new CNode(s.charAt(pos));
            currentNode.next = temp;
            temp.prev = currentNode;

            temp.next = firstC;
            firstC.prev = temp;

            currentNode = temp;
            length++;
            appendString(currentNode, s, pos + 1); // recursive case, keeps going until done
        }
    }

    // Append char array c to the end of the current MyStringBuilder2, and
    // return the current MyStringBuilder2.  Be careful for special cases!
    public MyStringBuilder2 append(char[] c) {
        if (c != null && c.length > 0) {
            CNode currentNode = firstC.prev;
            appendChar(currentNode, c, 0);
        } else {
            firstC = null;
            length = 0;

        }
        return this;
    }

    private void appendChar(CNode currentNode, char[] c, int pos) {// recursive method for the append char method
        if (c.length == 1) {
            CNode temp = new CNode(c[pos]);
            currentNode = firstC.prev;              // setting currentNode = last Node;
            currentNode.next = temp;
            temp.prev = currentNode;

            temp.next = firstC;
            firstC.prev = temp;
            length++;
        } else if (c.length > 0 && pos < c.length) {

            CNode temp = new CNode(c[pos]);
            currentNode.next = temp;
            temp.prev = currentNode;

            temp.next = firstC;
            firstC.prev = temp;

            currentNode = temp;
            length++;
            appendChar(currentNode, c, pos + 1); // recursive case, keeps going untl done

        }

    }

// Append char c to the end of the current MyStringBuilder2, and
// return the current MyStringBuilder2.  Be careful for special cases!
    public MyStringBuilder2 append(char c) {
        if (length == 0) {
            firstC = new CNode(c);

            firstC.prev = firstC;
            firstC.next = firstC;
            length++;
        } else {
            CNode currentNode = firstC.prev;
            CNode charr = new CNode(c);
            CNode newNode = charr; // adding first element in old stringbuilder
            currentNode.next = newNode;
            newNode.prev = currentNode;
            currentNode = newNode;
            length++;
            currentNode.next = firstC;
            firstC.prev = currentNode;

        }
        return this;
    }

    // Return the character at location "index" in the current MyStringBuilder2.
    // If index is invalid, throw an IndexOutOfBoundsException.
    public char charAt(int index) {
        CNode currentNode = firstC;
        if (index > length || index < 0) {
            throw new IndexOutOfBoundsException(" ");
        } else {
            return findChar(currentNode, index, 0);
        }
    }

    private char findChar(CNode currentNode, int index, int counter) { // helper method for charAt method

        if (counter != index) {
            char findChar = findChar(currentNode.next, index, counter + 1); // recursive case. stops until char is found
            return findChar;
        } else if (counter == index) { // if index = 0, return first nodes data
            return currentNode.data;
        } else {
            return 0;
        }

    }

// Delete the characters from index "start" to index "end" - 1 in the
// current MyStringBuilder2, and return the current MyStringBuilder2.
// If "start" is invalid or "end" <= "start" do nothing (just return the
// MyStringBuilder2 as is).  If "end" is past the end of the MyStringBuilder2, 
// only remove up until the end of the MyStringBuilder2. Be careful for 
// special cases!
    public MyStringBuilder2 delete(int start, int end) {
        if (start < 0 || start > length || end < start) {
            return this;
        }
        if (start == 0) {
            if (end - start > length) {
                length = 0;
                return this;
            }

            CNode currentNode = firstC;
            removeFront(currentNode, start, end); // recursive method to remove front node(s)

        } else {
            CNode currentNode = firstC;
            if (end > length) {
                end = length;
            }
            int pos = 0;  // keeping track of current location as you traverse thorugh the linked list
            removeData(currentNode, pos, start, end); // recursive method to remove nodes from start - end 
        }
        return this;
    }

    private MyStringBuilder2 removeData(CNode currentNode, int pos, int start, int end) { //helper method for the delete methoto delete nodes
        if (pos >= start && pos < end) { // deleting elements from start to end
            CNode nodeBefore = currentNode.prev;
            CNode nodeAfter = currentNode.next;
            nodeBefore.next = nodeAfter;
            nodeAfter.prev = nodeBefore;
            currentNode = nodeAfter;
            length--;
            removeData(currentNode, pos + 1, start, end); // recursive case. finishes until pos >end

        }
        if (pos < start) {
            removeData(currentNode.next, pos + 1, start, end); // recursive case. finishes until pos >= start
        }

        return this;

    }

    private void removeFront(CNode currentNode, int start, int end) {// helped method to remove front. 

        if (start < end) {

            firstC = firstC.next;
            CNode CurrentNode = firstC;
            length--;
            removeFront(currentNode.next, start + 1, end);
        }
    }

    // Delete the character at location "index" from the current
    // MyStringBuilder2 and return the current MyStringBuilder2.  If "index" is
    // invalid, do nothing (just return the MyStringBuilder2 as is).
    // Be careful for special cases!
    public MyStringBuilder2 deleteCharAt(int index) {
        if (index > length || index < 0) {
            return this;

        } else if (index == 0) {
            firstC = firstC.next;
            length--;

        } else if (index == length - 1) {
            CNode currentNode = firstC.prev;
            firstC.prev = currentNode.prev;
            length--;
        } else {
            CNode currentNode = firstC;
            deleteMiddle(currentNode, 0, index); // recursive case to find and delete node at an index
        }

        return this;

    }

    private void deleteMiddle(CNode currentNode, int pos, int index) {// helper method to delete node at the endex
        if (pos <= length - 1) {
            if (pos == index) {
                CNode nodeBefore = currentNode.prev;
                CNode nodeAfter = currentNode.next;
                nodeBefore.next = nodeAfter;
                nodeAfter.prev = nodeBefore;
                currentNode = nodeAfter;
                length--;
            }
            deleteMiddle(currentNode.next, pos + 1, index); // recursive case, keeps looping until pos == index
        }
    }

    // Find and return the index within the current MyStringBuilder2 where
    // String str first matches a sequence of characters within the current
    // MyStringBuilder2.  If str does not match any sequence of characters
    // within the current MyStringBuilder2, return -1.  Think carefully about
    // what you need to do for this method before implementing it.
    public int indexOf(String str) {

        // track of how many times stringbuilder data matches String str data
        CNode currentNode = firstC;
        CNode copyNode = currentNode;
        CNode counterNode = currentNode;

        return index(copyNode, 0, str, 0, 0, 0, counterNode); // recursive method to find the index within the MyStringBuilder2
        // where String tr first matches thr sequence of characters
    }

    private int index(CNode copyNode, int matches, String str, int pos, int i, int counter, CNode counterNode) {
// counter is a place holder for where the first match was found
// counterNode is the node for where the counter is

        if (pos < length && matches != str.length()) {

            if (copyNode.data == str.charAt(i)) {
                matches++;                              // matche is incremented everytime the comparisons are equal
                i++;
                pos++;
                copyNode = copyNode.next;
            } else {
                matches = 0;
                counter = counter + 1;
                pos = counter;                            // set pos = counter when the test to find the sequence of chars fails
                // and start from there
                counterNode = counterNode.next;
                copyNode = counterNode;
                i = 0;
            }
            int index = index(copyNode, matches, str, pos, i, counter, counterNode); // recursive case to find the pos of the matches
            return index;
        }
        if (matches == str.length()) {
            return counter;
        } else {
            return -1;
        }
    }

    // Insert String str into the current MyStringBuilder2 starting at index
    // "offset" and return the current MyStringBuilder2.  if "offset" == 
    // length, this is the same as append.  If "offset" is invalid
    // do nothing.
    public MyStringBuilder2 insert(int offset, String str) {
        if (offset < 0 || offset > length) {
            return this;
        }
        if (offset == 0) {

            CNode afterNode = firstC;
            CNode backNode = firstC.prev;
            firstC = new CNode(str.charAt(0));
            CNode currentNode = firstC;

            length++;
            return insertFront(backNode, currentNode, str, 0, offset, afterNode, 1, 0); // helped method to insert to front of linkedList

        } else if (offset == length) {
            CNode currentNode = firstC.prev;

            return insertEnd(currentNode, 0, str);      // helper method to insert at end of linkedList
        } else {
            CNode currentNode = firstC;
            CNode afterNode = currentNode.next;

            return insertStr(currentNode, str, 0, offset, afterNode, 0, 0); // helper method to insert in the middle of linkedlist
        }
    }

    private MyStringBuilder2 insertEnd(CNode currentNode, int i, String str) {
        if (i < str.length()) {
            CNode newNode = new CNode(str.charAt(i));
            currentNode.next = newNode;
            newNode.prev = currentNode;
            currentNode = newNode;
            length++;
            i++;
            return insertEnd(currentNode, i, str);// Recursive case– we have not finished going through the String
        }
        currentNode.next = firstC;
        firstC.prev = currentNode;
        return this;
    }

    private MyStringBuilder2 insertFront(CNode backNode, CNode currentNode, String str, int pos, int offset, CNode afterNode, int i, int counter) {
        // Recursive case– we have not finished going through the String
        if (i < str.length()) {
            CNode newNode = new CNode(str.charAt(i));
            currentNode.next = newNode;
            newNode.prev = currentNode;
            currentNode = newNode;
            length++;
            i++;
            return insertFront(backNode, currentNode, str, pos + 1, offset, afterNode, i, counter);
        }
        currentNode.next = afterNode;
        afterNode.prev = currentNode;
        firstC.prev = backNode;
        backNode.next = firstC;
        return this;

    }

    private MyStringBuilder2 insertStr(CNode currentNode, String str, int pos, int offset, CNode afterNode, int i, int counter) {
        // Recursive case– we have not finished going through the String
        if (pos < length && i < str.length()) {
            if (pos == offset) {
                afterNode = currentNode;
                // System.out.println(afterNode.data);
                currentNode = afterNode.prev;
                // System.out.println(currentNode.data);
                counter = 1;
            }
            if (counter == 1) {
                CNode newNode = new CNode(str.charAt(i));
                currentNode.next = newNode;
                newNode.prev = currentNode;
                currentNode = newNode;
                length++;
                i++;
            } else {
                currentNode = currentNode.next;
            }
            return insertStr(currentNode, str, pos + 1, offset, afterNode, i, counter);

        }
        currentNode.next = afterNode;
        afterNode.prev = currentNode;
        return this;

    }

    // Insert character c into the current MyStringBuilder2 at index
    // "offset" and return the current MyStringBuilder2.  If "offset" ==
    // length, this is the same as append.  If "offset" is invalid, 
    // do nothing.
    public MyStringBuilder2 insert(int offset, char c) {
        if (offset < 0 || offset > length) {
            return this;
        }
        if (offset == 0) {
            CNode afterNode = firstC;
            CNode backNode = firstC.prev;
            firstC = new CNode(c);
            CNode currentNode = firstC;

            firstC.next = afterNode;
            afterNode.prev = firstC;

            firstC.prev = backNode;
            backNode.next = firstC;
            length++;

            return this;

        } else if (offset == length) {
            CNode currentNode = firstC.prev;
            CNode newNode = new CNode(c);
            currentNode.next = newNode;
            newNode.prev = currentNode;
            currentNode = newNode;
            length++;

            currentNode.next = firstC;      // connecting front and ends
            // to make circular
            firstC.prev = currentNode;
            return this;

        } else {
            CNode currentNode = firstC;
            CNode afterNode = currentNode.next;

            return insertChar(currentNode, c, 0, offset, afterNode,0); // helper method to insert in the middle of linkedlist
        }

    }

    private MyStringBuilder2 insertChar(CNode currentNode, char c, int pos, int offset, CNode afterNode, int counter) {
         if (pos < length && counter ==0) {
            if (pos == offset) {
                afterNode = currentNode;
                // System.out.println(afterNode.data);
                currentNode = afterNode.prev;
                // System.out.println(currentNode.data);
                counter = 1;
            }
            if (counter == 1) {
                CNode newNode = new CNode(c);
                currentNode.next = newNode;
                newNode.prev = currentNode;
                currentNode = newNode;
                length++;
            } else {
                currentNode = currentNode.next;
            }
            return insertChar(currentNode,c, pos + 1, offset, afterNode, counter);

        }
        currentNode.next = afterNode;
        afterNode.prev = currentNode;
        return this;
    }

    // Return the length of the current MyStringBuilder2
    public int length() {
        return length;
    }

    // Delete the substring from "start" to "end" - 1 in the current
    // MyStringBuilder2, then insert String "str" into the current
    // MyStringBuilder2 starting at index "start", then return the current
    // MyStringBuilder2.  If "start" is invalid or "end" <= "start", do nothing.
    // If "end" is past the end of the MyStringBuilder2, only delete until the
    // end of the MyStringBuilder2, then insert.  This method should be done
    // as efficiently as possible.  In particular, you may NOT simply call
    // the delete() method followed by the insert() method, since that will
    // require an extra traversal of the linked list.
    public MyStringBuilder2 replace(int start, int end, String str) {
        CNode currentNode = firstC;
        CNode afterNode = currentNode.next;
        CNode temp = currentNode;
        CNode last = currentNode;
        if (start < 0 || start > end || end <= start) {
            return this;
        } else if (start == 0) {
            replaceStart(0, temp, currentNode, 0, start, end, str, 0); // helper method to replace at front
        } else if (end > length) {
            end = length - 1;
            return replaceEnd(0, temp, currentNode, 0, start, end, str, 0); // helper method to replace at end
        } else {
            return replaceMid(0, temp, afterNode, currentNode, 0, start, end, str, 0); // helper method to replace in middle
        }
        return this;
    }

    private MyStringBuilder2 replaceStart(int counter, CNode temp, CNode currentNode, int pos, int start, int end, String str, int i) {
        // Recursive case– we have not finished going through the String
        if (pos < length && i < str.length()) {
            if (pos == end) {
                temp = currentNode;
                length = length - (end - 1);
                counter = 1;
                end = -1;
                firstC = new CNode(str.charAt(i));
                i++;
                currentNode = firstC;
                pos = 0;
            }
            if (counter == 1) {

                CNode newNode = new CNode(str.charAt(i));
                currentNode.next = newNode;
                newNode.prev = currentNode;
                currentNode = newNode;
                length++;
                i++;
                pos++;
            } else {
                currentNode = currentNode.next;
            }
            return replaceStart(counter, temp, currentNode, pos + 1, start, end, str, i);

        }
        currentNode.next = temp;
        temp.prev = currentNode;
        return this;
    }

    private MyStringBuilder2 replaceEnd(int counter, CNode temp, CNode currentNode, int pos, int start, int end, String str, int i) {
        // Recursive case– we have not finished going through the String

        if (pos <= length && i < str.length()) {
            if (pos == start) {
                temp = currentNode;
            }
            if (pos == end) {
                currentNode = temp.prev;
                counter = 1;
                length = start;
                end = -1;
                pos = start;
            }
            if (counter == 1) {
                CNode newNode = new CNode(str.charAt(i));
                currentNode.next = newNode;
                newNode.prev = currentNode;
                currentNode = newNode;
                length++;
                i++;
            } else {
                currentNode = currentNode.next;
            }
            return replaceEnd(counter, temp, currentNode, pos + 1, start, end, str, i);

        }
        currentNode.next = firstC;
        firstC.prev = currentNode;
        return this;
    }

    private MyStringBuilder2 replaceMid(int counter, CNode temp, CNode afterNode, CNode currentNode, int pos, int start, int end, String str, int i) {
        // Recursive case– we have not finished going through the String
        if (pos <= length - 1 && i < str.length()) {
            if (pos == start) {
                temp = currentNode;
            }
            if (pos == end) {
                afterNode = currentNode;
                currentNode = temp.prev;

                counter = 1;
                length = length - (end - start);
                end = -1;
                pos = start;
            }
            if (counter == 1) {
                CNode newNode = new CNode(str.charAt(i));
                currentNode.next = newNode;
                newNode.prev = currentNode;
                currentNode = newNode;
                length++;
                i++;
            } else {
                currentNode = currentNode.next;
            }
            return replaceMid(counter, temp, afterNode, currentNode, pos + 1, start, end, str, i);

        }
        currentNode.next = afterNode;
        afterNode.prev = currentNode;
        return this;

    }

    // Return as a String the substring of characters from index "start" to
    // index "end" - 1 within the current MyStringBuilder2
    public String substring(int start, int end) {

        char[] array = new char[length];
        int counter = 0;
        CNode curr = firstC;
        return sub(curr, start, end, array, 0, 0); // helper method for the substring method

    }

    private String sub(CNode currentNode, int start, int end, char[] array, int pos, int i) {
        // Recursive case– we have not finished going through the String
        if (pos < length) {
            if (pos >= start && pos < end) {
                array[i] = currentNode.data;
                i++;
            }
            return new String(sub(currentNode.next, start, end, array, pos + 1, i));
        }
        return new String(array);
    }

    // Return the entire contents of the current MyStringBuilder2 as a String
    // in the reverse of the order that it is stored.
    public String revString() {
        char[] array = new char[length];
        char[] newArray = new char[length];
        int len = length - 1;
        CNode currentNode = firstC;

        addElements(array, 0, currentNode); // helper method to add elements
        String reversed = reverseElements(array, newArray, 0, len); // helper method to reverse elements that were added
        return reversed;
    }

    private void addElements(char[] array, int pos, CNode currentNode) {
        // Recursive case– we have not finished going through the String
        if (pos < length) {
            array[pos] = currentNode.data;
            addElements(array, pos + 1, currentNode.next);
        }
    }

    private String reverseElements(char[] array, char[] newArray, int pos, int len) {
        // Recursive case– we have not finished going through the String
        if (len >= 0) {
            newArray[pos] = array[len];
            reverseElements(array, newArray, pos + 1, len - 1);
        }
        return new String(newArray);
    }

    // Find and return the index within the current MyStringBuilder2 where
    // String str LAST matches a sequence of characters within the current
    // MyStringBuilder2.  If str does not match any sequence of characters
    // within the current MyStringBuilder2, return -1.  Think carefully about
    // what you need to do for this method before implementing it.  For some
    // help with this see the Assignment 3 specifications.
    public int lastIndexOf(String str) {
        CNode currentNode = firstC.prev;
        CNode copyNode = currentNode;
        CNode counterNode = copyNode;

        return last(currentNode, 0, str, length - 1, str.length() - 1, length - 1, counterNode); // helper method to find last index of matched sequence

    }

    private int last(CNode copyNode, int matches, String str, int pos, int i, int counter, CNode counterNode) {
        // Recursive case– we have not finished going through the String
        if (pos >= 0 && matches != str.length()) {
            if (copyNode.data == str.charAt(i)) {
                matches++;
                i--;
                pos--;
                copyNode = copyNode.prev;
            } else {
                matches = 0;
                counter = counter - 1;
                pos = counter;
                counterNode = counterNode.prev;
                copyNode = counterNode;
                i = str.length() - 1;
            }
            return last(copyNode, matches, str, pos, i, counter, counterNode);
        }
        if (matches == str.length()) {
            return counter - str.length() + 1;
        } else {
            return -1;
        }
    }

    // Find and return an array of MyStringBuilder2, where each MyStringBuilder2
    // in the return array corresponds to a part of the match of the array of
    // patterns in the argument.  If the overall match does not succeed, return
    // null.  For much more detail on the requirements of this method, see the
    // Assignment 3 specifications.
    public MyStringBuilder2[] regMatch(String[] pats) {

        MyStringBuilder2[] build = new MyStringBuilder2[pats.length];
        CNode currentNode = firstC;
        boolean isMatch = true;
        return findMatch(0, isMatch, currentNode, pats, build, 0, 0, 0, 0); // helper method to retrieve the array containing the sequence of matches 
    }

    private void reset(int i, MyStringBuilder2[] build) {
        // recursive case
        // resets i back to 0
        if (i >= 0) {
            build[i] = null;
            reset(--i, build);
        }
    }

    private MyStringBuilder2[] findMatch(int size, boolean isMatch, CNode currentNode, String[] pats, MyStringBuilder2[] build, int pos, int i, int y, int match) {
        // Recursive case– we have not finished going through the String

        if (pos <= length && isMatch) {
            if (currentNode.data == pats[i].charAt(y)) {
                if (build[i] == null) {
                    MyStringBuilder2 newData = new MyStringBuilder2();
                    build[i] = newData;
                }
                match++;
                build[i].append(currentNode.data);
                currentNode = currentNode.next;
                size++;
            } else {
                if (match == 0 && y == pats[i].length() - 1) {

                    reset(i, build);                  // reset the whole array back to 0
                    i = 0;
                    y = 0;
                    currentNode = currentNode.next;
                    firstC = currentNode;
                    pos++;
                    size = 0;                 // keeping track of the logical size of the length of build[i]                    
                } else if (match > 0 && y == pats[i].length() - 1 && i < pats.length - 1) {
                    match = 0;
                    i++;
                    y = 0;

                } else if (match > 0 && y == pats[i].length() - 1 && i == pats.length - 1) {
                    //matches[0] = this;
                    isMatch = false;
                    length = size;
                    return build;
                } else if (match == 0 && y == pats[i].length() - 1 && i == pats.length - 1) {
                    isMatch = false;
                    return null;
                }
                return findMatch(size, isMatch, currentNode, pats, build, pos, i, y + 1, match); // recursive step 
            }
            return findMatch(size, isMatch, currentNode, pats, build, pos + 1, i, y = 0, match); // recursive step
        }
        return build;
    }

    // You must use this inner class exactly as specified below.  Note that
    // since it is an inner class, the MyStringBuilder2 class MAY access the
    // data and next fields directly.
    private class CNode {

        private char data;
        private CNode next, prev;

        public CNode(char c) {
            data = c;
            next = null;
            prev = null;
        }

        public CNode(char c, CNode n, CNode p) {
            data = c;
            next = n;
            prev = p;
        }
    }
}
