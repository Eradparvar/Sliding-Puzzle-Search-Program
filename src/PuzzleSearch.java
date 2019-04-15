import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class PuzzleSearch implements IPuzzleSearch {
	private Queue<INode> unProcessedNode = new LinkedList<INode>();
	private Set<Integer> colored = new LinkedHashSet<Integer>();
	private Stack<INode> winningNodeStack = new Stack<INode>();

	@Override
	public void search(INode startingNode) {
		colored.add(startingNode.hashCode());
		unProcessedNode.offer(startingNode);
		INode nextNode;
		do {
			nextNode = unProcessedNode.poll();
			colored.add(nextNode.hashCode());
			for (INode iNode : nextNode.getNextNodes()) {
				if (!colored.contains(iNode.hashCode())) {
					colored.add(iNode.hashCode());
					unProcessedNode.add(iNode);
				}
			}
		} while (!unProcessedNode.isEmpty() && !nextNode.isSolution());

		if (!nextNode.isSolution()) {
			System.out.println("---No solution---");
			return;
		}
		if (nextNode.isSolution()) {
			winningNodeStack.push(nextNode);
			while (nextNode.getLevel() > 0) {
				if (nextNode.getParent() != null) {
					nextNode = nextNode.getParent();
					winningNodeStack.push(nextNode);
				}
			}
			printWinningNodeStack(winningNodeStack);
		}
	}

	private void printWinningNodeStack(Stack<INode> winningNodeStack2) {
		System.out.println("Printing solution");
		while (!winningNodeStack.isEmpty()) {
			System.out.println(winningNodeStack.pop());
		}
	}

}
