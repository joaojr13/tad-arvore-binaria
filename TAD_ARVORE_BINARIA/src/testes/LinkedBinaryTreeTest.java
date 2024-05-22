package testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.BoundaryViolationException;
import exceptions.InvalidPositionException;
import exceptions.NonEmptyTreeException;
import position.Position;
import source.LinkedBinaryTree;

class LinkedBinaryTreeTest {

	LinkedBinaryTree<String> tree;

	@BeforeEach
	void setUp() {
		tree = new LinkedBinaryTree<>();
	}

	@Test
	void testAddRoot() {
		assertTrue(tree.isEmpty());
		Position<String> root = tree.addRoot("Root");
		assertEquals("Root", root.element());
		assertFalse(tree.isEmpty());
		assertEquals(1, tree.size());
		assertThrows(NonEmptyTreeException.class, () -> tree.addRoot("New Root"));
	}

	@Test
	void testInsertLeft() {
		Position<String> root = tree.addRoot("Root");
		Position<String> leftChild = tree.insertLeft(root, "Left Child");
		assertEquals("Left Child", leftChild.element());
		assertTrue(tree.hasLeft(root));
		assertFalse(tree.hasLeft(leftChild));
		assertThrows(InvalidPositionException.class, () -> tree.insertLeft(root, "Another Left Child"));
	}

	@Test
	void testInsertRight() {
		Position<String> root = tree.addRoot("Root");
		Position<String> rightChild = tree.insertRight(root, "Right Child");
		assertEquals("Right Child", rightChild.element());
		assertTrue(tree.hasRight(root));
		assertFalse(tree.hasRight(rightChild));
		assertThrows(InvalidPositionException.class, () -> tree.insertRight(root, "Another Right Child"));
	}

	@Test
	void testRemove() {
		Position<String> root = tree.addRoot("Root");
		Position<String> leftChild = tree.insertLeft(root, "Left Child");
		Position<String> rightChild = tree.insertRight(root, "Right Child");
		assertEquals("Left Child", tree.remove(leftChild));
		assertFalse(tree.hasLeft(root));
		assertEquals("Right Child", tree.remove(rightChild));
		assertFalse(tree.hasRight(root));
		assertEquals("Root", tree.remove(root));
		assertTrue(tree.isEmpty());
	}

	@Test
	void testParent() {
		Position<String> root = tree.addRoot("Root");
		Position<String> leftChild = tree.insertLeft(root, "Left Child");
		Position<String> rightChild = tree.insertRight(root, "Right Child");
		try {
			assertEquals(root, tree.parent(leftChild));
			assertEquals(root, tree.parent(rightChild));
			assertThrows(BoundaryViolationException.class, () -> tree.parent(root));
		} catch (InvalidPositionException | BoundaryViolationException e) {
			fail("Unexpected exception: " + e.getMessage());
		}
	}

	@Test
	void testChildren() {
		Position<String> root = tree.addRoot("Root");
		Position<String> leftChild = tree.insertLeft(root, "Left Child");
		Position<String> rightChild = tree.insertRight(root, "Right Child");
		Iterable<Position<String>> children = tree.children(root);
		Iterator<Position<String>> iterator = children.iterator();
		assertEquals(leftChild, iterator.next());
		assertEquals(rightChild, iterator.next());
		assertFalse(iterator.hasNext());
	}

	@Test
	void testIsInternal() {
		Position<String> root = tree.addRoot("Root");
		Position<String> leftChild = tree.insertLeft(root, "Left Child");
		Position<String> rightChild = tree.insertRight(root, "Right Child");
		assertTrue(tree.isInternal(root));
		assertFalse(tree.isInternal(leftChild));
		assertFalse(tree.isInternal(rightChild));
	}

	@Test
	void testIsExternal() {
		Position<String> root = tree.addRoot("Root");
		Position<String> leftChild = tree.insertLeft(root, "Left Child");
		Position<String> rightChild = tree.insertRight(root, "Right Child");
		assertFalse(tree.isExternal(root));
		assertTrue(tree.isExternal(leftChild));
		assertTrue(tree.isExternal(rightChild));
	}

	@Test
	void testIsRoot() {
		Position<String> root = tree.addRoot("Root");
		Position<String> leftChild = tree.insertLeft(root, "Left Child");
		assertTrue(tree.isRoot(root));
		assertFalse(tree.isRoot(leftChild));
	}

	@Test
	void testSiblings() {
		Position<String> root = tree.addRoot("Root");
		Position<String> leftChild = tree.insertLeft(root, "Left Child");
		Position<String> rightChild = tree.insertRight(root, "Right Child");
		try {
			assertEquals(rightChild, tree.sibling(leftChild));
			assertEquals(leftChild, tree.sibling(rightChild));
			assertThrows(BoundaryViolationException.class, () -> tree.sibling(root));
		} catch (InvalidPositionException | BoundaryViolationException e) {
			fail("Unexpected exception: " + e.getMessage());
		}
	}

	@Test
	void testAttach() {
		LinkedBinaryTree<String> tree1 = new LinkedBinaryTree<>();
		LinkedBinaryTree<String> tree2 = new LinkedBinaryTree<>();

		tree1.addRoot("Root1");
		tree2.addRoot("Root2");

		Position<String> root = tree.addRoot("Root");
		Position<String> leftChild = tree.insertLeft(root, "Left");

		tree.attach(leftChild, tree1, tree2);

		assertEquals("Root1", tree.left(leftChild).element());
		assertEquals("Root2", tree.right(leftChild).element());
	}

	@Test
	void testInOrderPositions() {
        LinkedBinaryTree<Integer> tree = new LinkedBinaryTree<>();
        Position<Integer> root = tree.addRoot(5);
        Position<Integer> left = tree.insertLeft(root, 3);
        Position<Integer> right = tree.insertRight(root, 8);
        tree.insertLeft(left, 2);
        tree.insertRight(left, 4);
        tree.insertLeft(right, 7);
        tree.insertRight(right, 10);

        List<Integer> expected = List.of(2, 3, 4, 5, 7, 8, 10);

        List<Integer> actual = new ArrayList<>();
        for (Position<Integer> position : tree.positionsInorder()) {
            actual.add(position.element());
        }

        assertEquals(expected, actual);
	}

	@Test
	void test_buildExpression() {
		LinkedBinaryTree<String> linkedBinaryTree = new LinkedBinaryTree<String>();

		String[] expression = { "(", "(", "(", "(", "3", "+", "1", ")", "*", "3", ")", "/", "(", "(", "9", "-", "5",
				")", "+", "2", ")", ")", "-", "(", "(", "3", "*", "(", "7", "-", "4", ")", ")", "+", "6", ")", ")" };

		LinkedBinaryTree<String> treeExpression = linkedBinaryTree.buildExpression(expression);

		OutputStream outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
		System.setOut(printStream);

		treeExpression.printExpression(treeExpression, treeExpression.root());
		String consoleOutput = outputStream.toString();

		assertEquals("((((3+1)*3)/((9-5)+2))-((3*(7-4))+6))", consoleOutput);
	}

	@Test
	void test_binaryPostorder() {
		LinkedBinaryTree<String> linkedBinaryTree = new LinkedBinaryTree<String>();

		String[] expression = { "(", "(", "(", "(", "3", "+", "1", ")", "*", "3", ")", "/", "(", "(", "9", "-", "5",
				")", "+", "2", ")", ")", "-", "(", "(", "3", "*", "(", "7", "-", "4", ")", ")", "+", "6", ")", ")" };

		LinkedBinaryTree<String> treeExpression = linkedBinaryTree.buildExpression(expression);

		OutputStream outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
		System.setOut(printStream);

		treeExpression.binaryPostorder(treeExpression, treeExpression.root());
		String consoleOutput = outputStream.toString();

		assertEquals("31+3*95-2+/374-*6+-", consoleOutput);
	}

	@Test
	void test_evaluateExpression() {
		LinkedBinaryTree<String> linkedBinaryTree = new LinkedBinaryTree<String>();

		String[] expression = { "(", "(", "(", "(", "3", "+", "1", ")", "*", "3", ")", "/", "(", "(", "9", "-", "5",
				")", "+", "2", ")", ")", "-", "(", "(", "3", "*", "(", "7", "-", "4", ")", ")", "+", "6", ")", ")" };

		LinkedBinaryTree<String> treeExpression = linkedBinaryTree.buildExpression(expression);

		assertEquals(-13.0, linkedBinaryTree.evaluateExpression(treeExpression, treeExpression.root()));
	}

	@Test
	void test_binaryInOrder() {
		LinkedBinaryTree<String> linkedBinaryTree = new LinkedBinaryTree<String>();

		String[] expression = { "(", "(", "(", "(", "3", "+", "1", ")", "*", "3", ")", "/", "(", "(", "9", "-", "5",
				")", "+", "2", ")", ")", "-", "(", "(", "3", "*", "(", "7", "-", "4", ")", ")", "+", "6", ")", ")" };

		LinkedBinaryTree<String> treeExpression = linkedBinaryTree.buildExpression(expression);

		OutputStream outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
		System.setOut(printStream);

		treeExpression.binaryInOrder(treeExpression, treeExpression.root());
		String consoleOutput = outputStream.toString();

		assertEquals("3+1*3/9-5+2-3*7-4+6", consoleOutput);
	}
	
	@Test
	void test_makerBTSearch() {
		LinkedBinaryTree<Integer> linkedBinaryTree = new LinkedBinaryTree<Integer>();
		linkedBinaryTree = linkedBinaryTree.makerBtSearch();
		
		OutputStream outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
		System.setOut(printStream);
		
		linkedBinaryTree.binaryInOrder(linkedBinaryTree, linkedBinaryTree.root());
		String consoleOutput = outputStream.toString();
		
		String resultado = "";
		
		for (int i = 0; i < consoleOutput.length(); i += 2) {
            if (i + 1 < consoleOutput.length()) {
            	String substring = consoleOutput.substring(i, i + 2);
                resultado += substring + ", ";
            }
        }
		
		if (resultado.endsWith(", ")) {
		    resultado = resultado.substring(0, resultado.length() - 2);
		}
		
		assertEquals("12, 25, 31, 36, 42, 58, 62, 75, 90", resultado);
	}
	
	@Test
	void test_eulerTour() {
		LinkedBinaryTree<String> linkedBinaryTree = new LinkedBinaryTree<String>();

		String[] expression = { "(", "(", "(", "(", "3", "+", "1", ")", "*", "3", ")", "/", "(", "(", "9", "-", "5",
				")", "+", "2", ")", ")", "-", "(", "(", "3", "*", "(", "7", "-", "4", ")", ")", "+", "6", ")", ")" };

		LinkedBinaryTree<String> treeExpression = linkedBinaryTree.buildExpression(expression);

		OutputStream outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
		System.setOut(printStream);

		treeExpression.eulerTour(treeExpression, treeExpression.root());
		String consoleOutput = outputStream.toString();

		assertEquals("-/*+333+111+*333*/+-999-555-+222+/-+*333*-777-444-*+666+-", consoleOutput);
	}
	
	@Test
	void test_printExpression() {
		LinkedBinaryTree<String> linkedBinaryTree = new LinkedBinaryTree<String>();

		String[] expression = { "(", "(", "(", "(", "3", "+", "1", ")", "*", "3", ")", "/", "(", "(", "9", "-", "5",
				")", "+", "2", ")", ")", "-", "(", "(", "3", "*", "(", "7", "-", "4", ")", ")", "+", "6", ")", ")" };

		LinkedBinaryTree<String> treeExpression = linkedBinaryTree.buildExpression(expression);

		OutputStream outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
		System.setOut(printStream);

		treeExpression.printExpression(treeExpression, treeExpression.root());
		String consoleOutput = outputStream.toString();

		assertEquals("((((3+1)*3)/((9-5)+2))-((3*(7-4))+6))", consoleOutput);
	}
	
	@Test
	void test_countLeftExternalNodes() {
		LinkedBinaryTree<Integer> linkedBinaryTree = new LinkedBinaryTree<Integer>();
		linkedBinaryTree = linkedBinaryTree.makerBtSearch();
		
		assertEquals(2, linkedBinaryTree.countLeftExternalNodes());
		
	}
	
	@Test
	void test_countRightExternalNodes() {
		LinkedBinaryTree<Integer> linkedBinaryTree = new LinkedBinaryTree<Integer>();
		linkedBinaryTree = linkedBinaryTree.makerBtSearch();
		
		assertEquals(1, linkedBinaryTree.countRightExternalNodes());
	}
}
