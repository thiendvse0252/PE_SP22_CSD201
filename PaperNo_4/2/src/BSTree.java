/* This program contains 2 parts: (1) and (2)
   YOUR TASK IS TO COMPLETE THE PART  (2)  ONLY
 */
//(1)==============================================================
import java.io.*;
import java.util.*;

public class BSTree {

    Node root;

    BSTree() {
        root = null;
    }

    boolean isEmpty() {
        return (root == null);
    }

    void clear() {
        root = null;
    }

    void visit(Node p) {
        System.out.print("p.info: ");
        if (p != null) {
            System.out.println(p.info + " ");
        }
    }

    void fvisit(Node p, RandomAccessFile f) throws Exception {
        if (p != null) {
            f.writeBytes(p.info + " ");
        }
    }

    void breadth(Node p, RandomAccessFile f) throws Exception {
        if (p == null) {
            return;
        }
        Queue q = new Queue();
        q.enqueue(p);
        Node r;
        while (!q.isEmpty()) {
            r = q.dequeue();
            fvisit(r, f);
            if (r.left != null) {
                q.enqueue(r.left);
            }
            if (r.right != null) {
                q.enqueue(r.right);
            }
        }
    }

    void preOrder(Node p, RandomAccessFile f) throws Exception {
        if (p == null) {
            return;
        }
        fvisit(p, f);
        preOrder(p.left, f);
        preOrder(p.right, f);
    }

    void inOrder(Node p, RandomAccessFile f) throws Exception {
        if (p == null) {
            return;
        }
        inOrder(p.left, f);
        fvisit(p, f);
        inOrder(p.right, f);
    }

    void postOrder(Node p, RandomAccessFile f) throws Exception {
        if (p == null) {
            return;
        }
        postOrder(p.left, f);
        postOrder(p.right, f);
        fvisit(p, f);
    }

    void loadData(int k) { //do not edit this function
        String[] a = Lib.readLineToStrArray("data.txt", k);
        int[] b = Lib.readLineToIntArray("data.txt", k + 1);
        int[] c = Lib.readLineToIntArray("data.txt", k + 2);
        int n = a.length;
        for (int i = 0; i < n; i++) {
            insert(a[i], b[i], c[i]);
        }
    }

//===========================================================================
//(2)===YOU CAN EDIT OR EVEN ADD NEW FUNCTIONS IN THE FOLLOWING PART========
//===========================================================================
    void insert(String xForest, int xRate, int xSound) {
        //You should insert here statements to complete this function
        if (xForest.charAt(0) == 'B') {
            return;
        }
        if (this.isEmpty()) {
            root = new Node(new Bird(xForest, xRate, xSound));
            return;
        }
        Node cur = root;
        while (true) {
            if (xRate == cur.info.rate) {
                return;
            } else if (xRate < cur.info.rate) {
                if (cur.left == null) {
                    cur.left = new Node(new Bird(xForest, xRate, xSound));
                    return;
                }
                cur = cur.left;
            } else {
                if (cur.right == null) {
                    cur.right = new Node(new Bird(xForest, xRate, xSound));
                    return;
                }
                cur = cur.right;
            }
        }
    }

//Do not edit this function. Your task is to complete insert function above only.
    void f1() throws Exception {
        clear();
        loadData(1);
        String fname = "f1.txt";
        File g123 = new File(fname);
        if (g123.exists()) {
            g123.delete();
        }
        RandomAccessFile f = new RandomAccessFile(fname, "rw");
        breadth(root, f);
        f.writeBytes("\r\n");
        inOrder(root, f);
        f.writeBytes("\r\n");
        f.close();
    }

//=============================================================
    void preOrder2(Node p, RandomAccessFile f) throws Exception {
        if (p == null) {
            return;
        }
        if (p.info.sound < 6) {
            fvisit(p, f);
        }
        preOrder2(p.left, f);
        preOrder2(p.right, f);
    }

    void f2() throws Exception {
        clear();
        loadData(5);
        String fname = "f2.txt";
        File g123 = new File(fname);
        if (g123.exists()) {
            g123.delete();
        }
        RandomAccessFile f = new RandomAccessFile(fname, "rw");
        preOrder(root, f);
        f.writeBytes("\r\n");
        //------------------------------------------------------------------------------------
        /*You must keep statements pre-given in this function.
      Your task is to insert statements here, just after this comment,
      to complete the question in the exam paper.*/

        //------------------------------------------------------------------------------------
        preOrder2(root, f);
        f.writeBytes("\r\n");
        f.close();
    }

//=============================================================
    int count = 0;
    Node fourth;

    void preOrderSetFourth(Node p) throws Exception {
        if (p == null) {
            return;
        }
        if (count > 4) {
            return;
        }
        if (++count == 4) {
            fourth = p;
        }
        //-------
        preOrderSetFourth(p.left);
        preOrderSetFourth(p.right);
    }

    Node deleteByCopying(Node subRoot, int rate) {
        if (subRoot == null) {
            return null;
        }
        int cmp = rate - subRoot.info.rate;
        if (cmp < 0) {
            subRoot.left = deleteByCopying(subRoot.left, rate);
            return subRoot;
        } else if (cmp > 0) {
            subRoot.right = deleteByCopying(subRoot.right, rate);
            return subRoot;
        } else /*cmp == 0 -> FOUND*/ {
            if (subRoot.left == null && subRoot.right == null) {
                return null;
            } else if (subRoot.left != null && subRoot.right == null) {
                return subRoot.left;
            } else if (subRoot.left == null && subRoot.right != null) {
                return subRoot.right;
            } else /*2-ary Node*/ {
                /*get rightmost Node of left subtree*/
                Node rightMost = subRoot.left;
                while (rightMost.right != null) {
                    rightMost = rightMost.right;
                }
                subRoot.info = rightMost.info;
                subRoot.left = deleteByCopying(subRoot.left, rightMost.info.rate);
                return subRoot;
                //------------------------------------
            }
        }
    }

    void f3() throws Exception {
        clear();
        loadData(9);
        String fname = "f3.txt";
        File g123 = new File(fname);
        if (g123.exists()) {
            g123.delete();
        }
        RandomAccessFile f = new RandomAccessFile(fname, "rw");
        preOrder(root, f);
        f.writeBytes("\r\n");
        //------------------------------------------------------------------------------------
        /*You must keep statements pre-given in this function.
      Your task is to insert statements here, just after this comment,
      to complete the question in the exam paper.*/

        //------------------------------------------------------------------------------------
        count = 0;
        preOrderSetFourth(root);
        deleteByCopying(root, fourth.info.rate);
        preOrder(root, f);
        f.writeBytes("\r\n");
        f.close();
    }

//=============================================================
    void rotateRight(Node subRoot) { //subRoot.left !=null
        Node movedRoot = new Node(subRoot.info);
        movedRoot.left = subRoot.left.right;
        movedRoot.right = subRoot.right;
        //
        subRoot.info = subRoot.left.info;
        subRoot.left = subRoot.left.left;
        subRoot.right = movedRoot;
    }

    void f4() throws Exception {
        clear();
        loadData(13);;
        String fname = "f4.txt";
        File g123 = new File(fname);
        if (g123.exists()) {
            g123.delete();
        }
        RandomAccessFile f = new RandomAccessFile(fname, "rw");
        preOrder(root, f);
        f.writeBytes("\r\n");
        //------------------------------------------------------------------------------------
        /*You must keep statements pre-given in this function.
      Your task is to insert statements here, just after this comment,
      to complete the question in the exam paper.*/

        //------------------------------------------------------------------------------------
        count = 0;
        preOrderSetFourth(root); //set this.fourth = 4-th Node encountered
        if (fourth.left != null) {
            rotateRight(fourth);
        }
        preOrder(root, f);
        f.writeBytes("\r\n");
        f.close();
    }

}
