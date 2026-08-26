# Algorithm Library

<p align="center">
  A lightweight collection of reusable algorithms and data structures written in modern Java.
</p>

<p align="center">
  <a href="https://github.com/Team-TEJAVA/algorithm-library/blob/main/LICENSE"><img src="https://img.shields.io/badge/License-MIT-blue.svg" alt="MIT License"></a>
  <img src="https://img.shields.io/badge/Java-17%2B-ED8B00?logo=openjdk&logoColor=white" alt="Java 17 or newer">
  <a href="https://github.com/Team-TEJAVA/algorithm-library/stargazers"><img src="https://img.shields.io/github/stars/Team-TEJAVA/algorithm-library?style=social" alt="GitHub stars"></a>
</p>

## Overview

**Algorithm Library** is a lightweight Java library that provides reusable implementations of commonly used algorithms and data structures.

The project focuses on **small APIs, readable implementations, and dependency-free code**, making it useful for algorithm study, coding interviews, competitive programming, and as a reference when implementing algorithms in larger applications.

> [!NOTE]
> This project is under active development. More algorithms, examples, and tests will be added over time, and public APIs may change before the first stable release.

## Highlights

* Pure Java with no third-party runtime dependencies
* Directed and undirected graph support
* Weighted graph algorithms including shortest paths and minimum spanning trees
* Path reconstruction for shortest-path algorithms
* Negative-cycle and DAG-cycle detection
* Generic binary tree utilities
* Reusable dynamic programming helpers
* Implementations documented with their assumptions and complexity

## Available Algorithms

| Category            | Implementation     | Purpose                                                                                   | Time Complexity           |
| ------------------- | ------------------ | ----------------------------------------------------------------------------------------- | ------------------------- |
| Graph               | `Graph`            | Directed and undirected graph representation                                              | Edge insertion: `O(1)`    |
| Graph               | `Dijkstra`         | Single-source shortest paths with non-negative weights                                    | `O((V + E) log V)`        |
| Graph               | `BellmanFord`      | Single-source shortest paths with negative weights and reachable negative-cycle detection | `O(VE)`                   |
| Graph               | `FloydWarshall`    | All-pairs shortest paths and negative-cycle detection                                     | `O(V³)`                   |
| Graph               | `Kruskal`          | Minimum spanning tree or minimum spanning forest                                          | `O(E log E)`              |
| Graph               | `TopologicalSort`  | Topological ordering of a directed acyclic graph                                          | `O(V + E)`                |
| Data Structure      | `UnionFind`        | Disjoint-set union with path compression and union by size                                | Amortized `O(α(V))`       |
| Tree                | `BinaryTree<T>`    | Tree construction, traversal, search, height, and size                                    | `O(V)` per full traversal |
| Dynamic Programming | `Memoizer<T, R>`   | Generic function-result caching                                                           | Depends on the recurrence |
| Dynamic Programming | `LongRollingArray` | Two-row `long` storage for memory-efficient DP                                            | `O(N)` space              |
| Utility             | `Backtracker`      | Reconstructs a path from a parent array                                                   | `O(L)`                    |

## Requirements

* JDK 17 or later
* Git, if you want to clone or contribute to the repository

No external libraries or build tools are required.

## Getting Started

Clone the repository:

```bash
git clone https://github.com/Team-TEJAVA/algorithm-library.git
cd algorithm-library
```

Compile all sources:

```bash
mkdir -p out
javac -d out $(find src -name "*.java")
```

Run the included binary tree example:

```bash
java -cp out tree.TreeExample
```

You can also import the repository into IntelliJ IDEA and use `src/` as the source root.

## Quick Example

The following example creates a directed graph and finds the shortest path from vertex `1` to vertex `5` using Dijkstra's algorithm.

```java
import graph.Dijkstra;
import graph.Graph;

public class Main {
    public static void main(String[] args) {
        Graph graph = Graph.directed(5)
                .addEdge(1, 2, 2)
                .addEdge(1, 3, 5)
                .addEdge(2, 3, 1)
                .addEdge(2, 4, 2)
                .addEdge(3, 5, 3)
                .addEdge(4, 5, 1);

        Dijkstra result = Dijkstra.from(graph, 1);

        System.out.println(result.distTo(5)); // 5
        System.out.println(result.pathTo(5)); // [1, 2, 4, 5]
    }
}
```

`Graph.directed(n)` and `Graph.undirected(n)` currently treat `n` as the maximum vertex label, so valid labels range from `0` through `n`.

Dijkstra requires non-negative edge weights. Use `BellmanFord` when negative edges are present.

## Binary Tree Example

Create a binary tree from level-order values and run common traversals:

```java
import tree.BinaryTree;

BinaryTree<Integer> tree = BinaryTree.fromLevelOrder(
        1, 2, 3, 4, 5, null, 6
);

System.out.println(tree.preorder());   // [1, 2, 4, 5, 3, 6]
System.out.println(tree.inorder());    // [4, 2, 5, 1, 3, 6]
System.out.println(tree.postorder());  // [4, 5, 2, 6, 3, 1]
System.out.println(tree.levelOrder()); // [1, 2, 3, 4, 5, 6]
System.out.println(tree.height());     // 3
```

See [`TreeExample.java`](src/tree/TreeExample.java) for a complete runnable example.

## Project Structure

```text
src/
├── dp/
│   ├── Backtracker.java
│   ├── LongRollingArray.java
│   └── Memoizer.java
├── edge/
│   └── Edge.java
├── graph/
│   ├── BellmanFord.java
│   ├── Dijkstra.java
│   ├── FloydWarshall.java
│   ├── Graph.java
│   ├── Kruskal.java
│   ├── TopologicalSort.java
│   └── UnionFind.java
└── tree/
    ├── BinaryTree.java
    └── TreeExample.java
```

## Contributing

Contributions are welcome. New algorithms, bug fixes, tests, examples, and documentation improvements can all help improve the library.

1. Fork the repository and clone your fork.

2. Create a branch for your change.

3. Implement the change in the appropriate package under `src/`.

4. Add or update examples and tests for important behavior and edge cases.

5. Document input assumptions and time and space complexity where applicable.

6. Compile the complete project:

   ```bash
   javac -d out $(find src -name "*.java")
   ```

7. Open a pull request with a clear description of the change and how it was verified.

Please keep pull requests focused and avoid mixing unrelated refactors with new algorithms or bug fixes.

## Reporting Issues

If you find a bug or would like to request an algorithm, open a [GitHub issue](https://github.com/Team-TEJAVA/algorithm-library/issues).

When reporting a bug, please include a minimal example, expected behavior, and actual behavior where possible.

## License

This project is released under the [MIT License](LICENSE).

## Acknowledgements

Algorithm Library is maintained by [Team TEJAVA](https://github.com/Team-TEJAVA) and its contributors.
