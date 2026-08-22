package tree;

import java.util.*;

public class BinaryTree<T> {

    //1. 노드 구조
    public static class TreeNode<T> {
        private final T value; //현재 노드에 저장된 값
        private TreeNode<T> left; // 현재 노드의 왼쪽 자식 노드
        private TreeNode<T> right; // 현재 노드의 오른쪽 자식 노드


        //새로운 노드 생성
        public TreeNode(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public TreeNode<T> getLeft() {
            return left;
        }

        public TreeNode<T> getRight() {
            return right;
        }

        // 왼쪽 자식 노드 연결
        public void setLeft(TreeNode<T> left) {
            this.left = left;
        }

        // 오른쪽 자식 노드 연결
        public void setRight(TreeNode<T> right) {
            this.right = right;
        }
    }


    //2. 트리 기본 구조
    private final TreeNode<T> root;

    private BinaryTree(TreeNode<T> root) {
        this.root = root;
    }

    // 직접 생성하고 연결한 노드 구조를 이진트리로 생성
    public static <T> BinaryTree<T> of(TreeNode<T> root) {
        return new BinaryTree<>(root);
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    //3. 트리 생성
    // 레벨 순서로 주어진 값을 이용하여 이진트리 생성
    public static <T> BinaryTree<T> fromLevelOrder(List<T> values) {

        if (values == null || values.isEmpty() || values.get(0) == null) {
            return new BinaryTree<>(null);
        }

        TreeNode<T> root = new TreeNode<>(values.get(0));
        Queue<TreeNode<T>> queue = new ArrayDeque<>();

        queue.offer(root);

        int index = 1;  //root 다음 값부터 사용

        while (!queue.isEmpty() && index < values.size()) {

            TreeNode<T> parent = queue.poll();

            if (index < values.size()) {
                T leftValue = values.get(index++);

                if (leftValue != null) {
                    TreeNode<T> left = new TreeNode<>(leftValue);
                    parent.setLeft(left);
                    queue.offer(left);
                }
            }

            if (index < values.size()) {
                T rightValue = values.get(index++);

                if (rightValue != null) {
                    TreeNode<T> right = new TreeNode<>(rightValue);
                    parent.setRight(right);
                    queue.offer(right);
                }
            }
        }

        return new BinaryTree<>(root);
    }

    @SafeVarargs
    public static <T> BinaryTree<T> fromLevelOrder(T... values) {
        return fromLevelOrder(Arrays.asList(values));
    }

    //4. 순회
    // 전위 순회
    public List<T> preorder() {
        List<T> result = new ArrayList<>();
        preorder(root, result);
        return result;
    }

    private void preorder(TreeNode<T> node, List<T> result) {
        if (node == null) {
            return;
        }

        result.add(node.getValue());
        preorder(node.getLeft(), result);
        preorder(node.getRight(), result);
    }

    // 중위 순회
    public List<T> inorder() {
        List<T> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    private void inorder(TreeNode<T> node, List<T> result) {
        if (node == null) {
            return;
        }

        inorder(node.getLeft(), result);
        result.add(node.getValue());
        inorder(node.getRight(), result);
    }

    // 후위 순회
    public List<T> postorder() {
        List<T> result = new ArrayList<>();
        postorder(root, result);
        return result;
    }

    private void postorder(TreeNode<T> node, List<T> result) {
        if (node == null) {
            return;
        }

        postorder(node.getLeft(), result);
        postorder(node.getRight(), result);
        result.add(node.getValue());
    }

    // BFS / 레벨 순회
    public List<T> levelOrder() {

        List<T> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        Queue<TreeNode<T>> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {

            TreeNode<T> node = queue.poll();

            result.add(node.getValue());

            if (node.getLeft() != null) {
                queue.offer(node.getLeft());
            }

            if (node.getRight() != null) {
                queue.offer(node.getRight());
            }
        }

        return result;
    }

    //5. 트리 정보
    // 트리 높이
    public int height() {
        return height(root);
    }

    private int height(TreeNode<T> node) {
        if (node == null) {
            return 0;
        }

        return 1 + Math.max(
                height(node.getLeft()),
                height(node.getRight())
        );
    }

    // 전체 노드 개수
    public int size() {
        return size(root);
    }

    private int size(TreeNode<T> node) {
        if (node == null) {
            return 0;
        }

        return 1
                + size(node.getLeft())
                + size(node.getRight());
    }

    // 리프 노드 개수
    public int leafCount() {
        return leafCount(root);
    }

    private int leafCount(TreeNode<T> node) {
        if (node == null) {
            return 0;
        }

        if (node.getLeft() == null && node.getRight() == null) {
            return 1;
        }

        return leafCount(node.getLeft())
                + leafCount(node.getRight());
    }

    //6. 탐색
    // 특정 값 탐색 (포함 여부)
    public boolean contains(T value) {
        return contains(root, value);
    }

    private boolean contains(TreeNode<T> node, T value) {
        if (node == null) {
            return false;
        }

        if (Objects.equals(node.getValue(), value)) {
            return true;
        }

        return contains(node.getLeft(), value)
                || contains(node.getRight(), value);
    }
}