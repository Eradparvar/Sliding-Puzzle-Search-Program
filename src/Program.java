
public class Program {

	public static void main(String[] args) {
		Stopwatch stopwatch = new Stopwatch();
		stopwatch.start();
		System.out.println("***Program Started***");

//		Uncomment to run solvable version or unsolvable version
//				 Solvable version
		int[] puzzleBored = { 2, 6, 4, 7, 8, 3, 1, 5, 0 };

//				Unsolvable version
//		int[] puzzleBored = { 8, 1, 2, 0, 4, 3, 7, 6, 5 };

		PuzzleSearch puzzleSearch = new PuzzleSearch();
		Node startingNode = new Node(puzzleBored, 3, 3);
		puzzleSearch.search(startingNode);
		stopwatch.stop();
		System.out.println("Time: " + stopwatch.getSeconds() + " seconds");
		System.out.println("***Finished***");

	}
}
