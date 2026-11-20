package org.tutorg.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * TutorSearchTree is the class that contains tree information pertaining to students
 * @author Sharaf Zaman
 */

class TreeNode implements Comparable<TreeNode> {
    private static String TAG = "TreeNode";
    private Subject subject;
    private ArrayList<Tutor> tutors;

    TreeNode(Subject subject) {
        this.subject = subject;
        this.tutors = new ArrayList<>();
    }

    @Override
    public int compareTo(TreeNode node) {
        return subject.compareTo(node.subject);
    }

    public void addTutor(Tutor tutor) {
        if (tutor == null) {
            Log.wtf(TAG, "Tutor is null!");
            return;
        }
        this.tutors.add(tutor);
    }

    public ArrayList<Tutor> getTutors() {
        return tutors;
    }
}

/**
 * @author Sharaf Zaman
 *
 * TutorSearchTree builds the tree.
 */
public class TutorSearchTree extends Observable implements Observer {
    private static String TAG = "TutorSearchTree";

    private static TutorSearchTree sInstance = null;

    private AVLTree<TreeNode> tutorTree;
    private FirebaseFetcher fetcherInstance = null;

    public static TutorSearchTree getInstance()
    {
        if (sInstance == null) {
            sInstance = new TutorSearchTree();
        }
        return sInstance;
    }

    TutorSearchTree() {
        fetcherInstance = FirebaseFetcher.getInstance();
        fetcherInstance.addObserver(this);
    }

    /**
     * This observer is called from FirebaseFetcher's notify() method.
     */
    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof Tutor) {
            Tutor tutor = (Tutor) o;
            // here we just insert the node
            insertTutor(tutor);
        }
        setChanged();
        notifyObservers();
    }

    private void insertTutor(Tutor tutor) {
        // for each subject, we add tutor to the tree node. This results in duplicates
        // but we can have a few tutors for several courses and it is what is.
        for (Subject subject : tutor.getSubjects()) {
            TreeNode treeNode = new TreeNode(subject);
            if (tutorTree == null) {
                tutorTree = new AVLTree<>(treeNode);
            } else {
                Tree<TreeNode> node = tutorTree.find(treeNode);
                if (node instanceof EmptyTree || node == null) {
                    treeNode.addTutor(tutor);
                    tutorTree = tutorTree.insert(treeNode);
                } else {
                    node.value.addTutor(tutor);
                }
            }
        }
    }

    public void insertTutor(ArrayList<Subject> subjects, Tutor tutor) {
        // for each subject, we add tutor to the tree node. This results in duplicates
        // but we can have a few tutors for several courses and it is what is.
        for (Subject subject : subjects) {
            TreeNode treeNode = new TreeNode(subject);
            if (tutorTree == null) {
                tutorTree = new AVLTree<>(treeNode);
            } else {
                Tree<TreeNode> node = tutorTree.find(treeNode);
                if (node instanceof EmptyTree || node == null) {
                    treeNode.addTutor(tutor);
                    tutorTree = tutorTree.insert(treeNode);
                } else {
                    node.value.addTutor(tutor);
                }
            }
        }
    }

    private void buildTree(ArrayList<Tutor> tutors) {
        Log.d(TAG, "Build tree called");
        for (Tutor tutor : tutors) {
            insertTutor(tutor);
        }
        setChanged();
        notifyObservers();
    }

    /**
     * @return the list of tutors matching the course.
     */
    public ArrayList<Tutor> getMatchingTutors(Subject subject) {
        if (tutorTree == null) {
            return new ArrayList<>();
        }
        TreeNode treeNode = new TreeNode(subject);
        Tree<TreeNode> returnedItem = tutorTree.find(treeNode);
        if (returnedItem instanceof EmptyTree || returnedItem == null) {
            return new ArrayList<>();
        }
        return returnedItem.value.getTutors();
    }
}
