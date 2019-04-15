import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Node implements INode {
	private static Node winningNode = null;
	private static Integer rows;
	private static Integer cols;
	private int level;
	private Node parent;
	private boolean isSolution = false;
	private ArrayList<INode> nextNodes = null;
	private int[] puzzleBored;

	private int hole;

	// start node cons
	public Node(int[] startingPuzzleBored, int rows, int cols) {
		try {
			if (Node.rows != null || Node.cols != null) {
				throw new Exception("Only one starting constructor allowed");
			}
			if (rows * cols != startingPuzzleBored.length) {
				throw new Exception("Invaid row and col configurations");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Node.rows = rows;
		Node.cols = cols;
		this.puzzleBored = startingPuzzleBored;
		hole = getHoleValue(puzzleBored);

	}

	// cons winning node cons
	public Node(int[] startingPuzzleBored) {
		this.puzzleBored = startingPuzzleBored;
		hole = getHoleValue(puzzleBored);
	}

	// Created by children nodes
	public Node(Node currentNode, int postionToMove) {
		this.level = currentNode.level + 1;
		this.parent = currentNode;
		this.puzzleBored = createNewPuzzleBored(currentNode, postionToMove);
		this.hole = currentNode.getHole() + postionToMove;
	}

	private int[] createNewPuzzleBored(Node currentNode, int postionToMove) {
		int[] newPuzzleBored = Arrays.copyOf(currentNode.getPuzzleBored(), currentNode.getPuzzleBored().length);
		int valueToSwap = newPuzzleBored[currentNode.getHole() + postionToMove];
		newPuzzleBored[currentNode.getHole()] = valueToSwap;
		newPuzzleBored[currentNode.getHole() + postionToMove] = 0;

		return newPuzzleBored;
	}

	@Override
	public ArrayList<INode> getNextNodes() {
		if (nextNodes == null) {
			nextNodes = new ArrayList<INode>();
			int moveRight = 1;
			int moveLeft = -1;
			int moveUp = rows;
			int moveDown = -rows;
			var right = isValidMove(hole + moveRight, hole) ? new Node(this, moveRight) : null;
			nextNodes.add(right);
			nextNodes.add(isValidMove(hole + moveLeft, hole) ? new Node(this, moveLeft) : null);
			nextNodes.add(isValidMove(hole + moveUp, hole) ? new Node(this, moveUp) : null);
			nextNodes.add(isValidMove(hole + moveDown, hole) ? new Node(this, moveDown) : null);
			nextNodes.removeAll(Collections.singleton(null));
		}
		return nextNodes;
	}

	private boolean isValidMove(int newPosition, int currentHolePosition) {
		int diff = currentHolePosition - newPosition;
		if (newPosition < 0 || newPosition > puzzleBored.length - 1) {
			return false;
		}
		if (diff == -1) { // Slide tile left (hole goes right)
			return newPosition % rows != 0; // ... unless tile is on left edge
		} else if (diff == +1) { // Slide tile right (hole goes left)
			return currentHolePosition % rows != 0; // ... unless hole is on left edge
		} else {
			return Math.abs(diff) == rows; // Slide vertically
		}
	}

	public int getHole() {
		return hole;
	}

	public int[] getPuzzleBored() {
		return puzzleBored;
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public Node getParent() {
		return parent;
	}

	@Override
	public boolean isSolution() {
		if (winningNode == null) {
			initializeWinningNode(puzzleBored);
		}
		return winningNode.hashCode() == this.hashCode();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hole;
		result = prime * result + Arrays.hashCode(puzzleBored);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Node))
			return false;
		Node other = (Node) obj;
		if (hole != other.hole)
			return false;
		if (!Arrays.equals(puzzleBored, other.puzzleBored))
			return false;
		return true;
	}

	@Override
	public double getHValue() {
		try {
			throw new Exception("not yet implemented");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public double getGValue() {
		try {
			throw new Exception("not yet implemented");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public String toString() {
		String puzzleBoredString = "Level " + level;
		for (int index = 0; index < puzzleBored.length; index++) {
			if (index % rows == 0) {
				puzzleBoredString += "\n------\n";
			}
			puzzleBoredString += Integer.toString(puzzleBored[index]) + " ";
		}
		return puzzleBoredString + "\n";
	}

	private Integer getHoleValue(int[] puzzleBored) {
		Integer hole = null;
		for (int index = 0; index < puzzleBored.length; index++) {
			if (puzzleBored[index] == 0) {
				hole = index;
			}
		}
		if (hole == null) {
			try {
				throw new Exception("No hole found");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return hole;

	}

	private void initializeWinningNode(int[] puzzleBored) {
		if (winningNode == null) {
			// fill winningPuzzleBored with numbers
			int[] winningPuzzleBored = new int[puzzleBored.length];
			for (int i = 1; i < puzzleBored.length; i++) {
				winningPuzzleBored[i - 1] = i;
			}
			winningPuzzleBored[winningPuzzleBored.length - 1] = 0;
			winningNode = new Node(winningPuzzleBored);
		}
	}

}
