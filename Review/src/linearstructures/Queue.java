import java.util.List;
import java.util.ArrayList;

public class Queue <T> {
	private final List<T> queue;
	private final int size;
	private int start = 0;
	private int end = 0;

	public Queue(int size) {
		this.size = size;
		this.queue = new ArrayList<>(this.size);
		for (int i = 0; i < this.size; i++) this.queue.add(null);
	}

	public boolean full() {
		return (this.start + 1) % this.size == this.end;
	}

	public boolean empty() {
		return this.end - this.start == 0;
	}

	public boolean enqueue(T obj) {
		if (full()) return false;
		this.start = (this.start + 1) % this.size;
		this.queue.set(this.start, obj);
		return true;
	}

	public T dequeue() {
		if (empty()) return null;
		this.end = (this.end + 1) % this.size;
		return this.queue.get(this.end);
	}

	public int size() {
		if (this.start < this.end)
			return this.size - (this.end + 1) + this.start + 1;
		return this.start - this.end;
	}

	public T get(int index) {
		if (empty()) return null;
		return this.queue.get((this.end + index) % this.size);
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < size(); i++)
			str += String.format("%d: %s\n", i, get(i));
		return str;
	}
}
