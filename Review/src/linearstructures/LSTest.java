public class LSTest {
	public static void main(String[] args) {
		LSTest.queue();
	}

	public static void queue() {
		Queue<Integer> queue = new Queue<>(10);
		queue.enqueue(0);
		System.out.println(queue.size());
		queue.enqueue(0);
		System.out.println(queue.size());
		queue.enqueue(0);
		System.out.println(queue.size());
		queue.enqueue(0);
		System.out.println(queue.size());
		queue.enqueue(0);
		System.out.println(queue.size());
		queue.enqueue(0);
		System.out.println(queue.size());
		queue.enqueue(0);
		System.out.println(queue.size());
		queue.enqueue(0);
		System.out.println(queue.size());
		queue.enqueue(0);
		System.out.println(queue.size());
		queue.enqueue(0);
		System.out.println(queue.size());
		queue.enqueue(0);
		System.out.println(queue.size());
		queue.enqueue(0);
		System.out.println(queue.size());
		queue.dequeue();
		System.out.println(queue.size());
		queue.dequeue();
		System.out.println(queue.size());
		queue.dequeue();
		System.out.println(queue.size());
		queue.dequeue();
		System.out.println(queue.size());
		queue.enqueue(0);
		System.out.println(queue.size());
		queue.enqueue(0);
		System.out.println(queue.size());
		queue.enqueue(0);
		System.out.println(queue.size());
		queue.enqueue(0);
		System.out.println(queue.size());
		queue.enqueue(0);
		System.out.println(queue.size());
		queue.dequeue();
		System.out.println(queue.size());
		queue.dequeue();
		System.out.println(queue.size());
		queue.enqueue(0);
		System.out.println(queue.size());

		System.out.println(queue);
	}

	public static void stack() {
		Stack<String> stack = new Stack<>(10);
		stack.push("Test 1");
		stack.push("Test 2");
		stack.push("Test 3");
		stack.push("Test 4");
		stack.push("Test 5");
		stack.push("Test 6");
		stack.push("Test 7");
		stack.push("Test 8");
		stack.push("Test 9");
		stack.push("Test 10");
		System.out.println(stack);
	}
}
