import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


public class RedBlackTree {
	
	TreeNode root;
	
	public class TreeNode{
		int id;
		int count;
		TreeNode leftChild, rightChild, parent;
		boolean isRed;
		
		TreeNode(int id, int c)
		{
			this.isRed = true;
			this.id = id;
			this.count = c; 
		}
		
		/*Check if node is a left child of it's parent*/
		boolean isLeftChild()
		{
			
			if(this == this.parent.leftChild)
				return true;
			else
				return false;
		}
		
		
	}
	/*Returns sibling node of parent if it exists, otherwise, returns null*/
	TreeNode getUncle(TreeNode node) {
		// TODO Auto-generated method stub
		 if(node!=null && node.parent!=null && node.parent.parent!=null)
		 {
			 if(node.parent.isLeftChild())
			 {
				 return node.parent.parent.rightChild;
			 }
			 else{
				 return node.parent.parent.leftChild;
			 }
		 }
		return null;
	}
	/*Returns grandparent node if it exists, otherwise, returns null*/
	TreeNode getGrandparent(TreeNode node) {
		if (node != null && node.parent != null && node.parent.parent != null) {
			return node.parent.parent;
		} else {
			return null;
		}
	}
	
	/*Inserts new ID and count pair into tree*/
	public void insertNode(int ID, int count)
	{
		TreeNode node = new TreeNode(ID, count);
		if(root == null) //Inserting first node as root.
		{
			node.isRed = false;
			root = node;
		}
		else
		{
			insertIntoBST(node); //Standard Binary Search Tree insertion
			
		}
	}
	/*Standard Binary Search Tree Insert method*/
	void insertIntoBST(TreeNode node)
	{
		TreeNode current = root;
		TreeNode parent = root;
		while(current!=null)
		{
			parent = current;
			if(node.id > current.id)
				current = current.rightChild;
		
			else if(node.id < current.id)
				current = current.leftChild;
			
		}
		if(parent.id > node.id){
			parent.leftChild = node;
		}
		else
		{
			parent.rightChild = node;
		}
			node.parent = parent;
		
		 insertCase1(node);
		
	}
	/* Case 1 of red-black tree insert: node inserted is root
	 * make it black
	 * */
	 void insertCase1(TreeNode node) {

		if (node!=null){
			if(node.parent == null){
				node.isRed = false;
			}
			else{
				insertCase2(node);
			}
		}
		
	}
	 /*
	  * Case 2 of red-black tree insertion: parent is black. 
	  * If parent is black, new node inserted can stay red without violating 
	  * "no two red nodes in a sequence" property
	  * */
	 void insertCase2(TreeNode node)
	 {
		 if(!node.parent.isRed){
			 node.isRed=true;
			 return;
		}
		 else{ 
			 insertCase3(node);
		 }
	 }
	 
	 /*
	  * Case 3 of red-black tree insertion: Parent is red and uncle is also red
	  * Color flip parent, uncle and grandparent. Recurse on grandparent. This 
	  * pushes the problem up the tree
	  */
	 void insertCase3(TreeNode node)
	 {
		 TreeNode uncle = getUncle(node);
		 if(uncle!=null && uncle.isRed)
		 {
			 node.parent.isRed = false;
			 uncle.isRed = false;
			 TreeNode grandparent = getGrandparent(node);
			 grandparent.isRed = true;
			
			 insertCase1(grandparent); //recursion on grandparent
		 }
		 else{
			 insertCase4(node);
		 }
			 
	 }
	 
	 
	/*
	  * Case 4 of red-black tree insertion : Parent is red, uncle is black
	  */
	 void insertCase4(TreeNode node)
	 {

		 TreeNode grandparent = getGrandparent(node);
		 TreeNode parent = node.parent;
		 if(parent.isLeftChild() && !node.isLeftChild()) 
		 {
			 	leftRotate(parent);	
		 }
		 else if(!parent.isLeftChild() && node.isLeftChild())
		 {
			 //right-rotate
			 rightRotate(parent);
			 
		 }
		 
			 insertCase5(node);
		 
	}
	 /*Insertion Case 5: node and parent are both right children of their parents
	  * or node and parent are both left children of their parents*/
	 
	 void insertCase5(TreeNode node)
	 {
		TreeNode grandparent = getGrandparent(node);
		TreeNode parent = node.parent;
		parent.isRed = false;
		grandparent.isRed = true;
		if(!node.isLeftChild())
		{
			leftRotate(grandparent);
		}
		else{
			rightRotate(grandparent);
		}
	 }
	 
	 /*Fixes an unbalanced tree when it is leaning towards right*/
	 void leftRotate(TreeNode node){
		 if(node!=null && node.rightChild!=null)
		 {
			 TreeNode rightChild = node.rightChild, newGrandparent = node.parent;
			 node.rightChild = rightChild.leftChild;

			 if(node.rightChild!=null){
				 node.rightChild.parent = node;
			 }
			 node.parent = rightChild;
				rightChild.leftChild = node;
				rightChild.parent = newGrandparent;
				
				if (newGrandparent != null) {
					if (node == newGrandparent.leftChild) {
						newGrandparent.leftChild = rightChild;
					} else {
						newGrandparent.rightChild = rightChild;
					}
				} else {
					root = rightChild;
					root.isRed = false;
				}
		 }
	 }
	 
	 /*Fixes an unbalanced BST when it is leaning towards left*/
	 void rightRotate(TreeNode node) {
			if (node != null && node.leftChild != null) {
				TreeNode leftChild = node.leftChild, newGrandparent = node.parent;
				node.leftChild = leftChild.rightChild;
				if (node.leftChild != null) {
					node.leftChild.parent = node;
				}
				node.parent = leftChild;
				leftChild.rightChild = node;
				leftChild.parent = newGrandparent;
				if (newGrandparent != null) {
					if (node == newGrandparent.leftChild) {
						newGrandparent.leftChild = leftChild;
					} else {
						newGrandparent.rightChild = leftChild;
					}
				} else {
					root = leftChild;
				}
			}
	 }
	  /* Increase count of the event "theID" by m. If
	  * theID is not present, insert it. Print the count
	  * of theID after the addition.
	  * */
	 void increase(int theID, int m)
	 {
		 TreeNode node = searchNode(theID);
		 if(node==null) //insert new node since node with theID wasn't found
		 {
			 insertNode(theID, m);
			 node = searchNode(theID);
		 }
		 else{
			 node.count += m;
		 }
		 System.out.println(node.count);
	 }
	 


	/*
	 * Returns the left-most child in node's right subtree.
	 */
	TreeNode getSuccessor(TreeNode node) {
		TreeNode successor = null;
		if (node != null) {
			successor = node.rightChild;
			while (successor != null && successor.leftChild != null) {
				successor = successor.leftChild;
			}
		}
		return successor;
	}

	/*
	 * Returns the right-most child in node's left subtree.
	 */
	TreeNode getPredecessor(TreeNode node) {
		TreeNode predecessor = null;
		if (node != null) {
			predecessor = node.leftChild;
			while (predecessor != null && predecessor.rightChild != null) {
				predecessor = predecessor.rightChild;
			}
		}
		return predecessor;
	}
	
	void replace(TreeNode destination, TreeNode source){
		destination.id = source.id;
		destination.count = source.count;
	}
	
	 /*Delete node when ID is given*/
	void deleteNode(int theID)
	{
		TreeNode node = searchNode(theID);
		if(node != null)
		{
			deleteNode(node);
		}
	}
	
	/*Deletes given node and fixes red-black tree violations*/
	 void deleteNode(TreeNode node) {

		 if (node != null) {
				int deletedID = node.id;
				
				if (node.leftChild != null && node.rightChild != null) 
				{
					/*Case 1: Node has 2 children, replace node with predecessor
					 * and delete predecessor recursively 
					 * */
					TreeNode predecessor = getPredecessor(node);
					replace(node, predecessor);
					deleteNode(predecessor);
					
				} 
				else 
				{
					/*Case 2: Node has either one or zero children. call
					 ddeleteRecolor if the node being deleted is a black
					 node.
					 (if it's red, then no RBT properties are violated)*/
					boolean treeFixed = true;
					TreeNode child = null;
					if (!node.isRed) {
						treeFixed = deleteRecolor(node);
					}
					if (node.rightChild != null) {
//						Case 2.1: Node has right child
						child = node.rightChild;
						
						if (node.parent == null) {
							root = child;
							treeFixed = true;
						} 
						else if (node.isLeftChild()) {
							node.parent.leftChild = child;
							child.parent = node.parent;
							
						} 
						else {
							node.parent.rightChild = child;
							child.parent = node.parent;
						}
					} else {
//						Case 2.2: Node has left child

						child = node.leftChild;
						if (node.parent == null) {
							root = child;
							treeFixed = true;
						} else if (node.parent.rightChild == node) {
							if (child == null) {
								//Add dummy null leaf for rebalancing, then delete it.
								child = dummyLeaf(node.parent, true);
							}
							node.parent.rightChild = child;
						} else {
							if (child == null) {
								//Add dummy null leaf for rebalancing, then delete it.

								child = dummyLeaf(node.parent, false);
							}
							node.parent.leftChild = child;
						}
					}
					child.parent = node.parent;
					if (!treeFixed) {
						// If child replacing deleted node was previously black, and
						// not the current root
						deleteCase2(child);
						if (child.id == -1) {
							deleteDummyNode(child);
						}
					}
					if (child.id == -1) {
						deleteDummyNode(child);
					}
				}
//				
			}
		}

//			Case 1: Node is parent, which means the invalidation traveled up the tree, do nothing
		void deleteCase1(TreeNode node) {
			if (node.parent != null) {
				deleteCase2(node);
			} else {
				root = node;
			}
		}

	
		void deleteCase2(TreeNode node) {

			TreeNode nodeS = getSibling(node);
			if (nodeS.isRed) {
				node.parent.isRed = true;
				nodeS.isRed = false;
				if (node == node.parent.leftChild) {
					leftRotate(node.parent);
				} else {
					rightRotate(node.parent);
				}
			}
			deleteCase3(node);
		}

	
		void deleteCase3(TreeNode node) {

			TreeNode siblingNode = getSibling(node);
			if (!node.parent.isRed&& !siblingNode.isRed
					&& (siblingNode.leftChild == null || !siblingNode.leftChild.isRed)
					&& (siblingNode.rightChild == null || !siblingNode.rightChild.isRed)) {
				// the children being null implies they're black (assume leaf null
				// nodes are BLACK)
				siblingNode.isRed = true;
				deleteCase1(node.parent);
			} else {
				delete4(node);
			}
		}


		void delete4(TreeNode node) {

			TreeNode siblingNode = getSibling(node);
			if (node.parent.isRed && !siblingNode.isRed
					&& (siblingNode.leftChild == null || !siblingNode.leftChild.isRed)
					&& (siblingNode.rightChild == null || !siblingNode.rightChild.isRed)) {
				// the children being null implies they're black (assume leaf null
				// nodes are BLACK)
				siblingNode.isRed = true;
				node.parent.isRed = false;
			} else {
				delete5(node);
			}
		}

	
		void delete5(TreeNode node) {

			TreeNode siblingNode = getSibling(node);
			if (!siblingNode.isRed) {
				if (node == node.parent.leftChild && (siblingNode.rightChild == null || !siblingNode.rightChild.isRed)
						&& (siblingNode.leftChild != null && siblingNode.leftChild.isRed)) {
					
					siblingNode.isRed = true;
					siblingNode.leftChild.isRed = false;
					rightRotate(siblingNode);
				} else if (node == node.parent.rightChild && (siblingNode.leftChild == null || !siblingNode.leftChild.isRed)
						&& (siblingNode.rightChild != null && siblingNode.rightChild.isRed)) {
					siblingNode.isRed = true;
					siblingNode.rightChild.isRed = false;
					leftRotate(siblingNode);
				}
			}
			deleteCase6(node);
		}


		void deleteCase6(TreeNode node) {

			TreeNode nodeS = getSibling(node);

			nodeS.isRed = node.parent.isRed;
			node.parent.isRed = false;

			if (node == node.parent.leftChild) {
				nodeS.rightChild.isRed = false;
				leftRotate(node.parent);
			} else {
				nodeS.leftChild.isRed = false;
				rightRotate(node.parent);
			}
		}

		/*
		 * Node to be deleted is black with only one red child. Repaint child to restore
		 * "same number of black nodes from root to external node" property
		 */
		boolean deleteRecolor(TreeNode node) {

			if (!node.isRed && node.rightChild != null && node.rightChild.isRed) {
				node.rightChild.isRed = false;
				return true;
			} 
			else if (!node.isRed && node.leftChild != null && node.leftChild.isRed) {
				node.leftChild.isRed = false;
				return true;
			}
			return false;
		}

		/*
		 * "Deletes" dummy node by removing all references to it and setting the parent
		 * reference to null;
		 */
		void deleteDummyNode(TreeNode node) {

			if (node != null) {
				if (node == root) {
					root = null;
				} else {
					if (node.parent.leftChild == node) {
						node.parent.leftChild = null;
					} else if (node.parent.rightChild == node){
						node.parent.rightChild = null;
					}
				}
			}
		}
		
		/*
		 * Dummy null leaf for rotation */
		TreeNode dummyLeaf(TreeNode parent, boolean onRight) {
			TreeNode nullLeaf = new TreeNode(-1, -1);
			//nullLeaf.subtreeCount = -1;
			nullLeaf.isRed = false;
			nullLeaf.parent = parent;
			if (onRight) {
				parent.rightChild = nullLeaf;
			} else {
				parent.leftChild = nullLeaf;
			}
			return nullLeaf;
		}
		
		
		/*Returns sibling of tree node*/
		TreeNode getSibling(TreeNode node) {
			if (node!=null && node.parent!=null) {
				if (node==node.parent.rightChild) {
					return node.parent.leftChild;
				} else {
					return node.parent.rightChild;
				}
			} else {
				return null;
			}
		}
	 
	/*Search for node with given ID, 
	  * if node is not present in tree, return null
	  */
	  TreeNode searchNode(int theID) 
	  {
		TreeNode current = root;
		if(current!=null)
		{
		while(current!=null && current.id != theID)
		{
			if(current.id < theID)
			{
				current = current.rightChild;
			}
			else{
				current = current.leftChild;
			}
		}	
		}
		  
		  return current;
	}
	  
	  
	  
	
	/*Reduce count of node with id theID by m*/
	
	void reduce(int theID, int m){
		TreeNode node = searchNode(theID);
		if(node == null){
			System.out.println("0");
		}
		else{
			if((node.count - m) <= 0 )
			{
				deleteNode(node);
				System.out.println("0");
			}
			else
			{
				node.count-=m;
				System.out.println(node.count);
			}
		}
	}
	/*Print the count of theID. If not present, print 0*/
	void count(int theID){
		TreeNode node = searchNode(theID);
		if(node!=null)
		{
			System.out.println(node.count);
		}
		else{
			System.out.println("0");
		}
		
	}
	
	
	void next(int theID){
		TreeNode potentialNext = null;
		TreeNode currentNode = root;
		while(currentNode!=null)
		{
			if(currentNode.id > theID ){
				potentialNext = currentNode;
				currentNode = currentNode.leftChild;
			}
			else if(currentNode.id <= theID){

				currentNode = currentNode.rightChild;

			}
			
			
		}
			if(potentialNext!=null){
				System.out.println(potentialNext.id + " " + potentialNext.count);
			}
			else{
				System.out.println("0 0");
			}
		
	}
	
	void previous(int theID){
		TreeNode potentialPrevious = null;
		TreeNode currentNode = root;
		while(currentNode!=null)
		{
			if(currentNode.id < theID ){
				potentialPrevious = currentNode;
				currentNode = currentNode.rightChild;
			}
			else if(currentNode.id >= theID){

				currentNode = currentNode.leftChild;

			}
			
			
		}
			if(potentialPrevious!=null){
				System.out.println(potentialPrevious.id + " " + potentialPrevious.count);
			}
			else{
				System.out.println("0 0");
			}
		
	}
	
	//	Print the total count for IDs between ID1 and
	//	ID2 inclusively. Note, ID1 <= ID2
		
	void inRange(int id1, int id2){
		System.out.println(rangeAddition(root, id1, id2));
	}
	//Recursive function to calculate total count between id1 and id2;
	//Helper function for inRange(id1,id2)
	int rangeAddition(TreeNode node, int id1, int id2){
		if(node==null){
			return 0;
		}
		if(node.id>=id1 && node.id <=id2) // node is in range
		{
			return node.count + rangeAddition(node.leftChild, id1, id2) + rangeAddition(node.rightChild, id1, id2); 
		}
		else if(node.id <= id1) //node is less than range
		{
			//recurse on right subtree
			return rangeAddition(node.rightChild, id1, id2);

		}
		else if(node.id>=id2)
		{
			//recurse on left subtree
			return rangeAddition(node.leftChild, id1, id2);

		}
		return 0;
		
	}
	
	}
