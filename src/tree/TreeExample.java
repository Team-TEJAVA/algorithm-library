package tree;

import tree.BinaryTree.TreeNode;

public class TreeExample {

    public static void main(String[] args) {

        /*
         * 1. fromLevelOrder()를 이용한 트리 생성
         *
         * 생성되는 트리
         *
         *          1
         *        /   \
         *       2     3
         *      / \     \
         *     4   5     6
         */
        BinaryTree<Integer> tree =
                BinaryTree.fromLevelOrder(
                        1, 2, 3, 4, 5, null, 6
                );

        System.out.println("=== 트리 생성 ===");
        System.out.println("루트 값: " + tree.getRoot().getValue());


        /*
         * 2. DFS 기반 순회
         */
        System.out.println("\n=== DFS 순회 ===");

        // Root -> Left -> Right
        System.out.println("전위 순회: " + tree.preorder());

        // Left -> Root -> Right
        System.out.println("중위 순회: " + tree.inorder());

        // Left -> Right -> Root
        System.out.println("후위 순회: " + tree.postorder());


        /*
         * 3. BFS 기반 레벨 순회
         */
        System.out.println("\n=== BFS 순회 ===");

        System.out.println("레벨 순회: " + tree.levelOrder());


        /*
         * 4. 트리 정보 확인
         */
        System.out.println("\n=== 트리 정보 ===");

        System.out.println("트리 높이: " + tree.height());
        System.out.println("전체 노드 수: " + tree.size());
        System.out.println("리프 노드 수: " + tree.leafCount());


        /*
         * 5. 특정 값 탐색
         */
        System.out.println("\n=== 값 탐색 ===");

        System.out.println("5 존재 여부: " + tree.contains(5));
        System.out.println("10 존재 여부: " + tree.contains(10));


        /*
         * 6. TreeNode를 직접 이용한 트리 생성
         *
         * 생성되는 트리
         *
         *          10
         *        /    \
         *       20     30
         *      /
         *     40
         *
         * 문제에서 주어진 규칙에 따라 직접 트리를 구성해야 할 때
         * TreeNode를 직접 연결한 뒤 BinaryTree.of()를 사용할 수 있다.
         */

        TreeNode<Integer> root = new TreeNode<>(10);

        TreeNode<Integer> node20 = new TreeNode<>(20);
        TreeNode<Integer> node30 = new TreeNode<>(30);
        TreeNode<Integer> node40 = new TreeNode<>(40);

        root.setLeft(node20);
        root.setRight(node30);
        node20.setLeft(node40);

        // 직접 만든 root를 BinaryTree 객체로 변환
        BinaryTree<Integer> customTree =
                BinaryTree.of(root);


        /*
         * 7. 직접 생성한 트리에도 동일한 라이브러리 기능 적용
         */
        System.out.println("\n=== 직접 생성한 트리 ===");

        System.out.println("루트 값: "
                + customTree.getRoot().getValue());

        System.out.println("전위 순회: "
                + customTree.preorder());

        System.out.println("중위 순회: "
                + customTree.inorder());

        System.out.println("후위 순회: "
                + customTree.postorder());

        System.out.println("레벨 순회: "
                + customTree.levelOrder());

        System.out.println("트리 높이: "
                + customTree.height());

        System.out.println("전체 노드 수: "
                + customTree.size());

        System.out.println("리프 노드 수: "
                + customTree.leafCount());

        System.out.println("40 존재 여부: "
                + customTree.contains(40));
    }
}